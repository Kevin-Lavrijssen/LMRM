package experiments;
import java.io.IOException;
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
	
	public Trial() throws IOException {
		
		int nPropositions = 2;
		
		// Set up environment
		String fileLocation = "C:\\Users\\Kevin\\eclipse-workspace\\LogicalMealyRewardMachines\\firstMM.csv";
		MRM task = new MRM(fileLocation);
		// System.out.println(test.toString());
		e = new Environment(task);
		
		
		// Set up logicalAgent
		IRewardMachine emptyLMRM = new LMRM();
		logicalAgent = new Agent(emptyLMRM, e, nPropositions);
		
		// Set up standardAgent
		IRewardMachine emptyMRM = new MRM();
		standardAgent = new Agent(emptyMRM, e, nPropositions);
		
	}
	
	@Override
	public void run() throws IOException {
		
		// Gather initial data by one of the agents
		ArrayList<ArrayList<Log>> trainingData = standardAgent.explore(1000, 1000);
		
		// Build the reward machines
		standardAgent.constructAutomaton(trainingData);
		logicalAgent.constructAutomaton(trainingData);
		
		
	}

}
