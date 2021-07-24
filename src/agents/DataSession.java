package agents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import exceptions.PreconditionViolatedException;
import rewardmachines.RewardMachine;

public class DataSession {
	
	ArrayList<Trace> data;
	
	// Temporary multiprocessing
	ConsistencyChecker[] checkers;
	ArrayList<Thread> currentThreads;

	public DataSession(ArrayList<ArrayList<Log>> traces) {
		this.data = new ArrayList<Trace>();
		for (ArrayList<Log> trace : traces) {
			Trace t = new Trace(trace);
			data.add(t);
		}
		setConsistencyCheckers();
		currentThreads = new ArrayList<Thread>();
	}
	
	private void setConsistencyCheckers() {
		int cores = Runtime.getRuntime().availableProcessors();
		checkers = new ConsistencyChecker[cores];
		for (int i=0; i<cores; i++) {
			checkers[i] = new ConsistencyChecker(data, i, cores);
		}
	}

	public DataSession() {
		this.data = new ArrayList<Trace>();
		setConsistencyCheckers();
		currentThreads = new ArrayList<Thread>();
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

	public boolean consistent(RewardMachine rm) throws PreconditionViolatedException {
		
		// Individual consistency check
		for (Trace trace:data) {
			if(!trace.isConsistent(rm)) {return false;}
		}
		
		for (ConsistencyChecker checker:checkers) {
			checker.setRM(rm);
		}
		
		for (ConsistencyChecker checker:checkers) {
			Thread thread = new Thread(checker);
			thread.start();
			currentThreads.add(thread);
		}
		
		boolean done = false;
		while(done==false) {
			done=true;
			for(Thread t : currentThreads) {
				if(t.getState()!=Thread.State.TERMINATED) {try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} done = false; break;}
			}
		}
		
		return checkersConsistent();
		
	}

	private boolean checkersConsistent() {
		for(ConsistencyChecker c:checkers) {
			if(!c.isConsistent()) {return false;}
		}
		return true;
	}

	private boolean consistent(Trace t1, Trace t2, RewardMachine rm) throws PreconditionViolatedException {
		if(t1==t2) {throw new PreconditionViolatedException("A trace is always consistent with itself");}
		if(t1.explained()||t2.explained()) {return true;}
		
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
			if(trace1.get(remainderIndex1).getReward()!=trace2.get(remainderIndex2).getReward()) {return false;}
			remainderIndex1++;
			remainderIndex2++;
		}
	
		return true;
	
	}

	public void explain(RewardMachine rm) throws PreconditionViolatedException {
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

	public String[] getPathToBorder() throws PreconditionViolatedException {
		
		for(Trace trace:data) {
			if(!trace.explained()) {
				return trace.getExplainedPrefix();
			}
		}
		
		return null;
	}

	
}
