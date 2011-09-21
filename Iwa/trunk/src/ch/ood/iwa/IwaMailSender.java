package ch.ood.iwa;

import java.io.Serializable;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * A simple mailsender.<br/>
 * It uses the following properties from the iwa.properties file:<br/>
 * 
 * <pre>
 * # IWA default settings
 * iwa.mail.host=somesmtp.mailprovider.com
 * iwa.mail.sender.mail=someone@somedomain.com
 * iwa.mail.sender.name=Sysadmin
 * </pre>
 * 
 * You can also derive from this class to implement your specialized Mailsender
 * class, use {@link IwaMailSender#getMailSession()} to receive a ready-to-use
 * mail session.
 * 
 * @author Mischa
 * 
 */
public class IwaMailSender implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String MAIL_HOST_KEY = "mail.host";
	private static final String SENDER_MAIL_KEY = "iwa.mail.sender.mail";
	private static final String SENDER_NAME_KEY = "iwa.mail.sender.name";
	private static final String GOOGLE_APPENGINE_RUNTIME_ENVIRONMENT = "com.google.appengine.runtime.environment";

	/**
	 * Convenience method for sending an email
	 * 
	 * @param emailAddress
	 * @param content
	 */
	public static void send(String receiverEmail, String receiverName, String subject,
			String content) throws Exception {
		Session session = getMailSession();
		String encodingOptions = "text/plain; charset=UTF-8";

		String senderMail = IwaApplication.getInstance().getProperties()
				.getProperty(SENDER_MAIL_KEY);
		String senderName = IwaApplication.getInstance().getProperties()
				.getProperty(SENDER_NAME_KEY);

		MimeMessage msg = new MimeMessage(session);
		msg.setHeader("Content-Type", encodingOptions);
		
		IwaApplication.getInstance().log(null, null, " *** Sending Mail from " + senderName + "(" + senderMail + ")");
		IwaApplication.getInstance().log(null, null, " *** Sending Mail to " + receiverName + "(" + receiverEmail + ")");
		msg.setFrom(new InternetAddress(senderMail, senderName));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail, receiverName));
		msg.setSubject(subject, "UTF-8");
		msg.setText(content, "UTF-8");
		
		Transport.send(msg);
	}

	/**
	 * Returns a ready-to-use MailSession. <br/>
	 * Usage:<br/> 
	 * <pre>
	 * Session session = getMailSession();
	 * Message msg = new MimeMessage(session);
	 * msg.setFrom(new InternetAddress(senderMail, senderName));
	 * msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail, receiverName));
	 * msg.setSubject(subject);
	 * msg.setText(content);
	 * Transport.send(msg);
	 * </pre>
	 * 
	 * @return
	 */
	protected static Session getMailSession() {
		String runtimeEnvironment = System.getProperty(GOOGLE_APPENGINE_RUNTIME_ENVIRONMENT);
		Properties properties = new Properties();

		if (runtimeEnvironment == null) {
			// this is no GAE environment, so we need to provide some MailServer
			properties.put(MAIL_HOST_KEY,
					IwaApplication.getInstance().getProperties().getProperty(MAIL_HOST_KEY));
		}
		return Session.getDefaultInstance(properties, null);
	}
}
