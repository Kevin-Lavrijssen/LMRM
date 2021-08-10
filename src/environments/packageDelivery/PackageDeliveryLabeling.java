package environments.packageDelivery;
import environments.ILabelingFunction;
import rewardmachines.Observation;

public class PackageDeliveryLabeling implements ILabelingFunction {

	public PackageDeliveryLabeling() {}
	
	@Override
	public Observation label(String action, int[] state) {

		// Drop in the water
		if(state[0]==3 && (state[1]==1 || state[1]==3 || state[1]==4 || state[1]==6)) {
			return new Observation( (int) Math.pow(2, 0) , 15);	
		}
		
		// On bridge 1
		if(state[0]==3 && state[1]==5) {
			return new Observation( (int) Math.pow(2, 1), 15);
		}
		
		// On bridge 2
		if(state[0]==3 && state[1]==2) {
			return new Observation( (int) Math.pow(2, 2), 15);
		}
		
		// Pick up products at warehouse 1
		if(state[0]==1 && state[1]==6 && action.equals("PickUp")) {
			int productStack = 0;
			productStack += (int) Math.pow(2, 3);
			productStack += (int) Math.pow(2, 4);
			return new Observation(productStack, 15);
		}
		
		// Pick up products at warehouse 2
		if(state[0]==1 && state[1]==5 && action.equals("PickUp")) {
			int productStack = 0;
			productStack += (int) Math.pow(2, 3);
			productStack += (int) Math.pow(2, 6);
			return new Observation(productStack, 15);
		}
		
		// Pick up products at warehouse 3
		if(state[0]==1 && state[1]==4 && action.equals("PickUp")) {
			int productStack = 0;
			productStack += (int) Math.pow(2, 4);
			productStack += (int) Math.pow(2, 7);
			return new Observation(productStack, 15);
		}
		
		// Pick up products at warehouse 4
		if(state[0]==1 && state[1]==3 && action.equals("PickUp")) {
			int productStack = 0;
			productStack += (int) Math.pow(2, 5);
			productStack += (int) Math.pow(2, 6);
			return new Observation(productStack, 15);
		}		
		
		// Pick up products at warehouse 5
		if(state[0]==1 && state[1]==2 && action.equals("PickUp")) {
			int productStack = 0;
			productStack += (int) Math.pow(2, 3);
			productStack += (int) Math.pow(2, 7);
			return new Observation(productStack, 15);
		}
		
		// Pick up products at warehouse 6
		if(state[0]==1 && state[1]==1 && action.equals("PickUp")) {
			int productStack = 0;
			productStack += (int) Math.pow(2, 6);
			productStack += (int) Math.pow(2, 8);
			return new Observation(productStack, 15);
		}
		
		// Drop products at delivery point state[i]
		if(state[0]==5 && action.equals("Drop")) {
			return new Observation((int) Math.pow(2, 15-state[1]), 15);
		}
		
		// Default observation (no changes)
		return new Observation(0, 15);
	}

	@Override
	public int getNumberOfPropositions() {
		return 15;
	}

}
