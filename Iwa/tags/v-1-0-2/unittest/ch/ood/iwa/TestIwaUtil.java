package ch.ood.iwa;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test
 * 
 * @author Mischa
 *
 */
public class TestIwaUtil {
	
	private static final String VALID_EMAIL_ADDRESS = "mischa.christen@ood.ch";
	private static final String INVALID_EMAIL_ADDRESS_1 = "mischa.christen#ood.ch";
	private static final String INVALID_EMAIL_ADDRESS_2 = "@ood.ch";
	private static final String INVALID_EMAIL_ADDRESS_3 = "m@";

	// Class Under Test (cut)
	private static IwaUtil cut;

	@Before
	public void setUp() {
		cut = new IwaUtil();
	}
		
	@Test
	public void validEmail() {
		assertTrue(cut.isValidEmailAddress(VALID_EMAIL_ADDRESS));
	}

	@Test
	public void invalidEmail_1() {
		assertFalse(cut.isValidEmailAddress(INVALID_EMAIL_ADDRESS_1));
	}
	
	@Test
	public void invalidEmail_2() {
		assertFalse(cut.isValidEmailAddress(INVALID_EMAIL_ADDRESS_2));
	}
	
	@Test
	public void invalidEmail_3() {
		assertFalse(cut.isValidEmailAddress(INVALID_EMAIL_ADDRESS_3));
	}	
}
