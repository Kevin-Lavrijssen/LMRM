package environments;

import agents.Log;

public interface IEnvironment {

	public Log execute(String action);
	
	public void reset();
	
	public String[] getActions();
	
	public int getNumberOfPropositions();
	
}
