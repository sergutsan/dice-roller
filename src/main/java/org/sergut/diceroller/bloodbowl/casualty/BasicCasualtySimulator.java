package org.sergut.diceroller.bloodbowl.casualty;

import static org.sergut.diceroller.bloodbowl.BloodBowlSimulator.CASUALTY_THRESHOLD;
import static org.sergut.diceroller.bloodbowl.BloodBowlSimulator.multiplyRatios;

public class BasicCasualtySimulator extends CasualtySimulator {

	@Override
	public int getCasualtyRatio(int armorToBeat) {
		int armorBreakRatio = getRatioFrom2D6exceeding(armorToBeat);
		int casualtyRatio = getRatioFrom2D6exceeding(CASUALTY_THRESHOLD);
		return multiplyRatios(armorBreakRatio, casualtyRatio);
	}

}
