package ch.ood.iwa;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Helper 
 * 
 * @author Mischa
 *
 */
public class IwaUtil {

	/**
	 * Validate the form of an email address.
	 * 
	 * <P>
	 * Return <tt>true</tt> only if
	 * <ul>
	 * <li> <tt>aEmailAddress</tt> can successfully construct an
	 * {@link javax.mail.internet.InternetAddress}
	 * <li>when parsed with "@" as delimiter, <tt>aEmailAddress</tt> contains
	 * two tokens which satisfy
	 * {@link IwaUtil#textHasContent(String)}.
	 * </ul>
	 * 
	 * <P>
	 * The second condition arises since local email addresses, simply of the
	 * form "<tt>albert</tt>", for example, are valid for
	 * {@link javax.mail.internet.InternetAddress}, but almost always undesired.
	 * 
	 * This function has been copied from the www.web4j.com Framework that has been released under 
	 * the BSD Open Source License: Copyright © 2011 Hirondelle Systems. All Rights Reserved.
	 * 
	 */
	public boolean isValidEmailAddress(String aEmailAddress) {
		if (aEmailAddress == null)
			return false;
		boolean result = true;
		try {
			new InternetAddress(aEmailAddress);
			if (!hasNameAndDomain(aEmailAddress)) {
				result = false;
			}
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	private boolean hasNameAndDomain(String aEmailAddress) {
		String[] tokens = aEmailAddress.split("@");
		return tokens.length == 2 && textHasContent(tokens[0]) && textHasContent(tokens[1]);
	}

	/**
	 * Return <tt>true</tt> only if <tt>aText</tt> is not null, and is not empty
	 * after trimming. (Trimming removes both leading/trailing whitespace and
	 * ASCII control characters. See {@link String#trim()}.)
	 * 
	 * <P>
	 * For checking argument validity, {@link Args#checkForContent} should be
	 * used instead of this method.
	 * 
	 * This function has been copied from the www.web4j.com Framework that has been released under 
	 * the BSD Open Source License: Copyright © 2011 Hirondelle Systems. All Rights Reserved.
	 *  
	 * @param aText possibly-null.
	 */
	public boolean textHasContent(String aText) {
		return (aText != null) && (aText.trim().length() > 0);
	}

}
