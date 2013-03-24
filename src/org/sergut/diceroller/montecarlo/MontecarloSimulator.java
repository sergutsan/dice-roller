package org.sergut.diceroller.montecarlo;

import org.sergut.diceroller.DiceRoller;

public class MontecarloSimulator {
    
    private static MontecarloSimulator INSTANCE = null;
    
    public static MontecarloSimulator getInstance() {
	if (INSTANCE == null) {
	    INSTANCE = new MontecarloSimulator();
	}
	return INSTANCE;
    }

    /**
     * Simulate n dice rolls and return an account of successes. 
     * 
     * @param testDice something like "1d10!+2"
     * @param operator something like ">" or ">="
     * @param targetDice something like "1d10!+2"
     * @param maxRolls max number of rolls
     * @return a tuple with successful rolls and total rolls
     */
    public DiceResult simulateDice(String testDice, String operator, String targetDice, int maxRolls) {
	DiceRoller diceRoller = new DiceRoller();
	int success = 0;
	for (int i = 0; i < maxRolls; ++i) {
	    int result = diceRoller.rollDice(testDice);
	    int target = diceRoller.rollDice(targetDice);
	    // FIXME: (maybe) do this smartly with an enum
	    if (">=".equals(operator)) {
		if (result >= target) 
		    success++;
	    } else if (">".equals(operator)) {
		if (result > target) 
		    success++;
	    } else if ("=".equals(operator)) {
		if (result == target) 
		    success++;		    
	    } else if ("<=".equals(operator)) {
		if (result <= target) 
		    success++;
	    } else if ("<".equals(operator)) {
		if (result < target)  
		    success++;
	    } else {
		throw new IllegalStateException("Invalid operator: '" + operator + "'");
	    }
	}
	return new DiceResult(success, maxRolls);
    }

}
