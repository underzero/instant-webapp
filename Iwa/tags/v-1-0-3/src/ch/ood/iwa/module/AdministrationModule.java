package ch.ood.iwa.module;

import java.io.Serializable;

import ch.ood.iwa.module.ui.PermissionsView;
import ch.ood.iwa.module.ui.UsersView;

import com.vaadin.terminal.ThemeResource;

/**
 * This is the administration module for administrators to administrate things :-)
 * 
 * @author Mischa
 *
 */
public class AdministrationModule extends AbstractModule implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final ThemeResource icon = new ThemeResource("../runo/icons/16/settings.png");
		
	/**
	 * Default constructor
	 */
	public AdministrationModule()  {		
		super("Administration");		
		
		// This is the place to add more views		
		super.registerView(new UsersView());	
		super.registerView(new PermissionsView());
	}

	@Override
	public ThemeResource getIcon() {
		return icon;
	}	
}
