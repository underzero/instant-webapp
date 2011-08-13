package ch.ood.iwa.ui;

import org.vaadin.appfoundation.i18n.Lang;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;

/**
 * Helper to centrally create UI components
 *  
 * @author Mischa
 *
 */
public class UiFactory {
	
	public static Button createButton(String textKey) {
		Button button = new Button(Lang.getMessage(textKey));
		button.setIcon(getIconForName(textKey));
		button.setWidth("110px");
		return button;		
	}
	
	private static ThemeResource getIconForName(String name) {
		ThemeResource icon = null;
		
		if (name.equals("New")) {
			icon = new ThemeResource("../runo/icons/16/document-add.png");
		} else if (name.equals("Delete")) {
			icon = new ThemeResource("../runo/icons/16/trash.png");
		} else if (name.equals("Save")) {
			icon = new ThemeResource("../runo/icons/16/ok.png");
		} else if (name.equals("Send")) {
			icon = new ThemeResource("../runo/icons/16/email.png");
		} else if (name.equals("Refresh")) {
			icon = new ThemeResource("../runo/icons/16/reload.png");
		} else if (name.equals("EditRecipients")) {
			icon = new ThemeResource("../runo/icons/16/users.png");
		} else if (name.equals("Accept")) {
			icon = new ThemeResource("../runo/icons/16/ok.png");
		} else if (name.equals("Decline")) {
			icon = new ThemeResource("../runo/icons/16/cancel.png");
		}
		return icon;
	}
}
