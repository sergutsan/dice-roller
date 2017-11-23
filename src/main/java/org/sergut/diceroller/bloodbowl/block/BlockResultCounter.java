package org.sergut.diceroller.bloodbowl.block;

import java.util.HashMap;
import java.util.Map;

public class BlockResultCounter {
	
	private Map<BlockResult, Integer> blockResultCountMap = new HashMap<>(); 
	
	public BlockResultCounter() {
		for (BlockResult result : BlockResult.values()) {
			blockResultCountMap.put(result, 0);
		}
	}
	
	public void increment(BlockResult result) {
		Integer count = blockResultCountMap.get(result);
		count++;
		blockResultCountMap.put(result, count);
	}
	
	public Integer getCount(BlockResult result) {
		return blockResultCountMap.get(result);
	}
}
