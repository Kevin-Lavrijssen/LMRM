package agents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import exceptions.BehaviourUndefinedException;
import exceptions.PreconditionViolatedException;
import rewardmachines.RewardMachine;

public class DataSession {
	
	ArrayList<Trace> data;

	public DataSession(ArrayList<ArrayList<Log>> traces) {
		this.data = new ArrayList<Trace>();
		for (ArrayList<Log> trace : traces) {
			Trace t = new Trace(trace);
			data.add(t);
		}
	}
	
	public DataSession() {
		this.data = new ArrayList<Trace>();
	}

	public boolean explained() {
		for (Trace trace:data) {
			if(!trace.explained()) {return false;}
		}
		return true;
	}

	public Unexplained getNextUnexplained() throws PreconditionViolatedException {
		for(Trace trace:data) {
			if(!trace.explained()) {return trace.getNextUnexplained();}
		}
		throw new PreconditionViolatedException("getNextUnexplained() invoked, but all traces were explained.");
	}

	public boolean consistent(RewardMachine rm) throws PreconditionViolatedException, BehaviourUndefinedException {
		
		// Pairwise consistency check
		for(int trace1=0;trace1<data.size()-1;trace1++) {			
			Trace t1 = data.get(trace1);
			for (int trace2=trace1+1; trace2<data.size();trace2++) {
				Trace t2 = data.get(trace2);	
				if(!consistent(t1,t2,rm)) {return false;}
			}
		}
		
		return true;
		
	}

	private boolean consistent(Trace t1, Trace t2, RewardMachine rm) throws PreconditionViolatedException, BehaviourUndefinedException {
		if(t1==t2) {throw new PreconditionViolatedException("A trace is always consistent with itself");}
		if(t1.explained()||t2.explained()) {return true;}
		if(!t1.isConsistent(rm)) {return false;}
		if(!t2.isConsistent(rm)) {return false;}
		
		int[] remainder1 = t1.getRemainder(rm);
		int[] remainder2 = t2.getRemainder(rm);
	
		int remainderState1 = remainder1[0];
		int remainderState2 = remainder2[0];
		
		int remainderIndex1 = remainder1[1];
		int remainderIndex2 = remainder2[1];
		
		if(remainderState1!=remainderState2) {return true;}
		if(remainderIndex1==-1 || remainderIndex2==-1) {return true;}
		
		ArrayList<Log> trace1 = t1.getTrace();
		ArrayList<Log> trace2 = t2.getTrace();
		
		while(remainderIndex1<trace1.size() && remainderIndex2<trace2.size()) {
			if(!trace1.get(remainderIndex1).getObservation().equals(trace2.get(remainderIndex2).getObservation())) {return true;}
			if(trace1.get(remainderIndex1).getReward()!=trace2.get(remainderIndex2).getReward()) {
				
				System.out.println(rm.toString());
				System.out.println(t1.toString(remainderState1, remainderIndex1));
				System.out.println(t2.toString(remainderState2, remainderIndex2));
				System.out.println(t1.toString());
				System.out.println(t2.toString());
				return false;}
			remainderIndex1++;
			remainderIndex2++;
		}
	
		return true;
	
	}

	public void explain(RewardMachine rm) throws PreconditionViolatedException, BehaviourUndefinedException {
		for(Trace trace:data) {if(!trace.explained()) {trace.explain(rm);}}
	}

	public void add(ArrayList<ArrayList<Log>> trainingData) {
		for(ArrayList<Log> trace:trainingData) {data.add(new Trace(trace));}
		
	}

	public void reset() {
		for(Trace trace:data) {trace.reset();}
	}
	
	public String toString() {
		String string="";
		for(Trace trace:data) {string+=trace.toString();}
		return string;
	}

	
}
