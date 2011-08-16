package ch.ood.iwa.module.presenter.util;

import java.io.Serializable;
import java.util.List;

import org.vaadin.appfoundation.authorization.Role;
import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import com.vaadin.data.Item;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;

/**
 * A specialized {@link FormFieldFactory} for the Roles Form
 * 
 * @author Mischa
 *
 */
public class RoleFormFieldFactory extends DefaultFieldFactory implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	private static String[] visibleFields = {
		"identifier",				
	};
	private AbstractSelect.NewItemHandler newItemHandler;
	private ComboBox comboBox;	
	
	public RoleFormFieldFactory (AbstractSelect.NewItemHandler newItemHandler) {
		super();
		this.newItemHandler = newItemHandler;		
	}

	public static String[] getVisibleFields() {
		return visibleFields;
	}	
	
	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {				
		if ("identifier".equals(propertyId)) {
			return createRolesComboBox(item, propertyId);			
		} else {
			return null;
		}		
	}	
	
	/**
	 * Creates a Combobox to select or create a role
	 * 
	 * @param item
	 * @param pid
	 * @return
	 */
	private ComboBox createRolesComboBox(Item item, Object pid) {						
		String fieldName = Lang.getMessage("Role");
		comboBox = new ComboBox(fieldName);		
		comboBox.setWidth("200px");
		comboBox.setNewItemsAllowed(true);
		comboBox.setNewItemHandler(newItemHandler);
		comboBox.setImmediate(true);
		comboBox.setDescription(Lang.getMessage("EnterNewRoleInstructionMsg"));
		comboBox.setInputPrompt(Lang.getMessage("EnterNewRoleMsg"));
		comboBox.setNullSelectionAllowed(false);
		
		// TODO: View component should not read DB....
		// But maybe the FormFieldFactoy should be considered beeing part of the presenter...? 
		List<Role> roles = FacadeFactory.getFacade().list(Role.class);
		
		if (roles == null) return null;
					
		for (Role role : roles) {			
			comboBox.addItem(role.getIdentifier());
		}											
		return comboBox;		
	}		
}
