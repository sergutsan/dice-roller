package org.sergut.diceroller.savageworlds;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores the results for the different attack options.
 * 
 * Basically, a glorified map. 
 */
public class SavageWorldsSimulationResult {
    private Map<String, SavageWorldsDamageCounter> resultMap = new HashMap<String, SavageWorldsDamageCounter>();
    
    public void setResult(String key, SavageWorldsDamageCounter result) {
	this.resultMap.put(key, result);
    }
    
    public SavageWorldsDamageCounter getResult(String key) {
	return this.resultMap.get(key);
    }
    
}
