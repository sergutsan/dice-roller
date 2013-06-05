package org.sergut.diceroller.savageworlds;

/**
 * The description of a simulation job for an attach in Savage Worlds. 
 * 
 * By default, nothing is defined: all is false or -1. The only exceptions
 * are aim (default:BODY), iterations (default:10^5), attackerWildDie (default:1d6!),
 * attackerAdvanceDamage (default:1d6!)
 */
public class SavageWorldsSimulationJob implements Cloneable {
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
    public int defenderToughnessBody = -1;
    public int defenderToughnessArm = -1;
    public int defenderToughnessHead = -1;
    public boolean defenderShaken = false;
    public boolean defenderWildCard = false;
    public boolean defenderAttackedWild = false;
    public int maxIterations = 100000;
    public AttackAim attackAim = AttackAim.BODY;
    
    public SavageWorldsSimulationJob() {}

    // As everything is primitive or immutable (String), it is fine to use Object.clone().
    public SavageWorldsSimulationJob clone() throws CloneNotSupportedException {
    	return (SavageWorldsSimulationJob) super.clone();
    }
}
