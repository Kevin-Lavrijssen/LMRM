package agents;
import java.util.ArrayList;
import java.util.Random;
import environments.IEnvironment;
import exceptions.PreconditionViolatedException;
import experiments.IExperiment;
import rewardmachines.*;

public class Agent_DivideAndConquer {

	RewardMachine taskModel;
	IEnvironment e;
	int nPropositions;
	String[] actions;
	int daq;
	boolean taskLearned;
	
	IExperiment experiment;
	
	DataSession trainingSet;
	
	int dataSurface;
	
	public Agent_DivideAndConquer(RewardMachine emptyModel, IEnvironment e, int nPropositions, String[] actions, int daq, IExperiment experiment) {
		this.taskModel=emptyModel;
		this.e = e;
		this.nPropositions = nPropositions;
		this.actions=actions;
		this.daq = daq;
		this.taskLearned=false;
		trainingSet = new DataSession();
		this.experiment=experiment;
		this.dataSurface=0;
		this.currentExecutionTime=0;
		this.dataSurface=0;
	}
	
	public void learnTask() throws PreconditionViolatedException {
		
		ArrayList<ArrayList<Log>> initialSample = explore(null);
		trainingSet.add(initialSample);
		int c=0;
		
		while(!taskLearned && c<4) {
			long startTime = System.nanoTime();
			expandAutomaton(0);
			long executionTime = System.nanoTime()-startTime;
			currentExecutionTime+=executionTime;
			report();
			String[] pathToBorder = trainingSet.getPathToBorder();
			ArrayList<ArrayList<Log>> extension = explore(pathToBorder);
			trainingSet.add(extension);
			if(taskModel.getNumberOfStates()>100) {return;}
			c++;
		}
		
	}
	
	
	long currentExecutionTime;
	
private void report() {
		String result = "";
		result+=currentExecutionTime+",";
		result+=taskDistance(10,10000)+",";
		result+=dataSurface+",";
		result+=taskModel.getTableSize()+",";
		result+=taskModel.getType()+"\n";
		experiment.reportResults(result);
	}

private double taskDistance(int nTraces, int nSteps) {
	double totalDistance = 0;
	Random random = new Random();
	
	for (int i=0; i<nTraces; i++) {
		
		int[] expectedTrace = new int[nSteps];
		int[] actualTrace = new int[nSteps];
		
		e.reset();
		taskModel.reset();
		
		for(int step = 0; step<nSteps; step++) {
			Log execution = e.execute(actions[random.nextInt(actions.length)]);
			expectedTrace[step] = execution.getReward();
			
			// Undefined == -1
			actualTrace[step] = taskModel.execute(execution.getObservation());

		}
		
		totalDistance+=tools.Distance.minkowski(expectedTrace, actualTrace, 2);
		
	}
	
	return totalDistance;
	
}

private void expandAutomaton(int statesAdded) throws PreconditionViolatedException {
	
		// See if model explains the data
		if(trainingSet.explained()) {taskLearned=true; System.out.println("Task learned");return;}
		
		if(statesAdded>daq) {return;}
		
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
				expandAutomaton(statesAdded);
				return;
				
			}
			
		}
		
		// Create new state
		taskModel.addStateTransition(unexplained.getState(), unexplained.getObservation(), unexplained.getReward());
		trainingSet.explain(taskModel);
		
		// Recursive call
		expandAutomaton(statesAdded+1);
		return;
		
	}

	public ArrayList<ArrayList<Log>> explore(String[] prefix) {
		
		Random random = new Random();
		int maxActionIndex = actions.length;
		ArrayList<ArrayList<Log>> batch = new ArrayList<ArrayList<Log>>();
		
		int nSteps = 20;
		
		// Create a new empty trace
		ArrayList<Log> newTrace = new ArrayList<Log>();
		

		
		// Follow the path for the length of the prefix
		if(prefix!=null) {followPath(prefix); dataSurface+=prefix.length;}
		
		for (int i=0; i<10; i++) {
		
			// Exhaustively sample 3 deep
			for (String a1:actions) {
				for (String a2:actions) {
					
					// Reset the environment
					e.reset();
					
					Log l1 = e.execute(a1); Log l2 = e.execute(a2);
					newTrace.add(l1); newTrace.add(l2);
					dataSurface+=2;
					
					for (int iStep = 0; iStep<nSteps; iStep++) {
						
						// Execute  a step
						Log l = e.execute(actions[random.nextInt(maxActionIndex)]);
						// Append the log to the trace
						newTrace.add(l);
						dataSurface+=1;
						
					}
				}
					
				// Store the trace
				batch.add(newTrace);
				
			}
		}
		
		return batch;
		
	}
	
	public void followPath(String[] path){
		for(int i=0; i<path.length; i++) {
			e.execute(path[i]);
		}
		
	} 

}
