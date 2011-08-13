package ch.ood.iwa.persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;


/**
 * 
 * @author Mischa
 *
 */
public class TestEntityModelAgainstMySql extends TestEntityModel {
	
	public static String persistenceUnitName = "default";
		
	@BeforeClass
	public static void setUp() throws Exception {		
        FacadeFactory.registerFacade(persistenceUnitName, true);    
	}

	@AfterClass
	public static void tearDown() throws Exception {
		FacadeFactory.removeFacade(persistenceUnitName);
	}
}
