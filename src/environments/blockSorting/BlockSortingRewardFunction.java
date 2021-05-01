package environments.blockSorting;

import environments.IRewardFunction;
import rewardmachines.Observation;

public class BlockSortingRewardFunction implements IRewardFunction {

	public BlockSortingRewardFunction(int nTypes) {
		typesCarrying = new int[nTypes];
		this.nTypes = nTypes;
	}
	
	private int[] typesCarrying;
	private int nTypes;
	
	@Override
	public int execute(Observation o) {
		if(o.toInteger()==0) {return 0;}
		for (int i=0; i<nTypes;i++) {
			if(typesCarrying[i]==1 && o.toString().charAt(i+nTypes)==1) {typesCarrying[i]=0; return 1;}
			if(typesCarrying[i]==0 && o.toString().charAt(i)==1) {typesCarrying[i] = 1; return 0;}
		}
		return 0;
	}

	
	
}
