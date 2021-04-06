package experiments;

import java.io.IOException;

/**
 * An experiment sets up a whole system of environments, agents and reward models and controls its execition via run().
 * 
 * @author Kevin
 *
 */

public interface IExperiment {

	public void run() throws IOException;
	
}
