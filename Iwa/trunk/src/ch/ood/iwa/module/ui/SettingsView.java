package ch.ood.iwa.module.ui;

import org.vaadin.appfoundation.i18n.Lang;

import ch.ood.iwa.module.presenter.SettingsPresenter;
import ch.ood.iwa.module.presenter.util.ChangePasswordFieldFactory;
import ch.ood.iwa.ui.UiFactory;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

/**
 * View for the Settings Page
 * 
 * @author Mischa
 *
 */
public class SettingsView extends AbstractModuleView<CustomLayout, SettingsPresenter, SettingsPresenter.UI> 
						  implements SettingsPresenter.UI {

	private static final long serialVersionUID = 1L;
	private final Form changePasswordForm = new Form();		

	public SettingsView() {
		super(new CustomLayout("SettingsViewLayout"), new SettingsPresenter());
		getPresenter().setUi(this);
		setName(Lang.getMessage("Settings"));
		initLayout();
	}

	private void initLayout() {
		Label lblChangePasswordCaption = new Label(Lang.getMessage("ChangePassword"));

		BeanItem<PasswordBackingBean> pwdBeanItem = new BeanItem<PasswordBackingBean>(getPresenter().getPasswordBean());
		
		changePasswordForm.setFormFieldFactory(new ChangePasswordFieldFactory());
		changePasswordForm.setItemDataSource(pwdBeanItem);
		changePasswordForm.setVisibleItemProperties(ChangePasswordFieldFactory.VISIBLE_FIELDS);
		
		changePasswordForm.setWriteThrough(false); 		
		changePasswordForm.setInvalidCommitted(false); 	
		changePasswordForm.setImmediate(false);

		Button btnSave = UiFactory.createButton("Save");
		btnSave.addListener(getPresenter());
		
		((HorizontalLayout)changePasswordForm.getFooter()).setWidth("100%");
		changePasswordForm.getFooter().addComponent(btnSave);
		((HorizontalLayout)changePasswordForm.getFooter()).setComponentAlignment(btnSave, Alignment.MIDDLE_RIGHT);
		((HorizontalLayout)changePasswordForm.getFooter()).setMargin(true);
		((HorizontalLayout)changePasswordForm.getFooter()).setMargin(false, false, false, true);
		getContent().addComponent(lblChangePasswordCaption, "lblChangePasswordCaption");
		getContent().addComponent(changePasswordForm, "changePasswordForm");
	}

	@Override
	public boolean isFullScreen() {
		return false;
	}

	@Override
	public Form getChangePasswordForm() {		
		return changePasswordForm;
	}
}
