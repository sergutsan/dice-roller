package org.sergut.diceroller.savageworlds;

/**
 * The description of a simulation job for an attach in Savage Worlds. 
 * 
 * By default, nothing is defined: all is false or -1. The only exceptions
 * are aim (default:BODY), iterations (default:10^5), attackerWildDie (default:1d6!),
 * attackerAdvanceDamage (default:1d6!)
 */
public class SavageWorldsSimulationJob {
    public String attackDice = null;
    public String damageDice = null;
    public boolean attackerWildCard = false;
    public String attackerWildDie       = "1d6!";
    public String attackerAdvanceDamage = "1d6!";
    public boolean attackerTrademarkWeapon = false;
    public boolean attackerBerserk = false;
    public boolean wildAttack = false;
    public boolean rapidAttack = false;
    public boolean frenzyAttack = false;
    public boolean doubleAttack = false;
    public boolean attackerTwoFisted = false;
    public boolean attackerAmbidextrous = false;
    public int defenderParry = -1;
    public int defenderToughness = -1;
    public boolean defenderShaken = false;
    public boolean defenderWildCard = false;
    public boolean defenderAttackedWild = false;
    public int maxIterations = 100000;
    public AttackAim attackAim = AttackAim.BODY;
    
    public SavageWorldsSimulationJob() {}

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
	result.maxIterations = this.maxIterations;
	result.attackAim = this.attackAim;
	result.wildAttack = this.wildAttack;
	result.rapidAttack = this.rapidAttack;
	return result;
    }
}
