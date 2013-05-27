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
    // TODO: build a fluent interface to build jobs
}
