package ch.ood.iwa.module.ui;

import java.io.Serializable;

import org.vaadin.appfoundation.authentication.data.User;
import org.vaadin.appfoundation.i18n.Lang;

import ch.ood.iwa.module.presenter.UsersPresenter;
import ch.ood.iwa.module.presenter.util.UserFormFieldFactory;
import ch.ood.iwa.module.presenter.util.UsersContainer;
import ch.ood.iwa.ui.UiFactory;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

/**
 * View that handles Users
 * 
 * @author Mischa
 *
 */
public class UsersView 
				extends AbstractModuleView<CustomLayout,  UsersPresenter, UsersPresenter.UI>
				implements UsersPresenter.UI, Serializable {

	private static final long serialVersionUID = 1L;
	private Label label;
	private Table table;	
	private Form form = new Form();
	private Button btnNew;		
	private Button btnSave;
	private Button btnDelete;
	
	public UsersView() {		
		// Common View Settings
		super(new CustomLayout("SimpleTableFormLayout"), new UsersPresenter());
		getPresenter().setUi(this);
		setName(Lang.getMessage("Users"));
		initLayout();
		getPresenter().init();
	
		// Select first Entry (must happen after we initialized the listeners)
		table.select(table.firstItemId());
	}
		
	private void initLayout() {
		label = new Label(Lang.getMessage("Users"));
		getContent().addComponent(label, "label");					
		initTable();
		initForm();
	}
	
	private void initTable() {		
		table = new Table(null, getPresenter().getUsersContainer());		
		table.setWidth("100%");
		table.setHeight("120px");
		table.setVisibleColumns(UsersContainer.getVisibleColumns());
		table.setColumnHeaders(translateStringArray(UsersContainer.getColumnCaptions()));
		table.setSelectable(true);
		table.setNullSelectionAllowed(false);
		table.setImmediate(true);				
		getContent().addComponent(table, "table");
	}	

	private void initForm() {
		btnNew = UiFactory.createButton("New");
		btnDelete = UiFactory.createButton("Delete");
		btnSave = UiFactory.createButton("Save");
		
		form.setFormFieldFactory(new UserFormFieldFactory());
		
		/**
		 * Important because of the User.getPasswordAsString always
		 * returning an empty string
		 */		
		form.setImmediate(false);
		form.setWriteThrough(false);

		getContent().addComponent(btnNew, "btnNew");
		form.getFooter().addComponent(btnDelete);
		form.getFooter().addComponent(btnSave);		
				
		((HorizontalLayout)form.getFooter()).setSpacing(true);		
				
		getContent().addComponent(form, "form");
		
		// Set a dummy value to have the form displayed in any case
		form.setItemDataSource(new BeanItem<User>(new User()));
		form.setVisibleItemProperties(UserFormFieldFactory.getVisibleFields());
	}
	
	@Override
	public void activated(Object... params) {
		getPresenter().activated(params);
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
}
