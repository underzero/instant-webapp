package ch.ood.iwa;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.vaadin.appfoundation.i18n.InternationalizationServlet;
import org.vaadin.appfoundation.i18n.TmxSourceReader;
import org.vaadin.appfoundation.i18n.TranslationSource;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

/**
 * IWA ContextListener that does some initialization and clean up.
 * To provide your own implementation, simply change the web.xml 
 * to have your ContextListener called.
 * 
 * @author Mischa
 *
 */
public class IwaContextListener implements ServletContextListener {
	
	private static final Logger log = Logger.getLogger(IwaContextListener.class.getName());
	private static String TRANSLATION_FILE = "translations.xml";

	/**
	 * See {@link ServletContextListener#contextInitialized(ServletContextEvent)}
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
	    try {
	    	String pathToTranslationFile = event.getServletContext()
	    				.getRealPath("/") + "/WEB-INF/" + TRANSLATION_FILE;
	    		    		    		    		    	
	    	// Find out where we are running
	    	String serverInfo = event.getServletContext().getServerInfo();
	    		    	
	    	if (serverInfo.contains("Google App Engine")) {	    		
		        FacadeFactory.registerFacade("gae", true);		     
	    	} else {	    			    	
	    		FacadeFactory.registerFacade("default", true);	    		
	    	}
	    			        	    		    	
	        // Define the used password salt
	        System.setProperty("authentication.password.salt", "foobar");
	        
	        // Set the minimum length for passwords
	        System.setProperty("authentication.password.validation.length", "1");

	        // Set the minimum length for usernames
	        System.setProperty("authentication.username.validation.length", "1");
	   
	        File file = new File(pathToTranslationFile);	        
	        TranslationSource ts = new TmxSourceReader(file);
	        
	        InternationalizationServlet.loadTranslations(ts);	     
	        
	    } catch (Exception e) {	    	
	    	log.severe(getStackTrace(e));	    	 
	    }	       
	}
	
	/**
	 * See {@link ServletContextListener#contextDestroyed(ServletContextEvent)}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		FacadeFactory.clear();
		
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
}
