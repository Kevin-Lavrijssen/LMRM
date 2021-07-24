package experiments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import agents.Agent_Standard;
import agents.Log;
import environments.packageDelivery.PackageDeliveryEnvironment;
import environments.packageDelivery.PackageDeliveryRewardFunction_BC;
import environments.packageDelivery.PackageDeliveryRewardFunction_MPD;
import environments.packageDelivery.PackageDeliveryRewardFunction_SPD;
import exceptions.PreconditionViolatedException;
import rewardmachines.LMRM;
import rewardmachines.Observation;
import rewardmachines.RewardMachine;

public class PackageDeliveryExperiment implements IExperiment {

	@Override
	public void run() throws IOException, PreconditionViolatedException {
		
		//PackageDeliveryEnvironment e = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_MPD());
		
		//Agent_Standard agent = new Agent_Standard(new LMRM(), e, e.getNumberOfPropositions(), e.getActions());
		//agent.constructAutomaton(agent.explore(10000, 25));
		//RewardMachine learnedModel = agent.getTaskModel();
		
		int mispredictions = 0;
		//String[] actions = e.getActions();
		Random r = new Random();
		
		/*
		for (int n=0; n<5000; n++) {
			e.reset();
			learnedModel.reset();
			for (int i=0; i<25; i++) {
				int a = r.nextInt(actions.length);
				Log result = e.execute(actions[a]);
				int actual = result.getReward();
				int expected = learnedModel.execute(result.getObservation());
				if(actual!=expected) {mispredictions+=1;}
			}
		}
		
		double wrongPredictions = 0;
		double predictions = 0;
		
		for (String[] t : testTraces) {		
			e.reset();
			learnedModel.reset();
			for(int i = 0; i<t.length; i++) {
				Log result = e.execute(t[i]);
				if(learnedModel.execute(result.getObservation())!=result.getReward()){wrongPredictions++;}
				predictions++;
			}
				
		}
		
		double accuracy = 1-(wrongPredictions/predictions);
		System.out.println("Accuracy: "+accuracy);
		*/
		
		/*
		for (String[] t : testTraces) {
			int finalReward = 0;
			ArrayList<Observation> observations = new ArrayList<Observation>();			
			e.reset();
			
			for(int i = 0; i<t.length; i++) {
				Log result = e.execute(t[i]);
				finalReward = result.getReward();
				observations.add(result.getObservation());
			}
			
			if(finalReward!=-1) {
				System.out.println(testTraces.indexOf(t));
				System.out.println("Reward: "+finalReward);
				
				for(int x=0; x<observations.size(); x++) {
					System.out.println(observations.get(x).toString());
				}
				
			}
			
		}*/
		
		/*
		System.out.println("Accuracy: "+mispredictions);
		System.out.println(learnedModel.toString());
		*/
		
	}
		
		

	@Override
	public void reportResults(String result) {
		// TODO Auto-generated method stub	
	}

	
	
}
