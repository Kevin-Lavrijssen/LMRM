package rewardmachines;
import java.util.Random;
import exceptions.BehaviourUndefinedException;
import exceptions.PreconditionViolatedException;
import tools.Distance;

public class Evaluator {
	
	public Evaluator(RewardMachine target, RewardMachine result) {
		this.target=target;
		this.result=result;
		evaluated=false;
		minkowski1 = 0;
		minkowski2 =0;
		mispredictions = 0;
	}

	private RewardMachine target;
	
	private RewardMachine result;
	
	boolean evaluated;
	
	public boolean getEvaluated() {
		return evaluated;
	}
	
	private int minkowski1;
	
	public int getManhattanDistance() {
		return minkowski1;
	}
	
	private int minkowski2;
	
	public int getEuclideanDistance() {
		return minkowski2;
	}
	
	private int mispredictions;
	
	public int getMispredictions() {
		return mispredictions;
	}
	
	private int stateImprovement;
	
	public int getStateImprovement() {
		return stateImprovement;
	}
	
	private double relativeStateImprovement;
	
	public double getRelativeStateImprovement() {
		return relativeStateImprovement;
	}
	
	private int transitionImprovement;
	
	public int getTransitionImprovement() {
		return transitionImprovement;
	}
	
	private double relativeTransitionImprovement;
	
	public double getRelativeTransitionImprovement() {
		return relativeTransitionImprovement;
	}
	
	private long resultObjectSize;
	
	public long getResultObjectSize() {
		return resultObjectSize;
	}
	
	private long targetObjectSize;
	
	public long getTargetObjectSize() {
		return targetObjectSize;
	}
	
	public void evaluate(int nTraces, int nSteps, int nPropositions) throws PreconditionViolatedException, BehaviourUndefinedException {
		if(evaluated) {throw new PreconditionViolatedException("The evaluation has already been completed");}
		
		stateImprovement = target.getNumberOfStates()-result.getNumberOfStates();
		transitionImprovement = target.getTableSize()-result.getTableSize();
		
		int sizeAlphabet = (int)Math.pow(2, nPropositions);
		Random r = new Random();
		
		for (int trace=0; trace<nTraces; trace++) {
			target.reset();
			result.reset();
			
			int[] targetTrace = new int[nSteps];
			int[] resultTrace = new int[nSteps];
			
			for (int step=0; step<nSteps; step++) {
				Observation next = new Observation(r.nextInt(sizeAlphabet), nPropositions);
				targetTrace[step] = target.execute(next);
				resultTrace[step] = result.execute(next);
			}
			
			minkowski1+=Distance.minkowski(targetTrace, resultTrace, 1);
			minkowski2+=Distance.minkowski(targetTrace, resultTrace, 2);
			if(minkowski2!=0) {System.out.println("Euclidean is no longer 0");}
			mispredictions+=Distance.mispredictions(targetTrace, resultTrace);
			
		}
		
		relativeStateImprovement = result.getNumberOfStates()/target.getNumberOfStates();
		relativeTransitionImprovement = result.getTableSize()/target.getTableSize();
		
		//resultObjectSize = ObjectSizeFetcher.getObjectSize(result);
		//targetObjectSize = ObjectSizeFetcher.getObjectSize(target);
		
		evaluated = true;
		
	}
	
}
