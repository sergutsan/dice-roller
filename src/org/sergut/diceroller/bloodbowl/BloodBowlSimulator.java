package org.sergut.diceroller.bloodbowl;

import org.sergut.diceroller.bloodbowl.block.BlockDiceSimulator;
import org.sergut.diceroller.bloodbowl.block.BlockFactors;
import org.sergut.diceroller.bloodbowl.block.BlockResult;
import org.sergut.diceroller.montecarlo.DiceResult;
import org.sergut.diceroller.montecarlo.MontecarloSimulator;

public class BloodBowlSimulator {

	public static final int CASUALTY_THRESHOLD = 10;
	public static final int ARMOR_AGAINST_CLAWS = 7;
	
	private static final MontecarloSimulator SIMULATOR = new MontecarloSimulator();
	
	public static void main(String[] args) {
		BloodBowlSimulator simulator = new BloodBowlSimulator();
		BlockFactors factors = CmdLineParser.getFactorsFromCmdLine(args);
		simulator.launch(factors);
	}

	private void launch(BlockFactors factors) {
		System.out.println("Turnover:  " + makePrettyRatio(getTurnoverRatio(factors)));
		System.out.println("Knocked Down: " + makePrettyRatio(getKnockedDownRatio(factors)));
		System.out.println("  Casualty:   " + makePrettyRatio(getCombinedCasualtyRatio(factors)));
	}
	
	public int getTurnoverRatio(BlockFactors factors) {
		BlockDiceSimulator blockSimulator = new BlockDiceSimulator();
		int result = blockSimulator.getRatio(BlockResult.TURNOVER, factors);
		return result;
	}

	public int getKnockedDownRatio(BlockFactors factors) {
		BlockDiceSimulator blockSimulator = new BlockDiceSimulator();
		int result = blockSimulator.getRatio(BlockResult.KNOCK_DOWN, factors);
		return result;
	}

	public int getCombinedCasualtyRatio(BlockFactors factors) {
		// Step 1: Calculate % of Knocking Down
		BlockDiceSimulator blockSimulator = new BlockDiceSimulator();
		int knockDownRatio = blockSimulator.getRatio(BlockResult.KNOCK_DOWN, factors);
		// Step 2: Calculate % of causing Casualty
		// TODO: take into account Pilling On (i.e. repeat armor or injury)
		int casualtyRatio = getStrictCasualtyRatio(factors);
		return multiplyRatios(knockDownRatio, casualtyRatio);
	}

	private int getStrictCasualtyRatio(BlockFactors factors) {
		int armorGoal = factors.defenderArmor;
		if (factors.attackerClaws) {
			armorGoal = ARMOR_AGAINST_CLAWS;
		}
		if (factors.defenderStunty) {
			armorGoal--;
		}
		if (factors.attackerMightyBlow) {
			return casualtyRatioWithMightyBlow(armorGoal);
		}
		int armorBreakRatio = getRatioFrom2D6exceeding(armorGoal);
		int casualtyRatio = getRatioFrom2D6exceeding(CASUALTY_THRESHOLD);
		return multiplyRatios(armorBreakRatio, casualtyRatio);
	}

	private int casualtyRatioWithMightyBlow(int armorGoal) {
		int armorBreakRatio = getRatioFrom2D6exceeding(armorGoal);
		int armorBreakExtraRatio = getRatioFrom2D6("=", armorGoal - 1);

		int casualtyRatio = getRatioFrom2D6exceeding(CASUALTY_THRESHOLD);
		int casualtyExtraRatio = getRatioFrom2D6("=", CASUALTY_THRESHOLD - 1);

		int noMigthyBlowUsedRatio = multiplyRatios(armorBreakRatio, casualtyRatio);
		int mightyBlowUsedForArmorRatio  = multiplyRatios(armorBreakExtraRatio, casualtyRatio);
		int mightyBlowUsedForInjuryRatio = multiplyRatios(armorBreakRatio, casualtyExtraRatio);
		return noMigthyBlowUsedRatio + mightyBlowUsedForArmorRatio + mightyBlowUsedForInjuryRatio;
	}

	private int getRatioFrom2D6exceeding(int target) {
		return getRatioFrom2D6(">=", target);
	}
	
	private int getRatioFrom2D6(String operator, int target) {
		DiceResult result = SIMULATOR.simulateDice("2D6", operator, "" + target, 100000);
		return 1000 * result.successRolls / result.totalRolls;
	}
	
	/**
	 * Takes two ratios (as ints in [0-1000] and returns the multiplied
	 * ratio of the three (as another int in [0-1000].
	 */
	public int multiplyRatios(int ratio1, int ratio2) {
		int result = ratio1 * ratio2;
		result /= 1000;
		return result;
	}

	private String makePrettyRatio(int ratio) {
		int integerPart = ratio / 10;
		int decimalPart = ratio - 10 * integerPart;
		String padding = "";
		if (integerPart < 10) {
			padding = " ";
		}
		return padding + integerPart + "." + decimalPart + "%";
	}

}

