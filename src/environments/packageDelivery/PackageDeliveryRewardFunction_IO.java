package environments.packageDelivery;

import environments.IRewardFunction;
import rewardmachines.Observation;

public class PackageDeliveryRewardFunction_IO implements IRewardFunction {


	boolean inWater;	
	boolean P1;
	boolean P2;
	boolean P3;
	boolean P4;
	boolean P5;
	boolean P6;
	
	int nNoise;
	
	public PackageDeliveryRewardFunction_IO(int nNoise) {
		inWater = false;
		
		P1 = false;
		P2 = false;
		P3 = false;
		P4 = false;
		P5 = false;
		P6 = false;
		
		this.nNoise = nNoise;
	}

	@Override
	public int execute(Observation o) {
		
		// When in the water the reward remains -1
		if(inWater) {return -1;}
		
		if(!isCarryingProducts()) {
			if(o.toString().charAt(nNoise+11)=='1'){P1=true;}
			if(o.toString().charAt(nNoise+10)=='1'){P2=true;}
			if(o.toString().charAt(nNoise+ 9)=='1'){P3=true;}
			if(o.toString().charAt(nNoise+ 8)=='1'){P4=true;}
			if(o.toString().charAt(nNoise+ 7)=='1'){P5=true;}
			if(o.toString().charAt(nNoise+ 6)=='1'){P6=true;}
		}
		
		 
		// Negative reward if in the water
		if(o.toString().charAt(nNoise+14)=='1') {inWater = true; return -1;}
			
		// Deliver products to the matched delivery point
		if(isCarryingProducts() && o.toString().charAt(nNoise+5)=='1') {dropProducts(); return 1;}
		if(isCarryingProducts() && o.toString().charAt(nNoise+4)=='1') {dropProducts(); return 1;}
		if(isCarryingProducts() && o.toString().charAt(nNoise+3)=='1') {dropProducts(); return 1;}
		if(isCarryingProducts() && o.toString().charAt(nNoise+2)=='1') {dropProducts(); return 1;}
		if(isCarryingProducts() && o.toString().charAt(nNoise+1)=='1') {dropProducts(); return 1;}
		if(isCarryingProducts() && o.toString().charAt(nNoise+0)=='1') {dropProducts(); return 1;}
		
		
		
		// All other cases
		return 0;
		
	}

	@Override
	public void reset() {
		inWater = false;
		P1 = false;
		P2 = false;
		P3 = false;
		P4 = false;
		P5 = false;
		P6 = false;
	}
	
	private boolean isCarryingProducts() {
		return P1 || P2 || P3 || P4 || P5 || P6;
	}
	
	private void dropProducts() {
		P1 = false;
		P2 = false;
		P3 = false;
		P4 = false;
		P5 = false;
		P6 = false;
	}
}
