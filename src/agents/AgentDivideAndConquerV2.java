package agents;

import java.util.ArrayList;
import java.util.Random;

import environments.IEnvironment;
import exceptions.PreconditionViolatedException;
import rewardmachines.RewardMachine;

public class AgentDivideAndConquerV2 {

	RewardMachine taskModel;
	IEnvironment e;
	int nPropositions;
	String[] actions;
	ArrayList<String[]> borders;
	
	DataSession trainingSet;
	
	public AgentDivideAndConquerV2(RewardMachine emptyModel, IEnvironment e, int nPropositions, String[] actions) {
		this.taskModel=emptyModel;
		this.e = e;
		this.nPropositions = nPropositions;
		this.actions=actions;
	}
	
	public void learnAutomaton(int batchSize, int suffixLength, int cutOff) throws PreconditionViolatedException {
		
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
				
				//System.out.println("Just expanding border: "+borders.size());
				
				// Sample training data for the new part
				if(suffixLength==1) {trainingSet = new DataSession(sample(prefix, a1));}
				else{trainingSet = new DataSession(sample(prefix, a1, batchSize, suffixLength));}
				
				// Set the sampled data to the point of learning
				trainingSet.explain(taskModel);
				
				// Run the algorithm
				expandAutomaton();
			}
			
		}
		
	}

	private ArrayList<ArrayList<Log>> sample(String[] prefix, String a1, int batchSize, int suffixLength) {

		Random random = new Random();
		
		ArrayList<ArrayList<Log>> batch = new ArrayList<ArrayList<Log>>();
		
		for(int t = 0; t<batchSize; t++) {
			
			ArrayList<Log> trace = new ArrayList<Log>();
			
			// Reset environment
			e.reset();
			
			// Walk up to the border
			for(int action=0; action<prefix.length; action++) {
				trace.add(e.execute(prefix[action]));
			}
			
			// Execute transition to learn
			trace.add(e.execute(a1));
			
			// Sample random validation suffix
			for(int step=0; step<suffixLength; step++) {
				trace.add(e.execute(actions[random.nextInt(actions.length)]));
			}
			
			batch.add(trace);
			
		}
		
		
		return batch;
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

	public RewardMachine getTaskModel() {
		return taskModel;
	}

}
