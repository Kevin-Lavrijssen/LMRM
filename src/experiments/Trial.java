package experiments;
import java.io.IOException;
import java.util.ArrayList;
import agents.Agent;
import agents.Log;
import environments.Environment;
import exceptions.BehaviourUndefinedException;
import exceptions.PreconditionViolatedException;
import rewardmachines.RewardMachine;
import rewardmachines.LMRM;
import rewardmachines.MRM;

public class Trial implements IExperiment{
	
	Environment e;
	Agent logicalAgent;
	Agent standardAgent;
	
	public Trial() throws IOException {
		
		int nPropositions = 2;
		int nStates = 3;
		int maxReward = 2;
		
		// Set up environment
		//String fileLocation = "C:\\Users\\Kevin\\eclipse-workspace\\LogicalMealyRewardMachines\\testcases\\tc1\\MRM.csv";
		//MRM task = new MRM(fileLocation);
		// System.out.println(test.toString());
		//e = new Environment(task);
		
		// Set up environment
		//String fileLocation = "C:\\Users\\Kevin\\eclipse-workspace\\LogicalMealyRewardMachines\\testcases\\tc1\\MRM.csv";
		MRM task = new MRM(nStates, nPropositions, maxReward);
		System.out.println(task.toString());
		e = new Environment(task);
				
		
		// Set up logicalAgent
		RewardMachine emptyLMRM = new LMRM();
		logicalAgent = new Agent(emptyLMRM, e, nPropositions);
		
		// Set up standardAgent
		RewardMachine emptyMRM = new MRM();
		standardAgent = new Agent(emptyMRM, e, nPropositions);
		
	}
	
	@Override
	public void run() throws IOException, BehaviourUndefinedException, PreconditionViolatedException {
		
		// Gather initial data by one of the agents
		ArrayList<ArrayList<Log>> trainingData = standardAgent.explore(100, 10);
		
		// Build the reward machines
		standardAgent.constructAutomaton(trainingData);
		// logicalAgent.constructAutomaton(trainingData);
		
		
	}

}
