package ch.ood.iwa;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.vaadin.appfoundation.authentication.SessionHandler;
import org.vaadin.appfoundation.authentication.data.User;
import org.vaadin.appfoundation.authentication.util.PasswordUtil;
import org.vaadin.appfoundation.authentication.util.UserUtil;
import org.vaadin.appfoundation.authorization.Role;
import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;
import org.vaadin.appfoundation.view.ViewContainer;
import org.vaadin.appfoundation.view.ViewHandler;

import ch.ood.iwa.authorization.ModulePermissionManager;
import ch.ood.iwa.module.AdministrationModule;
import ch.ood.iwa.module.ApplicationModule;
import ch.ood.iwa.module.ModuleRegistry;
import ch.ood.iwa.module.ui.LoginView;
import ch.ood.iwa.ui.MainWindow;

import com.vaadin.Application;
import com.vaadin.terminal.ParameterHandler;
import com.vaadin.terminal.Terminal;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

/**
 * This is the IWA Application
 * 
 * @author Mischa
 *
 */
public abstract class IwaApplication extends Application 
									 implements ParameterHandler, HttpServletRequestListener {
	
	private static final long serialVersionUID = 1L;
	private static final String PROPERTIES_FILE_PATH = "/WEB-INF/iwa.properties";
	private static final String COOKIE_NAME_IWA_LOCALE = "IWA.LOCALE";
	private static final String CMD_LINE_PARAMETER_LANG = "lang";
	public static String LOGIN_URI = "Login";
	private static final Logger logger = Logger.getLogger(IwaApplication.class.getName());
	private static ThreadLocal<IwaApplication> threadLocal = new ThreadLocal<IwaApplication>();		
	private Properties properties = new Properties();		
	private ModuleRegistry moduleRegistry = new ModuleRegistry();
	private String theme = "iwa";
	private Locale locale;
	private transient HttpServletResponse response;	
	protected final String ROOT_USER_ROLE = "Administrator";
	
	public final static String LOGO_FILE_PATH = "../iwa/img/logo.png";	
	
	/**
	 * Part of the Thread Local Pattern implementation
	 */
	@Override
	public void onRequestStart(HttpServletRequest request, HttpServletResponse response) {
		IwaApplication.setInstance(this);
		this.response = response;
		readLocaleFromCookie(request);
	}
	
	/**
	 * Part of the Thread Local Pattern implementation
	 */
	@Override
	public void onRequestEnd(HttpServletRequest request, HttpServletResponse response) {
		threadLocal.remove();
	}

	/**
	 * Main Initialize method
	 */
	@Override
	public void init() {
		// Own initialization
		initIwa();
		// Initialization of the Application in the subclass
		initApplication();		
	}
	
	/**
	 * Useful for retrieving the properties from 
	 * the iwa.properties file 
	 * 
	 * @return
	 */
	public Properties getProperties() {
		return getInstance().properties;
	}
	
	/**
	 * Logs the stacktrace of a throwable with {@link Level#SEVERE} 
	 * 
	 * @param t
	 */
	public void logError(Throwable t) {		
		logger.severe(getStackTrace(t));
	}
	
	/**
	 * To be called when the Locale (language) changes
	 * 
	 * @param newLocale
	 */
	public void changeLocale(Locale newLocale) {
		this.locale = newLocale;
		super.setLocale(newLocale);
		Lang.initialize(getInstance());
		Lang.setLocale(newLocale);
		ViewHandler.clear();
		getModuleRegistry().clear();
		initializeModules();
		initializeFullScreenViews();
		registerFullScreenView(LoginView.class, LOGIN_URI);
		((MainWindow)getMainWindow()).refresh();
		writeLocalToCookie(newLocale);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleParameters(Map<String, String[]> parameters) {
		// Language Handling
		if (parameters.containsKey(CMD_LINE_PARAMETER_LANG)) {
			String value = parameters.get(CMD_LINE_PARAMETER_LANG)[0];
			
			if (value != null && (value.length() == 2)) {
				Locale newLocale = new Locale(value);
				changeLocale(newLocale);
			}
		}	
		
		// Show the Login Screen if requested
		if (parameters.containsKey(LOGIN_URI)) {
			ViewHandler.activateView(LoginView.class);
		}
		
		// enable the application to further process parameters
		handleParametersByApplication(parameters);
	}	
		
	/**
	 * Convenience method for logging messages.<br/>
	 * If you have problems finding your messages, make shure your <a href="http://download.oracle.com/javase/7/docs/technotes/guides/logging/overview.html">
	 * java logging</a> system is properly configured
	 * 
	 * @param theme The Log theme, if null, the name of {@link IwaApplication} is taken
	 * @param level The {@link Level}, if null, {@link Level#INFO} is taken
	 * @param message The message to log, will be enhanced with a timestamp and the current user (if any)
	 */
	public void log(String theme, Level level, String message) {
		if (theme == null || theme.isEmpty()) {
			theme = IwaApplication.class.getName();
		}
		if (level == null) {
			level = Level.INFO;
		}
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
		
		String userName = "[No User]";
		if (SessionHandler.get() != null) {
			userName = SessionHandler.get().getUsername();
		}
		Logger.getLogger(theme).log(level, timeStamp + " (User: " + userName + "): " + message);
	}
	
	/**
	 * Returns the registry that contains all IWA modules
	 * 
	 * @return
	 */
	public ModuleRegistry getModuleRegistry() {
		return moduleRegistry;
	}

	/**
	 * Part of the Thread Local Pattern implementation
	 * 
	 * @return
	 */
	public static IwaApplication getInstance() {
		return threadLocal.get();
	}
	
	/**
	 * Internal main initialization method 
	 */
	private void initIwa() {
		setInstance(this);
		loadProperties();
		super.setLocale(locale);
		Lang.initialize(this);
		Lang.setLocale(getLocale());
		super.setTheme(theme);
		SessionHandler.initialize(this);
		createRootUser();		
		ViewHandler.initialize(this);

		// Must run before MainWindow Creation
		initializeModules();

		Window mainWindow = new MainWindow();
		mainWindow.addParameterHandler(this);
		setMainWindow(mainWindow);

		// Must run after MainWindow Creation
		initializeFullScreenViews(); 
		registerFullScreenView(LoginView.class, LOGIN_URI);

		setErrorHandler();
		
		// Show Login Screen
		ViewHandler.activateView(LoginView.class);
	}
	

	/**
	 * This is a global error catcher to avoid having Stack Traces Pop Ups
	 * on Visual Components 
	 * 
	 */
	private void setErrorHandler() {

		setErrorHandler(new Terminal.ErrorListener() {
			private static final long serialVersionUID = 1L;

			public void terminalError(Terminal.ErrorEvent event) {
				Throwable throwable = event.getThrowable();
				
				// If this is just an Validation exception, we do nothing
				if (isVaadinValidationException(throwable.getCause())) {
					return;
				}		
				logger.warning(getStackTrace(throwable));
				getMainWindow().showNotification(Lang.getMessage("InternalExceptionMsg"), 
												 throwable.getMessage(), 
												 Notification.TYPE_ERROR_MESSAGE);				
			}
		});
	}
	
	/**
	 * Internal helper to determine a certain exception type 
	 * we handle explicitly
	 * 
	 * @param throwable
	 * @return
	 */
	private boolean isVaadinValidationException(Throwable throwable) {
		
		if (throwable == null) return false;		
		if (throwable instanceof com.vaadin.data.Validator.EmptyValueException 
				|| throwable instanceof com.vaadin.data.Validator.InvalidValueException) {
			return true;
		}		
		return false;
	}
	
	/**
	 * Helper to convert Stack Trace into a String
	 * @param cause
	 * @return
	 */
	private String getStackTrace(Throwable cause) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		cause.printStackTrace(printWriter);
		return stringWriter.toString();
	}
	
	/**
	 * Loads the IWA properties file
	 */
	private void loadProperties() {	
		try {			
			FileInputStream fileInputStream = new FileInputStream(getContext().getBaseDirectory().getPath() + PROPERTIES_FILE_PATH);
			getInstance().getProperties().load(fileInputStream);
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}		
	}
		
	/**
	 * This is to prevent "out-of-synch" situations
	 * as proposed in http://vaadin.com/forum/-/message_boards/view_message/158627
	 * or http://vaadin.com/web/joonas/wiki/-/wiki/Main/Supporting%20Multible%20Tabs
	 * 
	 * TODO: 
	 * revisit, needs changes in Appfoundation library, might be fixed with 
	 * Vaadin 7 anyway
	 *  
	 */
	/*
	@Override
	public Window getWindow(String name) {

		// If the window is identified by name, we are good to go
		Window w = super.getWindow(name);

		// If not, we must create a new window for this new browser window/tab
		if (w == null) {
			
			w = new MainWindow();
			
			// Iwa specific
			w.addParameterHandler(this);
			
			// Use the random name given by the framework to identify this
			// window in future
			w.setName(name);
			
			addWindow(w);

			// Move to the url to remember the name in the future
			w.open(new ExternalResource(w.getURL()));			
		}
		return w;
	}
	*/	

	/**
	 * Creates an initial root user
	 */
	private void createRootUser() {
		if (UserUtil.checkUsernameAvailability("r")) {
			try {
				User user = new User();
				user.setRole(ROOT_USER_ROLE);
				user.setUsername("r");
				user.setName("Initial Root User");
				user.setEmail("iwa-root@ood.ch");
				user.setPassword(PasswordUtil.generateHashedPassword("r"));				
				UserUtil.storeUser(user);
				FacadeFactory.getFacade().store(new Role(ROOT_USER_ROLE));
				new ModulePermissionManager().addPermission(ROOT_USER_ROLE, new AdministrationModule());
				new ModulePermissionManager().addPermission(ROOT_USER_ROLE, new ApplicationModule());				
			} catch (Exception e) {
				logError(e);
			}
		}
	}

	/**
	 * Allows to register a Full Screen View and associate it with an uri so that the 
	 * view can be called by typing a browser address like http://somedomain/myapp#myViewsUri
	 * 
	 * @param view
	 * @param uri
	 */
	protected void registerFullScreenView(Object view, String uri) {
		ViewHandler.addView(view, (ViewContainer) getMainWindow());
		if (uri != null && ViewHandler.getViewItem(view) == null) {
			ViewHandler.addUri(uri, view);	
		}
	}
	

	/**
	 * Part of the Thread Local Pattern implementation
	 * 
	 * Set the current application instance
	 * @param application
	 */
	private static void setInstance(IwaApplication application) {
		if (getInstance() == null) {
			threadLocal.set(application);
		}
	}
	
	/**
	 * Helper for the Multi-Language support
	 *  
	 * @param request
	 */
	private void readLocaleFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return;
		}
		for (int i=0; i < cookies.length; i++) {
            if (COOKIE_NAME_IWA_LOCALE.equals(cookies[i].getName())) {
            	String localeString = cookies[i].getValue();
            	if (localeString != null && (!localeString.isEmpty())) {
            		try {
						Locale locale = new Locale(localeString);
						this.locale =  locale;
						return;
					} catch (Exception e) {
						this.locale = Locale.getDefault();
					}
            	}
            }
        }
		this.locale = Locale.getDefault();
	}
	
	/**
	 * Helper for the Multi-Language support
	 * 
	 * @param locale
	 */
	private void writeLocalToCookie(Locale locale) {
		Cookie cookie = new Cookie(COOKIE_NAME_IWA_LOCALE, locale.getLanguage());
		response.addCookie(cookie);
	}
	
	/**
	 * The main module init method
	 */
	private void initializeModules() {
		// Register default module at start
		getModuleRegistry().registerModule(new ApplicationModule());	
		
		// call Application to register modules
		initializeModulesByApplication();
		
		// Register default module at end		
		getModuleRegistry().registerModule(new AdministrationModule());		
	}
			
	/**
	 * Override in your application
	 */
	protected abstract void initApplication();

	/**
	 * Override in your application
	 */
	protected abstract void initializeFullScreenViews();

	/**
	 * Override in your application
	 */
	protected abstract void initializeModulesByApplication();
	
	/**
	 * Override in your application to handle URL parameters
	 */
	protected abstract void handleParametersByApplication(Map<String, String[]> parameters);
}
