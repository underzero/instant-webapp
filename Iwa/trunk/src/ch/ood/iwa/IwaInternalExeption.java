package ch.ood.iwa;

import org.vaadin.appfoundation.i18n.Lang;

public class IwaInternalExeption extends IwaException {

	private static final long serialVersionUID = 1L;

	public IwaInternalExeption(String details) {
		super (Lang.getMessage("InternalExceptionMsg"), details);
	}
	
}
