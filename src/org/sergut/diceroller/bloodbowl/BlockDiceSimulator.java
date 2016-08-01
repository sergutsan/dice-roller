package org.sergut.diceroller.bloodbowl;

import java.util.ArrayList;
import java.util.List;

import org.sergut.diceroller.adt.dice.Die;
import org.sergut.diceroller.adt.dice.RegularDie;

public class BlockDiceSimulator {

	private static final Die DIE = new RegularDie(6); // no harm intended
	
	private int max_rolls = 100000;
	
	public int getRatio(BlockResult intendedResult, BlockFactors factors) {
		int resultCount = 0;
		for (int i = 0; i < max_rolls; i++) {
			BlockResult blockResult = getResult(factors);
			if (blockResult == intendedResult) {
				resultCount++;
			}
		}
		return Math.round(1000 * resultCount / max_rolls);
	}
	
	private BlockResult getResult(BlockFactors factors) {
		int diceCount = getDiceCount(factors);
		List<Integer> preliminaryDiceResults = getDiceResults(diceCount);
		BlockResultChooser chooser = getChooser(factors);
		BlockResult defaultResult = getDefaultResult(factors);
		return preliminaryDiceResults
				.stream()
				.map(x -> die2result(x, factors))
				.reduce(defaultResult, chooser::getResult);
	}

	private BlockResult getDefaultResult(BlockFactors factors) {
		if (factors.defenderStrength > factors.attackerStrength) {
			return BlockResult.KNOCK_DOWN;
		}
		return BlockResult.TURNOVER;
	}

	private BlockResultChooser getChooser(BlockFactors factors) {
		if (factors.defenderStrength > factors.attackerStrength) {
			return new BlockResultWorster();
		}
		return new BlockResultBester();
	}

	private int getDiceCount(BlockFactors factors) {
		int max = Math.max(factors.attackerStrength, factors.defenderStrength);
		int min = Math.min(factors.attackerStrength, factors.defenderStrength);
		if (max == min) {
			return 1;
		}
		if (max > 2 * min) {
			return 3;
		}
		return 2;
	}

	private BlockResult die2result(Integer dieResult, BlockFactors factors) {
		switch (dieResult) {
		case 1: return BlockResult.TURNOVER;
		case 3: return BlockResult.NOBODY_DOWN;
		case 4: return BlockResult.NOBODY_DOWN;
		case 6: return BlockResult.KNOCK_DOWN;
		case 2: 
			if (!factors.attackerBlock) {
				return BlockResult.TURNOVER;
			}
			if (factors.defenderBlock) {
				return BlockResult.NOBODY_DOWN;
			}
			return BlockResult.KNOCK_DOWN;
		case 5: 
			if (factors.defenderDodge && !factors.attackerTackle) {
				return BlockResult.NOBODY_DOWN;
			}
			return BlockResult.KNOCK_DOWN;
		default: 
			throw new RuntimeException("Invalid die result: " + dieResult);
		}
	}

	private List<Integer> getDiceResults(int numberOfDice) {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < numberOfDice; i++) {
			result.add(DIE.roll());
		}
		return result;
	}

	public void setMaxRolls(int newLimit) {
		this.max_rolls = newLimit;
	}
}
