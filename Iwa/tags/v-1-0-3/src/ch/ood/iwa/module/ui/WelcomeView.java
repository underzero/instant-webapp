package ch.ood.iwa.module.ui;

import org.vaadin.appfoundation.i18n.Lang;

import ch.ood.iwa.IwaApplication;
import ch.ood.iwa.module.presenter.WelcomePresenter;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * A welcome view 
 * 
 * @author Mischa
 *
 */
public class WelcomeView extends AbstractModuleView<VerticalLayout, WelcomePresenter, WelcomePresenter.UI> implements WelcomePresenter.UI {

	private static final long serialVersionUID = 1L;	
		
	public WelcomeView() {
		super("Home", new VerticalLayout(), new WelcomePresenter());
		getPresenter().setUi(this);		
		getContent().setSpacing(true);		
		Embedded e = new Embedded(null, new ThemeResource(IwaApplication.LOGO_FILE_PATH));
		getContent().addComponent(e);
		getContent().addComponent(new Label(Lang.getMessage("WelcomeMsg")));	
	}
	
	@Override
	public void addComponent(Component c) {
		getContent().addComponent(c);
	}
		
	public void addComponentAsFirst(Component c) {
		getContent().addComponentAsFirst(c);
	}
	
	@Override
	public void removeComponent(Component c) {
		getContent().removeComponent(c);
	}	
		
	@Override
	public boolean isFullScreen() {
		return false;
	}
}
