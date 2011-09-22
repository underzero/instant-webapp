package ch.ood.iwa.authorization;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.vaadin.appfoundation.authorization.Role;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import ch.ood.iwa.module.Module;


/**
 * Simple Permission Manager for the IWA Framework that handles Module Permissions
 * 
 * @author Mischa
 *
 */
public class ModulePermissionManager {

	/**
	 * Returns whether a role has the permission for a given module
	 * 
	 * @param role
	 * @param module
	 * @return true if permission is granted, false if not
	 */
	public boolean hasPermission(Role role, Module module) {
		if (readPermission(role.getIdentifier(), module.getName()) == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Returns whether a role has the permission for a given module
	 *
	 * @param roleName
	 * @param module
	 * @return true if permission is granted, false if not
	 */
	public boolean hasPermission(String roleName, Module module) {
		if (readPermission(roleName, module.getName()) == null) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Adds a permission. Behaves idempotent, i.e. does not complain
	 * if permission has already been there.
	 * 
	 * @param role
	 * @param module
	 */
	public void addPermission(Role role, Module module) {
		if (readPermission(role.getIdentifier(), module.getName()) == null) {
			ModulePermission permission = new ModulePermission(role.getIdentifier(), module.getName());
			FacadeFactory.getFacade().store(permission);
		}
	}

	/**
	 * Adds a permission. Behaves idempotent, i.e. does not complain
	 * if permission has already been there.
	 * 
	 * @param role
	 * @param module
	 */
	public void addPermission(String roleName, Module module) {
		if (readPermission(roleName, module.getName()) == null) {
			ModulePermission permission = new ModulePermission(roleName, module.getName());
			FacadeFactory.getFacade().store(permission);
		}
	}	
	
	/**
	 * Removes a permission. Behaves idempotent, i.e. does not complain
	 * if permission has not been there
	 *  
	 * @param role
	 * @param module
	 */
	public void removePermission(Role role, Module module) {
		ModulePermission permission = readPermission(role.getIdentifier(), module.getName());
		
		if (permission != null) {
			FacadeFactory.getFacade().delete(permission);
		}
	}
	
	/**
	 * Removes a permission. Behaves idempotent, i.e. does not complain
	 * if permission has not been there
	 *  
	 * @param role
	 * @param module
	 */
	public void removePermission(String roleName, Module module) {
		ModulePermission permission = readPermission(roleName, module.getName());
		
		if (permission != null) {
			FacadeFactory.getFacade().delete(permission);
		}
	}	

	/**
	 * Removes all permission entries for a given role
	 * 
	 * @param role
	 */
	public void removeAllPermissions(Role role) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleName", role.getIdentifier());

		Collection<ModulePermission> permissions = FacadeFactory.getFacade().list(
				"SELECT p FROM ModulePermission p WHERE p.roleName = :roleName", params);
		
		FacadeFactory.getFacade().deleteAll(permissions);
	}
	
	/**
	 * Internal helper method
	 * 
	 * @param role
	 * @param module
	 * @return
	 */
	private ModulePermission readPermission(String roleName, String moduleName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleName", roleName);
		params.put("moduleName", moduleName);

		ModulePermission permission = FacadeFactory
				.getFacade()
				.find("SELECT p FROM ModulePermission p WHERE p.roleName = :roleName AND p.moduleName = :moduleName",
						params);
		
		return permission;
	}
}
