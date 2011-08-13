package ch.ood.iwa;

public class IwaException extends Exception {

	private static final long serialVersionUID = 1L;
	private String details;
		
	public IwaException (String message, String details) {
		super(message);
		this.details = details;
	}
	
	public String getDetails() {
		return details;
	}
	
}
