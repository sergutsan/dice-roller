package org.sergut.diceroller.savageworlds;

/**
 * A simple counter for several damage-related concepts in Savage Worlds.
 */
public class SavageWorldsDamageCounter {
    int nothing = 0; // Nothing happened
    int shaken  = 0; // Shaken
    int wound1  = 0; // 1 wound
    int wound2  = 0; // 2 wounds
    int wound3  = 0; // 3 wounds
    int wound4m = 0; // 4 or more
    
    @Override public String toString() {
	return "Nothing:" + nothing + 
	      ", Shaken:" + shaken +
	      ", 1 wound:" + wound1 +
	      ", 2 wounds: " + wound2 +
	      ", 3 wounds: " + wound3 +
	      ", 4 wounds or more:" + wound4m;
    }
    
    /**
     * Convenience method: add up the 'wound' results and return the addition.
     *  
     * @return the addition of all "wound" results
     */
    public int getWounds() {
	return wound1 + wound2 + wound3 + wound4m;
    }
}