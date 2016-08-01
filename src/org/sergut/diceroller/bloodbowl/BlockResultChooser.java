package org.sergut.diceroller.bloodbowl;

@FunctionalInterface
public interface BlockResultChooser {
	BlockResult getResult(BlockResult r1, BlockResult r2);
}

