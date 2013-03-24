package org.sergut.diceroller.cmd;

import org.sergut.diceroller.IllegalDiceExpressionException;
import org.sergut.diceroller.montecarlo.DiceResult;
import org.sergut.diceroller.montecarlo.MontecarloSimulator;

public class Simulator {
    
    private static void usage() {
	System.out.println("USAGE: java Simulator <test-dice-expression> <operand> <goal-expression>");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	if (args.length != 3) {
	    usage();
	    return;
	}
	Simulator sim = new Simulator();
	sim.simulateDice(args[0], args[1], args[2]);
    }
    
    private void simulateDice(String testDice, String op, String goalDice) {
	int maxRolls = 500000;
	try {
	    MontecarloSimulator simulator = MontecarloSimulator.getInstance();
	    DiceResult result = simulator.simulateDice(testDice, op, goalDice, maxRolls);
	    showResults(result);
	} catch (NumberFormatException ex) {
	    System.out.println("There is an error with one of the numbers");
	} catch (IllegalDiceExpressionException ex) {
	    System.out.println("Invalid expression: " + ex.getExpression());
	}
    }

    private void showResults(DiceResult result) {
	double ratio = 100.0 * result.successRolls / result.totalRolls;
	System.out.printf("%2.2f%%\n", ratio);
    }

}
