package org.sergut.diceroller.savageworlds;

import org.sergut.diceroller.DiceRoller;

public class SavageWorldsSimulator {
    public SavageWorldsSimulationResult simulate(SavageWorldsSimulationJob job) {
	DiceRoller diceRoller = new DiceRoller();
	SavageWorldsSimulationResult result = new SavageWorldsSimulationResult();
	result.setResult("Normal, body", runSingleAttack(job, AimOption.BODY, AttackOption.NORMAL, diceRoller));
	result.setResult("Normal, head", runSingleAttack(job, AimOption.HEAD, AttackOption.NORMAL, diceRoller));
	result.setResult("Wild, body", runSingleAttack(job, AimOption.BODY, AttackOption.WILD, diceRoller));
	result.setResult("Wild, head", runSingleAttack(job, AimOption.HEAD, AttackOption.WILD, diceRoller));
	return result;
    }
    
    private SavageWorldsDamageCounter runSingleAttack(SavageWorldsSimulationJob job, AimOption aimOpn, AttackOption attackOpn, DiceRoller diceRoller) {
	SavageWorldsDamageCounter result = new SavageWorldsDamageCounter();
	for (int i = 0; i < job.maxIterations; ++i) {
	    String actualAttackDice = new String(job.attackDice);
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
	    case HEAD: attack += 4; break;
	    }
	    switch (attackOpn) {
	    case NORMAL: break;
	    case WILD:   attack += 2; break;
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
}
