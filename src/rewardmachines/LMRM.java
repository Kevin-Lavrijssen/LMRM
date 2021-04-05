package rewardmachines;

import java.io.IOException;

public class LMRM implements IRewardMachine{

	private int nStates;
	
	private int currentState;
	
	private TransitionRewardTable table;
	
	public LMRM() {
		nStates = 1;
		currentState = 0;
		// this.nPropositions = nPropositions;
		table = new TransitionRewardTable();
	}
	
	/**
	 * This method executes a given observation on the machine. 
	 * => Current state is updated
	 * => Reward is emitted
	 */
	
	@Override
	public int execute(Observation observation) throws IOException {
		
		if(this.pushedSource == this.currentState  && this.pushedObservation.equals(observation)) {
			this.currentState = this.pushedDestination;
			return this.pushedReward;
		}
		
		ITableEntry entry = table.get(currentState, observation);
		this.currentState = entry.getDestination();
		return entry.getReward();
	}

	@Override
	public void reset() {
		currentState = 0;		
	}

	/** 
	 * Set of temporary variables to track a transient transition
	 */
	
	int pushedSource;
	Observation pushedObservation;
	int pushedDestination;
	int pushedReward;
	
	@Override
	public void pushTransition(int source, Observation o, int destination, int reward) {
		this.pushedSource = source;
		this.pushedObservation = o;
		this.pushedDestination = destination;
		this.pushedReward = reward;
	}

	@Override
	public void commitTransition() {
		// See if there is a transition to merge
		ITableEntry entry = table.get(pushedSource, pushedDestination, pushedReward);
		if (entry!=null) {entry.merge(pushedObservation);}
		
		// Else create a new transition
		table.addEntry(new LogicalTableEntry(pushedSource, pushedObservation, pushedDestination, pushedReward));
	}

	@Override
	public void addStateTransition(int source, Observation o, int reward) {
		// Create new state
		this.nStates += 1;
		
		// Create transition to new state
		ITableEntry entry = new LogicalTableEntry(source, o, nStates-1, reward);
		table.addEntry(entry);
	}

	
	
}
