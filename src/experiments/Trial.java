package experiments;
import java.util.ArrayList;
import agents.Agent;
import agents.Log;
import environments.Environment;
import rewardmachines.IRewardMachine;
import rewardmachines.LMRM;
import rewardmachines.MRM;

public class Trial implements IExperiment{
	
	Environment e;
	Agent logicalAgent;
	Agent standardAgent;
	
	public Trial() {
		
		int nPropositions = 5;
		int nStates = 10;
		int maxReward  = 2;
		
		// Set up environment
		IRewardMachine task = new MRM(nStates, nPropositions, maxReward);
		e = new Environment(task);
		
		
		// Set up logicalAgent
		IRewardMachine emptyLMRM = new LMRM();
		logicalAgent = new Agent(emptyLMRM, e, nPropositions);
		
		// Set up standardAgent
		IRewardMachine emptyMRM = new MRM();
		standardAgent = new Agent(emptyMRM, e, nPropositions);
		
	}
	
	@Override
	public void run() {
		
		// Gather initial data by one of the agents TODO
		ArrayList<ArrayList<Log>> trainingData = standardAgent.explore(10, 10);
		
		// Build the reward machines TODO
		standardAgent.constructAutomaton(trainingData);
		logicalAgent.constructAutomaton(trainingData);
		
	}

}
