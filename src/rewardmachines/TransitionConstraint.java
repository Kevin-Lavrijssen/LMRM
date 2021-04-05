package rewardmachines;

public class TransitionConstraint {

	String constraint;
	
	public TransitionConstraint(Observation o) {
		constraint = o.toString();
	}
	
	public void merge(Observation o) {
		if(distance(o)!=1) {throw new IllegalArgumentException("Illegal merge: merge("+constraint+", "+o.toString()+")");}
		
		String observation = o.toString();
		String mergedConstraint = "";
		
		for (int i=0; i<observation.length(); i++) {
			if(constraint.charAt(i)==observation.charAt(i)) {mergedConstraint+=constraint.charAt(i);}
			else {mergedConstraint+='?';}
		}
		
		constraint = mergedConstraint;
		
	}
	
	private int distance(Observation o) {
		String observation = o.toString();
		int dist = 0;
		for (int i=0; i<observation.length(); i++) {
			if(constraint.charAt(i)=='?') {continue;}
			if(constraint.charAt(i)==observation.charAt(i)) {continue;}
			dist+=1;
		}
		return dist;				
	}
	
	public boolean evaluate(Observation o) {
		return distance(o)==0;
	}
	
	public String toString() {
		return constraint;
	}
	
}
