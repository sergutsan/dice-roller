package org.sergut.diceroller.montecarlo.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.sergut.diceroller.IllegalDiceExpressionException;
import org.sergut.diceroller.montecarlo.DiceResult;
import org.sergut.diceroller.montecarlo.MontecarloSimulator;


public class MontecarloSimulatorTest {
    @Test
    public void throwsExceptionIfInvalidExpression() {
	String[] diceDescriptions = {
		"2e10",   // invalid letter 'e'
		"3r40",   // invalid letter 'r'
		"r",      // no numbers
		"dd",     // no numbers
		"2Dd5",   // two d's
		"2dd10",  // two d's
		"3d40d",  // two d's
		"d2d5",   // two d's
		"--2",    // two -
		"++2",    // two +
		"10!",    // exploding constant
		"1d10-",  // incomplete expression
		"1d8!+",  // incomplete expression
		};
	for (String invalidDice : diceDescriptions) {
	    String validDice = "1d4";
	    String operator = ">=";
	    int maxRolls = 1;
	    MontecarloSimulator simulator = MontecarloSimulator.getInstance();
	    try { 
		simulator.simulateDice(invalidDice, operator, validDice, maxRolls);
		fail("Expression " + invalidDice + " was not rejected as test-dice but should.");
	    } catch (IllegalDiceExpressionException e) {
		// right behaviour, moving on...
	    }
	    try { 
		simulator.simulateDice(validDice, operator, invalidDice, maxRolls);
		fail("Expression " + invalidDice  + " was not rejected as goal-dice but should.");
	    } catch (IllegalDiceExpressionException e) {
		// right behaviour, moving on...
	    }
	}
    }
    
    @Test
    public void returnsRightResult() {
	int maxRolls = 100000;
	returnRightResultInThisCase("1d10",  ">", "5",   maxRolls / 2, maxRolls);	
	returnRightResultInThisCase("1d4+4", ">", "1d4", maxRolls,     maxRolls);	
	returnRightResultInThisCase("1d4+4", "<", "1d4", 0,            maxRolls);
    }
    
    public void returnRightResultInThisCase(String testDice, 
	    				String op, 
	    				String targetDice, 
	    				int expectedSuccess, 
	    				int maxRolls) {
	MontecarloSimulator simulator = MontecarloSimulator.getInstance();
	int tolerance = maxRolls / 20; // 5%
	DiceResult result = simulator.simulateDice(testDice, op, targetDice, maxRolls);
	assertTrue(result.totalRolls == maxRolls);
	assertTrue(Math.abs(result.successRolls - expectedSuccess) < tolerance);	
    }
}
