package rewardmachines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


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
	 * @throws IOException
	 * Exception thrown when there is no entry that satisfies the input. (i.e. transition undefined)
	 * 
	 */
	
	ITableEntry get(int source, Observation o) throws IOException {
		for(ITableEntry entry : table) {
			if(entry.evaluate(source, o)) {return entry;}
		}
		throw new IOException("The state/observation pair is not included in the table");
	}
	
	ITableEntry get(int source, int destination, int reward) {
		for (ITableEntry entry : table) {
			if(entry.getSource()==source && entry.getDestination()==destination && entry.getReward()==reward) {
				return entry;
			}
		}
		
		return null;
		
	}
	
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
	
	public String toString() {
		String me = "";
		for (ITableEntry e:table) {
			me+=e.toString();
		}
		return me;
	}
	
}
