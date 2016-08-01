package org.sergut.diceroller.bloodbowl;

import java.util.Comparator;

public enum BlockResult implements Comparable<BlockResult>{
	// TURNOVER includes "Attacker Down" is as bad as "Both Down without Block", 
	TURNOVER,
	// NOBODY_DOWN includes "Push" and "Both Down when both have Block"
	NOBODY_DOWN, 
	// KNOCK_DOWN includes all results in which only the defender is Knocked Down
	KNOCK_DOWN;
}