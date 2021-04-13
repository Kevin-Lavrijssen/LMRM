package environments;

import exceptions.BehaviourUndefinedException;
import rewardmachines.RewardMachine;
import rewardmachines.Observation;

public class Environment {

	RewardMachine task;
	
	public Environment(RewardMachine task) {
		this.task=task;
	}
	
	public int execute(Observation o) throws BehaviourUndefinedException {
		return task.execute(o);
	}
	
	public void reset() {
		task.reset();
	}
	
}
