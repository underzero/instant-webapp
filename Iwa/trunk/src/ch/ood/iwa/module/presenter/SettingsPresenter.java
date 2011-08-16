package ch.ood.iwa.module.presenter;

import java.io.Serializable;

import org.vaadin.appfoundation.authentication.SessionHandler;
import org.vaadin.appfoundation.authentication.util.UserUtil;
import org.vaadin.appfoundation.i18n.Lang;

import ch.ood.iwa.module.ui.IwaModuleUI;
import ch.ood.iwa.module.ui.PasswordBackingBean;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;

/**
 * Presenter for the Settings Page
 * 
 * @author Mischa
 *
 */
public class SettingsPresenter extends AbstractModulePresenter<SettingsPresenter.UI> 
							   implements ClickListener, Serializable {
	
	private static final long serialVersionUID = 1L;
	private PasswordBackingBean passwordBean = new PasswordBackingBean();

	/**
	 * This is the interface that decouples Presenter and View
	 * 
	 */
	public static interface UI extends IwaModuleUI {
		Form getChangePasswordForm();
	}

	@Override
	public void buttonClick(ClickEvent event) {		
		// Update the model with the data from the form
		getUi().getChangePasswordForm().commit();
					
		try {
			UserUtil.changePassword(SessionHandler.get(), 
									getPasswordBean().getOldPassword(), 
									getPasswordBean().getNewPassword(), 
									getPasswordBean().getNewPasswordRepeated());
			
			// Inform the lucky user
			getUi().showInfo(Lang.getMessage("PasswordChangedSuccessMsg"), null);
			
			// Empty Fields 
			getPasswordBean().reset();
			
			// Synchronize View 
			getUi().getChangePasswordForm().discard();
						
		} catch (Exception e) {
			getUi().showError(Lang.getMessage("PasswordChangedFailedMsg"), null);
		}
	}

	public void setPasswordBean(PasswordBackingBean passwordBean) {
		this.passwordBean = passwordBean;
	}

	public PasswordBackingBean getPasswordBean() {
		return passwordBean;
	}
}
