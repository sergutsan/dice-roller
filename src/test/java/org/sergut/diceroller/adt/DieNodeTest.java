package org.sergut.diceroller.adt.test;

import org.junit.Test;
import org.sergut.diceroller.adt.DieNode;
import org.sergut.diceroller.adt.DieType;

public class DieNodeTest {

    @Test
    public void allDieTypesCanBeBuilt() {
	for (DieType dieType : DieType.values()) {
	    int sideCount = (int) Math.ceil(100 * Math.random());
	    int dieCount  = (int) Math.ceil(100 * Math.random());
	    new DieNode(sideCount, dieCount, dieType);
	}
    }
    
}
