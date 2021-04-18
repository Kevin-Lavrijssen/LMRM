package rewardmachines;

import java.util.ArrayList;

public class TransitionConstraint {

	ArrayList<String> dnf;
	
	public TransitionConstraint(Observation o) {
		dnf = new ArrayList<String>();
		dnf.add(o.toString());
	}
	
	public ArrayList<String> getDNF(){
		return dnf;
	}

	public void include(Observation o) {
		mergeObservation(o);
		update();
	}

	private void update() {
		while (true) {
			int iSize = dnf.size();
			merge();
			if(iSize==dnf.size()) {break;}
		}
	}

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
