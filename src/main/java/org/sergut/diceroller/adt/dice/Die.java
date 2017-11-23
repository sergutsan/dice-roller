package org.sergut.diceroller.adt.dice;

/**
 * A die to be rolled. 
 * 
 * @author sergut
 *
 */
public interface Die {
    /**
     * Returns the result of rolling the die.
     * 
     * @return the result of rolling the die.
     */
    int roll();
    
    /**
     * Returns the number of sides of this die.
     * 
     * @return the number of sides of this die.
     */
    int getSideCount();
    
}
