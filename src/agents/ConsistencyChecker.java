package agents;

import java.util.ArrayList;
import exceptions.PreconditionViolatedException;
import rewardmachines.RewardMachine;

public class ConsistencyChecker implements Runnable{

	private ArrayList<Trace> data;
	private RewardMachine rm;
	private int increment;
	private int setOff;
	private boolean consistent;
	
	public ConsistencyChecker(ArrayList<Trace> data, int setOff, int increment) {
		this.data = data;
		this.increment = increment;
		this.setOff = setOff;
		this.consistent=false;
	}
	
	public void setRM(RewardMachine rm) {
		this.rm = rm;
	}
	
	public boolean isConsistent() {
		return consistent;
	}
	
	@Override
	public void run() {
		
		// Pairwise consistency check
		for(int trace1=setOff;trace1<data.size()-1;trace1+=increment) {			
			Trace t1 = data.get(trace1);
			for (int trace2=trace1+1; trace2<data.size();trace2++) {
				Trace t2 = data.get(trace2);	
				try {
					if(!consistent(t1,t2,rm)) {consistent = false; return;}
				} catch (PreconditionViolatedException e) {
					// TODO Auto-generated catch block
					System.out.println(rm.getPushedTransition());
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
		
		consistent = true; return;
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

}
