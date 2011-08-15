package ch.ood.iwa.sample;

import java.io.Serializable;

import ch.ood.iwa.module.ui.AbstractModuleView;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

/**
 * Template View as a starting point for your IWA view
 * 
 * @author Mischa
 *
 */
public class IwaSampleView 
				extends AbstractModuleView<VerticalLayout, IwaSamplePresenter, IwaSamplePresenter.UI>
				implements IwaSamplePresenter.UI, Serializable {

	private static final long serialVersionUID = 1L;
	private Button btnHello;		
	
	public IwaSampleView() {		
		super(new VerticalLayout(), new IwaSamplePresenter());
		getPresenter().setUi(this);
		setName("Template");
		initLayout();
		getPresenter().initListeners(); 		
	}
		
	private void initLayout() {
		btnHello = new Button("Say Hello");
		getContent().addComponent(btnHello);					
	}
	
	@Override
	public boolean isFullScreen() {
		return false;
	}

	@Override
	public Button getHelloButton() {
		return btnHello;
	}
}
