package ch.ood.iwa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

/**
 * Test 
 * 
 * @author Mischa
 *
 */
public class PasswordGeneratorTest {
	
	private static final int PASSWORD_LENGTH = 8;

	@Test
	public void testPasswordLength() {
		String password = PasswordGenerator.createPassword(PASSWORD_LENGTH, PASSWORD_LENGTH);
		assertEquals(PASSWORD_LENGTH, password.length());
	}
	
	@Test
	public void testPasswordUniqueness() {
		String password1 = PasswordGenerator.createPassword(PASSWORD_LENGTH, PASSWORD_LENGTH);
		String password2 = PasswordGenerator.createPassword(PASSWORD_LENGTH, PASSWORD_LENGTH);
		assertNotSame(password1, password2);
	}
}
