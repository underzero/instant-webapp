package ch.ood.iwa.module.ui;

import java.io.Serializable;

import org.vaadin.appfoundation.authorization.Role;
import org.vaadin.appfoundation.i18n.Lang;

import ch.ood.iwa.authorization.ModulePermission;
import ch.ood.iwa.module.presenter.PermissionsPresenter;
import ch.ood.iwa.module.presenter.util.PermissionFormFieldFactory;
import ch.ood.iwa.module.presenter.util.PermissionsContainer;
import ch.ood.iwa.module.presenter.util.RoleFormFieldFactory;
import ch.ood.iwa.ui.UiFactory;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

/**
 * View that handles Permissions and Roles
 * 
 * @author Mischa
 *
 */
public class PermissionsView 
				extends AbstractModuleView<CustomLayout, PermissionsPresenter, PermissionsPresenter.UI>
				implements PermissionsPresenter.UI, Serializable {

	private static final long serialVersionUID = 1L;
	private Label label;
	private Table table;	
	private Form form = new Form();
	private Button btnNew;		
	private Button btnSave;
	private Button btnDelete;
	private Label lblRoles;
	private Form frmRoles = new Form();	
	private Button btnDeleteRole;	
	
	public PermissionsView() {		
		// Common View Settings
		super("Permissions", new CustomLayout("PermissionsViewLayout"), new PermissionsPresenter());
		getPresenter().setUi(this);		
		initLayout();
		getPresenter().initListeners();
	
		// Select first Entry (must happen after we initialized the listeners)
		table.select(table.firstItemId());
	}
					
	private void initLayout() {
		// Permissions
		label = new Label(Lang.getMessage("Permissions"));
		getContent().addComponent(label, "lblPermissions");					
		initTable();
		initForm();
		
		// Roles
		lblRoles = new Label(Lang.getMessage("Roles"));
		getContent().addComponent(lblRoles, "lblRoles");
		initRolesForm();
	}
	
	private void initTable() {		
		table = new Table(null, getPresenter().getPermissionsContainer());		
		table.setWidth("100%");
		table.setHeight("200px");
		table.setVisibleColumns(PermissionsContainer.getVisibleColumns());
		table.setColumnHeaders(translateStringArray(PermissionsContainer.getColumnCaptions()));
		table.setSelectable(true);
		table.setNullSelectionAllowed(false);
		table.setImmediate(true);				
		getContent().addComponent(table, "tblPermissions");
	}	

	private void initForm() {
		btnNew = UiFactory.createButton("New");
		btnDelete = UiFactory.createButton("Delete");
		btnSave = UiFactory.createButton("Save");

		form.setFormFieldFactory(new PermissionFormFieldFactory());
		form.setImmediate(true);
		form.setWriteThrough(true);
		
		getContent().addComponent(btnNew, "btnNew");
		form.getFooter().addComponent(btnDelete);
		form.getFooter().addComponent(btnSave);			
				
		((HorizontalLayout)form.getFooter()).setSpacing(true);		
				
		getContent().addComponent(form, "frmPermissions");
		
		// Set a dummy value to have the form displayed in any case
		form.setItemDataSource(new BeanItem<ModulePermission>(new ModulePermission()));
		form.setVisibleItemProperties(PermissionFormFieldFactory.getVisibleFields());
	}
	
	private void initRolesForm() {		
		btnDeleteRole = UiFactory.createButton("Delete");		
		frmRoles.setFormFieldFactory(new RoleFormFieldFactory(getPresenter()));				
		frmRoles.getFooter().addComponent(btnDeleteRole);				
				
		((HorizontalLayout)frmRoles.getFooter()).setSpacing(true);						
		getContent().addComponent(frmRoles, "frmRoles");
		
		// Set a dummy value to have the form displayed in any case
		frmRoles.setItemDataSource(new BeanItem<Role>(new Role()));
		frmRoles.setVisibleItemProperties(RoleFormFieldFactory.getVisibleFields());
	}
	
	@Override
	public boolean isFullScreen() {
		return false;
	}

	@Override
	public Table getTable() {
		return table;
	}

	@Override
	public Form getForm() {
		return form;
	}

	@Override
	public Button getBtnNew() {
		return btnNew;
	}

	@Override
	public Button getBtnSave() {
		return btnSave;
	}

	@Override
	public Button getBtnDelete() {
		return btnDelete;
	}
	
	@Override
	public Form getFrmRoles() {
		return frmRoles;
	}

	@Override
	public Button getBtnDeleteRole() {
		return btnDeleteRole;
	}
}
