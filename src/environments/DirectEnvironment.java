package environments;

import java.util.HashMap;

import agents.Log;
import rewardmachines.RewardMachine;
import rewardmachines.Observation;

public class DirectEnvironment implements IEnvironment{

	RewardMachine task;
	HashMap<String, Observation> map;
	String[] actions;
	int nPropositions;
	
	public DirectEnvironment(RewardMachine task, int nPropositions) {
		this.task=task;
		this.nPropositions=nPropositions;
		map = new HashMap<String, Observation>();
		actions = new String[(int) Math.pow(2, nPropositions)];
		
		for (int i=0; i<Math.pow(2, nPropositions); i++) {
			Observation o = new Observation(i, nPropositions);
			actions[i] = o.toString();
			map.put(o.toString(), o);
		}
		
	}
	
	@Override
	public Log execute(String o) {
		Observation observation = map.get(o);
		int reward = task.execute(observation);
		return new Log(observation, reward);
	}
	
	@Override
	public void reset() {
		task.reset();
	}

	@Override
	public String[] getActions() {
		return actions;
	}

	@Override
	public int getNumberOfPropositions() {
		return nPropositions;
	}
	
}
