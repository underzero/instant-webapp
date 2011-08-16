package ch.ood.iwa.module.presenter;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.vaadin.appfoundation.authentication.SessionHandler;
import org.vaadin.appfoundation.authentication.data.User;
import org.vaadin.appfoundation.authentication.exceptions.AccountLockedException;
import org.vaadin.appfoundation.authentication.exceptions.InvalidCredentialsException;
import org.vaadin.appfoundation.authentication.util.AuthenticationUtil;
import org.vaadin.appfoundation.authentication.util.PasswordUtil;
import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;
import org.vaadin.appfoundation.view.ViewHandler;

import ch.ood.iwa.IwaApplication;
import ch.ood.iwa.IwaMailSender;
import ch.ood.iwa.IwaPersistenceHelper;
import ch.ood.iwa.PasswordGenerator;
import ch.ood.iwa.module.ui.ChangeLocaleDialog.ChangeLocaleDialogListener;
import ch.ood.iwa.module.ui.IwaModuleUI;
import ch.ood.iwa.module.ui.LostPasswordDialog.LostPasswordDialogListener;
import ch.ood.iwa.module.ui.WelcomeView;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

/**
 * Presenter handling the login logic
 * 
 * @author Mischa
 *
 */
public class LoginPresenter extends AbstractModulePresenter<LoginPresenter.UI> implements Button.ClickListener {
	
	private static final long serialVersionUID = 1L;
	private static final String IWA_LANGS_KEY = "iwa.languages";
	
	/**
	 * This is the interface that decouples Presenter and View
	 * 
	 */
	public static interface UI extends IwaModuleUI {
		String getUsername();
		String getPassword();
		void resetPasswordField();
		Button getLoginButton();
		Button getLostPasswordButton();
		Button getChangeLocaleButton();
		void showLostPasswordDialog(LostPasswordDialogListener listener);
		void hideLostPasswordDialog();
		void showChangeLocaleDialog(ChangeLocaleDialogListener listener,
									Set<Locale> availableLocales, 
									Locale currentLocale);
		void hideChangeLocaleDialog();
	}
		
	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getSource().equals(getUi().getLoginButton())) {
			login(getUi().getUsername(), getUi().getPassword());
		} else if (event.getSource().equals(getUi().getLostPasswordButton())) {
			getUi().showLostPasswordDialog(lostPasswordDialogListener);
		} else if (event.getSource().equals(getUi().getChangeLocaleButton())) {
			getUi().showChangeLocaleDialog(changeLocaleDialogListener, 
										   getAvailableLocales(),
										   IwaApplication.getInstance().getLocale());
		} 
	}
	
	private Set<Locale> getAvailableLocales() {
		Set<Locale> availableLocales = new LinkedHashSet<Locale>();
		String availableLocalesString = IwaApplication.getInstance().getProperties().getProperty(IWA_LANGS_KEY);
		if (availableLocalesString == null) return availableLocales;
		StringTokenizer stringTokenizer = new StringTokenizer(availableLocalesString, ";");
		while (stringTokenizer.hasMoreTokens()) {
			availableLocales.add(new Locale(stringTokenizer.nextToken()));	
		}
		return availableLocales;
	}
	
	private LostPasswordDialogListener lostPasswordDialogListener = new LostPasswordDialogListener() {
		private static final long serialVersionUID = 1L;

		@Override
		public void notifyEmailAdress(String emailAdress) {
			User user = findUserByEmail(emailAdress); 
			if (user == null) {
				getUi().hideLostPasswordDialog();
				getUi().showError(Lang.getMessage("NoUserFoundForEmailAddressMsg"), null);
			} else {
				sendNewPassword(user);
				getUi().hideLostPasswordDialog();
				getUi().showInfo(Lang.getMessage("PasswordSentSuccessMsg"), null);
			}
		}
	}; 
	
	private ChangeLocaleDialogListener changeLocaleDialogListener = new ChangeLocaleDialogListener() {
		private static final long serialVersionUID = 1L;

		@Override
		public void notifyNewLocale(Locale newLocale) {
			getUi().hideChangeLocaleDialog();
			if (newLocale != null) {
				IwaApplication.getInstance().changeLocale(newLocale);
			}	
			
		}
	};
	
	private void sendNewPassword(User user) {
		String newPassword = generateNewPassword();
		updatePasswordAndPersistUser(user, newPassword);
		String message = Lang.getMessage("NewPasswordMailContentMsg", 
									user.getUsername(),  
									IwaApplication.getInstance().getProperties().getProperty("vemico.environment"), 
									newPassword);
		
		System.out.println(message); // TODO: Remove me
		
		try {
			IwaMailSender.send(user.getEmail(), user.getName(), Lang.getMessage("NewPasswordMailSubjectMsg"), message);
		} catch (Exception e) {
			getUi().showError(Lang.getMessage("PasswordSentFailedMsg"), null);
		}
	}
	
	private String generateNewPassword() {
		return PasswordGenerator.createPassword(6, 6);
	}
	
	private void updatePasswordAndPersistUser(User user, String newPassword) {
		user = new IwaPersistenceHelper().refreshUser(user);
		user.setPassword(PasswordUtil.generateHashedPassword(newPassword));
		FacadeFactory.getFacade().store(user);
	}
	
	private User findUserByEmail(String email) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("email", email);
						
		User user = FacadeFactory.getFacade().find("SELECT u FROM User u WHERE u.email = :email", parameters);
		return user;
	}
	
	private void login(String username, String password) {		
		try {
			User currentUser = AuthenticationUtil.authenticate(username, password);
			SessionHandler.setUser(currentUser);			
			getUi().getMainWindow().initializeNavigation();
			getUi().getMainWindow().setCurrentUser(currentUser.getName());
			ViewHandler.activateView(WelcomeView.class);
			getUi().resetPasswordField();

		} catch (InvalidCredentialsException e) {
			getUi().showError(Lang.getMessage("LoginFailedMsg"), null);

		} catch (AccountLockedException e) {
			getUi().showError(Lang.getMessage("AccountLockedMsg"), null);			
		}				
	}
}
