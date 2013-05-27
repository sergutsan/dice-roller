package org.sergut.diceroller.savageworlds;

import org.sergut.diceroller.DiceRoller;

public class SavageWorldsSimulator {
    public SavageWorldsSimulationResult simulate(SavageWorldsSimulationJob job) {
	DiceRoller diceRoller = new DiceRoller();
	SavageWorldsSimulationResult result = new SavageWorldsSimulationResult();
	if (job.includeNonWildAttack) {
	    if (job.includeBodyAttack) {
		result.setResult("Normal, body", runSingleAttack(job, AimOption.BODY, AttackOption.NORMAL, diceRoller));
	    }
	    if (job.includeHeadAttack) {
		result.setResult("Normal, head", runSingleAttack(job, AimOption.HEAD, AttackOption.NORMAL, diceRoller));
	    }
	}
	if (job.includeWildAttack) {
	    if (job.includeBodyAttack) {
		result.setResult("Wild, body", runSingleAttack(job, AimOption.BODY, AttackOption.WILD, diceRoller));
	    } 
	    if (job.includeHeadAttack) { 
		result.setResult("Wild, head", runSingleAttack(job, AimOption.HEAD, AttackOption.WILD, diceRoller));
	    }
	}
	return result;
    }
    
    private SavageWorldsDamageCounter runSingleAttack(SavageWorldsSimulationJob job, AimOption aimOpn, AttackOption attackOpn, DiceRoller diceRoller) {
	SavageWorldsDamageCounter result = new SavageWorldsDamageCounter();
	for (int i = 0; i < job.maxIterations; ++i) {
	    String actualAttackDice;
	    if (job.attackerWildCard) {
		actualAttackDice = new String("b[" + job.attackDice + ",1d6!]");
	    } else {
		actualAttackDice = new String(job.attackDice);
	    }
	    String actualDamageDice = new String(job.damageDice);
	    int attack = diceRoller.rollDice(actualAttackDice);
	    switch (aimOpn) {
	    case BODY: break;
	    case ARM:  attack -= 2; break;
	    case HEAD: attack -= 4; break;
	    }
	    switch (attackOpn) {
	    case NORMAL: break;
	    case WILD:   attack += 2; break;
	    }
	    if (attack >= job.defenderParry + 4) {
		actualDamageDice += "+1d6!";
	    } else if (attack < job.defenderParry) {
		result.nothing++;
		continue;
	    }
	    int damage = diceRoller.rollDice(actualDamageDice);
	    switch (aimOpn) {
	    case BODY: break;
	    case ARM:  break;
	    case HEAD: damage += 4; break;
	    }
	    switch (attackOpn) {
	    case NORMAL: break;
	    case WILD:   damage += 2; break;
	    }
	    int success = damage - job.defenderToughness;
	    if (success >= 16) {
		result.wound4m++;
	    } else if (success >= 12) {
		result.wound3++;
	    } else if (success >= 8) {
		result.wound2++;
	    } else if (success >= 4 || (job.defenderShaken && success >= 0)) {
		result.wound1++;
	    } else if (success >= 0) {
		result.shaken++;
	    } else {
		result.nothing++; 
	    }
	}
	return result;
    }

    private SavageWorldsDamageCounter runRapidAttack(SavageWorldsSimulationJob job, AimOption aimOpn, AttackOption attackOpn, DiceRoller diceRoller) {
	SavageWorldsDamageCounter result = new SavageWorldsDamageCounter();
	for (int i = 0; i < job.maxIterations; ++i) {
	    String actualAttackDice;
	    actualAttackDice = new String(job.attackDice + "-4");
	    final int attackCount = 3;
	    int[] attack = new int[attackCount];
	    for (int j = 0; j < attackCount; j++) {
		attack[i] = diceRoller.rollDice(actualAttackDice);
		switch (aimOpn) {
		case BODY: break;
		case ARM:  attack[j] -= 2; break;
		case HEAD: attack[j] -= 4; break;
		}
		switch (attackOpn) {
		case NORMAL: break;
		case WILD:   attack[j] += 2; break;
		}
	    }
	    int wildDieResult = diceRoller.rollDice("1d6!");
	    attack = fixAttackWithWildDie(attack, wildDieResult);
	    // Now let's see the damage of those three rapid attacks 
	    // (assuming no bennies to soak wounds as they fall!)
	    boolean defenderShaken = job.defenderShaken;
	    int defenderWounds = 0;
	    for (int j = 0; j < attackCount; j++) {
		String actualDamageDice = new String(job.damageDice);
		if (attack[j] >= job.defenderParry + 4) {
		    actualDamageDice += "+1d6!";
		} else if (attack[j] < job.defenderParry) {
		    continue;
		}
		int damage = diceRoller.rollDice(actualDamageDice);
		switch (aimOpn) {
		case BODY:              break;
		case ARM:               break;
		case HEAD: damage += 4; break;
		}
		switch (attackOpn) {
		case NORMAL:              break;
		case WILD:   damage += 2; break;
		}
		int success = damage - job.defenderToughness;
		if (success >= 4) {
		    defenderWounds += success / 4;
		    defenderShaken = true;
		} else if (success >= 0) {
		    if (defenderShaken) {
			defenderWounds++;
		    }
		    defenderShaken = true;
		} else {
		    // NOP
		}
	    }
	    if (defenderWounds >= 4) {
		result.wound4m++;
	    } else if (defenderWounds == 3) {
		result.wound3++;
	    } else if (defenderWounds == 2) {
		result.wound2++;
	    } else if (defenderWounds == 1) {
		result.wound1++;		
	    } else if (!job.defenderShaken && defenderShaken && defenderWounds == 0) {
		result.shaken++;
	    } else {
		result.nothing++;
	    }
	}
	return result;
    }

    /*
     * If the wild die is higher than the lowest of the rapid attacks, use
     * the wild die instead. In case of a tie, substitute the earliest one
     * occurring.
     * 
     * For example: 3,2,5;6 => 3,6,5    4,2,4;3 => 4,3,4   4,1,1;4 => 4,4,1
     */
    private int[] fixAttackWithWildDie(int[] attack, int wildDieResult) {
	int min = Integer.MAX_VALUE;
	int minIdx = -1;
	for (int i = 0; i < attack.length; i++) {
	    if (attack[i] < min) {
		min = attack[i];
		minIdx = i;
	    }
	}
	if (wildDieResult > min) {
	    attack[minIdx] = wildDieResult;
	}
	return attack;
    }
}
