package environments.packageDelivery;

import java.util.Random;

import environments.ILabelingFunction;
import rewardmachines.Observation;

public class NoiseWrapperLabelingFunction implements ILabelingFunction{

	private ILabelingFunction labeling;
	int noisePropositions;
	Random r = new Random();
	
	public NoiseWrapperLabelingFunction(ILabelingFunction labeling, int noisePropositions) {
		this.labeling=labeling;
		this.noisePropositions=noisePropositions;
	}
	
	@Override
	public Observation label(String action, int[] state) {
		Observation o = labeling.label(action, state);
		int noiseValue = r.nextInt((int) Math.pow(2, noisePropositions));
		Observation n = new Observation(noiseValue, noisePropositions);
		return new Observation(n.toString()+o.toString());
	}

	@Override
	public int getNumberOfPropositions() {
		return noisePropositions+labeling.getNumberOfPropositions();
	}

}
