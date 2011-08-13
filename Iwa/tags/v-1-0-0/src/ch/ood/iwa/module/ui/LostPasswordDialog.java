package ch.ood.iwa.module.ui;

import java.io.Serializable;

import org.vaadin.appfoundation.i18n.Lang;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Little Dialog to request a new password
 * 
 * @author Mischa
 *
 */
public class LostPasswordDialog extends Window {

	private static final long serialVersionUID = 1L;
	private TextField emailAdress = new TextField();
	private Button btnSend;
	private final LostPasswordDialogListener lostPasswordDialogListener;
	
	/**
	 * Callback Interface
	 */
	public interface LostPasswordDialogListener extends Serializable {
		void notifyEmailAdress(String emailAdress);
	}

	/**
	 * Constructor
	 * 
	 * @param lostPasswordDialogListener
	 */
	public LostPasswordDialog(LostPasswordDialogListener lostPasswordDialogListener) {
		this.setModal(true);
		this.lostPasswordDialogListener = lostPasswordDialogListener;
		initLayout();
	}
	
	/**
	 * Layout is done with nested layouts
	 */
	private void initLayout() {
		this.setWidth("280px");
		this.setHeight("180px");
		this.setCaption(Lang.getMessage("RequestNewPassword"));
		Layout mainLayout = new VerticalLayout();
		mainLayout.addComponent(createForm());
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setWidth("100%");
		btnSend = new Button(Lang.getMessage("Send"));
		btnSend.addListener(buttonClickListener);
		buttonLayout.addComponent(btnSend);
		buttonLayout.setComponentAlignment(btnSend, Alignment.BOTTOM_RIGHT);
		buttonLayout.setMargin(true);
		buttonLayout.setMargin(true, false, false, false);
		mainLayout.addComponent(buttonLayout);
		getContent().addComponent(mainLayout);
	}
	
	/**
	 * Created the input "form"
	 * 
	 * @return
	 */
	private HorizontalLayout createForm() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setWidth("100%");
		layout.setSpacing(true);
		Label label = new Label(Lang.getMessage("Email"));
		layout.addComponent(label);
		layout.addComponent(emailAdress);
		emailAdress.setWidth("100%");
		layout.setExpandRatio(emailAdress, 3);
		layout.setExpandRatio(label, 1);
		return layout;
	}
	
	/**
	 * Listener
	 */
	private Button.ClickListener buttonClickListener = new Button.ClickListener() {
		private static final long serialVersionUID = 1L;

		@Override
		public void buttonClick(ClickEvent event) {
			lostPasswordDialogListener.notifyEmailAdress((String)emailAdress.getValue());
		}
	};
}
