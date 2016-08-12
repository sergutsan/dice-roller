package org.sergut.diceroller.bloodbowl.casualty;

import org.sergut.diceroller.montecarlo.DiceResult;
import org.sergut.diceroller.montecarlo.MontecarloSimulator;

public abstract class CasualtySimulator {

	public abstract int getCasualtyRatio(int armorToBeat);

	private static final MontecarloSimulator SIMULATOR = MontecarloSimulator.getInstance();
	
	protected int getRatioFrom2D6exceeding(int target) {
		return getRatioFrom2D6(">=", target);
	}
	
	protected int getRatioFrom2D6(String operator, int target) {
		DiceResult result = SIMULATOR.simulateDice("2D6", operator, "" + target, 100000);
		return 1000 * result.successRolls / result.totalRolls;
	}
	
}
