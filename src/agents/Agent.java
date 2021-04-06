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
	
	DataSession trainingSet;
	
	public Agent(IRewardMachine emptyModel, Environment e, int nPropositions) {
		this.taskModel=emptyModel;
		this.e = e;
		this.nPropositions = nPropositions;
		this.automatonConstructed = false;
		trainingSet = new DataSession();
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
	
	private void expandAutomaton() throws IOException {
		
		// See if model explains the data
		if(trainingSet.explained()) {return;}
		
		// Get next unexplained
		Unexplained unexplained = trainingSet.getNextUnexplained();
		
		// Try to extend 
		for (int state = 0; state<taskModel.getNumberOfStates(); state++) {
			
			// Push new transition
			taskModel.pushTransition(unexplained.getState(), unexplained.getObservation(), state, unexplained.getReward());
			
			// Check consistency with data
			if(trainingSet.consistent(taskModel)) {
				
				// Commit changes 
				taskModel.commitTransition();
				
				// Explain data
				trainingSet.explain(taskModel);
				
				// Recursive call
				expandAutomaton();
				return;
				
			}
		
		// Create new state
		taskModel.addStateTransition(unexplained.getState(), unexplained.getObservation(), unexplained.getReward());
		
		// Recursive call
		expandAutomaton();
		return;
			
		}
		
	}

	public void constructAutomaton(ArrayList<ArrayList<Log>> trainingData) throws IOException {
		if(this.automatonConstructed) {throw new IllegalArgumentException("Automaton already built");}
		trainingSet.add(trainingData);
		trainingSet.reset();
		expandAutomaton();		
		this.automatonConstructed=true;
	}
	
	public void addData(ArrayList<ArrayList<Log>> data) {
		trainingSet.add(data);
	}
	
}
