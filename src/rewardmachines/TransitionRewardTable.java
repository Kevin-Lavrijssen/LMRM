package rewardmachines;

import java.util.ArrayList;
import java.util.Random;

import exceptions.BehaviourUndefinedException;


class TransitionRewardTable {

	ArrayList<ITableEntry> table;
	
	TransitionRewardTable() {
		table = new ArrayList<ITableEntry>();
	}
	
	/**
	 * Check every entry in the table and look for the one that applies to the given state reward pair.
	 * => For every state action pair there is always at most one entry that applies
	 * 
	 * @param source
	 * The state in which the observation is done.
	 * 
	 * @param o
	 * The state of the world observed.
	 * 
	 * @return
	 * The next state to which the transition leads, the reward obtained.
	 * 
	 * @throws BehaviourUndefinedException
	 * Exception thrown when there is no entry that satisfies the input. (i.e. transition undefined)
	 * 
	 */
	
	ITableEntry get(int source, Observation o) throws BehaviourUndefinedException {
		for(ITableEntry entry : table) {
			if(entry.evaluate(source, o)) {return entry;}
		}
		throw new BehaviourUndefinedException("The state/observation pair is not included in the table");
	}
	
	/**
	 * Method that return the entry with the given source, destination and reward if it exists. Else returns null.
	 * 
	 * @param source		| The source of the entry.
	 * @param destination	| The destination of the entry.
	 * @param reward		| The reward of the entry.
	 * @return				| The corresponding entry if it exists, else null.
	 */
	
	ITableEntry get(int source, int destination, int reward) {
		for (ITableEntry entry : table) {
			if(entry.getSource()==source && entry.getDestination()==destination && entry.getReward()==reward) {
				return entry;
			}
		}
		
		return null;
		
	}
	
	/**
	 * Method that adds the given entry to the transition/reward table.
	 * 
	 * @param entry	| ITableEntry that is added to the table.
	 */
	
	void addEntry(ITableEntry entry) {
		table.add(entry);
	}
	
	/**
	 * Make sure every state is the destination of at least one transition that comes from state other than itself.
	 * @param nStates
	 */
	
	void ensureConnection(int nStates) {
		int tableSize = table.size();
		for (int state = 0; state<nStates; state++) {
			while(true) {
				Random r = new Random();
				int index = r.nextInt(tableSize);
				ITableEntry entry = table.get(index);
				if(entry.getSource()!=state) {entry.setDestination(state); break;}
			}
		}		
	}
	
	/**
	 * Displays the table as a string by calling toString on each of its entries.
	 */
	
	public String toString() {
		String me = "";
		for (ITableEntry e:table) {
			me+=e.toString();
		}
		return me;
	}
	
	/**
	 * Method that returns the number of entries in the table.
	 * 
	 * @return	Number of entries in the table. 
	 */
	
	public int getSize() {
		return table.size();
	}
	
}
