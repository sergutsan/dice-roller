package org.sergut.diceroller.bloodbowl.test;

import static org.junit.Assert.assertTrue;

public abstract class SimulatorTester {
	
	protected void assertWithSomeError(int ratio, int target) {
		System.out.println("Ratio: " + ratio + "   Target: " + target);
		assertTrue(ratio < target + 10 && ratio > target - 10);
	}

}
