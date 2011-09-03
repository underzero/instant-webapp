package ch.ood.iwa.persistence;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 
 * @author Mischa
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({    
    TestIwaEntityModelAgainstMySql.class})
public class RunAllRdbmsSpecificTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(RunAllRdbmsSpecificTests.class.getName());
		return suite;
	}
}
