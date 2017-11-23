package org.sergut.diceroller.test;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.sergut.diceroller.DiceRoller;
import org.sergut.diceroller.IllegalDiceExpressionException;

public class DiceRollerTest {
    
    private DiceRoller diceRoller = new DiceRoller();
    
    @Test
    public void testsValidExpressions() {
	String[] diceDescriptions = {
		"1d6!+1d4!",
		"1+1d6",
		"2+3d4+4d6!+6d8",
		"1+1d6",
		"2d5",
		"2D100",
		"1d6!-2",
		"1d6!-1d100+3d4",
		"b[1d6!-1d100+3d4,1d4!]",
		"b[1d6!,1d4!]",
		"w[1d6!,1d4-1d100,3d4,1d10!+2]",
		};
	for (String diceDescription : diceDescriptions) {
	    diceRoller.rollDice(diceDescription);
	}	
    }

    @Test
    public void testsRaiseException() {
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
		"b[[1d6!-1d100+3d4,1d4!]",
		"w[1d6!,1d4-1d100,3d4]]",
		"w[1d6!,[1d4-1d100,3d4]]",
		"w[1d6!,[1d4-1d100,3d4]]",
		"w[1d6!,[1d4-1d100,3d4]",
		};
	for (String diceDescription : diceDescriptions) {
	    try { 
		diceRoller.rollDice(diceDescription);
		fail("Expression " + diceDescription + " was not rejected but should.");
	    } catch (IllegalDiceExpressionException e) {
		// right behaviour, moving on...
	    }
	}	
    }

}
