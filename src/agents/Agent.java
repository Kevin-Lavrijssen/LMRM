package agents;
import java.util.ArrayList;
import environments.Environment;
import rewardmachines.*;

public class Agent {

	IRewardMachine taskModel;
	Environment e;
	int nPropositions;
	boolean automatonConstructed;
	
	ArrayList<ArrayList<Log>> trainingSet;
	
	public Agent(IRewardMachine emptyModel, Environment e, int nPropositions) {
		this.taskModel=emptyModel;
		this.e = e;
		this.nPropositions = nPropositions;
		this.automatonConstructed = false;
	}
	
	public ArrayList<ArrayList<Log>> explore(int nTraces, int nSteps) {
		
		ArrayList<ArrayList<Log>> batch = new ArrayList<ArrayList<Log>>();
		
		for (int iTrace = 0; iTrace<nTraces; iTrace++) {
			
			// Create a new empty trace
			ArrayList<Log> newTrace = new ArrayList<Log>();
			
			// Reset the environment
			e.reset();
			
			for (int iStep = 0; iStep<nSteps; iStep++) {
				
				// Execute  a step
				
				// Create a log
				
				// Append the log to the trace
				
			}
			
			// Store the trace
			
		}
		
		return batch;
		
	}
	
	public void exploit() {
		
	}
	
	private void rollBack() {
		// TODO Second phase
	}
	
	private void expandAutomaton(ArrayList<ArrayList<Log>> trainingData) {
		
	}

	public void constructAutomaton(ArrayList<ArrayList<Log>> trainingData) {
		if(this.automatonConstructed) {throw new IllegalArgumentException("Automaton already built");}
		expandAutomaton(trainingData);		
		this.automatonConstructed=true;
	}
	
}
