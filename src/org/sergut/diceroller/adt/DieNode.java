package org.sergut.diceroller.adt;

import org.sergut.diceroller.adt.dice.Die;
import org.sergut.diceroller.adt.dice.ExplodingDie;
import org.sergut.diceroller.adt.dice.RegularDie;

/**
 * A node that represents a group of dice of the same type and the same 
 * number of sides. 
 * 
 * @author sergut
 */
public class DieNode implements Node {

    private Die die;
    private int dieCount;    
    private DieType dieType;
    
    public DieNode(int dieCount, int sideCount, DieType dieType) {
	this.dieCount = dieCount;
	this.dieType  = dieType;
	switch (dieType) {
	case REGULAR:   die = new RegularDie(sideCount);   break;
	case EXPLODING: die = new ExplodingDie(sideCount); break;
	default:
	    throw new IllegalStateException("Invalid die type: '" + dieType + "'.");
	}
    }

    public int getDiceCount() {
	return dieCount;
    }
    
    @Override
    public int getResult() {
	int result = 0;
	for (int i = 0; i < dieCount; i++)
	    result += die.roll();
	return result;
    }

    @Override
    public String toString() {
	String result = dieCount + "d" + die.getSideCount();
	if (dieType == DieType.EXPLODING)
	    result += "!";
	return result;	
    }

}
