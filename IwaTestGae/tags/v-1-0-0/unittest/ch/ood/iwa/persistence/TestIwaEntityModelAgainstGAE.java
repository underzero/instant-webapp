package ch.ood.iwa.persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/**
 * Integration Test for the entity model
 * <br/>
 * Make shure this is run with the correct libraries in the class path.
 * <ul>
 * <li>GAE Library (project classpath)</li>
 * <li>${GOOGLE_APPENGINE_SDK_ROOT}/lib/testing/appengine-testing.jar</li>
 * <li>${GOOGLE_APPENGINE_SDK_ROOT}/lib/impl/appengine-api-stubs.jar</li>
 * <li>${GOOGLE_APPENGINE_SDK_ROOT}/lib/impl/appengine-api.jar</li>
 * <li>${GOOGLE_APPENGINE_SDK_ROOT}/lib/impl/appengine-local-runtime.jar</li>
 * <li>${GOOGLE_APPENGINE_SDK_ROOT}/lib/impl/appengine-api-labs.jar</li>
 * <ul>
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
