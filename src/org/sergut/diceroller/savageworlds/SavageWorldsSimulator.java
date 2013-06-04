package org.sergut.diceroller.savageworlds;

import org.sergut.diceroller.DiceRoller;

public class SavageWorldsSimulator {

    /*
     * FIXME: As the parser is not able to understand expressions like 
     *   2 + b[1d12!,1d6!] we cannot just modify the dice expressions 
     *   for attack and damage. So we add the modifiers in a small tuple
     *   and imbricate that in the logic. Works OK, but not as elegant.  
     */
    
    final static int RAPID_ATTACK_COUNT = 3;
    
    final DiceRoller diceRoller = new DiceRoller(); 

    public SavageWorldsDamageCounter simulate(SavageWorldsSimulationJob job) {
	Modifier modifier = getModifier(job);
	if (job.rapidAttack) {
	    return runRapidAttack(job, modifier);
	} else {
	    return runSingleAttack(job, modifier);
	}
    }
    
    private SavageWorldsDamageCounter runSingleAttack(SavageWorldsSimulationJob job, Modifier modifier) {
	SavageWorldsDamageCounter result = new SavageWorldsDamageCounter();
	for (int i = 0; i < job.maxIterations; ++i) {
	    String actualAttackDice = new String(job.attackDice);
	    if (job.attackerWildCard) {
		actualAttackDice = new String("b[" + job.attackDice + "," + job.attackerWildDie + "]");
	    }
	    String actualDamageDice = new String(job.damageDice);
	    int attack = diceRoller.rollDice(actualAttackDice);
	    attack += modifier.attack;
	    if (attack >= job.defenderParry + 4) {
		actualDamageDice += "+" + job.attackerAdvanceDamage;
	    } else if (attack < job.defenderParry) {
		result.nothing++;
		continue;
	    }
	    int damage = diceRoller.rollDice(actualDamageDice);
	    damage += modifier.damage;
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

    public SavageWorldsDamageCounter runRapidAttack(SavageWorldsSimulationJob job, Modifier modifier) {
	SavageWorldsDamageCounter result = new SavageWorldsDamageCounter();
	for (int i = 0; i < job.maxIterations; ++i) {
	    String actualAttackDice;
	    actualAttackDice = new String(job.attackDice + "-4");
	    int[] attack = new int[RAPID_ATTACK_COUNT];
	    for (int j = 0; j < RAPID_ATTACK_COUNT; j++) {
		attack[i] = diceRoller.rollDice(actualAttackDice) + modifier.attack;
	    }
	    int wildDieResult = diceRoller.rollDice(job.attackerWildDie) + modifier.attack;
	    attack = fixAttackWithWildDie(attack, wildDieResult);
	    // Now let's see the damage of those three rapid attacks 
	    // (assuming no bennies to soak wounds as they fall!)
	    boolean defenderShaken = job.defenderShaken;
	    int defenderWounds = 0;
	    for (int j = 0; j < RAPID_ATTACK_COUNT; j++) {
		String actualDamageDice = new String(job.damageDice);
		if (attack[j] >= job.defenderParry + 4) {
		    actualDamageDice += "+" + job.attackerAdvanceDamage;
		} else if (attack[j] < job.defenderParry) {
		    continue;
		}
		int damage = diceRoller.rollDice(actualDamageDice) + modifier.damage;
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
		    // NOP: damage is lower than toughness
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

    private Modifier getModifier(SavageWorldsSimulationJob job) {
	Modifier result = new Modifier();
	if (job.attackerTrademarkWeapon) {
	    result.attack += 1;
	}
	if (job.wildAttack) {
	    result.attack += 2;
	    result.damage += 2;
	}
	if (job.attackAim == AttackAim.ARM) {
	    result.attack -= 2;
	}
	if (job.attackAim == AttackAim.HEAD) {
	    result.attack -= 4;
	    result.damage += 4;
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
    
    private class Modifier {
	public int attack = 0;
	public int damage = 0;
    }
    
    private SavageWorldsSimulator() {}
    
    private static SavageWorldsSimulator INSTANCE = null;
    
    public static SavageWorldsSimulator getInstance() {
	if (INSTANCE == null) {
	    INSTANCE = new SavageWorldsSimulator();
	}
	return INSTANCE;
    }
}
