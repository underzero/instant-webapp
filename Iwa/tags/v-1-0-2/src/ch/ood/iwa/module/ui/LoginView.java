package ch.ood.iwa.module.ui;

import java.util.Locale;
import java.util.Set;

import org.vaadin.appfoundation.i18n.Lang;

import ch.ood.iwa.module.presenter.LoginPresenter;
import ch.ood.iwa.module.ui.ChangeLocaleDialog.ChangeLocaleDialogListener;
import ch.ood.iwa.module.ui.LostPasswordDialog.LostPasswordDialogListener;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

/**
 * The View for the login page
 * 
 * @author Mischa
 *
 */
public class LoginView 
				extends AbstractModuleView<CustomLayout, LoginPresenter, LoginPresenter.UI>  
				implements LoginPresenter.UI  {
	
	private static final long serialVersionUID = 1L;	
	private Label lblUsername = new Label(Lang.getMessage("Username") + ":");
	private Label lblPassword = new Label(Lang.getMessage("Password") + ":");	
	private TextField txtUsername = new TextField();
	private PasswordField txtPassword = new PasswordField();
	private Button btnLogin = new Button(Lang.getMessage("Login"));
	private Button btnLostPassword = new Button(Lang.getMessage("LostPassword"));
	private Button btnChangeLocale = new Button(Lang.getMessage("ChangeLocale"));
	private Label lblCopyright = new Label(Lang.getMessage("CopyrightMsg"));
	private LostPasswordDialog lostPasswordDialog;
	private ChangeLocaleDialog changeLocaleDialog;
			
	public LoginView() {
		super("LoginView", new CustomLayout("LoginViewLayout"), new LoginPresenter());		
		initView();
		initListeners();	
		getPresenter().setUi(this);
	}	
	
	private void initView() {									
		getContent().addComponent(lblUsername, "lblUsername");
		getContent().addComponent(lblPassword, "lblPassword");
		getContent().addComponent(txtUsername, "txtUsername");
		getContent().addComponent(txtPassword, "txtPassword");
		getContent().addComponent(btnLogin, "btnLogin");		
		getContent().addComponent(lblCopyright, "lblCopyright");
				
		btnLostPassword.setStyleName(BaseTheme.BUTTON_LINK);
		getContent().addComponent(btnLostPassword, "btnLostPassword");
		btnChangeLocale.setStyleName(BaseTheme.BUTTON_LINK);
		getContent().addComponent(btnChangeLocale, "btnChangeLocale");
	}
	
	private void initListeners() {
		btnLogin.addListener(getPresenter());
		btnLostPassword.addListener(getPresenter());
		btnChangeLocale.addListener(getPresenter());
	}
		
	public void showError(String caption, String message) {
		getWindow().showNotification(caption, message, Notification.TYPE_ERROR_MESSAGE);
	}
	
	@Override
	public String getUsername() {
		return (String)txtUsername.getValue();
	}

	@Override
	public String getPassword() {
		return (String)txtPassword.getValue();
	}

	@Override
	public boolean isFullScreen() {
		return true;
	}
	
	@Override
	public void showLostPasswordDialog(LostPasswordDialogListener listener) {
		lostPasswordDialog = new LostPasswordDialog(listener);
		getWindow().addWindow(lostPasswordDialog);
	}

	@Override
	public void hideLostPasswordDialog() {
		getWindow().removeWindow(lostPasswordDialog);
		lostPasswordDialog = null;
	}
	
	@Override
	public void showChangeLocaleDialog(ChangeLocaleDialogListener listener, 
									   Set<Locale> availableLocales, 
									   Locale currentLocale) {
		changeLocaleDialog = new ChangeLocaleDialog(listener, availableLocales, currentLocale);
		getWindow().addWindow(changeLocaleDialog);
	}

	@Override
	public void hideChangeLocaleDialog() {
		getWindow().removeWindow(changeLocaleDialog);
		changeLocaleDialog = null;
		
	}
	
	@Override
	public void resetPasswordField() {
		txtPassword.setValue("");		
	}
	

	@Override
	public Button getLoginButton() {
		return btnLogin;
	}

	@Override
	public Button getLostPasswordButton() {
		return btnLostPassword;
	}
	

	@Override
	public Button getChangeLocaleButton() {
		return btnChangeLocale;
	}
}

