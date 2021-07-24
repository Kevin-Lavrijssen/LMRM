package agents;

import rewardmachines.Observation;

public class Log {

	private Observation o;
	private int r;
	private String a;
	
	public Log(String a, Observation o, int r) {
		this.a=a;
		this.o=o;
		this.r=r;
	}
	
	public Observation getObservation() {
		return o;
	}
	
	public int getReward() {
		return r;
	}
	
	public String getAction() {
		return a;
	}
	
	public String toString() {
		return "("+a+", "+o.toString()+","+r+")#";
	}
	
}
