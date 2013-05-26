package org.sergut.diceroller.savageworlds;

import org.sergut.diceroller.DiceRoller;

public class SavageWorldsSimulationJob {
    private String attackDice;
    private String damageDice;
    private int defenderParry;
    private int defenderToughness;
    private boolean defenderShaken = false;
    private int maxIterations;

    public SavageWorldsSimulationJob(String attackDice, String damageDice, int parry, int toughness, int maxIter) {
	this(attackDice, damageDice, parry, toughness, false, maxIter);
    }

    public SavageWorldsSimulationJob(String attackDice, String damageDice, int parry, int toughness, boolean shaken, int maxIter) {
	this.attackDice = attackDice;
	this.damageDice = damageDice;
	this.defenderParry = parry;
	this.defenderToughness = toughness;
	this.defenderShaken = shaken;
	this.maxIterations = maxIter;
    }

    public SavageWorldsSimulationResult simulate() {
	DiceRoller diceRoller = new DiceRoller();
	SavageWorldsSimulationResult result = new SavageWorldsSimulationResult();
	result.setResult("Normal, body", runSingleAttack(AimOption.BODY, AttackOption.NORMAL, diceRoller));
	result.setResult("Normal, head", runSingleAttack(AimOption.HEAD, AttackOption.NORMAL, diceRoller));
	result.setResult("Wild, body", runSingleAttack(AimOption.BODY, AttackOption.WILD, diceRoller));
	result.setResult("Wild, head", runSingleAttack(AimOption.HEAD, AttackOption.WILD, diceRoller));
	return result;
    }
    
    private SavageWorldsDamageCounter runSingleAttack(AimOption aimOpn, AttackOption attackOpn, DiceRoller diceRoller) {
	SavageWorldsDamageCounter result = new SavageWorldsDamageCounter();
	for (int i = 0; i < maxIterations; ++i) {
	    String actualAttackDice = new String(attackDice);
	    String actualDamageDice = new String(damageDice);
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
	    if (attack >= defenderParry + 4) {
		actualDamageDice += "+1d6!";
	    } else if (attack < defenderParry) {
		actualDamageDice = "-100";
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
	    int success = damage - defenderToughness;
	    if (success >= 16) {
		result.wound4m++;
	    } else if (success >= 12) {
		result.wound3++;
	    } else if (success >= 8) {
		result.wound2++;
	    } else if (success >= 4 || (defenderShaken && success >= 0)) {
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
