package agents;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
	
	public ArrayList<ArrayList<Log>> explore(int nTraces, int nSteps) throws IOException {
		
		ArrayList<ArrayList<Log>> batch = new ArrayList<ArrayList<Log>>();
		
		for (int iTrace = 0; iTrace<nTraces; iTrace++) {
			
			Random random = new Random();
			int maxIntValue = (int) Math.pow(2, nPropositions);
			
			// Create a new empty trace
			ArrayList<Log> newTrace = new ArrayList<Log>();
			
			// Reset the environment
			e.reset();
			
			for (int iStep = 0; iStep<nSteps; iStep++) {
				
				// Execute  a step
				Observation o = new Observation(random.nextInt(maxIntValue), nPropositions);
				int r = e.execute(o);
				
				// Create a log
				Log l = new Log(o, r);
				
				// Append the log to the trace
				newTrace.add(l);
				
			}
			
			// Store the trace
			batch.add(newTrace);
			
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
