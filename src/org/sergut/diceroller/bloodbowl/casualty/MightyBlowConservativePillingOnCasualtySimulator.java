package org.sergut.diceroller.bloodbowl.casualty;

import static org.sergut.diceroller.bloodbowl.BloodBowlSimulator.CASUALTY_THRESHOLD;
import static org.sergut.diceroller.bloodbowl.BloodBowlSimulator.multiplyRatios;

public class MightyBlowConservativePillingOnCasualtySimulator extends CasualtySimulator {

	@Override
	public int getCasualtyRatio(int armorToBeat) {
		int armorBreakRatio = getRatioFrom2D6exceeding(armorToBeat);
		int armorBreakExtraRatio = getRatioFrom2D6("=", armorToBeat - 1);

		int casualtyRatio = getRatioFrom2D6exceeding(CASUALTY_THRESHOLD);
		int casualtyExtraRatio = getRatioFrom2D6("=", CASUALTY_THRESHOLD - 1);
		int noCasualtyRatio = getRatioFrom2D6("<", CASUALTY_THRESHOLD - 1);

		int noMigthyBlowNoPillingOnRatio = multiplyRatios(armorBreakRatio, casualtyRatio);
		int mightyBlowForInjuryNoPillingOnRatio = multiplyRatios(armorBreakRatio, casualtyExtraRatio);
		int pillingOnForInjuryNoMightyBlowRatio = multiplyRatios(armorBreakRatio, noCasualtyRatio, casualtyRatio);
		int pillingOnPlusMightyBlowForInjuryRatio = multiplyRatios(armorBreakRatio, noCasualtyRatio, casualtyExtraRatio);
		int mightyBlowForArmorNoPillinOnRatio = multiplyRatios(armorBreakExtraRatio, casualtyRatio);
		int mightyBlowForArmorPillingOnForInjuryRatio = multiplyRatios(armorBreakExtraRatio, noCasualtyRatio, casualtyRatio);

		return noMigthyBlowNoPillingOnRatio 
				* mightyBlowForInjuryNoPillingOnRatio
				* pillingOnForInjuryNoMightyBlowRatio
				* pillingOnPlusMightyBlowForInjuryRatio
				* mightyBlowForArmorNoPillinOnRatio
				* mightyBlowForArmorPillingOnForInjuryRatio;
	}

}
