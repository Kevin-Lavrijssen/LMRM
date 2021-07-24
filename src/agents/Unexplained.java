package agents;

import rewardmachines.Observation;

public class Unexplained {

	private int state;
	private Log log;
	private String[] prefix;
	
	public Unexplained(int state, Log log, String[] prefix) {
		this.state = state;
		this.log = log;
		this.prefix=prefix;
	}
	
	public int getState() {
		return state;
	}
	
	public int getReward() {
		return log.getReward();
	}
	
	public Observation getObservation() {
		return log.getObservation();
	}
	
	public String[] getPrefix() {
		return prefix;
	}
	
}
