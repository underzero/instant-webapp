package ch.ood.iwa.module.presenter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.vaadin.appfoundation.authentication.data.User;
import org.vaadin.appfoundation.authentication.util.PasswordUtil;
import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;
import org.vaadin.dialogs.ConfirmDialog;

import ch.ood.iwa.IwaPersistenceHelper;
import ch.ood.iwa.module.presenter.util.UserFormFieldFactory;
import ch.ood.iwa.module.presenter.util.UsersContainer;
import ch.ood.iwa.module.ui.IwaModuleUI;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.Table;

/**
 * Presenter for the Users View
 * 
 * @author Mischa
 *
 */
public class UsersPresenter extends AbstractModulePresenter<UsersPresenter.UI> implements Serializable {

	private static final long serialVersionUID = 1L;
	private UsersContainer usersContainer;
	
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
	}

	/**
	 * Constructor
	 */
	public UsersPresenter() {		
		if (usersContainer == null) {
			usersContainer = new UsersContainer();
			usersContainer.populateContainer();			
		}					
	}
		
	/**
	 * Initializes the presenter
	 */
	public void init() {		
		initListeners();
		refreshView();
	}
	
	private void initListeners() {		
		getUi().getTable().addListener(tableValueChangeListener);
		getUi().getBtnNew().addListener(buttonClickListener);
		getUi().getBtnDelete().addListener(buttonClickListener);
		getUi().getBtnSave().addListener(buttonClickListener);						
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
	
	private BeanItem<User> getSelectedUserItem() {
		Object selectedItemId = getUi().getTable().getValue();						
		@SuppressWarnings("unchecked")
		BeanItem<User> userBeanItem = (BeanItem<User>)getUi().getTable().getItem(selectedItemId);
		return userBeanItem;
	}
	
	private User getSelectedUser () {
		if (getSelectedUserItem() != null) {
			return getSelectedUserItem().getBean();
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
			}
		}
	};
		
	private void synchronizeFormWithTable() {
		if (getSelectedUserItem() != null) {
			getUi().getForm().setItemDataSource(getSelectedUserItem());						
		} else {
			// set a dummy bean item to prevent the fields from disappearing
			getUi().getForm().setItemDataSource(new BeanItem<User>(new User()));			
		}
		// must happen after setting the datasource...
		getUi().getForm().setVisibleItemProperties(UserFormFieldFactory.getVisibleFields());		
	}
	
	private void handleNewButtonClicked() {
		// Unselect table
		getUi().getTable().select(getUi().getTable().getNullSelectionItemId());
		
		// set a fresh new bean item
		getUi().getForm().setItemDataSource(new BeanItem<User>(new User()));			
	
		// must happen after setting the datasource...
		getUi().getForm().setVisibleItemProperties(UserFormFieldFactory.getVisibleFields());
		
		getUi().getBtnSave().setEnabled(true);
	}
	
	private void handleDeleteButtonClicked() {
		getUi().showConfirmation("", Lang.getMessage("ConfirmDeleteMsg"), new ConfirmDeleteUserDialogListener());
	}
	
	private void deleteUser() {			
		// Cache the current items neighbours
		User selectedUser = getSelectedUser();
		
		if (selectedUser == null) return;
		
		Object nextNeighbour = getUi().getTable().nextItemId(selectedUser);
		Object previousNeighbour = getUi().getTable().prevItemId(selectedUser);
		
		// remove from store
		FacadeFactory.getFacade().delete(selectedUser);
		
		// remove from container, refreshes the view
		usersContainer.removeItem(selectedUser);
		
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
		BeanItem<User> userBeanItem = (BeanItem<User>) getUi().getForm().getItemDataSource();
		User user = userBeanItem.getBean();
		
		/**
		 * Avoid duplicate entry for the same email address.
		 * This has to be checked programatically as GAE does not 
		 * support unique constraints
		 */
		if (user.getId() == null  && isUserExisting(user.getEmail())) {
			getUi().showError(Lang.getMessage("DuplicateEntryMsg", "email"), null);
			return;
		}		
		
		// Encrypt and set the password, only if a new one is given
		if (user.getNewPassword() != null && user.getNewPassword().length() > 0) {
			String hashedPassword = PasswordUtil.generateHashedPassword(user.getNewPassword());
			user.setPassword(hashedPassword);
		}

		try {
			// Store the user
			FacadeFactory.getFacade().store(user);
			
		} catch (Exception e) {
			getUi().showError(Lang.getMessage("ChangesSaveFailedMsg") , "Details: " + e.getMessage());
		}		
		updateTable();
	}	
		
	/**
	 * Checks whether a User with that given email address is
	 * already in the DB 
	 * 
	 * @param email
	 * @return
	 */
	private boolean isUserExisting(String email) {		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);

		User user = FacadeFactory.getFacade().find("SELECT u FROM User u WHERE u.email = :email", params);
		
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}		
	
	public UsersContainer getUsersContainer() {
		return usersContainer;
	}
		
	public void activated(Object... params) {
		updateTable();
	}
	
	private void updateTable() {		
		User selectedUser = getSelectedUser();
		
		if (selectedUser == null) {			
			@SuppressWarnings("unchecked")
			BeanItem<User> beanItem = (BeanItem<User>)getUi().getForm().getItemDataSource();
			selectedUser = beanItem.getBean();			
		}
		
		if (selectedUser == null) {
			return;
		}
			
		// Updates table
		usersContainer.populateContainer();	
		
		// Get the current id 
		selectedUser = new IwaPersistenceHelper().refreshUser(selectedUser);
		
		// Select row
		getUi().getTable().select(selectedUser);						
	}

	private void refreshView() {
		if (getSelectedUser() == null) {
			getUi().getBtnNew().setEnabled(true);
			getUi().getBtnDelete().setEnabled(false);
			getUi().getBtnSave().setEnabled(false);			
		} else {
			getUi().getBtnNew().setEnabled(true);
			getUi().getBtnDelete().setEnabled(true);
			getUi().getBtnSave().setEnabled(true);			
		}
	}
	
	/**
	 * Little helper, can not be implemented anonymously for GAE requires it to be Serializable 
	 */
	private class ConfirmDeleteUserDialogListener implements ConfirmDialog.Listener, Serializable {
		private static final long serialVersionUID = 1L;
		@Override
		public void onClose(ConfirmDialog dialog) {
			if (dialog.isConfirmed()) {
				deleteUser();
			} 
		}
	}		
}
