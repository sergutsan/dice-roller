package org.sergut.diceroller.adt.dice;

/**
 * A regular die. 
 * 
 * @author sergut
 */
public class RegularDie implements Die {
    /**
     * Number of sides
     */
    private int sides;
    
    public RegularDie(int sides) {
	this.sides = sides;
    }
    
    @Override
    public int roll() {
	int result = (int) Math.ceil(sides * Math.random());
	return result;
    }

    @Override
    public int getSideCount() {
	return sides;
    }
    

}
