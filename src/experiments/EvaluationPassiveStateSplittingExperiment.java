package experiments;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import agents.Agent_Standard;
import agents.Log;
import environments.DirectEnvironment;
import environments.IEnvironment;
import exceptions.PreconditionViolatedException;
import rewardmachines.Evaluator;
import rewardmachines.LMRM;
import rewardmachines.MRM;
import rewardmachines.RewardMachine;

public class EvaluationPassiveStateSplittingExperiment implements IExperiment{

	@Override
	public void run() throws IOException, PreconditionViolatedException {
		
		// Configuration
		int nRuns = 5;
		int maxStates = 8;
		int maxPropositions = 6;
		
		// CSV Header
		String header = "#States_target,#Propositions,MAX_Reward,ElapsedTime_Nano,dEuclidean,dManhattan,#Mispredictions,relativeStateImprovement,relativeTransitionImprovement,absoluteStateImprovement,absoluteTransitionImprovement, size \n";
		
		
		// Outer two loops traverse the grid of configurations
		for (int nStates = 5; nStates <= maxStates; nStates++) {
			for(int nPropositions = 3; nPropositions <= maxPropositions; nPropositions++ ) {
				
				// Create file to store configuration results
				String fileName = "EvaluationPassiveStateSplitting_" + nStates +"_" + nPropositions + ".csv" ;
				File experiment = new File(fileName);
				experiment.createNewFile(); // if file already exists will do nothing 
				FileOutputStream resultsExperiment = new FileOutputStream(experiment, false); 
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(resultsExperiment));
				bw.write(header);
				
				// Run the experiment for the required number of runs
				for (int n = 0; n<nRuns; n++) {
					
					long startExecutionTime = System.nanoTime();
					String results = individualExperiment(nStates, nPropositions, 2);
					bw.write(results);
					long elapsedExecutionTime = System.nanoTime()-startExecutionTime;
					
					System.out.println("Experiment Completed:");
					System.out.println("Run: "+n);
					System.out.println("States: "+nStates);
					System.out.println("Propositions: "+nPropositions);
					System.out.println("Time to complete: "+elapsedExecutionTime);
					
				}
				bw.flush();
				bw.close();
			}
		}
		
	}

	@Override
	public void reportResults(String result) {
		// TODO Auto-generated method stub
		
	}

public String individualExperiment(int states, int nPropositions, int maxReward) throws IOException, PreconditionViolatedException{
		
		MRM task = new MRM(states, nPropositions, maxReward);
		IEnvironment e = new DirectEnvironment(task, nPropositions);
		System.out.println(task.toString());		
		
		// Set up logicalAgent
		RewardMachine emptyLMRM = new LMRM();
		Agent_Standard logicalAgent = new Agent_Standard(emptyLMRM, e, nPropositions, e.getActions());
		logicalAgent.setCutOff(states+1);
		
		// Set up standardAgent
		RewardMachine emptyMRM = new MRM();
		Agent_Standard standardAgent = new Agent_Standard(emptyMRM, e, nPropositions, e.getActions());
		standardAgent.setCutOff(states+1);
		
		// Gather initial data by one of the agents
		ArrayList<ArrayList<Log>> trainingData = standardAgent.explore(15000, 50);
				
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
