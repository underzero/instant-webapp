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
	
	void setUi(U ui);
	
}
