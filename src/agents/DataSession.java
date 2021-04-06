package agents;

import java.io.IOException;
import java.util.ArrayList;

import rewardmachines.IRewardMachine;

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

	public void reset() {
		for (Trace trace:data) {
			trace.reset();
		}
	}
	
	public void add(ArrayList<ArrayList<Log>> traces) {
		for (ArrayList<Log> trace : traces) {
			Trace t = new Trace(trace);
			data.add(t);
		}
	}
	
	public boolean consistent(IRewardMachine rm) {
		for (Trace trace:data) {
			if(!trace.consistent(rm)) {return false;}
		}
		return true;
	}
	
	public void explain(IRewardMachine rm) {
		for (Trace trace:data) {
			trace.explain(rm);
		}
	}
	
	public boolean explained() {
		for (Trace trace:data) {
			if(!trace.explained()) {return false;}
		}
		return true;
	}
	
	public Unexplained getNextUnexplained() throws IOException{
		for (Trace trace:data) {
			if(!trace.explained()) {return trace.getNextUnexplained();}
		}
		throw new IOException("The traces were already explained, cannot retrieve unexplained.");
	}
	
}
