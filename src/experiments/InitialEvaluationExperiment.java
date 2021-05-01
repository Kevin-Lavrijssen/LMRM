package experiments;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import agents.Agent;
import agents.Log;
import environments.DirectEnvironment;
import environments.IEnvironment;
import exceptions.PreconditionViolatedException;
import rewardmachines.Evaluator;
import rewardmachines.LMRM;
import rewardmachines.MRM;
import rewardmachines.RewardMachine;

public class InitialEvaluationExperiment implements IExperiment{

	@Override
	public void run() throws IOException, PreconditionViolatedException {
		
		// Number of runs for each setting
		int nRuns = 6;
		
		// Parameters for the test of increasing states
		int states_maxStates = 10;
		int states_nPropositions = 2;
		
		// Parameters for the test of increasing number of propositions
		int propositions_maxPropositions = 11;
		int propositions_nStates = 2;
	
		// CSV Header
		String header = "#States_target,#Propositions,MAX_Reward,ElapsedTime_Nano,dEuclidean,dManhattan,#Mispredictions,relativeStateImprovement,relativeTransitionImprovement,absoluteStateImprovement,absoluteTransitionImprovement, size \n";
		
		/*
		// Create file to store results
		File stateExperiment = new File("BasicStateExperiment.csv");
		stateExperiment.createNewFile(); // if file already exists will do nothing 
		FileOutputStream resultsStateExperiment = new FileOutputStream(stateExperiment, false); 
		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(resultsStateExperiment));
		bw1.write(header);
		// Run for the different states
		for (int run=0; run<nRuns;run++) {
			for(int states = 2; states<states_maxStates; states++) {
				String results = individualExperiment(states, states_nPropositions, 2);
				bw1.write(results);
			}
		}
		
		bw1.flush();
		bw1.close();
		
		
		// Create file to store results
		File propExperiment = new File("BasicPropositionExperiment_1.csv");
		propExperiment.createNewFile(); // if file already exists will do nothing 
		FileOutputStream resultsPropExperiment = new FileOutputStream(propExperiment, false); 
		BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(resultsPropExperiment));
		bw2.write(header);
		// Run for the different states
		for (int run=0; run<nRuns;run++) {
			for(int nPropositions = 1; nPropositions<6; nPropositions++) {
				String results = individualExperiment(propositions_nStates, nPropositions, 2);
				bw2.write(results);
			}
		}
		
		bw2.flush();
		bw2.close();
		
		*/
		
		// Create file to store results
				File propExperiment2 = new File("BasicPropositionExperiment_2.csv");
				propExperiment2.createNewFile(); // if file already exists will do nothing 
				FileOutputStream resultsPropExperiment2 = new FileOutputStream(propExperiment2, false); 
				BufferedWriter bw3 = new BufferedWriter(new OutputStreamWriter(resultsPropExperiment2));
				bw3.write(header);
				// Run for the different states
				for (int run=0; run<nRuns;run++) {
					String results = individualExperiment(propositions_nStates, 5, 2);
					bw3.write(results);
				}
				
				bw3.flush();
				bw3.close();
		
	}

	
	public String individualExperiment(int states, int nPropositions, int maxReward) throws IOException, PreconditionViolatedException{
		
		MRM task = new MRM(states, nPropositions, maxReward);
		IEnvironment e = new DirectEnvironment(task, nPropositions);
		System.out.println(task.toString());		
		
		// Set up logicalAgent
		RewardMachine emptyLMRM = new LMRM();
		Agent logicalAgent = new Agent(emptyLMRM, e, nPropositions, e.getActions());
		logicalAgent.setCutOff(states+1);
		
		// Set up standardAgent
		RewardMachine emptyMRM = new MRM();
		Agent standardAgent = new Agent(emptyMRM, e, nPropositions, e.getActions());
		standardAgent.setCutOff(states+1);
		
		// Gather initial data by one of the agents
		ArrayList<ArrayList<Log>> trainingData = standardAgent.explore(4000, 8);
				
		// Build the reward machines
		long startTimeStandard = System.nanoTime();
		standardAgent.constructAutomaton(trainingData);
		long elapsedTimeStandard = System.nanoTime()-startTimeStandard;
		
		// Build the reward machines
		long startTimeLogical = System.nanoTime();
		logicalAgent.constructAutomaton(trainingData);
		long elapsedTimeLogical = System.nanoTime()-startTimeStandard;
		
		// Evaluate both automaton wrt the goal
		Evaluator eStandard = new Evaluator(task, standardAgent.getTaskModel());
		eStandard.evaluate(2000, 25, nPropositions);
		Evaluator eLogical = new Evaluator(task, logicalAgent.getTaskModel());
		eLogical.evaluate(2000, 25, nPropositions);
		
		// Line for each type of agent
		String logicalResult = "LMRM,";
		String standardResult = "MRM,";
		
		// Add experiment parameters
		logicalResult += states + ",";
		logicalResult += nPropositions + ",";
		logicalResult += maxReward-1 + ",";
		
		standardResult += states + ",";
		standardResult += nPropositions + ",";
		standardResult += maxReward-1 + ",";
		
		// TicToc
		logicalResult += elapsedTimeLogical + ",";
		standardResult += elapsedTimeStandard + ",";
		
		// Euclidean distance over all of the traces combined
		logicalResult += eLogical.getEuclideanDistance() + ",";
		standardResult += eStandard.getEuclideanDistance() + ",";
		
		// Manhattan distance over all of the traces combined
		logicalResult += eLogical.getManhattanDistance() + ",";
		standardResult += eStandard.getManhattanDistance() + ",";
		
		// Total amount of mispredictions over all of the traces
		logicalResult += eLogical.getMispredictions() + ",";
		standardResult += eStandard.getMispredictions() + ",";
		
		// The number of states of the learned model wrt the target in %
		logicalResult += eLogical.getRelativeStateImprovement() + ",";
		standardResult += eStandard.getRelativeStateImprovement() + ",";
		
		// The number of transitions of the learned model wrt the target in %
		logicalResult += eLogical.getRelativeTransitionImprovement() + ",";
		standardResult += eStandard.getRelativeTransitionImprovement() + ",";
		
		// Absolute number of states of the learned model wrt the target 
		logicalResult += eLogical.getStateImprovement() + ",";
		standardResult += eStandard.getStateImprovement() + ",";
		
		// Absolute number of transitions of the learned model wrt the target
		logicalResult += eLogical.getTransitionImprovement() + ",";
		standardResult += eStandard.getTransitionImprovement() + ",";
		
		// Absolute number of transitions of the learned model wrt the target
		logicalResult += logicalAgent.getTaskModel().getTableSize();
		standardResult += standardAgent.getTaskModel().getTableSize();
		
		// Absolute number of transitions of the learned model wrt the target
		//logicalResult += eLogical.getTargetObjectSize() + ",";
		//standardResult += eStandard.getTargetObjectSize() + ",";
		
		// Absolute number of transitions of the learned model wrt the target
		//logicalResult += eLogical.getResultObjectSize();
		//standardResult += eStandard.getResultObjectSize();
				
		// End the line
		logicalResult += "\n";
		standardResult += "\n";
		
		return logicalResult + standardResult;
	}
	
}
