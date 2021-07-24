package agents;

import java.util.ArrayList;
import java.util.Random;

import environments.IEnvironment;
import exceptions.PreconditionViolatedException;
import rewardmachines.Observation;
import rewardmachines.RewardMachine;

public class AgentDivideAndConquerV3 {

	RewardMachine taskModel;
	IEnvironment e;
	int nPropositions;
	String[] actions;
	ArrayList<String[]> borders;
	
	DataSession trainingSet;
	ArrayList<ArrayList<Log>> counterExamples;
	
	public AgentDivideAndConquerV3(RewardMachine emptyModel, IEnvironment e, int nPropositions, String[] actions) {
		this.taskModel=emptyModel;
		this.e = e;
		this.nPropositions = nPropositions;
		this.actions=actions;
	}
	
	public void learnAutomaton(int nTracesAccuracy, int nStepsAccuracy, int cutOff) throws PreconditionViolatedException {
		
		while(true) {
			
			// Add way of calculating border
			// Border can be calculated by seeing if for every state, every transition is explained
			
			// You can get the prefix from the counterexample and trace from there
			
			completeAutomaton(cutOff);
			counterExamples = gatherCounterExamples(nTracesAccuracy, nStepsAccuracy);
			
			if(counterExamples.isEmpty()) {break;}
			else{processCounterExamples();}
			
		}
		
	}
	
	private void processCounterExamples() {
		
		for(ArrayList<Log> counterexample:counterExamples) {
			processCounterExample(counterexample);
		}
		
	}

	private void processCounterExample(ArrayList<Log> counterexample) {
		
		taskModel.reset();
		for(int i=0; i<counterexample.size()-1; i++) {
			
		}
		
	}

	private ArrayList<ArrayList<Log>> gatherCounterExamples(int nTracesAccuracy, int nStepsAccuracy) {
		
		ArrayList<ArrayList<Log>> batch =  new ArrayList<ArrayList<Log>>();
		
		Random random =  new Random();
		
		for (int trace=0; trace<nTracesAccuracy; trace++) {
			ArrayList<Log> newTrace = new ArrayList<Log>();
			e.reset();
			taskModel.reset();
			for (int step=0; step<nStepsAccuracy; step++) {
				String nextAction = actions[random.nextInt(actions.length)];
				int expectedReward = taskModel.execute(new Observation(nextAction));
				Log actual = e.execute(nextAction);
				
				// Add the new observation reward pair 
				newTrace.add(actual);
				
				// If the expected reward does not match the actual reward; the trace is added to the batch as a counter example
				if(actual.getReward()!=expectedReward) {batch.add(newTrace); break;}
			}
		}
		
		return batch;
		
	}

	private void completeAutomaton(int cutOff) throws PreconditionViolatedException {
		// Initialize borders to the empty string
		borders = new ArrayList<String[]>();
		borders.add(new String[] {});

		while(true) {

			if(taskModel.getNumberOfStates()>cutOff) {break;}
			
			if(borders.isEmpty()) {break;}
		
			String[] prefix = borders.get(0);

			//System.out.println("About to pop next border: "+borders.size());
			borders.remove(prefix);
			//System.out.println("Border popped: "+borders.size());
			
			for(String a1:actions) {
				
				// Sample training data for the new part
				trainingSet = new DataSession(sample(prefix, a1));
				
				// Set the sampled data to the point of learning
				trainingSet.explain(taskModel);
				
				// Run the algorithm
				expandAutomaton();
			}
			
		}
				
	}
	
	private ArrayList<ArrayList<Log>> sample(String[] prefix, String a1) {
		
		ArrayList<ArrayList<Log>> batch = new ArrayList<ArrayList<Log>>();
		
		for(String stateSuccessor:actions) {
			
			ArrayList<Log> trace = new ArrayList<Log>();
			
			// Reset environment
			e.reset();
			
			// Walk up to the border
			for(int action=0; action<prefix.length; action++) {
				trace.add(e.execute(prefix[action]));
			}
			
			// Execute transition to learn
			trace.add(e.execute(a1));
			
			// Execute the state successor
			trace.add(e.execute(stateSuccessor));
			
			batch.add(trace);
			
		}
		
		
		return batch;
	}
	
	private void expandAutomaton() throws PreconditionViolatedException {
		
		// See if model explains the data
		// if(trainingSet.explained()) {return;}
		
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
				//trainingSet.explain(taskModel);
				
				// Recursive call
				// expandAutomaton();
				return;
				
			}
			
		}
		
		// Create new state
		taskModel.addStateTransition(unexplained.getState(), unexplained.getObservation(), unexplained.getReward());
		//trainingSet.explain(taskModel);
		
		// Add new border
		borders.add(unexplained.getPrefix());
		//System.out.println("Border Size = "+borders.size());
		
		
		
		// Recursive call
		// expandAutomaton();
		return;
		
	}

	
	
}
