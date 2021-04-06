package agents;

import rewardmachines.Observation;

public class Unexplained {

	private int state;
	private Log log;
	
	public Unexplained(int state, Log log) {
		this.state = state;
		this.log = log;
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
	
}
