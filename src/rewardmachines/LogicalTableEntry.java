package rewardmachines;

public class LogicalTableEntry implements ITableEntry {

	int source;
	TransitionConstraint constraint;
	int destination;
	int reward;
	
	public LogicalTableEntry(int source, Observation o, int destination, int reward) {
		this.source = source;
		this.constraint = new TransitionConstraint(o);
		this.destination = destination;
		this.reward = reward;
	}
	
	@Override
	public void merge(Observation o) {
		constraint.merge(o);
	}

	@Override
	public boolean evaluate(int source, Observation o) {
		if(this.source==source) {return constraint.evaluate(o);}
		return false;
	}

	@Override
	public void split(Observation o) {
		// TODO Auto-generated method stub	
	}

	@Override
	public int getSource() {
		return this.source;
	}

	@Override
	public int getDestination() {
		return this.destination;
	}

	@Override
	public int getReward() {
		return this.reward;
	}

	@Override
	public void setDestination(int destination) {
		this.destination=destination;
	}

	public String toString() {
		return source + "," +constraint.toString() + "," + destination + "," + reward +"\n";
	}
	
}
