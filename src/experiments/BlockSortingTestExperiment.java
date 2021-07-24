package experiments;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import agents.Agent_Standard;
import environments.blockSorting.BlockSortingEnvironment;
import exceptions.PreconditionViolatedException;
import rewardmachines.LMRM;

public class BlockSortingTestExperiment implements IExperiment{

	String result;
	
	public BlockSortingTestExperiment() {
		result="";
	}
	
	@Override
	public void run() throws IOException, PreconditionViolatedException {
		int nTypes = 2;
		int nBinsPerType = 1; 
		int nSourcesPerType = 1;
		double nondeterminism = 0;
		int xSize = 3; 
		int ySize = 3;
		BlockSortingEnvironment b = new BlockSortingEnvironment(nTypes, nBinsPerType, nSourcesPerType, nondeterminism, xSize, ySize);
		Agent_Standard agent = new Agent_Standard(new LMRM(), b, b.getNumberOfPropositions(), b.getActions());
		agent.constructAutomaton(agent.explore(10000, 25));
		

	}

	@Override
	public void reportResults(String result) {
		this.result+=result;
	}
	
	
	
}
