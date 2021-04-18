package experiments;

import java.io.IOException;

import exceptions.BehaviourUndefinedException;
import exceptions.PreconditionViolatedException;
import rewardmachines.Observation;

public class Main {

	public static void main(String[] args) throws IOException, BehaviourUndefinedException, PreconditionViolatedException {
		 
		//Trial exp = new Trial();
		//exp.run();
		 
		//LogicalTableEntryTests ltet = new LogicalTableEntryTests();
		//ltet.run();
		
		InitialEvaluationExperiment e =  new InitialEvaluationExperiment();
		e.run();
		
	 }
	
}
