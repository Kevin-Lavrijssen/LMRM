package agents;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import environments.DirectEnvironment;
import environments.IEnvironment;
import exceptions.PreconditionViolatedException;
import rewardmachines.*;

public class Agent {

	RewardMachine taskModel;
	IEnvironment e;
	int nPropositions;
	boolean automatonConstructed;
	int cutOff;
	String[] actions;
	
	DataSession trainingSet;
	
	public Agent(RewardMachine emptyModel, IEnvironment e, int nPropositions, String[] actions) {
		this.taskModel=emptyModel;
		this.e = e;
		this.nPropositions = nPropositions;
		this.automatonConstructed = false;
		this.cutOff=Integer.MAX_VALUE;
		this.actions=actions;
		trainingSet = new DataSession();
	}
	
	// Debugged -> OK!
	
	public ArrayList<ArrayList<Log>> explore(int nTraces, int nSteps) {
		
		Random random = new Random();
		int maxActionIndex = actions.length;
		ArrayList<ArrayList<Log>> batch = new ArrayList<ArrayList<Log>>();
		
		for (int iTrace = 0; iTrace<nTraces; iTrace++) {
			//System.out.println("Gathering new trace:");
			// Create a new empty trace
			ArrayList<Log> newTrace = new ArrayList<Log>();
			
			// Reset the environment
			e.reset();
			
			for (int iStep = 0; iStep<nSteps; iStep++) {
				
				// Execute  a step
				Log l = e.execute(actions[random.nextInt(maxActionIndex)]);
				
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
	
	public void setCutOff(int cutOff) {
		this.cutOff=cutOff;
	}
	
	private void rollBack() {
		// TODO Second phase
	}
	
	public RewardMachine getTaskModel() {
		return this.taskModel;
	}
	
	private void expandAutomaton() throws PreconditionViolatedException {
		
		// Cut off for taking wrong branch, has to be set explicitly
		if(taskModel.getNumberOfStates()>this.cutOff) {return;}
		
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
			
		}
		
		// Create new state
		taskModel.addStateTransition(unexplained.getState(), unexplained.getObservation(), unexplained.getReward());
		trainingSet.explain(taskModel);
		
		// Recursive call
		expandAutomaton();
		return;
		
	}

	public void constructAutomaton(ArrayList<ArrayList<Log>> trainingData) throws IOException, PreconditionViolatedException {
		if(this.automatonConstructed) {throw new IllegalArgumentException("Automaton already built");}
		trainingSet.add(trainingData);
		trainingSet.reset();
		taskModel.reset();
		expandAutomaton();		
		this.automatonConstructed=true;
	}
	
	public void addData(ArrayList<ArrayList<Log>> data) {
		trainingSet.add(data);
	}
	
}
