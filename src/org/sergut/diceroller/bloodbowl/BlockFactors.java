package org.sergut.diceroller.bloodbowl;

enum PillingOn { NONE, CONSERVATIVE, AGGRESIVE };

public class BlockFactors {
	// Default case: two Human Linemen
	public int attackerStrength  = 3;
	public boolean attackerBlock = false;
	public boolean attackerTackle = false;
	public boolean attackerMightyBlow = false;
	public boolean attackerClaws = false;
	public PillingOn attackerPillingOn = PillingOn.NONE;
	
	public int defenderStrength = 3;
	public int defenderArmor = 8;
	public boolean defenderBlock = false;
	public boolean defenderDodge = false;
	public boolean defenderStunty = false;
}

