package environments;
import java.io.IOException;

import rewardmachines.IRewardMachine;
import rewardmachines.Observation;

public class Environment {

	IRewardMachine task;
	
	public Environment(IRewardMachine task) {
		this.task=task;
	}
	
	public int execute(Observation o) throws IOException {
		return task.execute(o);
	}
	
	public void reset() {
		task.reset();
	}
	
}
