package ch.ood.iwa.module.presenter;

import java.io.Serializable;

/**
 * Common Superclass for all IWA Module Presenters
 * 
 * @author Mischa
 *
 * @param <U> The type of the UI (the interface Presenter and View communicate)
 */
public class AbstractModulePresenter<U> implements ModulePresenter<U>, Serializable {
	
	private static final long serialVersionUID = 1L;
	private U ui;
	
	/**
	 * Set the View of this presenter
	 */
	@Override
	public void setUi(U ui) {
		this.ui = ui;		
	}
	
	/**
	 * Returns the View of this presenter
	 * @return
	 */
	protected U getUi() {
		return ui;
	}	
}
