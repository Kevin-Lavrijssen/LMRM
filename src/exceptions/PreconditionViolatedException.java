package exceptions;

@SuppressWarnings("serial")
public class PreconditionViolatedException extends Exception{
	
	public PreconditionViolatedException(String message) {
		super(message);
	}

}
