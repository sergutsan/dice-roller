package org.sergut.diceroller.bloodbowl.casualty;

import static org.sergut.diceroller.bloodbowl.BloodBowlSimulator.multiplyRatios;


public class MightyBlowAggressivePillingOnCasualtySimulator extends CasualtySimulator {

	private CasualtySimulator noNeedToRepeatArmorSimulator = new MightyBlowConservativePillingOnCasualtySimulator();

	private CasualtySimulator repeatArmorSimulator = new MightyBlowCasualtySimulator();
	
	@Override
	public int getCasualtyRatio(int armorToBeat) {
		int noNeedToRepeatArmorRatio = noNeedToRepeatArmorSimulator.getCasualtyRatio(armorToBeat);
		int armorNotBrokenRatio = oppositeOf(getRatioFrom2D6exceeding(armorToBeat));
		int repeatArmorRatio = repeatArmorSimulator.getCasualtyRatio(armorToBeat);
		
		return noNeedToRepeatArmorRatio + multiplyRatios(armorNotBrokenRatio, repeatArmorRatio);
	}

}
