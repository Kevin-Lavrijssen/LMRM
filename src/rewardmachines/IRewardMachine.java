package rewardmachines;

import java.io.IOException;

public interface IRewardMachine {

	public int execute(Observation o) throws IOException;
	
	public void reset();
	
	public void pushTransition(int source, Observation o, int destination, int reward);
	
	public void commitTransition();
	
	public void addStateTransition(int source, Observation o, int reward);
	
}
