package rewardmachines;

public class StandardTableEntry implements ITableEntry{

	int source;
	Observation observation;
	int destination;
	int reward;
	
	public StandardTableEntry(int source, Observation observation, int destination, int reward) {
		this.source=source;
		this.observation=observation;
		this.destination=destination;
		this.reward=reward;
	}

	@Override
	public void merge(Observation o) {
		// Not applicable for standard transitions
	}

	@Override
	public boolean evaluate(int source, Observation o) {
		return this.source==source && this.observation.equals(o);
	}

	@Override
	public void split(Observation o) {
		// Not applicable for standard transitions
	}

	@Override
	public int getSource() {
		return source;
	}

	@Override
	public int getDestination() {
		return destination;
	}

	@Override
	public int getReward() {
		return reward;
	}

	@Override
	public void setDestination(int destination) {
		this.destination = destination;
	}
	
}
