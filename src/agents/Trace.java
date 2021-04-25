package agents;
import java.util.ArrayList;
import java.util.Iterator;

import exceptions.BehaviourUndefinedException;
import exceptions.PreconditionViolatedException;
import rewardmachines.RewardMachine;

public class Trace {

	private ArrayList<Log> trace;
	
	public int[] getRemainder(RewardMachine rm) throws PreconditionViolatedException, BehaviourUndefinedException{
		
		rm.setState(currentState);
		int remainderState = currentState;
		int remainderIndex = index;
		
		while(remainderIndex<trace.size()) {
			Log log = trace.get(remainderIndex);
			if(!rm.isDefined(log.getObservation())) {return new int[] {remainderState, remainderIndex};}
			if(log.getReward()!=rm.execute(log.getObservation())) {
				throw new PreconditionViolatedException("Automaton inconsistent with the trace");}
			remainderState=rm.getCurrentState();
			remainderIndex+=1;
		}
		
		return new int[] {-1,-1};
		
	}
	
	public ArrayList<Log> getTrace(){
		return trace;
	}
	
	private int currentState;
	
	public int getCurrentState() {
		return currentState;
	}
	
	private int index;
	
	
	
	public Trace(ArrayList<Log> trace) {
		this.trace=trace;
		currentState=0;
		index=0;
	}

	public boolean explained() {
		return currentState==-1 && index==-1;
	}

	public Unexplained getNextUnexplained() {
		return new Unexplained(currentState, trace.get(index));
	}

	public void explain(RewardMachine rm) throws PreconditionViolatedException, BehaviourUndefinedException {
		if (currentState==-1 && index==-1) {throw new PreconditionViolatedException("Cannot further explain a trace that has already been explained");}
		
		rm.setState(currentState);
		int newIndex = index;
		int newState = currentState;
		for(int i=index; i<trace.size();i++) {
			
			Log log = trace.get(i);
			if(rm.isDefined(log.getObservation())) {rm.execute(log.getObservation()); newState = rm.getCurrentState(); newIndex=i+1;}
			else {index=newIndex; currentState=newState; return;}
		}
		
		rm.reset();
		currentState=-1;
		index=-1;
		
	}

	public void reset() {
		index=0;
		currentState=0;
	}
	
	public String toString(int remainderState, int remainderIndex){
		String string="Current state = "+currentState+" Remainder state = "+remainderState+" Index = "+index+" Remainder Index = "+remainderIndex+"\n"+"Remainder = ";
		
		for (int i=remainderIndex; i<trace.size(); i++) {
			string+=trace.get(i).toString();
		}
		
		return string+="\n";
	}
	
	public String toString(){
		String string="Full trace = ";
		
		for (int i=0; i<trace.size(); i++) {
			string+=trace.get(i).toString();
		}
		
		return string+="\n";
	}

	public boolean isConsistent(RewardMachine rm) throws BehaviourUndefinedException {
		
		if(this.explained()) {return true;}
		
		rm.setState(currentState);
		int i=index;
		while(i<trace.size()) {
			Log log = trace.get(i);
			if(!rm.isDefined(log.getObservation())) {return true;}
			if(log.getReward()!=rm.execute(log.getObservation())) {return false;}
			i+=1;
		}
		
		return true;
	}
	
}
