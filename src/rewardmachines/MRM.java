package rewardmachines;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MRM extends RewardMachine{
	
	
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
	
	public MRM() {
		super();
	}
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public void commitTransition() {
		System.out.println("Transition Committed");
		ITableEntry entry = new StandardTableEntry(pushedSource, pushedObservation, pushedDestination, pushedReward);
		table.addEntry(entry);
		clearTemporaryTransition();
	}
	
	/**
	 * {@inheritDoc}
	 */

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
	
}
