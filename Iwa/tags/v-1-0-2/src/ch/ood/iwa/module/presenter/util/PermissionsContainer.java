package ch.ood.iwa.module.presenter.util;

import java.io.Serializable;
import java.util.List;

import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import ch.ood.iwa.authorization.ModulePermission;

import com.vaadin.data.util.BeanItemContainer;

/**
 * A specialized BeanItemContainer for Permissions, used by Tables
 * 
 * @author Mischa
 *
 */
public class PermissionsContainer extends BeanItemContainer<ModulePermission> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final Object[] VISIBLE_COLUMNS = new Object[] {
    		"roleName", 
    		"moduleName"};
    private static final String[] COL_HEADERS = new String[] {"Role", "Module"};
	
	public PermissionsContainer() {
		super(ModulePermission.class);		
	}

	public static Object[] getVisibleColumns() {
		return VISIBLE_COLUMNS;
	}
	
	public static String[] getColumnCaptions() {
		return COL_HEADERS;
	}
	
	public void populateContainer() {				
		removeAllItems();

		List<ModulePermission> results = FacadeFactory.getFacade().list(ModulePermission.class);
				                  
		if (results != null) {
			for (ModulePermission modulePermission : results) {			
				addItem(modulePermission);
			}
		}
	}
}
