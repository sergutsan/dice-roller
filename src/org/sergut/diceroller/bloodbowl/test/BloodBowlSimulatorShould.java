package org.sergut.diceroller.bloodbowl.test;

import org.junit.Before;
import org.junit.Test;
import org.sergut.diceroller.bloodbowl.BlockFactors;
import org.sergut.diceroller.bloodbowl.BloodBowlSimulator;

public class BloodBowlSimulatorShould extends SimulatorTester {

	private BloodBowlSimulator simulator;
	
	private BlockFactors factors;

	@Before
	public void buildUp () {
		factors = new BlockFactors();
		simulator = new BloodBowlSimulator();
	}

	@Test
	public void return333ForTurnoverForBasicBlock() {
		int ratio = simulator.getTurnoverRatio(factors);
		assertWithSomeError(333, ratio);
	}

	@Test
	public void return23ForCasualtyForBasicBlock() {
		int ratio = simulator.getCombinedCasualtyRatio(factors);
		assertWithSomeError(23, ratio);
	}

	@Test
	public void return32ForCasualtyForAttackerWithClaws() {
		factors.attackerClaws = true;
		int ratio = simulator.getCombinedCasualtyRatio(factors);
		assertWithSomeError(32, ratio);
	}

	@Test
	public void return32ForCasualtyForAttackerWithClawsDefenderArmor9() {
		factors.attackerClaws = true;
		factors.defenderArmor = 9;
		int ratio = simulator.getCombinedCasualtyRatio(factors);
		assertWithSomeError(32, ratio);
	}

	@Test
	public void return48ForCasualtyForAttackerWithMightyBlow() {
		factors.attackerMightyBlow = true;
		int ratio = simulator.getCombinedCasualtyRatio(factors);
		assertWithSomeError(48, ratio);
	}


}
