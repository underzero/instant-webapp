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
    TestIwaEntityModelAgainstGAE.class})
public class RunAllIwaGaeSpecificTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(RunAllIwaGaeSpecificTests.class.getName());
		return suite;
	}
}
