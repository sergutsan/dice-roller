package org.sergut.diceroller.bloodbowl.test;

import static org.junit.Assert.*;
import static org.sergut.diceroller.bloodbowl.BlockResult.*;

import org.junit.Test;
import org.sergut.diceroller.bloodbowl.BlockResult;
import org.sergut.diceroller.bloodbowl.BlockResultBester;


public class BlockResultBesterShould {

	private static final BlockResultBester BESTER = new BlockResultBester();
	
	@Test
	public void returnReturnRightResult() {
		assertCorrect(TURNOVER,    TURNOVER,    TURNOVER);
		assertCorrect(TURNOVER,    NOBODY_DOWN, NOBODY_DOWN);
		assertCorrect(TURNOVER,    KNOCK_DOWN,  KNOCK_DOWN);
		assertCorrect(NOBODY_DOWN, TURNOVER,    NOBODY_DOWN);
		assertCorrect(NOBODY_DOWN, NOBODY_DOWN, NOBODY_DOWN);
		assertCorrect(NOBODY_DOWN, KNOCK_DOWN,  KNOCK_DOWN);
		assertCorrect(KNOCK_DOWN,  TURNOVER,    KNOCK_DOWN);
		assertCorrect(KNOCK_DOWN,  NOBODY_DOWN, KNOCK_DOWN);
		assertCorrect(KNOCK_DOWN,  KNOCK_DOWN,  KNOCK_DOWN);
	}

	private void assertCorrect(BlockResult die1, BlockResult die2, BlockResult expected) {
		assertEquals(expected, BESTER.getResult(die1, die2));
	}
}
