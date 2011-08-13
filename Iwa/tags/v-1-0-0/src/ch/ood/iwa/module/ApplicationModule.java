package ch.ood.iwa.module;

import java.io.Serializable;

import ch.ood.iwa.module.ui.SettingsView;
import ch.ood.iwa.module.ui.WelcomeView;

import com.vaadin.terminal.ThemeResource;

/**
 * The General Applications Module
 * 
 * @author Mischa
 *
 */
public class ApplicationModule extends AbstractModule implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
	private static final ThemeResource icon = new ThemeResource("../runo/icons/16/globe.png");
	
	public ApplicationModule()  {
		super("General");
		
		// This is the place to add more views	
		super.registerView(new SettingsView());
		super.registerView(new WelcomeView());
	}

	@Override
	public ThemeResource getIcon() {
		return icon;
	}	
}
