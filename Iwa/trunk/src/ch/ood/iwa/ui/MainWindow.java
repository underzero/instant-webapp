package ch.ood.iwa.ui;

import org.vaadin.appfoundation.authentication.SessionHandler;
import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.view.View;
import org.vaadin.appfoundation.view.ViewContainer;
import org.vaadin.appfoundation.view.ViewHandler;

import ch.ood.iwa.IwaApplication;
import ch.ood.iwa.IwaException;
import ch.ood.iwa.authorization.ModulePermissionManager;
import ch.ood.iwa.module.Module;
import ch.ood.iwa.module.ui.LoginView;
import ch.ood.iwa.module.ui.ModuleView;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

/**
 * IWA Main Window 
 * 
 * @author Mischa
 *
 */
public class MainWindow extends Window implements ViewContainer, ItemClickListener, Button.ClickListener {

	private static final long serialVersionUID = 1L;	
	private Label lblCurrentUser = new Label("Logged off...");
	private Button btnLogout;	
	private Panel fullScreenLayout = new Panel();
	private Panel mainLayout = new Panel(new VerticalLayout());	
	private HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();	
	private MainArea mainArea  = new MainArea();
	private Accordion menu  = new Accordion();	
	private View currentView;

	/**
	 * Default Constructor
	 */
	public MainWindow() {
		initLayout();
	}
	
	private void initLayout() {
		splitPanel.setSplitPosition(250, HorizontalSplitPanel.UNITS_PIXELS);
		splitPanel.addComponent(menu);
		splitPanel.addComponent(mainArea);
		
		mainLayout.getContent().addComponent(getHeaderPanel());
		mainLayout.getContent().addComponent(splitPanel);
		
		((VerticalLayout)mainLayout.getContent()).setExpandRatio(splitPanel, 5);
		
		splitPanel.setSizeFull();
	}
	
	@Override
	public void activate(View view) {
		
		ModuleView moduleView = (ModuleView)view;
		currentView = view;
		
		if (moduleView.isFullScreen()) {
			setContent(fullScreenLayout);
			fullScreenLayout.setSizeFull();
			fullScreenLayout.removeAllComponents();
			fullScreenLayout.addComponent((Component)view);
			
		} else {
			setContent(mainLayout);
			mainLayout.setSizeFull();
			mainLayout.getContent().setSizeFull();
			mainArea.activate(view);	
		}
	}

	@Override
	public void deactivate(View view) {
		mainArea.deactivate(view);
	}

	@Override
	public void itemClick(ItemClickEvent event) {	
		String source = (String) event.getItemId();		
		if (source.equals("Logout")) {
			ViewHandler.activateView(LoginView.class);
		} else {
			try {
				View view = IwaApplication.getInstance().getModuleRegistry().getViewByName(source);
				ViewHandler.activateView(view.getClass());
			} catch (IwaException e) {
				showNotification(e.getMessage(), e.getDetails(), Notification.TYPE_ERROR_MESSAGE);
			}			
		}
	}

	/**
	 * This is the logout button
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		SessionHandler.logout();
		ViewHandler.activateView(LoginView.class);
	}
	
	/**
	 * Set the current user to display it in the header panel
	 * 
	 * @param currentUser
	 */
	public void setCurrentUser(String currentUser) {
		lblCurrentUser.setValue(Lang.getMessage("LoggedInAs", currentUser));
	}
		
	/**
	 * Produces the header panel
	 * 
	 * @return
	 */
	private Panel getHeaderPanel() {
        Panel panel = new Panel(new HorizontalLayout());
        panel.setHeight("130px"); 
        ((HorizontalLayout)panel.getContent()).setSpacing(true);
        ((HorizontalLayout)panel.getContent()).setMargin(true, true, false, true);
        ((HorizontalLayout)panel.getContent()).setWidth("100%");
        
        // Logo
        Embedded logo = new Embedded(null, new ThemeResource(IwaApplication.LOGO_FILE_PATH));
        panel.getContent().addComponent(logo);
        
        // Current User
        lblCurrentUser.addStyleName("label-right-align");
        panel.getContent().addComponent(lblCurrentUser);
        ((HorizontalLayout)panel.getContent()).setComponentAlignment(lblCurrentUser, Alignment.MIDDLE_RIGHT);
        ((HorizontalLayout)panel.getContent()).setExpandRatio(lblCurrentUser, 10.0f);

        // Logout Button
        btnLogout = new Button("(" + Lang.getMessage("Logout") + ")");
        btnLogout.setStyleName(BaseTheme.BUTTON_LINK);
        btnLogout.setDescription(Lang.getMessage("Logout"));
        btnLogout.addListener(this); 
        panel.getContent().addComponent(btnLogout);
        ((HorizontalLayout)panel.getContent()).setComponentAlignment(btnLogout, Alignment.MIDDLE_RIGHT);
        ((HorizontalLayout)panel.getContent()).setExpandRatio(btnLogout, 0.1f);               
        return panel;
	}
	
	/**
	 * Initializes the Navigation Main Menu
	 */
	public void initializeNavigation() {
		// Guard
		if (SessionHandler.get() == null) {
			return;
		}
		
		// Clean up first
		menu.removeAllComponents();
		
		for (Module module : IwaApplication.getInstance().getModuleRegistry().getAllModules()) {
			if (new ModulePermissionManager().hasPermission(SessionHandler.get().getRole(), module)) {
				Tree tree = module.getViewNamesAsTree();
				tree.addListener(this);
				menu.addTab(tree, module.getDisplayName(), module.getIcon());
				// register all views of this module
				for (View view : module.getAllViews()) {
					ViewHandler.addView(view.getClass(), this);
				}				
			}
		}
	}
	
	/**
	 * Refreshes the whole view.<br/>
	 * This is useful for example when an Locale change has taken place
	 * 
	 */
	public void refresh() {
		if (SessionHandler.get() != null) {
			setCurrentUser(SessionHandler.get().getName());
		}
		btnLogout.setCaption("(" + Lang.getMessage("Logout") + ")");
		initializeNavigation();
		ViewHandler.activateView(currentView.getClass());
	}
}

