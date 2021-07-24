package experiments;

import java.io.IOException;

import exceptions.PreconditionViolatedException;
import rewardmachines.Observation;

public class Main {

	public static void main(String[] args) throws IOException, PreconditionViolatedException {
		 
		//Trial exp = new Trial();
		//exp.run();
		 
		//LogicalTableEntryTests ltet = new LogicalTableEntryTests();
		//ltet.run();
		
		//InitialEvaluationExperiment e =  new InitialEvaluationExperiment();
		//e.run();
		
		//BlockSortingTestExperiment bE = new BlockSortingTestExperiment();
		//bE.run();
		
		//DivideAndConquerTestExperiment e =  new DivideAndConquerTestExperiment();
		//e.run();
		
		//EvaluationPassiveStateSplittingExperiment e = new EvaluationPassiveStateSplittingExperiment();
		//e.run();
		
		PackageDeliveryExperiment e = new PackageDeliveryExperiment();
		e.run();
		
	 }
	
}
