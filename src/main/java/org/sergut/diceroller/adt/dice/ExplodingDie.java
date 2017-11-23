package org.sergut.diceroller.adt.dice;

/**
 * A die that explodes, that is, when it rolls the maximum result
 * rolls again adds. This can happen any number of times. 
 * 
 * @author sergut
 *
 */
public class ExplodingDie implements Die {
    /**
     * Number of sides
     */
    private int sides;
    
    public ExplodingDie(int sides) {
	this.sides = sides;
    }
    
    @Override
    public int roll() {
	int result = 0, rollResult = 0;
	do {
	    rollResult = (int) Math.ceil(sides * Math.random());
	    result += rollResult;
	} while (rollResult == sides);
	return result;
    }

    @Override
    public int getSideCount() {
	return sides;
    }
    

}
