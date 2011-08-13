package ch.ood.iwa.sample;

import java.util.Map;

import ch.ood.iwa.IwaApplication;
import ch.ood.iwa.authorization.ModulePermissionManager;

public class IwaSampleApplication extends IwaApplication {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleParametersByApplication(Map<String, String[]> arg0) {
				
	}

	@Override
	protected void initApplication() {
		
	}

	@Override
	protected void initializeFullScreenViews() {
				
	}

	@Override
	protected void initializeModulesByApplication() {
		new ModulePermissionManager().addPermission(ROOT_USER_ROLE, new IwaSampleModule());	
		getModuleRegistry().registerModule(new IwaSampleModule());				
	}
}
