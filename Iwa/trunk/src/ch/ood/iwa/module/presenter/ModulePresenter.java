package ch.ood.iwa.module.presenter;

import java.io.Serializable;

/**
 * The interface all IWA presenters must implement
 * 
 * @author Mischa
 *
 * @param <U>
 */
public interface ModulePresenter<U> extends Serializable {
	
	/**
	 * Sets the View for the presenter
	 * 
	 * @param ui
	 */
	void setUi(U ui);
	
}
