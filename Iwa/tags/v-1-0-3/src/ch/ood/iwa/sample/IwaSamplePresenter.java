package ch.ood.iwa.sample;

import java.io.Serializable;

import ch.ood.iwa.module.presenter.AbstractModulePresenter;
import ch.ood.iwa.module.ui.IwaModuleUI;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * Template Presenter as a start for your implementation
 * 
 * @author Mischa
 *
 */
public class IwaSamplePresenter extends AbstractModulePresenter<IwaSamplePresenter.UI> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Interface for communication between Presenter and View
	 */
	public static interface UI extends IwaModuleUI {		
		Button getHelloButton();
	}
	
	/**
	 * Do the event wiring 
	 */
	public void initListeners() {		
		getUi().getHelloButton().addListener(buttonClickListener);
	}
	
	/**
	 * Button Click Listener
	 */
	private ClickListener buttonClickListener = new ClickListener() {
		private static final long serialVersionUID = 1L;

		@Override
		public void buttonClick(ClickEvent event) {
			if (event.getButton().equals(getUi().getHelloButton())) {
				getUi().showInfo("Hello", "Hello, World!");
			} 		
		}
	};	
}
