package org.sergut.diceroller.bloodbowl.test;

import static org.junit.Assert.assertEquals;
import static org.sergut.diceroller.bloodbowl.block.BlockResult.KNOCK_DOWN;
import static org.sergut.diceroller.bloodbowl.block.BlockResult.NOBODY_DOWN;
import static org.sergut.diceroller.bloodbowl.block.BlockResult.TURNOVER;

import org.junit.Test;
import org.sergut.diceroller.bloodbowl.block.BlockResult;
import org.sergut.diceroller.bloodbowl.block.BlockResultChooser;
import org.sergut.diceroller.bloodbowl.block.BlockResultWorster;


public class BlockResultWorsterShould {

	private static final BlockResultChooser WORSTER = new BlockResultWorster();
	
	@Test
	public void returnReturnRightResult() {
		assertCorrect(TURNOVER,    TURNOVER,    TURNOVER);
		assertCorrect(TURNOVER,    NOBODY_DOWN, TURNOVER);
		assertCorrect(TURNOVER,    KNOCK_DOWN,  TURNOVER);
		assertCorrect(NOBODY_DOWN, TURNOVER,    TURNOVER);
		assertCorrect(NOBODY_DOWN, NOBODY_DOWN, NOBODY_DOWN);
		assertCorrect(NOBODY_DOWN, KNOCK_DOWN,  NOBODY_DOWN);
		assertCorrect(KNOCK_DOWN,  TURNOVER,    TURNOVER);
		assertCorrect(KNOCK_DOWN,  NOBODY_DOWN, NOBODY_DOWN);
		assertCorrect(KNOCK_DOWN,  KNOCK_DOWN,  KNOCK_DOWN);
	}

	private void assertCorrect(BlockResult die1, BlockResult die2, BlockResult expected) {
		assertEquals(expected, WORSTER.getResult(die1, die2));
	}
}
