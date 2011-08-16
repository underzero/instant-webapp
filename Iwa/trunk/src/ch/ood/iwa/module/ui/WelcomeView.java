package ch.ood.iwa.module.ui;

import org.vaadin.appfoundation.i18n.Lang;

import ch.ood.iwa.IwaApplication;
import ch.ood.iwa.module.presenter.WelcomePresenter;

import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.terminal.ThemeResource;
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
		super(new VerticalLayout(), new WelcomePresenter());
		getPresenter().setUi(this);
		setName(Lang.getMessage("Home"));
		getContent().setSpacing(true);		
		Embedded e = new Embedded(null, new ThemeResource(IwaApplication.LOGO_FILE_PATH));
		getContent().addComponent(e);
		getContent().addComponent(new Label(Lang.getMessage("WelcomeMsg")));
	}

	@Override
	public void activated(Object... params) {
	}

	@Override
	public void deactivated(Object... params) {
	}

	@Override
	public void click(ClickEvent event) {
	}
	
	@Override
	public boolean isFullScreen() {
		return false;
	}
}
