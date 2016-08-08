package org.sergut.diceroller.bloodbowl.test;

public abstract class SimulatorTester {
	
	protected void assertWithSomeError(int expected, int actual) {
		assertWithSomeError(expected, actual , 10);
	}

	protected void assertWithSomeError(int expected, int actual, int precision) {
		if (Math.abs(expected - actual) > precision) {
			int min = expected - precision;
			int max = expected + precision;
			throw new AssertionError("expected something within [" + min + ", " + max + "] but was " + actual);
		}
	}

}
