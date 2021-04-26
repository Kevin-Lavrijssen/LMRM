package environments;

import rewardmachines.Observation;

public interface IRewardFunction {

	public int evaluate(Observation o);
	
}
