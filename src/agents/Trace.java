package agents;
import java.util.ArrayList;

import exceptions.PreconditionViolatedException;
import rewardmachines.RewardMachine;

public class Trace {

	private ArrayList<Log> trace;
	
	public Trace(ArrayList<Log> trace) {
		this.trace=trace;
		currentState=0;
		index=0;
	}
	
	public int[] getRemainder(RewardMachine rm) throws PreconditionViolatedException{
		
		//rm.setState(currentState);
		int remainderState = currentState;
		int remainderIndex = index;
		
		
		
		/*
		while(remainderIndex<trace.size()) {
			Log log = trace.get(remainderIndex);
			if(!rm.isDefined(remainderState, log.getObservation())) {return new int[] {remainderState, remainderIndex};}
			int[] destinationReward = rm.execute(remainderState, log.getObservation());
			if(log.getReward()!=destinationReward[1]) {
				System.out.println("Expected: "+log.getReward() + " Actual: " + destinationReward[1]);
				throw new PreconditionViolatedException("Automaton inconsistent with the trace");}
			remainderState=destinationReward[0];
			remainderIndex+=1;
		}*/
		
		while(remainderIndex<trace.size()) {
			Log log = trace.get(remainderIndex);
			int[] destinationReward = rm.execute(remainderState, log.getObservation());
			if(destinationReward == null) {return new int[] {remainderState, remainderIndex};}
			if(log.getReward()!=destinationReward[1]) {
				System.out.println("State: " + remainderState + " Observation: "+log.getObservation().toString());
				System.out.println("Expected: "+log.getReward() + " Actual: " + destinationReward[1]);
				System.out.println(this.toString());
				throw new PreconditionViolatedException("Automaton inconsistent with the trace");}
			remainderState=destinationReward[0];
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
	
	
	


	public boolean explained() {
		return currentState==-1 && index==-1;
	}

	public Unexplained getNextUnexplained() {
		
		String[] prefix = new String[index+1];
		
		for(int i=0; i<=index; i++) {
			prefix[i]=trace.get(i).getAction();
		}
		
		return new Unexplained(currentState, trace.get(index), prefix);
	}

	public void explain(RewardMachine rm) throws PreconditionViolatedException {
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

	public boolean isConsistent(RewardMachine rm) {
		
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

	public String[] getExplainedPrefix() throws PreconditionViolatedException {
		if(index==0) {throw new PreconditionViolatedException("Index = 0");}
		String[] prefix = new String[index];
		for(int i=0; i<index; i++) {
			prefix[i]=trace.get(i).getAction();
		}
		return prefix;
		
	}
	
}
