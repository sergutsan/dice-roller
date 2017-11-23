package org.sergut.diceroller.bloodbowl.casualty;

import static org.sergut.diceroller.bloodbowl.BloodBowlSimulator.CASUALTY_THRESHOLD;
import static org.sergut.diceroller.bloodbowl.BloodBowlSimulator.multiplyRatios;

public class MightyBlowCasualtySimulator extends CasualtySimulator {

	@Override
	public int getCasualtyRatio(int armorToBeat) {
		int armorBreakRatio = getRatioFrom2D6exceeding(armorToBeat);
		int armorBreakExtraRatio = getRatioFrom2D6("=", armorToBeat - 1);

		int casualtyRatio = getRatioFrom2D6exceeding(CASUALTY_THRESHOLD);
		int casualtyExtraRatio = getRatioFrom2D6("=", CASUALTY_THRESHOLD - 1);

		int noMigthyBlowUsedRatio = multiplyRatios(armorBreakRatio, casualtyRatio);
		int mightyBlowUsedForArmorRatio  = multiplyRatios(armorBreakExtraRatio, casualtyRatio);
		int mightyBlowUsedForInjuryRatio = multiplyRatios(armorBreakRatio, casualtyExtraRatio);
		return noMigthyBlowUsedRatio + mightyBlowUsedForArmorRatio + mightyBlowUsedForInjuryRatio;
	}



}
