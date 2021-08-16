package environments.packageDelivery;

import agents.Log;
import environments.IEnvironment;
import environments.ILabelingFunction;
import environments.IRewardFunction;
import rewardmachines.Observation;

public class PackageDeliveryEnvironment implements IEnvironment{

	public PackageDeliveryEnvironment(double nondeterminism, IRewardFunction rf, ILabelingFunction lf) {
		
		actions = new String[] {"PickUp", "Drop", "North", "East", "South", "West"};
		world = new PackageDeliveryWorld(actions);
		labeling = lf;
		rewardFunction = rf;
		
	}
	
	private String[] actions;
	private PackageDeliveryWorld world;
	private ILabelingFunction labeling;
	private IRewardFunction rewardFunction;
	
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
