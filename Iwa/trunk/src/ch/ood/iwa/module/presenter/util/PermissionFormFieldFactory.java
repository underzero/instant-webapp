package ch.ood.iwa.module.presenter.util;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.vaadin.appfoundation.authorization.Role;
import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import ch.ood.iwa.IwaApplication;
import ch.ood.iwa.authorization.ModulePermission;
import ch.ood.iwa.module.Module;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;

/**
 * A specialzed {@link FormFieldFactory} for the Permissions Form
 * 
 * @author Mischa
 *
 */
public class PermissionFormFieldFactory extends DefaultFieldFactory implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	private static String[] visibleFields = {
		"roleName",
		"moduleName",					
	};
	private ComboBox comboBoxRoles;
	private ComboBox comboBoxModules;

	public static String[] getVisibleFields() {
		return visibleFields;
	}	
	
	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		String pid = (String) propertyId;
		
		if ("roleName".equals(pid)) {
			return createRolesComboBox(item, pid);
		} else if ("moduleName".equals(pid)) {
			return createModulesComboBox(item, pid);
			
		} else {
			return null;
		}		
	}	
	
	/**
	 * Creates a Combobox to select a role
	 * 
	 * @param item
	 * @param pid
	 * @return
	 */
	private ComboBox createRolesComboBox(Item item, Object pid) {						
		String fieldName = Lang.getMessage("Role");
		comboBoxRoles = new ComboBox(fieldName);
		comboBoxRoles.setRequired(true);
		comboBoxRoles.setRequiredError(Lang.getMessage("ValueRequiredMsg", fieldName));
		comboBoxRoles.setNullSelectionAllowed(false);
		
		// TODO: View component should not read DB....
		// But maybe the FormFieldFactoy should be considered beeing part of the presenter...? 
		List<Role> roles = FacadeFactory.getFacade().list(Role.class);
		
		if (roles == null) return null;
					
		for (Role role : roles) {			
			comboBoxRoles.addItem(role.getIdentifier());
		}									
		
		// See if we have a value in the field and select according list entry
		@SuppressWarnings("unchecked")
		BeanItem<ModulePermission> permissionItem = (BeanItem<ModulePermission>) item;
		if (permissionItem != null && permissionItem.getBean() != null) {
			comboBoxRoles.select(permissionItem.getBean().getRoleName());
		} else {
			// select any (first) one
			comboBoxRoles.select(comboBoxRoles.getItemIds().iterator().next());
		}
		return comboBoxRoles;		
	}			
	
	/**
	 * Creates a Combobox to select a module
	 * 
	 * @param item
	 * @param pid
	 * @return
	 */
	private ComboBox createModulesComboBox(Item item, Object pid) {				
		String fieldName = Lang.getMessage("Module");
		comboBoxModules = new ComboBox(fieldName);
		comboBoxModules.setRequired(true);
		comboBoxModules.setRequiredError(Lang.getMessage("ValueRequiredMsg", fieldName));
		comboBoxModules.setNullSelectionAllowed(false);
		
		Set<Module> modules = IwaApplication.getInstance().getModuleRegistry().getAllModules();
							
		for (Module module : modules) {
			comboBoxModules.addItem(module.getName());
		}									
		
		// See if we have a value in the field and select according list entry
		@SuppressWarnings("unchecked")
		BeanItem<ModulePermission> permissionItem = (BeanItem<ModulePermission>) item;
		if (permissionItem != null && permissionItem.getBean() != null) {
			comboBoxModules.select(permissionItem.getBean().getModuleName());
		} else {
			// select any (first) one
			comboBoxModules.select(comboBoxModules.getItemIds().iterator().next());
		}		
		return comboBoxModules;		
	}				
}
