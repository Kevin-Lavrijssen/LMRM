package environments;

import rewardmachines.Observation;

public interface ILabelingFunction {

	public Observation label(String action, int[] state);
	
	public int getNumberOfPropositions();
	
}
