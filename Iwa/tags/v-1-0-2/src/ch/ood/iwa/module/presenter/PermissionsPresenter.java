package ch.ood.iwa.module.presenter;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.vaadin.appfoundation.authentication.data.User;
import org.vaadin.appfoundation.authorization.Role;
import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import ch.ood.iwa.IwaPersistenceHelper;
import ch.ood.iwa.authorization.ModulePermission;
import ch.ood.iwa.module.presenter.util.PermissionFormFieldFactory;
import ch.ood.iwa.module.presenter.util.PermissionsContainer;
import ch.ood.iwa.module.presenter.util.RoleFormFieldFactory;
import ch.ood.iwa.module.ui.IwaModuleUI;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.Table;

/**
 * Presenter for the Permissions View
 * 
 * @author Mischa
 *
 */
public class PermissionsPresenter extends AbstractModulePresenter<PermissionsPresenter.UI> 
								  implements AbstractSelect.NewItemHandler, Serializable {

	private static final long serialVersionUID = 1L;
	private PermissionsContainer permissionsContainer;
	
	/**
	 * This is the interface that decouples Presenter and View
	 * 
	 */
	public static interface UI extends IwaModuleUI {		
		Table getTable();
		Form getForm();		
		Button getBtnNew();
		Button getBtnDelete();
		Button getBtnSave();
		Form getFrmRoles();		
		Button getBtnDeleteRole();						
	}

	/**
	 * Constructor
	 */
	public PermissionsPresenter() {		
		if (permissionsContainer == null) {
			permissionsContainer = new PermissionsContainer();
			permissionsContainer.populateContainer();			
		}					
	}
		
	public void initListeners() {		
		getUi().getTable().addListener(tableValueChangeListener);
		getUi().getBtnNew().addListener(buttonClickListener);
		getUi().getBtnDelete().addListener(buttonClickListener);
		getUi().getBtnSave().addListener(buttonClickListener);					
		getUi().getBtnDeleteRole().addListener(buttonClickListener);
		refreshView();
	}
	
	/**
	 * Value Change Listener for the Table
	 */	
	private ValueChangeListener tableValueChangeListener = new ValueChangeListener() {				
		private static final long serialVersionUID = 1L;

		@Override
		public void valueChange(ValueChangeEvent event) {			
			synchronizeFormWithTable();
			refreshView();
		}
	};
	
	private BeanItem<ModulePermission> getSelectedPermissionItem() {
		Object selectedItemId = getUi().getTable().getValue();						
		@SuppressWarnings("unchecked")
		BeanItem<ModulePermission> permissionBeanItem = (BeanItem<ModulePermission>)getUi().getTable().getItem(selectedItemId);
		return permissionBeanItem;
	}
	
	private ModulePermission getSelectedPermission () {
		if (getSelectedPermissionItem() != null) {
			return getSelectedPermissionItem().getBean();
		} else {
			return null;
		}
	}
		
	/**
	 * Button Click Listener
	 */
	private ClickListener buttonClickListener = new ClickListener() {
		private static final long serialVersionUID = 1L;

		@Override
		public void buttonClick(ClickEvent event) {						
			if (event.getButton().equals(getUi().getBtnNew())) {
				handleNewButtonClicked();
			} else if (event.getButton().equals(getUi().getBtnDelete())) {
				handleDeleteButtonClicked();
			} else if (event.getButton().equals(getUi().getBtnSave())) {
				handleSaveButtonClicked();
			} else if (event.getButton().equals(getUi().getBtnDeleteRole())) {
				handleDeleteRoleButtonClicked();
			}
		}
	};
		
	private void synchronizeFormWithTable() {
		if (getSelectedPermissionItem() != null) {
			getUi().getForm().setItemDataSource(getSelectedPermissionItem());						
		} else {
			// set a dummy bean item to prevent the fields from disappearing
			getUi().getForm().setItemDataSource(new BeanItem<ModulePermission>(new ModulePermission()));			
		}
		// must happen after setting the datasource...
		getUi().getForm().setVisibleItemProperties(PermissionFormFieldFactory.getVisibleFields());		
	}
	
	private void handleNewButtonClicked() {
		// Unselect table
		getUi().getTable().select(getUi().getTable().getNullSelectionItemId());
		
		// set a fresh new bean item
		getUi().getForm().setItemDataSource(new BeanItem<ModulePermission>(new ModulePermission()));			
	
		// must happen after setting the datasource...
		getUi().getForm().setVisibleItemProperties(PermissionFormFieldFactory.getVisibleFields());
		
		getUi().getBtnSave().setEnabled(true);
	}
	
	private void handleDeleteButtonClicked() {			
		// Cache the current items neighbours
		ModulePermission selectedPermission = getSelectedPermission();
		
		if (selectedPermission == null) return;
		
		Object nextNeighbour = getUi().getTable().nextItemId(selectedPermission);
		Object previousNeighbour = getUi().getTable().prevItemId(selectedPermission);
		
		// remove from store
		FacadeFactory.getFacade().delete(selectedPermission);
		
		// remove from container, refreshes the view
		permissionsContainer.removeItem(selectedPermission);
		
		// Select some other row
		if (nextNeighbour != null) {
			getUi().getTable().select(nextNeighbour);
		} else if (previousNeighbour != null){
			getUi().getTable().select(previousNeighbour);
		} else if (getUi().getTable().size() == 0) {
			getUi().getTable().select(null);
			
		} else {
			getUi().getTable().select(getUi().getTable().firstItemId());
		}				
	}	
	
	private void handleSaveButtonClicked() {
		getUi().getForm().commit();		
		@SuppressWarnings("unchecked")
		BeanItem<ModulePermission> permissionBeanItem = (BeanItem<ModulePermission>) getUi().getForm().getItemDataSource();
		ModulePermission permission = permissionBeanItem.getBean();
		
		// Check for duplicates as Unique Keys seem not work properly in GAE		
		Collection<ModulePermission> existingPermissions = getPermissionsContainer().getItemIds();		
		for (ModulePermission existingPermission : existingPermissions) {
			if (existingPermission.equals(permission)) {
				return;
			}
		}	
		FacadeFactory.getFacade().store(permission);		
		updateTable(permission);	
	}	
		
	private void handleDeleteRoleButtonClicked() {
		// get selected role		
		@SuppressWarnings("unchecked")
		BeanItem<Role> roleBeanItem = (BeanItem<Role>)getUi().getFrmRoles().getItemDataSource();
		Role role = roleBeanItem.getBean();
				
		// check that not used somwhere and is save to delete
		List<User> users = FacadeFactory.getFacade().list(User.class);
		
		for (User user : users) {
			if (user.getRole().equals(role.getIdentifier())) {
				getUi().showError(Lang.getMessage("RoleStillInUseMsg", "User"), null);
				return;
			}
		}
		
		List<ModulePermission> modulePermissions = FacadeFactory.getFacade().list(ModulePermission.class);
		
		for (ModulePermission permission : modulePermissions) {
			if (permission.getRoleName().equals(role.getIdentifier())) {
				getUi().showError(Lang.getMessage("RoleStillInUseMsg", "Permission"), null);
				return;
			}
		}		
				
		// delete		
		FacadeFactory.getFacade().delete(new IwaPersistenceHelper().refreshRole(role));
		
		// Update UI to hide the Role
		getUi().getFrmRoles().setItemDataSource(new BeanItem<Role>(new Role()));
		getUi().getFrmRoles().setVisibleItemProperties(RoleFormFieldFactory.getVisibleFields());
		synchronizeFormWithTable();				
	}	
	
	private void updateTable(ModulePermission selectedPermission) {
		permissionsContainer.populateContainer();	
		
		// Get the current id 
		selectedPermission = new IwaPersistenceHelper().refreshPermission(selectedPermission);
		
		// Select row
		getUi().getTable().select(selectedPermission);						
	}
	
	public PermissionsContainer getPermissionsContainer() {
		return permissionsContainer;
	}
	
	private void refreshView() {
		if (getSelectedPermission() == null) {
			getUi().getBtnNew().setEnabled(true);
			getUi().getBtnDelete().setEnabled(false);
			getUi().getBtnSave().setEnabled(false);			
		} else {
			getUi().getBtnNew().setEnabled(true);
			getUi().getBtnDelete().setEnabled(true);
			getUi().getBtnSave().setEnabled(true);			
		}
	}

	@Override
	public void addNewItem(String newItemCaption) {
		
		// read existing roles
		List<Role> existingRoles = FacadeFactory.getFacade().list(Role.class);
					
		// go through all roles, leave if role already existing
		for (Role role : existingRoles) {
			if (role.getIdentifier().equals(newItemCaption)) return;
		}		
			
		// Store new entry
		FacadeFactory.getFacade().store(new Role(newItemCaption));
		
		// Inform user
		getUi().showInfo(Lang.getMessage("ChangesSavedMsg"), null);
		
		// Update UI to show the new Role
		getUi().getFrmRoles().setItemDataSource(new BeanItem<Role>(new Role()));
		getUi().getFrmRoles().setVisibleItemProperties(RoleFormFieldFactory.getVisibleFields());
		synchronizeFormWithTable();
	}

}
