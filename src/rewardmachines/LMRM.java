package rewardmachines;


public class LMRM extends RewardMachine{
	
	public LMRM() {
		super();
	}
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public void commitTransition() {
		// See if there is a transition to merge
		ITableEntry entry = table.get(pushedSource, pushedDestination, pushedReward);
		if (entry!=null) {entry.merge(pushedObservation);}
		
		// Else create a new transition
		table.addEntry(new LogicalTableEntry(pushedSource, pushedObservation, pushedDestination, pushedReward));
		clearTemporaryTransition();
	}
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public void addStateTransition(int source, Observation o, int reward) {
		// Create new state
		nStates+=1;
		
		// Create transition to new state
		ITableEntry entry = new LogicalTableEntry(source, o, nStates-1, reward);
		table.addEntry(entry);
		clearTemporaryTransition();
	}
}
