package experiments;

import java.io.IOException;

import exceptions.PreconditionViolatedException;

/**
 * An experiment sets up a whole system of environments, agents and reward models and controls its execition via run().
 * 
 * @author Kevin
 *
 */

public interface IExperiment {

	public void run() throws IOException, PreconditionViolatedException;
	
	public void reportResults(String result);
	
}
