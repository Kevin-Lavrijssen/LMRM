package agents;

import java.io.IOException;
import java.util.ArrayList;

import rewardmachines.IRewardMachine;

public class Trace {

	private ArrayList<Log> trace;
	private int currentState;
	private int index;
	
	public Trace(ArrayList<Log> trace) {
		this.trace=trace;
		currentState=0;
		index=0;
	}

	public void reset() {
		currentState=0;
		index=0;
	}

	public boolean explained() {
		return currentState==-1 && index==-1;
	}

	public void explain(IRewardMachine rm) {
		
		if(index==-1) {return;}
		
		rm.setState(currentState);
		for (int i=index; i<trace.size(); i++) {
			Log l = trace.get(i);
			
			try {
				int reward = rm.execute(l.getObservation());
				if(reward!=l.getReward()) {throw new IOException("Check consistency before explaining data!");}
			} catch (IOException e) {
				currentState = rm.getCurrentState();
				index = i;
				return;
			}
			
			currentState=-1;
			index=-1;
		}
	}

	public boolean consistent(IRewardMachine rm) {
		for (int i=index; i<trace.size(); i++) {
			Log l = trace.get(i);
			
			try {
				int reward = rm.execute(l.getObservation());
				if(reward!=l.getReward()) {return false;}
			} catch (IOException e) {
				return true;
			}
		}
		return true;
	}

	public Unexplained getNextUnexplained() throws IOException {
		if(index==-1) {throw new IOException("The traces was already explained, cannot retrieve unexplained.");}
		return new Unexplained(currentState, trace.get(index));
	}
	
	
	
}
