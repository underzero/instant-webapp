package ch.ood.iwa.persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/**
 * 
 * @author Mischa
 *
 */
public class TestIwaEntityModelAgainstGAE extends TestIwaEntityModel {
	
	private static LocalServiceTestHelper googleAppEngineTestHelper;

	public static String persistenceUnitName = "gae";
	
	@BeforeClass
	public static void setUp() throws Exception {
		
		googleAppEngineTestHelper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    	googleAppEngineTestHelper.setUp();
    	System.setProperty("appengine.orm.disable.duplicate.emf.exception", "true");    		
			
        FacadeFactory.registerFacade(persistenceUnitName, true);    
	}

	@AfterClass
	public static void tearDown() throws Exception {
		FacadeFactory.removeFacade(persistenceUnitName);
	}
	
}
