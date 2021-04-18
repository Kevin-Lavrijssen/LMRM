package rewardmachines;
import java.io.IOException;

import exceptions.BehaviourUndefinedException;

public abstract class RewardMachine {


	/////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////// Reward machine ////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Construct an empty reward machine that can be completed by standard or logical transitions.
	 */
	
	RewardMachine(){
		nStates = 1;
		currentState=0;
		table = new TransitionRewardTable();
	}
	
	/**
	 * The number of states that are currently part of the machine
	 */
	
	protected int nStates;
	
	/**
	 * Returns the number of states currently in the model.
	 * 
	 * @return	|The number of states currently in the model.
	 */
	
	public int getNumberOfStates() {
		return nStates;
	}
	
	/** 
	 * The current state of the machine providing the context to evaluate the next observation.
	 */
	
	protected int currentState;
	
	/**
	 * Returns the current state of the model
	 * 
	 * @return | The current state of the model.
	 */
	
	public int getCurrentState() {
		return currentState;
	}
	
	/**
	 * Sets the current state of the model to the given state.
	 * 
	 * @param currentState	|The new current state of the reward machine.
	 * 
	 */
	
	public void setState(int currentState) {
		this.currentState=currentState;
	}
	
	/**
	 * Transition/reward table that constitutes the machine and consists of ITableEntry objects. 
	 * Both StandardTableEntry and LogicalTableEntry implement this interface.
	 */
	
	protected TransitionRewardTable table;
	
	/**
	 * Returns the size of the table
	 */
	
	public int getTableSize() {
		return table.getSize();
	}
	
	/**
	 * Prints a string representation of the machine.
	 */
	
	public String toString() {
		return table.toString();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////// Transient transition /////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/** 
	 * Set of temporary variables to track a transient transition
	 */
	
	int pushedSource = -1;
	Observation pushedObservation = null;
	int pushedDestination = -1;
	int pushedReward = -1;
	
	/**
	 * Method to push a new transient transition. This transition can be made permanent by the commitTransition()
	 * method.
	 * 
	 * @param source		| The start state of the transient transition.
	 * @param o				| The observation triggering the transient transition.
	 * @param destination	| The destination of the transient transition.
	 * @param reward		| The reward emitted by the transient transition.
	 */
	
	public void pushTransition(int source, Observation o, int destination, int reward) {
		this.pushedSource = source;
		this.pushedObservation = o;
		this.pushedDestination = destination;
		this.pushedReward = reward;
	}
	
	/**
	 * Method to clear the transient transition.
	 */
	
	void clearTemporaryTransition() {
		this.pushedSource = -1;
		this.pushedObservation = null;
		this.pushedDestination = -1;
		this.pushedReward = -1;		
	}
	
	/**
	 * Method to make the transient transition permanent. This method is implemented by the concrete
	 * reward machines because the ITableEntry that is created is different for the different types
	 * of machines.
	 */
	
	public abstract void commitTransition();
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////// Operations //////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Method that executes the machine for a given observation. The corresponding entry is retrieved
	 * from the table and the current state of the machine is updated. The method returns the reward 
	 * emitted by the transition. The method also takes into account the transient transition as if 
	 * it were a permanent transition.
	 * 
	 * @param observation	| The observation to execute on the machine.
	 * @return				| The reward resulting from taking the transition.
	 * @throws BehaviourUndefinedException 	| Exception thrown when the behavior for the current state and the provided
	 * 										| input is undefined.
	 */
	
	public int execute(Observation observation) throws BehaviourUndefinedException {
		
		if(this.pushedSource == this.currentState  && this.pushedObservation!=null && this.pushedObservation.equals(observation)) {
			this.currentState = this.pushedDestination;
			return this.pushedReward;
		}
		
		try {
			ITableEntry entry = table.get(currentState, observation);
			this.currentState = entry.getDestination();
			return entry.getReward();
		} catch (BehaviourUndefinedException e) {return 0;}
		
		
	}
	
	public boolean isDefined(Observation observation) {
		if(this.pushedSource == this.currentState  && this.pushedObservation!=null && this.pushedObservation.equals(observation)) {
			return true;
		}
		
		try {
		ITableEntry entry = table.get(currentState, observation);
		return true;
		} catch (BehaviourUndefinedException e) {return false;}
	}

	/**
	 * Sets the reward machine back to the initial state.
	 */
	
	public void reset() {
		currentState = 0;		
	}
	
	/**
	 * Method that creates a new state and a transition to that new state. The method is implemented by the
	 * concrete reward machines because the type of the ITableEntry will differ for each of the concrete
	 * types.
	 * 
	 * @param source	| The source of the new transition
	 * @param o			| The observation triggering the new transition.
	 * @param reward	| The reward emitted by the new transition.
	 */
	
	public abstract void addStateTransition(int source, Observation o, int reward);

}
