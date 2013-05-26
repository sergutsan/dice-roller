package org.sergut.diceroller.savageworlds;

import org.sergut.diceroller.DiceRoller;

public class SavageWorldsSimulationJob {
    private String attackDice;
    private String damageDice;
    private int defenderParry;
    private int defenderToughness;
    private int maxIterations;

    public SavageWorldsSimulationJob(String attackDice, String damageDice, int parry, int toughness, int maxIter) {
	this.attackDice = attackDice;
	this.damageDice = damageDice;
	this.defenderParry = parry;
	this.defenderToughness = toughness;
	this.maxIterations = maxIter;
    }

    public SavageWorldsSimulationResult simulate() {
	SavageWorldsSimulationResult result = new SavageWorldsSimulationResult();
	DiceRoller diceRoller = new DiceRoller();
	SavageWorldsDamageCounter damageCounter = new SavageWorldsDamageCounter();
	for (int i = 0; i < maxIterations; ++i) {
	    String actualDamageDice = new String(damageDice);
	    int attack = diceRoller.rollDice(attackDice);
	    if (attack >= defenderParry + 4) {
		actualDamageDice += "+1d6!";
	    } else if (attack < defenderParry) {
		actualDamageDice = "0";
	    }
	    int damage = diceRoller.rollDice(actualDamageDice);
	    int success = damage - defenderToughness;
	    if (success >= 16) {
		damageCounter.wound4m++;
	    } else if (success >= 12) {
		damageCounter.wound3++;
	    } else if (success >= 8) {
		damageCounter.wound2++;
	    } else if (success >= 4) {
		damageCounter.wound1++;
	    } else if (success >= 0) {
		damageCounter.shaken++;
	    } else {
		damageCounter.nothing++; 
	    }
	}
	result.setResult(damageCounter);
	return result;
    }

}
