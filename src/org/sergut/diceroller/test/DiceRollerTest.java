package org.sergut.diceroller.test;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.sergut.diceroller.DiceRoller;

public class DiceRollerTest {
    
    private DiceRoller diceRoller = new DiceRoller();
    
    @Test
    public void invalidLettersRaiseException() {
	String[] invalidLetterDice = 
		{
		"2e10", 
		"3r40",
		"r",
		"dd",
		"2Dd5",
		};
	testDiceDescriptionsRaiseIllegalArgumentException(invalidLetterDice);
    }

    @Test
    public void tooManyValidLettersRaiseException() {
	String[] tooManyValidLettersDice = 
		{
		"2dd10", 
		"3d40d",
		"d2d5",
		};
	testDiceDescriptionsRaiseIllegalArgumentException(tooManyValidLettersDice);
    }

    private void testDiceDescriptionsRaiseIllegalArgumentException(String[] descriptions) {
	for (String diceDescription : descriptions) {
	    try { 
		diceRoller.rollDice(diceDescription);
		fail("Expression " + diceDescription + " was not rejected but should.");
	    } catch (IllegalArgumentException e) {
		// right behaviour, moving on...
	    }
	}	
    }

}
