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
		
		// Bridge 1 constraint
		if(o.toInteger()==2 && P1==true) {return 0;}
		if(o.toInteger()==2 && P1==false) {inWater=true; return -1;}

		// Bridge 2 constraint
		if(o.toInteger()==4 && (P5==true || P6==true)) {return 0;}
		if(o.toInteger()==4 && P5==false && P6==false) {inWater=true; return -1;}
		
		// Negative reward if in the water
		if(o.toInteger()==(int)Math.pow(2, 0)) {inWater = true; return -1;}
			
		// Deliver products to the matched delivery point
		if(P1==true && o.toInteger()==(int) Math.pow(2, 9)) {P1=false; return 1;}
		if(P2==true && o.toInteger()==(int) Math.pow(2,10)) {P2=false; return 1;}
		if(P3==true && o.toInteger()==(int) Math.pow(2,11)) {P3=false; return 1;}
		if(P4==true && o.toInteger()==(int) Math.pow(2,12)) {P4=false; return 1;}
		if(P5==true && o.toInteger()==(int) Math.pow(2,13)) {P5=false; return 1;}
		if(P6==true && o.toInteger()==(int) Math.pow(2,14)) {P6=false; return 1;}
		
		
		
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
}
