package ch.ood.iwa.module.ui;

import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.view.AbstractView;
import org.vaadin.dialogs.ConfirmDialog;

import ch.ood.iwa.module.presenter.ModulePresenter;
import ch.ood.iwa.ui.MainWindow;

import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Window.Notification;

/**
 * This is the common supertype for all IWA Module Views
 * @author Mischa
 *
 * @param <A> The layout of the view
 * @param <P> The presenter for this view
 * @param <V> The UI interface type presenter and view use to communicate
 */
public abstract class AbstractModuleView<A extends ComponentContainer, P extends ModulePresenter<V>, V> 
							extends AbstractView<A> 
							implements ClickListener, ModuleView, IwaModuleUI {

	private static final long serialVersionUID = 1L;
	private String name;
	private String displayName;
	private P presenter;
	
	/**
	 * Constructor 
	 * 
	 * @param layout
	 * @param presenter
	 */
	protected AbstractModuleView(String name, A layout, P presenter) {
		super(layout);
		setName(name);
		this.presenter = presenter;
		this.name = name;
		
		
	}
		
	/**
	 * {@inheritDoc}
	 * 
	 * @param name
	 */
	@Override
	public void setName(String name) {		
		this.name = name;
		// Workaround to be able to unit-test this class (no application set) 
		try {
			setDisplayName(Lang.getMessage(name));	
		} catch (NullPointerException e) {
			setDisplayName(name);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {		
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the (translated) display name. If you would 
	 * normally call the {@link AbstractModuleView#setName(String)} method
	 * with the untranslated name
	 * 
	 * @param displayName
	 */
	protected void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	protected P getPresenter() {
		return presenter;
	}	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public MainWindow getMainWindow() {
		return (MainWindow)getWindow();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showError(String caption, String message) {
		getWindow().showNotification(caption, message, Notification.TYPE_ERROR_MESSAGE);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showInfo(String caption, String message) {
		getWindow().showNotification(caption, message, Notification.TYPE_HUMANIZED_MESSAGE);		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showConfirmation(String caption, String message, ConfirmDialog.Listener callback) {
		ConfirmDialog.show(getWindow(), caption, message, 
							Lang.getMessage("Yes"), Lang.getMessage("No"), callback);			
	}	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void activated(Object... params) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deactivated(Object... params) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void click(ClickEvent event) {	
	}
	
	/**
	 * Convenience method
	 * 
	 * @param array
	 * @return
	 */
	protected String[] translateStringArray (String[] array) {
		String [] translatedValues = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			translatedValues[i] = Lang.getMessage(array[i]); 
		}
		return translatedValues;
	}	
}
