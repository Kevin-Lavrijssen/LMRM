package rewardmachines;

interface ITableEntry {

	void merge(Observation o);
	
	boolean evaluate(int source, Observation o);
	
	void split(Observation o);
	
	int getSource();
	
	int getDestination();
	
	int getReward();   
	
	/**
	 * TODO CHECK if necessary !!!!!!!!!!!!!!!!!!!!!!
	 * @param destination
	 * @return
	 */
	void setDestination(int destination);
	
	public String toString();
	
}
