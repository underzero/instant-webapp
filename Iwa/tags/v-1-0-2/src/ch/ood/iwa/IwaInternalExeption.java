package ch.ood.iwa;

import org.vaadin.appfoundation.i18n.Lang;

/**
 * IWA internal exeptions
 * 
 * @author Mischa
 *
 */
public class IwaInternalExeption extends IwaException {

	private static final long serialVersionUID = 1L;

	/**
	 * Convenience Constructor
	 * 
	 * @param details
	 */
	public IwaInternalExeption(String details) {
		super (Lang.getMessage("InternalExceptionMsg"), details);
	}
	
}
