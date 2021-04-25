package rewardmachines;

import java.util.ArrayList;

public class TransitionConstraint {

	ArrayList<String> dnf;
	
	/**
	 * Creates the new transition constraint based on the given observation. Upon initialization a transition constraint
	 * covers only one observation. After that, the coverage can be increased or decreased by one observation at a time.
	 * 
	 * @param o | The observation that is initially covered.
	 */
	
	public TransitionConstraint(Observation o) {
		dnf = new ArrayList<String>();
		dnf.add(o.toString());
	}
	
	/**
	 * Method to retrieve the full constraint in disjunctive normal form.
	 * 
	 * @return	The transition constraint in disjunctive normal form.
	 */
	
	public ArrayList<String> getDNF(){
		return dnf;
	}

	/**
	 * Method to increase the coverage of the constraint to include the given observation. 
	 * 
	 * @param o	| The observation the transition constraint should cover.
	 */
	
	public void include(Observation o) {
		mergeObservation(o);
		update();
	}
	
	/**
	 * This method checks if the any of the conjunctions can be merged, and if so, merges them.
	 */

	private void update() {
		while (true) {
			int iSize = dnf.size();
			merge();
			if(iSize==dnf.size()) {break;}
		}
	}

	/**
	 * Method to merge the conjunctions of the transition constraint until there are no more merges possible and the 
	 * disjunctive normal form is in its smallest form.
	 */
	
	private void merge() {

		for(int i=0; i<dnf.size()-1; i++) {
			for (int j=i; j<dnf.size();j++) {
				String c1 = dnf.get(i);
				String c2 = dnf.get(j);
				if(mergeable(c1, c2)) {
					dnf.remove(c1);
					dnf.remove(c2);
					String c = merge(c1, c2);
					dnf.add(c);
					return;
				}
			}
		}
	}
	
	private void mergeObservation(Observation o) {
		for (String conjunction : dnf) {
			if(mergeable(conjunction, o.toString())) {
				dnf.remove(conjunction);
				String nConjunction = merge(conjunction, o.toString());
				dnf.add(nConjunction);
				return;
				}
		}
		dnf.add(o.toString());
	}

	private String merge(String s1, String s2) {
		
		String merged = "";
		for(int i=0; i<s1.length(); i++) {
			if(s1.charAt(i)==s2.charAt(i)) {merged+=s1.charAt(i); continue;}
			merged+='?';
		}
		return merged;
		
	}

	private boolean mergeable(String s1, String s2) {
		
		int distance = 0;
		for (int i = 0; i<s1.length(); i++) {
			if(s1.charAt(i)!=s2.charAt(i)) {distance+=1;}
		}
		
		return distance==1;
		
	}

	public boolean evaluate(Observation o) {
		for (String conjunction : dnf) {
			if(covers(conjunction, o.toString())) {return true;}
		}
		return false;
	}

	private boolean covers(String conjunction, String observation) {
		for (int i=0; i<observation.length(); i++) {
			if(conjunction.charAt(i)=='?') {continue;}
			if(conjunction.charAt(i)!=observation.charAt(i)) {return false;}
		}
		return true;
	}
	
}
