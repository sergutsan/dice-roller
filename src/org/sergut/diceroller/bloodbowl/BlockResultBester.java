package org.sergut.diceroller.bloodbowl;

public class BlockResultBester implements BlockResultChooser {

	@Override
	public BlockResult getResult(BlockResult r1, BlockResult r2) {
		if (r1.compareTo(r2) < 0) 
			return r2;

		return r1;
	}

}
