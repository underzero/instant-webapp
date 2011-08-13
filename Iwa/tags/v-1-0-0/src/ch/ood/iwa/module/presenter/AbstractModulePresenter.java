package ch.ood.iwa.module.presenter;

import java.io.Serializable;

/**
 * Common Superclass for all IWA Module Presenters
 * 
 * @author Mischa
 *
 * @param <U>
 */
public class AbstractModulePresenter<U> implements ModulePresenter<U>, Serializable {
	
	private static final long serialVersionUID = 1L;

	private U ui;
	
	@Override
	public void setUi(U ui) {
		this.ui = ui;		
	}
	
	protected U getUi() {
		return ui;
	}	
}
