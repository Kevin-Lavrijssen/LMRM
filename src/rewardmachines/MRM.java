package rewardmachines;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MRM implements IRewardMachine{

	/**
	 * ###############################################################################################################################
	 * #############################   Part of the class that specifies a full automaton  ############################################
	 * ###############################################################################################################################
	 */
	
	// TODO: Might not be necessary: private int nPropositions;
	
	private int nStates;
	
	private int currentState;
	
	private TransitionRewardTable table;
	
	public MRM(int nStates, int nPropositions, int maxReward) {
		// Create the new transition table
		table = new TransitionRewardTable();
		
		Random r = new Random();
		
		// Randomly complete the transition table
		for (int source = 0; source<nStates; source++) {
			for (int interpretation=0; interpretation<Math.pow(2, nPropositions); interpretation++) {
				Observation observation = new Observation(interpretation, nPropositions);
				int destination = r.nextInt(nStates);
				int reward = r.nextInt(maxReward);
				table.addEntry(new StandardTableEntry(source, observation, destination, reward));
				
			}
		}
		
		// Make sure the automaton is fully connected
		table.ensureConnection(nStates);
		
		// this.nPropositions=nPropositions;
		this.nStates=nStates;
		this.currentState=0;
		
	}
	
	public MRM(String fileLocation) throws IOException {
		this.table = new TransitionRewardTable();
		
		try (BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        
		    	System.out.println(line);
		    	String[] values = line.split(",");
		        
		        int source = Integer.parseInt(values[0]);
		        Observation observation = new Observation(values[1]);
		        int destination = Integer.parseInt(values[2]);
		        int reward = Integer.parseInt(values[3]);
		        
		        StandardTableEntry e = new StandardTableEntry(source, observation, destination, reward);
		        table.addEntry(e);
		    }
		}
	}

	/**
	 * This method executes a given observation on the machine. 
	 * => Current state is updated
	 * => Reward is emitted
	 */
	
	@Override
	public int execute(Observation observation) throws IOException {
		
		if(this.pushedSource == this.currentState  && this.pushedObservation!=null && this.pushedObservation.equals(observation)) {
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
	 * ###############################################################################################################################
	 * ##################################   Part of the class aimed at learning automata  ############################################
	 * ###############################################################################################################################
	 */
	
	public MRM() {
		nStates = 1;
		currentState = 0;
		// this.nPropositions = nPropositions;
		table = new TransitionRewardTable();
	}
	
	public int getNumberOfStates() {
		return this.nStates;
	}

	/** 
	 * Set of temporary variables to track a transient transition
	 */
	
	int pushedSource = -1;
	Observation pushedObservation = null;
	int pushedDestination = -1;
	int pushedReward = -1;
	
	@Override
	public void pushTransition(int source, Observation o, int destination, int reward) {
		this.pushedSource = source;
		this.pushedObservation = o;
		this.pushedDestination = destination;
		this.pushedReward = reward;
	}

	@Override
	public void commitTransition() {
		System.out.println("Transition Committed");
		ITableEntry entry = new StandardTableEntry(pushedSource, pushedObservation, pushedDestination, pushedReward);
		table.addEntry(entry);
		clearTemporaryTransition();
	}

	@Override
	public void addStateTransition(int source, Observation o, int reward) {
		System.out.println("New state constructed");
		// Create new state
		this.nStates += 1;
		
		// Create transition to new state
		ITableEntry entry = new StandardTableEntry(source, o, nStates-1, reward);
		table.addEntry(entry);
		clearTemporaryTransition();
	}
	
	private void clearTemporaryTransition() {
		this.pushedSource = -1;
		this.pushedObservation = null;
		this.pushedDestination = -1;
		this.pushedReward = -1;		
	}

	@Override
	public void setState(int currentState) {
		this.currentState=currentState;
	}

	@Override
	public int getCurrentState() {
		return currentState;
	}
	
	@Override 
	public String toString() {
		return table.toString();
	}
	
}
