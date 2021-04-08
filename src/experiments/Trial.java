package experiments;
import java.io.IOException;
import java.util.ArrayList;
import agents.Agent;
import agents.Log;
import environments.Environment;
import rewardmachines.RewardMachine;
import rewardmachines.LMRM;
import rewardmachines.MRM;

public class Trial implements IExperiment{
	
	Environment e;
	Agent logicalAgent;
	Agent standardAgent;
	
	public Trial() throws IOException {
		
		int nPropositions = 2;
		
		// Set up environment
		String fileLocation = "C:\\Users\\Kevin\\eclipse-workspace\\LogicalMealyRewardMachines\\firstMM.csv";
		MRM task = new MRM(fileLocation);
		// System.out.println(test.toString());
		e = new Environment(task);
		
		
		// Set up logicalAgent
		RewardMachine emptyLMRM = new LMRM();
		logicalAgent = new Agent(emptyLMRM, e, nPropositions);
		
		// Set up standardAgent
		RewardMachine emptyMRM = new MRM();
		standardAgent = new Agent(emptyMRM, e, nPropositions);
		
	}
	
	@Override
	public void run() throws IOException {
		
		// Gather initial data by one of the agents
		ArrayList<ArrayList<Log>> trainingData = standardAgent.explore(10, 100);
		
		// Build the reward machines
		standardAgent.constructAutomaton(trainingData);
		// logicalAgent.constructAutomaton(trainingData);
		
		
	}

}
