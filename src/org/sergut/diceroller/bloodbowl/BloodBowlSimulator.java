package org.sergut.diceroller.bloodbowl;

import java.util.Arrays;
import java.util.List;

import org.sergut.diceroller.bloodbowl.block.BlockDiceSimulator;
import org.sergut.diceroller.bloodbowl.block.BlockFactors;
import org.sergut.diceroller.bloodbowl.block.BlockResult;
import org.sergut.diceroller.bloodbowl.block.PillingOn;
import org.sergut.diceroller.bloodbowl.casualty.AggressivePillingOnCasualtySimulator;
import org.sergut.diceroller.bloodbowl.casualty.BasicCasualtySimulator;
import org.sergut.diceroller.bloodbowl.casualty.CasualtySimulator;
import org.sergut.diceroller.bloodbowl.casualty.ConservativePillingOnCasualtySimulator;
import org.sergut.diceroller.bloodbowl.casualty.MightyBlowAggressivePillingOnCasualtySimulator;
import org.sergut.diceroller.bloodbowl.casualty.MightyBlowCasualtySimulator;
import org.sergut.diceroller.bloodbowl.casualty.MightyBlowConservativePillingOnCasualtySimulator;

public class BloodBowlSimulator {

	public static final int CASUALTY_THRESHOLD = 10;
	public static final int ARMOR_AGAINST_CLAWS = 7;
	
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

	/**
	 * Takes N ratios (as ints in [0-1000] and returns the multiplied
	 * ratio of the three (as another int in [0-1000].
	 */
	public static int multiplyRatios(int... ratios) {
		int result = 1000;
		for (int ratio : ratios) {
			result *= ratio;
			result /= 1000;
		}
		return result;
	}
	
	private int getStrictCasualtyRatio(BlockFactors factors) {
		int armorGoal = factors.defenderArmor;
		if (factors.attackerClaws) {
			armorGoal = ARMOR_AGAINST_CLAWS;
		}
		if (factors.defenderStunty) {
			armorGoal--;
		}
		CasualtySimulator simulator = getCasualtySimulator(factors);
		return simulator.getCasualtyRatio(armorGoal);
	}
	
	private CasualtySimulator getCasualtySimulator(BlockFactors factors) {
		PillingOn pillingOn = factors.attackerPillingOn;
		boolean mightyBlow  = factors.attackerMightyBlow;
		
		switch (pillingOn) {
		case NONE:         return mightyBlow? new MightyBlowCasualtySimulator()                      : new BasicCasualtySimulator(); 
		case CONSERVATIVE: return mightyBlow? new MightyBlowConservativePillingOnCasualtySimulator() : new ConservativePillingOnCasualtySimulator();
		case AGGRESIVE:    return mightyBlow? new MightyBlowAggressivePillingOnCasualtySimulator()   : new AggressivePillingOnCasualtySimulator();
		default:
			throw new IllegalStateException("Invalid Pilling On value: " + factors.attackerPillingOn);
		}
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

