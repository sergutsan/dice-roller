package org.sergut.diceroller.bloodbowl.test;

import org.junit.Before;
import org.junit.Test;

import org.sergut.diceroller.bloodbowl.BlockDiceSimulator;
import org.sergut.diceroller.bloodbowl.BlockFactors;
import org.sergut.diceroller.bloodbowl.BlockResult;
import static org.sergut.diceroller.bloodbowl.BlockResult.*;


public class BlockDiceThrowerShould extends SimulatorTest {
	
	private BlockFactors factors;
	
	private BlockDiceSimulator diceThrower;
	
	@Before
	public void buildUp () {
		factors = new BlockFactors();
		diceThrower = new BlockDiceSimulator();
	}
	
	@Test
	public void returnThirdForAllForBasicBlock() {
		for (BlockResult result : BlockResult.values()) {
			int ratio = diceThrower.getRatio(result, factors);
			assertWithSomeError(ratio, 333);
		}
	}

	@Test
	public void returnThirdForAllForBasicBlockWithDodgeAndTackle() {
		factors.attackerTackle = true;
		factors.defenderDodge = true;
		for (BlockResult result : BlockResult.values()) {
			int ratio = diceThrower.getRatio(result, factors);
			assertWithSomeError(ratio, 333);
		}
	}

	@Test
	public void returnHalfForKnockDownWithAttackingBlock() {
		factors.attackerBlock = true;
		int ratio = diceThrower.getRatio(KNOCK_DOWN, factors);
		assertWithSomeError(ratio, 500);
	}

	@Test
	public void returnSixthForKnockedDownWithDefenderDodge() {
		factors.defenderDodge = true;
		int ratio = diceThrower.getRatio(KNOCK_DOWN, factors);
		assertWithSomeError(ratio, 166);
	}

	@Test
	public void returnTwoThirdsForNobodyDownWithBothBlodgers() {
		factors.attackerBlock = true;
		factors.defenderBlock = true;
		factors.defenderDodge = true;
		int ratio = diceThrower.getRatio(NOBODY_DOWN, factors);
		assertWithSomeError(ratio, 666);
	}

	@Test
	public void return27ForTurnoverIfAttackerStrongerAndWithBlock() {
		factors.attackerBlock = true;
		factors.attackerStrength = 4;
		int ratio = diceThrower.getRatio(TURNOVER, factors);
		assertWithSomeError(ratio, 27);
	}
	
	@Test
	public void return555ForKnockedDownIfAttackerStronger() {
		factors.attackerStrength = 4;
		int ratio = diceThrower.getRatio(KNOCK_DOWN, factors);
		assertWithSomeError(ratio, 555);
	}
	
	@Test
	public void return333ForNobodyDownIfAttackerStronger() {
		factors.attackerStrength = 4;
		int ratio = diceThrower.getRatio(NOBODY_DOWN, factors);
		assertWithSomeError(ratio, 333);
	}
	
	@Test
	public void return750ForKnockDownIfAttackerStrongerAndWithBlock() {
		factors.attackerBlock = true;
		factors.attackerStrength = 4;
		int ratio = diceThrower.getRatio(KNOCK_DOWN, factors);
		assertWithSomeError(ratio, 750);
	}
	
	@Test
	public void return259ForPushIfAttackerTwiceStronger() {
		factors.attackerStrength = 7;
		int ratio = diceThrower.getRatio(NOBODY_DOWN, factors);
		assertWithSomeError(ratio, 259);
	}
	
	@Test
	public void return555ForTurnoverIfDefenderStronger() {
		factors.defenderStrength = 4;
		int ratio = diceThrower.getRatio(TURNOVER, factors);
		assertWithSomeError(ratio, 555);
	}

	
}
