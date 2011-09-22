package ch.ood.iwa.module.ui;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import org.vaadin.appfoundation.i18n.Lang;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Select;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Little Dialog for selecting Locale
 * 
 * @author Mischa
 *
 */
public class ChangeLocaleDialog extends Window {

	private static final long serialVersionUID = 1L;
	private ComboBox comboBox;
	private Button btnChange;
	private final ChangeLocaleDialogListener changeLocaleDialogListener;
	private Set<Locale> availableLocales = new LinkedHashSet<Locale>();
	private Locale currentLocale;
	
	/**
	 * Callback Interface
	 */
	public interface ChangeLocaleDialogListener extends Serializable {
		void notifyNewLocale(Locale newLocale);
	}

	/**
	 * Constructor
	 * 
	 * @param lostPasswordDialogListener
	 */
	public ChangeLocaleDialog(ChangeLocaleDialogListener changeLocaleDialogListener, 
							  Set<Locale> availableLocales, 
							  Locale currentLocale) {
		this.setModal(true);
		this.changeLocaleDialogListener = changeLocaleDialogListener;
		this.availableLocales = availableLocales;
		this.currentLocale = currentLocale;
		initLayout();
	}
	
	/**
	 * Layout is done with nested layouts
	 */
	private void initLayout() {
		this.setWidth("280px");
		this.setHeight("180px");
		this.setCaption(Lang.getMessage("ChangeLocale"));
		Layout mainLayout = new VerticalLayout();
		mainLayout.addComponent(createComboBox());
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setWidth("100%");
		btnChange = new Button(Lang.getMessage("Change"));
		btnChange.addListener(buttonClickListener);
		buttonLayout.addComponent(btnChange);
		buttonLayout.setComponentAlignment(btnChange, Alignment.BOTTOM_RIGHT);
		buttonLayout.setMargin(true);
		buttonLayout.setMargin(true, false, false, false);
		mainLayout.addComponent(buttonLayout);
		getContent().addComponent(mainLayout);
	}
	

	/**
	 * Creates the select component for the new locale
	 * 
	 * @return
	 */
	private ComboBox createComboBox() {
		comboBox = new ComboBox(Lang.getMessage("NewLocale"));
		comboBox.setItemCaptionMode(Select.ITEM_CAPTION_MODE_EXPLICIT);
		comboBox.setNullSelectionAllowed(false);
		for (Locale locale : availableLocales) {
			comboBox.addItem(locale);
			comboBox.setItemCaption(locale, locale.getDisplayLanguage(currentLocale));
		}
		return comboBox;
	}
	
	/**
	 * Listener
	 */
	private Button.ClickListener buttonClickListener = new Button.ClickListener() {
		private static final long serialVersionUID = 1L;

		@Override
		public void buttonClick(ClickEvent event) {
			changeLocaleDialogListener.notifyNewLocale((Locale)comboBox.getValue());
		}
	};
}
