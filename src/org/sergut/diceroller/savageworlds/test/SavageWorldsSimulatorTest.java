package org.sergut.diceroller.savageworlds.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.sergut.diceroller.IllegalDiceExpressionException;
import org.sergut.diceroller.savageworlds.SavageWorldsSimulationJob;
import org.sergut.diceroller.savageworlds.SavageWorldsSimulator;


public class SavageWorldsSimulatorTest {
    private SavageWorldsSimulationJob defaultJob;

    @Before
    public void buildDefaultJob() {
	defaultJob = new SavageWorldsSimulationJob();
	defaultJob.attackDice = null;
	defaultJob.damageDice= null;
	defaultJob.attackerWildCard = false;
	defaultJob.defenderParry = -1;
	defaultJob.defenderToughness = -1;
	defaultJob.defenderShaken = false;
	defaultJob.defenderWildCard = false;
	defaultJob.includeBodyAttack = true;
	defaultJob.includeArmAttack  = false;
	defaultJob.includeHeadAttack = false;
	defaultJob.includeNonWildAttack = true;
	defaultJob.includeWildAttack    = false;
	defaultJob.includeNonRapidAttack = true;
	defaultJob.includeRapidAttack    = false;
	defaultJob.maxIterations = 100000;
    }

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
	    SavageWorldsSimulationJob job = defaultJob.clone();
	    job.attackDice = invalidDice;
	    job.maxIterations = 1;
	    job.damageDice = "1d4";
	    SavageWorldsSimulator simulator = new SavageWorldsSimulator();
	    try { 
		simulator.simulate(job);
		fail("Expression " + invalidDice + " was not rejected as test-dice but should.");
	    } catch (IllegalDiceExpressionException e) {
		// right behaviour, moving on...
	    }
	}
    }
    //    
    //    @Test
    //    public void returnsRightResult() {
    //	int maxRolls = 100000;
    //	returnRightResultInThisCase("1d10",  ">", "5",   maxRolls / 2, maxRolls);	
    //	returnRightResultInThisCase("1d4+4", ">", "1d4", maxRolls,     maxRolls);	
    //	returnRightResultInThisCase("1d4+4", "<", "1d4", 0,            maxRolls);
    //    }
    //    
    //    public void returnRightResultInThisCase(String testDice, 
    //	    				String op, 
    //	    				String targetDice, 
    //	    				int expectedSuccess, 
    //	    				int maxRolls) {
    //	MontecarloSimulator simulator = MontecarloSimulator.getInstance();
    //	int tolerance = maxRolls / 10; // 10%
    //	DiceResult result = simulator.simulateDice(testDice, op, targetDice, maxRolls);
    //	assertTrue(result.totalRolls == maxRolls);
    //	assertTrue(Math.abs(result.successRolls - expectedSuccess) < tolerance);	
    //    }
}
