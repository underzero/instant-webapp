package ch.ood.iwa.sample;

import java.util.Map;

import org.vaadin.appfoundation.view.ViewHandler;

import ch.ood.iwa.IwaApplication;
import ch.ood.iwa.authorization.ModulePermissionManager;
import ch.ood.iwa.module.ui.LoginView;

/**
 * Sample Application to demonstrate the usage of 
 * Instant Web App Framework IWA or as a starting point for your own
 * IWA application.
 *  
 * @author Mischa
 *
 */
public class IwaSampleApplication extends IwaApplication {

	private static final long serialVersionUID = 1L;
	private static final String FULL_SCREEN_VIEW_URL = "FullScreenSampleView";
	private static final String LOGIN_VIEW_URL = "LoginView";

	@Override
	protected void handleParametersByApplication(Map<String, String[]> parameters) {
		if (parameters.containsKey(FULL_SCREEN_VIEW_URL)) {
			ViewHandler.activateView(IwaSampleFullScreenView.class);
		} else if (parameters.containsKey(LOGIN_VIEW_URL)) {
			ViewHandler.activateView(LoginView.class);
		}			
	}

	@Override
	protected void initApplication() {}

	@Override
	protected void initializeFullScreenViews() {
		registerFullScreenView(IwaSampleFullScreenView.class, FULL_SCREEN_VIEW_URL);
	}

	@Override
	protected void initializeModulesByApplication() {
		new ModulePermissionManager().addPermission(ROOT_USER_ROLE, new IwaSampleModule());	
		getModuleRegistry().registerModule(new IwaSampleModule());				
	}
}
