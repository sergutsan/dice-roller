package org.sergut.diceroller.montecarlo.test;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.sergut.diceroller.IllegalDiceExpressionException;
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
	    try { 
		MontecarloSimulator simulator = MontecarloSimulator.getInstance();
		simulator.simulateDice(invalidDice, operator, validDice, maxRolls);
		fail("Expression " + invalidDice + " was not rejected as test-dice but should.");
	    } catch (IllegalDiceExpressionException e) {
		// right behaviour, moving on...
	    }
	    try { 
		MontecarloSimulator simulator = MontecarloSimulator.getInstance();
		simulator.simulateDice(validDice, operator, invalidDice, maxRolls);
		fail("Expression " + invalidDice  + " was not rejected as goal-dice but should.");
	    } catch (IllegalDiceExpressionException e) {
		// right behaviour, moving on...
	    }
	}	

    }
}
