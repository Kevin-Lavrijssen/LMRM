package experiments;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

import agents.Agent_Standard;
import agents.Log;
import environments.packageDelivery.NoiseWrapperLabelingFunction;
import environments.packageDelivery.PackageDeliveryEnvironment;
import environments.packageDelivery.PackageDeliveryLabeling;
import environments.packageDelivery.PackageDeliveryRewardFunction_BC;
import environments.packageDelivery.PackageDeliveryRewardFunction_IO;
import environments.packageDelivery.PackageDeliveryRewardFunction_MPD;
import environments.packageDelivery.PackageDeliveryRewardFunction_SPD;
import environments.packageDelivery.TestData;
import exceptions.PreconditionViolatedException;
import rewardmachines.LMRM;
import rewardmachines.Observation;
import rewardmachines.RewardMachine;

public class PackageDeliveryExperiment implements IExperiment {

	@Override
	public void run() throws IOException, PreconditionViolatedException {
		
		long tic = System.nanoTime();
		
		// Environments
		PackageDeliveryEnvironment spd = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_SPD(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment bc = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_BC(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment mpd = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_MPD(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment io = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_IO(2), new NoiseWrapperLabelingFunction(new PackageDeliveryLabeling(), 2));
		
		// Core Validation Data
		ArrayList<String[]> core = new TestData().getTraces();
		
		// Test Parameters
		int traceLength = 50;
		
		// CSV Header
		String header = "BatchSize, ExecutionTime, CoreAccuracy, RandomTraceAccuracy, States, Transitions \n";
		
		
		for(int run=0; run<5; run++) {
		
		
			
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////// SPD ////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// Create file to store configuration results
		String fileName = "spd_"+run+".csv" ;
		File experiment = new File(fileName);
		experiment.createNewFile(); // if file already exists will do nothing 
		FileOutputStream resultsExperiment = new FileOutputStream(experiment, false); 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(resultsExperiment));
		bw.write(header);
		
		// Experiment Loop
		for (int batchSize=25; batchSize<=30000; batchSize=batchSize*2) {
			
			
			// Set up logicalAgent
			RewardMachine emptyLMRM = new LMRM();
			Agent_Standard logicalAgent = new Agent_Standard(emptyLMRM, spd, spd.getNumberOfPropositions(), spd.getActions());
			
			// Learn automaton
			long startExecutionTime = System.nanoTime();
			logicalAgent.constructAutomaton(logicalAgent.explore(batchSize, traceLength, run));
			long elapsedExecutionTime = System.nanoTime()-startExecutionTime;
			
			// Evaluate
			double coreAccuracy = evaluateCoreAccuracy(logicalAgent.getTaskModel(), core, spd);
			double randomAccuracy = evaluateRandomAccuracy(logicalAgent.getTaskModel(), spd);
			
			int states = logicalAgent.getTaskModel().getNumberOfStates();
			int transitions = logicalAgent.getTaskModel().getTableSize();
			
			// Store result
			String result = batchSize+","+elapsedExecutionTime+","+coreAccuracy+","+randomAccuracy+","+states+","+transitions+"\n" ;
			bw.write(result);
			
		}
		
		bw.flush();
		bw.close();
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////// BC ////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		
		// Create file to store configuration results
		fileName = "bc"+run+".csv" ;
		experiment = new File(fileName);
		experiment.createNewFile(); // if file already exists will do nothing 
		resultsExperiment = new FileOutputStream(experiment, false); 
		bw = new BufferedWriter(new OutputStreamWriter(resultsExperiment));
		bw.write(header);
		
		// Experiment Loop
		for (int batchSize=25; batchSize<=30000; batchSize=batchSize*2) {
			
			
			// Set up logicalAgent
			RewardMachine emptyLMRM = new LMRM();
			Agent_Standard logicalAgent = new Agent_Standard(emptyLMRM, bc, bc.getNumberOfPropositions(), bc.getActions());
			
			// Learn automaton
			long startExecutionTime = System.nanoTime();
			logicalAgent.constructAutomaton(logicalAgent.explore(batchSize, traceLength, run));
			long elapsedExecutionTime = System.nanoTime()-startExecutionTime;
			
			// Evaluate
			double coreAccuracy = evaluateCoreAccuracy(logicalAgent.getTaskModel(), core, bc);
			double randomAccuracy = evaluateRandomAccuracy(logicalAgent.getTaskModel(), bc);
			
			int states = logicalAgent.getTaskModel().getNumberOfStates();
			int transitions = logicalAgent.getTaskModel().getTableSize();
			
			// Store result
			String result = batchSize+","+elapsedExecutionTime+","+coreAccuracy+","+randomAccuracy+","+states+","+transitions+"\n" ;
			bw.write(result);
			
		}
		
		bw.flush();
		bw.close();
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////// MPD ////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		
		// Create file to store configuration results
		fileName = "mpd"+run+".csv" ;
		experiment = new File(fileName);
		experiment.createNewFile(); // if file already exists will do nothing 
		resultsExperiment = new FileOutputStream(experiment, false); 
		bw = new BufferedWriter(new OutputStreamWriter(resultsExperiment));
		bw.write(header);
		
		// Experiment Loop
		for (int batchSize=25; batchSize<=30000; batchSize=batchSize*2) {
			
			
			// Set up logicalAgent
			RewardMachine emptyLMRM = new LMRM();
			Agent_Standard logicalAgent = new Agent_Standard(emptyLMRM, mpd, mpd.getNumberOfPropositions(), mpd.getActions());
			
			// Learn automaton
			long startExecutionTime = System.nanoTime();
			logicalAgent.constructAutomaton(logicalAgent.explore(batchSize, traceLength, run));
			long elapsedExecutionTime = System.nanoTime()-startExecutionTime;
			
			// Evaluate
			double coreAccuracy = evaluateCoreAccuracy(logicalAgent.getTaskModel(), core, mpd);
			double randomAccuracy = evaluateRandomAccuracy(logicalAgent.getTaskModel(), mpd);
			
			int states = logicalAgent.getTaskModel().getNumberOfStates();
			int transitions = logicalAgent.getTaskModel().getTableSize();
			
			// Store result
			String result = batchSize+","+elapsedExecutionTime+","+coreAccuracy+","+randomAccuracy+","+states+","+transitions+"\n" ;
			bw.write(result);
			
			
			
			
			
		}
		
		bw.flush();
		bw.close();
		
		
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////// IO ////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/*
		
		// Create file to store configuration results
		fileName = "io.csv" ;
		experiment = new File(fileName);
		experiment.createNewFile(); // if file already exists will do nothing 
		resultsExperiment = new FileOutputStream(experiment, false); 
		bw = new BufferedWriter(new OutputStreamWriter(resultsExperiment));
		bw.write(header);
		
		// Experiment Loop
		for (int batchSize=25; batchSize<=30000; batchSize=batchSize*2) {
			
			
			// Set up logicalAgent
			RewardMachine emptyLMRM = new LMRM();
			Agent_Standard logicalAgent = new Agent_Standard(emptyLMRM, io, io.getNumberOfPropositions(), io.getActions());
			
			// Learn automaton
			long startExecutionTime = System.nanoTime();
			logicalAgent.constructAutomaton(logicalAgent.explore(batchSize, traceLength));
			long elapsedExecutionTime = System.nanoTime()-startExecutionTime;
			
			// Evaluate
			double coreAccuracy = evaluateCoreAccuracy(logicalAgent.getTaskModel(), core, io);
			double randomAccuracy = evaluateRandomAccuracy(logicalAgent.getTaskModel(), io);
			
			int states = logicalAgent.getTaskModel().getNumberOfStates();
			int transitions = logicalAgent.getTaskModel().getTableSize();
			
			// Store result
			String result = batchSize+","+elapsedExecutionTime+","+coreAccuracy+","+randomAccuracy+","+states+","+transitions+"\n";
			bw.write(result);
			
		}
		
		bw.flush();
		bw.close();
		*/
		}
		
		
		long toc = System.nanoTime()-tic;
		System.out.println(toc);
	}
		
		

	private double evaluateRandomAccuracy(RewardMachine taskModel, PackageDeliveryEnvironment e) {
		double totalEval = 0;
		double mispredictions = 0;
		Random r = new Random();
		
		for (int n=0; n<500; n++) {
			e.reset();
			taskModel.reset();
			for(int i=0; i<50; i++) {
				Log actual = e.execute(e.getActions()[r.nextInt(e.getActions().length)]);
				int expected = taskModel.execute(actual.getObservation());
				if(expected!=actual.getReward()) {mispredictions++;}
				totalEval++;
			}
		}
		
		return 1-(mispredictions/totalEval);
	}



	private double evaluateCoreAccuracy(RewardMachine taskModel, ArrayList<String[]> traces, PackageDeliveryEnvironment e) {
		double totalEval = 0;
		double mispredictions = 0;
		
		for (String[] trace:traces) {
			e.reset();
			taskModel.reset();
			for(int i=0; i<trace.length; i++) {
				Log actual = e.execute(trace[i]);
				int expected = taskModel.execute(actual.getObservation());
				if(expected!=actual.getReward()) {mispredictions++;}
				totalEval++;
			}
		}
		
		return 1-(mispredictions/totalEval);
	}



	@Override
	public void reportResults(String result) {
		// TODO Auto-generated method stub	
	}

	
	
}
