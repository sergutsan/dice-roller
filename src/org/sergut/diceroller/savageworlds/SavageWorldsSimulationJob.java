package org.sergut.diceroller.savageworlds;

/**
 * The description of a simulation job for an attach in Savage Worlds
 */
public class SavageWorldsSimulationJob {
    public String attackDice = null;
    public String damageDice= null;
    public boolean attackerWildCard = false;
    public int defenderParry = -1;
    public int defenderToughness = -1;
    public boolean defenderShaken = false;
    public boolean defenderWildCard = false;
    public int maxIterations = -1;
    public boolean includeBodyAttack = true;
    public boolean includeArmAttack  = false;
    public boolean includeHeadAttack = false;
    public boolean includeNonWildAttack = true;
    public boolean includeWildAttack    = false;
    public boolean includeNonRapidAttack = true;
    public boolean includeRapidAttack    = false;
    
    public SavageWorldsSimulationJob() {
	attackDice = null;
	damageDice= null;
	attackerWildCard = false;
	defenderParry = -1;
	defenderToughness = -1;
	defenderShaken = false;
	defenderWildCard = false;
	includeBodyAttack = true;
	includeArmAttack  = false;
	includeHeadAttack = false;
	includeNonWildAttack = true;
	includeWildAttack    = false;
	includeNonRapidAttack = true;
	includeRapidAttack    = false;
	maxIterations = 100000;
    }

    // TODO: implement a fluent interface to build jobs
    public SavageWorldsSimulationJob clone() {
	SavageWorldsSimulationJob result = new SavageWorldsSimulationJob();
	result.attackDice = this.attackDice;
	result.damageDice = this.damageDice;
	result.attackerWildCard = this.attackerWildCard;
	result.defenderParry = this.defenderParry;
	result.defenderToughness = this.defenderToughness;
	result.defenderShaken = this.defenderShaken;
	result.defenderWildCard = this.defenderWildCard;
	result.includeBodyAttack = this.includeBodyAttack;
	result.includeArmAttack  = this.includeArmAttack;
	result.includeHeadAttack = this.includeHeadAttack;
	result.includeNonWildAttack = this.includeNonWildAttack;
	result.includeWildAttack    = this.includeWildAttack;
	result.includeNonRapidAttack = this.includeNonRapidAttack;
	result.includeRapidAttack    = this.includeRapidAttack;
	result.maxIterations = this.maxIterations;
	return result;
    }
}
