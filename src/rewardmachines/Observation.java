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
	
	public Observation(String o) {
		observation = o;
		integerValue = toInt(o);
	}

	private int toInt(String o) {
		int val = 0;
		for (int i = o.length()-1; i>=0; i--) {
			val+=Math.pow(2, o.length()-i-1)*o.charAt(i);
		}
		return val;
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
