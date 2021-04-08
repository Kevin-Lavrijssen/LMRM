package agents;

import java.io.IOException;
import java.util.ArrayList;

import rewardmachines.RewardMachine;

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

	public void explain(RewardMachine rm) {
		
		if(index==-1 && currentState==-1) {return;}
		
		rm.setState(currentState);
		for (int i=index; i<trace.size(); i++) {
			Log l = trace.get(i);
			
			try {
				int reward = rm.execute(l.getObservation());
				if(reward!=l.getReward()) {throw new IOException("Check consistency before explaining data!");}
			} catch (IOException e) {
				currentState = rm.getCurrentState();
				index = i;
				rm.reset();
				return;
			}
		}
		
		currentState=-1;
		index=-1;
		rm.reset();
		
	}

	public boolean consistent(RewardMachine rm) {
		// If the trace has been explained it is consistent with the rm
		if (index==-1 && currentState==-1) {return true;}
		
		// Else try to explain the remainder
		rm.setState(currentState);
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
	
	public String toString() {
		if(index==-1) {return "Done \n";}
		String string = "[Current State : "+currentState+"]";
		for (int i=index; i<trace.size(); i++) {
			string+=trace.get(i).toString()+"|";
		}
		return string+"\n";
		
	}
	
	
}
