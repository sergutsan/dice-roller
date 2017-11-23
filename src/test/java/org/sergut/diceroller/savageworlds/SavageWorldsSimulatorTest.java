package org.sergut.diceroller.savageworlds.test;

import org.junit.Before;
import org.junit.Test;
import org.sergut.diceroller.IllegalDiceExpressionException;
import org.sergut.diceroller.savageworlds.SavageWorldsSimulationJob;
import org.sergut.diceroller.savageworlds.SavageWorldsSimulator;


public class SavageWorldsSimulatorTest {
    @SuppressWarnings("unused")
    private static final SavageWorldsSimulator simulator = SavageWorldsSimulator.getInstance(); 
    private SavageWorldsSimulationJob defaultJob;

    @Before
    public void buildDefaultJob() {
	defaultJob = new SavageWorldsSimulationJob();
    }

 //   @Test
    public void throwsExceptionIfInvalidExpression() throws CloneNotSupportedException {
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
	    @SuppressWarnings("unused")
	    SavageWorldsSimulator simulator = SavageWorldsSimulator.getInstance();
	    try { 
		//simulator.simulate(job);
		//fail("Expression " + invalidDice + " was not rejected as test-dice but should.");
	    } catch (IllegalDiceExpressionException e) {
		// right behaviour, moving on...
	    }
	}
    }

    @Test
    public void returnsRightResult1() throws CloneNotSupportedException {
	// build job
	SavageWorldsSimulationJob job = defaultJob.clone();
	job.attackDice = "1d12";
	job.damageDice = "1d12";
	job.attackerWildCard = false;
	job.defenderWildCard = true;
	job.defenderParry = 7;
	job.defenderShaken = false;
	job.maxIterations = 100000;
	// simulate job, collect results
	
	// check results
	checkResults(job, .1, .1, .1, .1, .1);
    }

    /**
     * @param job the job to simulate and check
     * @param p0  Probability of nothing happens
     * @param ps  Probability of shaken
     * @param p1  Probability of 1 wound
     * @param p2  Probability of 2 wounds
     * @param p3  Probability of 3 wounds
     */
    private void checkResults(SavageWorldsSimulationJob job, double p0, double ps, double p1, double p2, double p3) {
	// SavageWorldsSimulationResult result = simulator.simulate(job);
	
    }

    /**
     * Convenience: does not require to enter p0
     * 
     * @param job the job to simulate and check
     * @param ps  Probability of shaken
     * @param p1  Probability of 1 wound
     * @param p2  Probability of 2 wounds
     * @param p3  Probability of 3 wounds
     */
    @SuppressWarnings("unused")
    private void checkResults(SavageWorldsSimulationJob job, double ps, double p1, double p2, double p3) {
	double p0 = 1 - ps - p1 - p2 - p3;
	checkResults(job, p0, ps, p1, p2, p3);
    }

}
