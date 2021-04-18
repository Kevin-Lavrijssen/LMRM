package experiments;
import java.io.IOException;
import java.util.ArrayList;

import agents.Agent;
import agents.Log;
import environments.Environment;
import exceptions.BehaviourUndefinedException;
import exceptions.PreconditionViolatedException;
import rewardmachines.LMRM;
import rewardmachines.MRM;
import rewardmachines.RewardMachine;

public class InitialEvaluationExperiment implements IExperiment{

	@Override
	public void run() throws IOException, BehaviourUndefinedException, PreconditionViolatedException {
		
		// Number of runs for each setting
		int nRuns = 10;
		
		// Parameters for the test of increasing states
		int states_maxStates = 10;
		int states_nPropositions = 2;
		
		// Parameters for the test of increasing number of propositions
		int propositions_maxPropositions = 5;
		int propositions_nStates = 3;
	
		// Run for the different states
		for (int run=0; run<nRuns;run++) {
			for(int states = 2; states<states_maxStates; states++) {
				String results = individualExperiment(states, states_nPropositions, 2);
				// Add results to file
			}
		}
		
		// Run for the different states
		for (int run=0; run<nRuns;run++) {
			for(int nPropositions = 1; nPropositions<propositions_maxPropositions; nPropositions++) {
				String results = individualExperiment(propositions_nStates, nPropositions, 2);
				// Add results to file
			}
		}
		
	}

	
	public String individualExperiment(int states, int nPropositions, int maxReward) throws IOException, PreconditionViolatedException, BehaviourUndefinedException{
		
		MRM task = new MRM(states, nPropositions, maxReward);
		Environment e = new Environment(task);
				
		
		// Set up logicalAgent
		RewardMachine emptyLMRM = new LMRM();
		Agent logicalAgent = new Agent(emptyLMRM, e, nPropositions);
		
		// Set up standardAgent
		RewardMachine emptyMRM = new MRM();
		Agent standardAgent = new Agent(emptyMRM, e, nPropositions);
		
		// Gather initial data by one of the agents
		ArrayList<ArrayList<Log>> trainingData = standardAgent.explore(1000, 20);
				
		// Build the reward machines
		long startTimeStandard = System.nanoTime();
		standardAgent.constructAutomaton(trainingData);
		long elapsedTimeStandard = System.nanoTime()-startTimeStandard;
		
		// Build the reward machines
		long startTimeLogical = System.nanoTime();
		logicalAgent.constructAutomaton(trainingData);
		long elapsedTimeLogical = System.nanoTime()-startTimeStandard;
		
		// Line for each type of agent
		String logicalResult = "";
		String standardResult = "";
		
		// Add experiment parameters
		logicalResult += states + ",";
		logicalResult += nPropositions + ",";
		logicalResult += maxReward + ",";
		
		standardResult += states + ",";
		standardResult += nPropositions + ",";
		standardResult += maxReward + ",";
		
		// TicToc
		logicalResult += elapsedTimeLogical + ",";
		standardResult += elapsedTimeStandard + ",";
		
		// nStatesLearned-nStatesActual
		logicalResult += (task.getNumberOfStates()-logicalAgent.getTaskModel().getNumberOfStates()) + ",";
		standardResult += (task.getNumberOfStates()-standardAgent.getTaskModel().getNumberOfStates()) + ",";
		
		// size of table
		logicalResult += (task.getTableSize()-logicalAgent.getTaskModel().getTableSize()) + ",";
		standardResult += (task.getTableSize())-standardAgent.getTaskModel().getTableSize() + ",";
		
		
		
		// Distance Minkowsky 1
		logicalResult += elapsedTimeLogical + ",";
		standardResult += elapsedTimeStandard + ",";
		
		// Distance Minkowsky 2
		logicalResult += elapsedTimeLogical + ",";
		standardResult += elapsedTimeStandard + ",";
		
		// Mispredictions
		logicalResult += elapsedTimeLogical + ",";
		standardResult += elapsedTimeStandard + ",";
		
		return null;
	}
}
