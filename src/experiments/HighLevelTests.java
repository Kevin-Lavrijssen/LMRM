package experiments;

import java.io.IOException;
import java.util.ArrayList;

import agents.Agent;
import agents.DataSession;
import agents.Log;
import environments.Environment;
import rewardmachines.MRM;
import rewardmachines.RewardMachine;

public class HighLevelTests implements IExperiment{

	Agent standardAgent;
	Environment e;
	MRM task;
	
	public HighLevelTests() throws IOException {
		
		int nPropositions = 2;
		
		// Set up environment
		String fileLocation = "C:\\Users\\Kevin\\eclipse-workspace\\LogicalMealyRewardMachines\\firstMM.csv";
		MRM task = new MRM(fileLocation);
		this.task=task;
		// System.out.println(test.toString());
		e = new Environment(task);
		
		// Set up standardAgent
		RewardMachine emptyMRM = new MRM();
		standardAgent = new Agent(emptyMRM, e, nPropositions);
	}
	
	public void run() throws IOException {
		
		// Gather initial data by one of the agents
		ArrayList<ArrayList<Log>> trainingData1 = standardAgent.explore(1000, 1000);
		ArrayList<ArrayList<Log>> trainingData2 = standardAgent.explore(1000, 1000);
		ArrayList<ArrayList<Log>> trainingData3 = standardAgent.explore(1000, 1000);
		ArrayList<ArrayList<Log>> trainingData4 = standardAgent.explore(1000, 1000);
		ArrayList<ArrayList<Log>> trainingData5 = standardAgent.explore(1000, 1000);
		
		verifyTrainingData(e, trainingData1);
		verifyTrainingData(e, trainingData2);
		verifyTrainingData(e, trainingData3);
		verifyTrainingData(e, trainingData4);
		verifyTrainingData(e, trainingData5);
		
		DataSession ds1 = new DataSession(trainingData1);
		DataSession ds2 = new DataSession(trainingData2);
		DataSession ds3 = new DataSession(trainingData3);
		DataSession ds4 = new DataSession(trainingData4);
		DataSession ds5 = new DataSession(trainingData5);
		
		if(!ds1.consistent(task)) {throw new IOException("Consistency check is flawed.");}
		if(!ds2.consistent(task)) {throw new IOException("Consistency check is flawed.");}
		if(!ds3.consistent(task)) {throw new IOException("Consistency check is flawed.");}
		if(!ds4.consistent(task)) {throw new IOException("Consistency check is flawed.");}
		if(!ds5.consistent(task)) {throw new IOException("Consistency check is flawed.");}
		
		System.out.println("Consistency check seems ok");
		
		ds1.explain(task);
		ds2.explain(task);
		ds3.explain(task);
		ds4.explain(task);
		ds5.explain(task);
		
		if(!ds1.explained()) {throw new IOException("Explaining is flawed.");}
		if(!ds2.explained()) {throw new IOException("Explaining is flawed.");}
		if(!ds3.explained()) {throw new IOException("Explaining is flawed.");}
		if(!ds4.explained()) {throw new IOException("Explaining is flawed.");}
		if(!ds5.explained()) {throw new IOException("Explaining is flawed.");}
		
		System.out.println("Explaining seems ok");
		
		
	}
	
	public void verifyTrainingData(Environment e, ArrayList<ArrayList<Log>> trainingData) throws IOException {
		for (ArrayList<Log> trace:trainingData) {
			e.reset();
			for(Log l:trace) {
				if(l.getReward()!=e.execute(l.getObservation())) {throw new IOException("Gathering training data is flawed.");}
			}
		}
		
		System.out.println("Gathering training data is ok");
	}
	
}
