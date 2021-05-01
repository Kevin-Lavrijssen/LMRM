package experiments;

import java.io.IOException;

import exceptions.PreconditionViolatedException;
import rewardmachines.LogicalTableEntry;
import rewardmachines.Observation;

public class LogicalTableEntryTests implements IExperiment{

	@Override
	public void run() throws IOException, PreconditionViolatedException {
		Observation o1 = new Observation("01");
		LogicalTableEntry entry = new LogicalTableEntry(1, o1, 1, 1);
		
		if(!entry.evaluate(1, o1)) {System.out.println("Basic evaluation does not work");}
		System.out.print(entry.toString());
		
		Observation o2 = new Observation("11");
		entry.merge(o2);
		
		Observation o3 = new Observation("10");
		
		if(!entry.evaluate(1, o1)) {System.out.println("Merged evaluation does not work");}
		if(!entry.evaluate(1, o2)) {System.out.println("Merged evaluation does not work");}
		if(entry.evaluate(1, o3)) {System.out.println("Merged evaluation does not work");}
		System.out.print(entry.toString());
		
		entry = new LogicalTableEntry(1, o1, 1, 1);
		entry.merge(o3);
		
		if(!entry.evaluate(1, o1)) {System.out.println("Merged evaluation does not work");}
		if(entry.evaluate(1, o2)) {System.out.println("Merged evaluation does not work");}
		if(!entry.evaluate(1, o3)) {System.out.println("Merged evaluation does not work");}
		System.out.print(entry.toString());
		
	}

	
}
