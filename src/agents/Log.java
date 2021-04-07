package agents;

import rewardmachines.Observation;

public class Log {

	private Observation o;
	private int r;
	
	public Log(Observation o, int r) {
		this.o=o;
		this.r=r;
	}
	
	public Observation getObservation() {
		return o;
	}
	
	public int getReward() {
		return r;
	}
	
	public String toString() {
		return "(o:"+o.toString()+",(r:"+r+")";
	}
	
}
