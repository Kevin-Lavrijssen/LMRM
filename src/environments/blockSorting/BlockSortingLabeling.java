package environments.blockSorting;

import environments.ILabelingFunction;
import rewardmachines.Observation;

public class BlockSortingLabeling implements ILabelingFunction{
	
	public BlockSortingLabeling(int nTypes) {
		this.nTypes = nTypes;
		this.nPropositions = nTypes*2;
	}
	
	private int nTypes;
	private int nPropositions;

	@Override
	public Observation label(String action, int[] state) {
		if(action.equals("PickUp") && state[2]>0) {return new Observation((int) Math.pow(2, state[2]-1), nPropositions);}
		if(action.equals("Drop") && state[2]<0) {return new Observation((int) Math.pow(2, nTypes-state[2]-1), nPropositions);}
		return new Observation(0, nPropositions);
	}

	@Override
	public int getNumberOfPropositions() {
		return nPropositions;
	}

}
