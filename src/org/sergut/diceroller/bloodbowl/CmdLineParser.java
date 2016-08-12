package org.sergut.diceroller.bloodbowl;

import org.sergut.diceroller.bloodbowl.block.BlockFactors;
import org.sergut.diceroller.bloodbowl.block.PillingOn;

public class CmdLineParser {
	public static BlockFactors getFactorsFromCmdLine(String[] args) {
		BlockFactors result = new BlockFactors();
		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "-att-block": result.attackerBlock   = true; break;
			case "-att-tackle": result.attackerTackle = true; break;
			case "-att-MB": result.attackerMightyBlow = true; break;
			case "-att-claws": result.attackerClaws   = true; break;
			case "-def-block": result.defenderBlock   = true; break;
			case "-def-dodge": result.defenderDodge   = true; break;
			case "-def-stunty": result.defenderStunty = true; break;
			case "-att-PO: ":
				result.attackerPillingOn = getPillingOn(args[i+1]);
				i++;
				break;
			case "-att-strength": 
				result.attackerStrength = Integer.parseInt(args[i+1]);
				i++;
				break;				
			case "-def-strength": 
				result.defenderStrength = Integer.parseInt(args[i+1]);
				i++;
				break;				
			case "-def-armor": 
				result.defenderArmor = Integer.parseInt(args[i+1]);
				i++;
				break;				
			default: throw new IllegalArgumentException("Invalid argument: " + args[i]);
			}
		}
		return result;
	}

	private static PillingOn getPillingOn(String arg) {
		PillingOn result = PillingOn.NONE;
		switch (arg) {
		case "none": result = PillingOn.NONE; break;
		case "conservative" : result = PillingOn.CONSERVATIVE; break;
		case "aggresive": result = PillingOn.AGGRESIVE; break;
		default: 
			throw new IllegalArgumentException("Invalid argument: " + arg);
		}
		return result;
	}


}
