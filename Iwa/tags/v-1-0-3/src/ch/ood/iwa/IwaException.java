package ch.ood.iwa;

/**
 * An "Layer Supertype" pattern implementation 
 * 
 * @author Mischa
 *
 */
public class IwaException extends Exception {

	private static final long serialVersionUID = 1L;
	private String details;
		
	/**
	 * Convenience Constructor
	 * 
	 * @param message
	 * @param details
	 */
	public IwaException (String message, String details) {
		super(message);
		this.details = details;
	}
	
	public String getDetails() {
		return details;
	}
	
}
