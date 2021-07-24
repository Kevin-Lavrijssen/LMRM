package environments;

import rewardmachines.Observation;

public interface IRewardFunction {

	public int execute(Observation o);
	
	public void reset();
	
}
