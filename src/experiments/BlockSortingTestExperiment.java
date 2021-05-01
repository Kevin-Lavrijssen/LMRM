package experiments;
import java.io.IOException;
import agents.Agent;
import environments.blockSorting.BlockSortingEnvironment;
import exceptions.PreconditionViolatedException;
import rewardmachines.LMRM;

public class BlockSortingTestExperiment implements IExperiment{

	@Override
	public void run() throws IOException, PreconditionViolatedException {
		int nTypes = 2;
		int nBinsPerType = 1; 
		int nSourcesPerType = 1;
		double nondeterminism = 0;
		int xSize = 3; 
		int ySize = 3;
		BlockSortingEnvironment b = new BlockSortingEnvironment(nTypes, nBinsPerType, nSourcesPerType, nondeterminism, xSize, ySize);
		Agent agent = new Agent(new LMRM(), b, b.getNumberOfPropositions(), b.getActions());
		agent.constructAutomaton(agent.explore(4000, 8));
	}
	
	
	
}
