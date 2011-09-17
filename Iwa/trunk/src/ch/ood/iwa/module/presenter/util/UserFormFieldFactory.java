package ch.ood.iwa.module.presenter.util;

import java.io.Serializable;
import java.util.List;

import org.vaadin.appfoundation.authentication.data.User;
import org.vaadin.appfoundation.authorization.Role;
import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.TextField;

/**
 * A specialzed {@link FormFieldFactory} for the User Form
 * 
 * @author Mischa
 *
 */
public class UserFormFieldFactory extends DefaultFieldFactory implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	private static String[] visibleFields = {
		"username",
		"name",		
		"email",
		"role",
		"passwordAsString"			
	};
	private ComboBox comboBox;

	public static String[] getVisibleFields() {
		return visibleFields;
	}	
	
	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		String pid = (String) propertyId;
		
		TextField f = new TextField();
		String fieldName = "[Translation missing]";
		
		if ("username".equals(pid)) {
			fieldName = Lang.getMessage("Username");
			f.setRequired(true);
			f.setRequiredError(Lang.getMessage("ValueRequiredMsg", fieldName));		
			
		} else if ("name".equals(pid)) {
			fieldName = Lang.getMessage("Name");
			f.setRequired(true);
			f.setRequiredError(Lang.getMessage("ValueRequiredMsg", fieldName));		

		} else if ("email".equals(pid)) {
			fieldName = Lang.getMessage("Email");
			f.setRequired(true);
			f.addValidator(new EmailFieldValidator());			
			f.setRequiredError(Lang.getMessage("ValueRequiredMsg", fieldName));		
			
		} else if ("role".equals(pid)) {
			return createRoleComboBox(item, pid);
			
		} else if ("passwordAsString".equals(pid)) {
			fieldName = Lang.getMessage("NewPassword");
			
		} else {
			return null;
		}		
		f.setCaption(fieldName);
		return f; 
	}	
	
	/**
	 * Creates a Combobox to select a role
	 * 
	 * @param item
	 * @param pid
	 * @return
	 */
	private ComboBox createRoleComboBox(Item item, Object pid) {						
		String fieldName = Lang.getMessage("Role");
		comboBox = new ComboBox(fieldName);
		comboBox.setNullSelectionAllowed(false);
		
		List<Role> roles = FacadeFactory.getFacade().list(Role.class);
		
		if (roles == null) return null;
					
		for (Role role : roles) {
			comboBox.addItem(role.getIdentifier());
			/**
			 * Here would be the hook to introduce i18n for the Role display name.
			 * But this would require some smart updating of the i18n texts file to be 
			 * a proper solution... 
			 */				
		}									
		
		// See if we have a value in the field and select according list entry
		@SuppressWarnings("unchecked")
		BeanItem<User> userItem = (BeanItem<User>) item;
		if (userItem != null && userItem.getBean() != null) {
			comboBox.select(userItem.getBean().getRole());
		}
				
		return comboBox;		
	}			
}
