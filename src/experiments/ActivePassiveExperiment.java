package experiments;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import agents.AgentDivideAndConquerV2;
import agents.Agent_Standard;
import agents.Log;
import agents.Trace;
import environments.DirectEnvironment;
import environments.IEnvironment;
import environments.IRewardFunction;
import exceptions.PreconditionViolatedException;
import rewardmachines.LMRM;
import rewardmachines.MRM;
import rewardmachines.RewardMachine;

public class ActivePassiveExperiment implements IExperiment{

	@Override
	public void run() throws IOException, PreconditionViolatedException {
		
		// Experiment parameters
		int[] states = new int[] {2, 4, 6,8};
		int[] propositions = new int[] {4};
		int[] traces = new int[] {8, 16,32,64,128,256, 512};
		
		String header = "Algorithm, nStates, nPropositions, Accuracy, ExecutionTime, nTraces, size \n";
		
		// Loop managing different runs of the experiment
		for (int run=0; run<5; run++) {
			
			// Create file
			String fileName = "activePassive_"+run+".csv" ;
			File experiment = new File(fileName);
			experiment.createNewFile(); // if file already exists will do nothing 
			FileOutputStream resultsExperiment = new FileOutputStream(experiment, false); 
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(resultsExperiment));
			bw.write(header);			
			
			for(int nStates:states) {
				for(int nPropositions:propositions) {
					for(int nTraces:traces) {
						
						MRM task = new MRM(nStates, nPropositions, 2);
						IEnvironment e = new DirectEnvironment(task, nPropositions);
						
						// Set up active algorithm
						RewardMachine emptyLMRM_a = new LMRM();
						AgentDivideAndConquerV2 active = new AgentDivideAndConquerV2(emptyLMRM_a, e, nPropositions, e.getActions());
						
						// Set up passive algorithm
						RewardMachine emptyLMRM_p = new LMRM();
						Agent_Standard passive = new Agent_Standard(emptyLMRM_p, e, nPropositions, e.getActions());
						
						// Learn models
						long startTimePassive = System.nanoTime();
						passive.constructAutomaton(passive.explore(nTraces, 2*nStates-1, run));
						double executionTimePassive = (startTimePassive-System.nanoTime())/Math.pow(10, 9);
						
						long startTimeActive = System.nanoTime();
						active.learnAutomaton(nTraces, 2*nStates-1, nStates*2);
						double executionTimeActive = (startTimeActive-System.nanoTime())/Math.pow(10, 9);
						
						// Evaluate models
						int nValidationTraces = 10*nStates*(int)Math.pow(2, nPropositions);
						ArrayList<ArrayList<Log>> validationSet = passive.explore(nValidationTraces, 2*nStates-1, 13);
						
						double passiveAccuracy = evaluateModel(validationSet, passive.getTaskModel());
						double activeAccuracy = evaluateModel(validationSet, active.getTaskModel());
						
						// Write results
						String passiveResult = "Passive,"+nStates+","+nPropositions+","+passiveAccuracy+","+executionTimePassive+","+nTraces+","+ passive.getTaskModel().getNumberOfStates() +"\n";
						String activeResult = "Active,"+nStates+","+nPropositions+","+activeAccuracy+","+executionTimeActive+","+nTraces+","+active.getTaskModel().getNumberOfStates()+"\n";
						
						bw.write(passiveResult);
						bw.write(activeResult);
						
					}
				}
			}
			
			bw.flush();
			bw.close();
			
		}
		
		
	}

	@Override
	public void reportResults(String result) {
		// TODO Auto-generated method stub
		
	}

	
	private double evaluateModel(ArrayList<ArrayList<Log>> val, RewardMachine model) {
		double mispredictions = 0;
		double totalPredictions=0;
		
		for(ArrayList<Log> trace:val) {
			model.reset();
			for(Log l:trace) {
				if(l.getReward()!=model.execute(l.getObservation())) {mispredictions++;}
				totalPredictions++;
			}
		}
		
		return 1-(mispredictions/totalPredictions);
		
	}
	
	
}
