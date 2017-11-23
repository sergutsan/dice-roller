package org.sergut.diceroller.montecarlo;

public class DiceResult {
    public final int successRolls;
    public final int totalRolls;
    
    public DiceResult(int successRolls, int totalRolls) {
	this.successRolls = successRolls;
	this.totalRolls   = totalRolls;
    }
}
