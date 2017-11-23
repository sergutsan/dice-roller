package org.sergut.diceroller.bloodbowl.block;

@FunctionalInterface
public interface BlockResultChooser {
	BlockResult getResult(BlockResult r1, BlockResult r2);
}

