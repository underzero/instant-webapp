package ch.ood.iwa;

import java.util.Random;

/**
 * Another little Helper
 * 
 * @author Mischa
 *
 */
public class PasswordGenerator {

	/**
	 * Generates a random string that can be used as a password
	 *  
	 * @param min
	 * @param max
	 * @return the generated password as String
	 */
	public static String createPassword(int min, int max) {
		Random randGen = new Random(System.currentTimeMillis());

		// Guard
		if (min > max) return null;

		int length;

		if (min == max) {
			length = min;
		} else {
			length = randGen.nextInt(max + 1 - min) + min;
		}

		StringBuilder curStr = new StringBuilder();
		int counter = 0;

		while (counter < length) {
			// Range:  A = 65 to z = 122
			// 91-96 are special characters -> skip
			int value = randGen.nextInt(122 + 1 - 65) + 65;

			if (value >= 91 && value <= 96) {
				continue;
			} else {
				counter++;
			}
			char z = (char) value;
			curStr.append(z);
		}
		return curStr.toString();
	}
}
