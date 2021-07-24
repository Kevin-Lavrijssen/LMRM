package experiments;
import java.io.IOException;
import java.util.ArrayList;
import agents.Agent_Standard;
import agents.Log;
import environments.DirectEnvironment;
import exceptions.PreconditionViolatedException;
import rewardmachines.RewardMachine;
import rewardmachines.Evaluator;
import rewardmachines.LMRM;
import rewardmachines.MRM;

public class Trial implements IExperiment{
	
	DirectEnvironment e;
	Agent_Standard logicalAgent;
	Agent_Standard standardAgent;
	MRM task;
	int nPropositions;
	
	public Trial() throws IOException {
		
		nPropositions = 5;
		int nStates = 2;
		int maxReward = 2;
		
		// Set up environment
		//String fileLocation = "C:\\Users\\Kevin\\eclipse-workspace\\LogicalMealyRewardMachines\\testcases\\tc1\\MRM.csv";
		//MRM task = new MRM(fileLocation);
		// System.out.println(test.toString());
		//e = new Environment(task);
		
		// Set up environment
		//String fileLocation = "C:\\Users\\Kevin\\eclipse-workspace\\LogicalMealyRewardMachines\\testcases\\tc1\\MRM.csv";
		task = new MRM(nStates, nPropositions, maxReward);
		System.out.println(task.toString());
		e = new DirectEnvironment(task, nPropositions);
				
		
		// Set up logicalAgent
		RewardMachine emptyLMRM = new LMRM();
		logicalAgent = new Agent_Standard(emptyLMRM, e, nPropositions, e.getActions());
		
		// Set up standardAgent
		RewardMachine emptyMRM = new MRM();
		standardAgent = new Agent_Standard(emptyMRM, e, nPropositions, e.getActions());
		
	}
	
	@Override
	public void run() throws IOException, PreconditionViolatedException {
		
		// Gather initial data by one of the agents
		ArrayList<ArrayList<Log>> trainingData = standardAgent.explore(1000, 20);
		
		// Build the reward machines
		logicalAgent.constructAutomaton(trainingData);
		standardAgent.constructAutomaton(trainingData);


		
		
		Evaluator evaluator = new Evaluator(task, standardAgent.getTaskModel());
		evaluator.evaluate(100, 100, nPropositions);
		
		System.out.println();
		System.out.println("Manhattan distance: " + evaluator.getManhattanDistance());
		System.out.println("Euclidean distance: " + evaluator.getEuclideanDistance());
		System.out.println("Mispredictions: "+evaluator.getMispredictions());
		System.out.println("Improvement in number of states: "+evaluator.getStateImprovement());
		System.out.println("Improvement in number of transitions: "+evaluator.getTransitionImprovement());
		
		Evaluator levaluator = new Evaluator(task, logicalAgent.getTaskModel());
		levaluator.evaluate(100, 100, nPropositions);
		
		System.out.println();
		System.out.println("Manhattan distance: " + levaluator.getManhattanDistance());
		System.out.println("Euclidean distance: " + levaluator.getEuclideanDistance());
		System.out.println("Mispredictions: "+levaluator.getMispredictions());
		System.out.println("Improvement in number of states: "+levaluator.getStateImprovement());
		System.out.println("Improvement in number of transitions: "+levaluator.getTransitionImprovement());
		
	}

	@Override
	public void reportResults(String result) {
		// TODO Auto-generated method stub
		
	}

}
