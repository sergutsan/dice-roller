package org.sergut.diceroller.bloodbowl;

public enum BlockResult implements Comparable<BlockResult>{
	/* TURNOVER aims to include:
	 * - "Attacker Down" 
	 * - "Both Down", attacker without Block
	 * It really does not matter whether the defender has Block or not, 
	 * as it is a Turnover anyway, which is the worst result. 
	 */ 
	TURNOVER,
	/* NOBODY_DOWN aims to include all those results that are 
	 * neither Turnovers nor involve an Armor roll: 
	 * - "Push"
	 * - "Both Down" when both have Block
	 * - "Both Down" when the defender has Wrestle (both players are 
	 *   placed prone, but none is Knocked Down
	 * - "Defender Stumbles" and has Dodge
	 */
	NOBODY_DOWN, 
	/* KNOCK_DOWN aims to includes all those results in which 
	 * only the defender is Knocked Down: 
	 * - "Defender Down"
	 * - "Defender Stumbles" and cannot use Dodge
	 */
	KNOCK_DOWN;
}
