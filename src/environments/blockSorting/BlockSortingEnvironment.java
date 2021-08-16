package environments.blockSorting;

import agents.Log;
import environments.IEnvironment;
import rewardmachines.Observation;

public class BlockSortingEnvironment implements IEnvironment{

	public BlockSortingEnvironment(int nTypes, int nBinsPerType, int nSourcesPerType, double nondeterminism, int xSize, int ySize) {
		
		actions = new String[] {"PickUp", "Drop", "North", "East", "South", "West"};
		world = new BlockSortingWorld(nTypes, nBinsPerType, nSourcesPerType, nondeterminism, xSize, ySize, actions);
		labeling = new BlockSortingLabeling(nTypes);
		rewardFunction = new BlockSortingRewardFunction(nTypes);
	}
	
	private String[] actions;
	private BlockSortingWorld world;
	private BlockSortingLabeling labeling;
	private BlockSortingRewardFunction rewardFunction;
	
	
	@Override
	public Log execute(String action) {
		int[] newState = world.execute(action);
		Observation observation = labeling.label(action, newState);
		int reward = rewardFunction.execute(observation);
		return new Log(action, observation, reward);
				
	}
	
	@Override
	public void reset() {
		world.reset();
		rewardFunction.reset();
	}

	@Override
	public String[] getActions() {
		return actions;
	}

	@Override
	public int getNumberOfPropositions() {
		return labeling.getNumberOfPropositions();
	}
	
}
