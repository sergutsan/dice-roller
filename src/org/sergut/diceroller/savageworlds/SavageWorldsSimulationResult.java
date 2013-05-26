package org.sergut.diceroller.savageworlds;

/**
 * Stores the results for the different attack options
 */
public class SavageWorldsSimulationResult {
    enum AttackOptions {
	NORMAL,
	WILD,
	RAPID,
    }
    
    enum AimOptions {
	BODY, 
	ARM,
	HEAD,
    }
    
    private SavageWorldsDamageCounter result;
    
    public void setResult(SavageWorldsDamageCounter result) {
	this.result = result;
    }
    
    public SavageWorldsDamageCounter getResult() {
	return this.result;
    }
    
}
