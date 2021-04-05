package tests;
import rewardmachines.Observation;

public class Main {

	 public static void main(String[] args) {
		 
		 int nPropositions = 5;
		 
		 for (int i = 0; i<Math.pow(2, nPropositions); i++) {
			 Observation o = new Observation(i, nPropositions);
			 System.out.println(o.toString());
		 }
		 
		 Observation o1 = new Observation(10, 5);
		 Observation o2 = new Observation(10, 5);
		 System.out.println(o1.equals(o2));
		 
	 }
	
}
