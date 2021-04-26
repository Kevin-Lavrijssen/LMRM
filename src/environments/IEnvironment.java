package environments;

import agents.Log;
import exceptions.BehaviourUndefinedException;

public interface IEnvironment {

	public Log execute(String action) throws BehaviourUndefinedException;
	
	public void reset();
	
	public String[] getActions();
	
	public int getNumberOfPropositions();
	
}
