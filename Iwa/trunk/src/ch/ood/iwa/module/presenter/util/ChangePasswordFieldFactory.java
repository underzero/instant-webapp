package ch.ood.iwa.module.presenter.util;

import java.io.Serializable;

import org.vaadin.appfoundation.i18n.Lang;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.PasswordField;

/**
 * A specialized {@link FormFieldFactory}
 * 
 * @author Mischa
 *
 */
public class ChangePasswordFieldFactory extends DefaultFieldFactory implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	public static String[] VISIBLE_FIELDS = {
		"oldPassword", "newPassword", "newPasswordRepeated"
	};
		
	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		
		PasswordField f = new PasswordField();
		String fieldName = "[Translation missing]";
		
		String pid = (String) propertyId;
		
		if ("oldPassword".equals(pid)) {
			fieldName = Lang.getMessage("OldPassword");
				
		} else if ("newPassword".equals(pid)) {
			fieldName = Lang.getMessage("NewPassword");
			
		} else if ("newPasswordRepeated".equals(pid)) {
			fieldName = Lang.getMessage("NewPasswordRepeated");			
		}
		f.setCaption(fieldName);
		f.setRequired(true);
		f.setRequiredError(Lang.getMessage("ValueRequiredMsg", fieldName));
		return f; 
	}
}
