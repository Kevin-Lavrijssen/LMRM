package rewardmachines;

public class Observation {

	private String observation;
	private int integerValue;
	
	public Observation(int intValue, int nPropositions) {
		String bitVector = Integer.toBinaryString(intValue);
		String o = "";
		
		int leadingZeros = nPropositions - bitVector.length();
		
		for (int i=0; i<leadingZeros;i++) {
			o = o + "0";
		}
		
		o = o + bitVector;
		
		integerValue = intValue;
		observation = o;
		
	}
	
	public String toString() {
		return observation;
	}
	
	public int toInteger() {
		return integerValue;
	}
	
	public boolean equals(Observation o) {
		return this.toString().equals(o.toString());
	}
	
}
