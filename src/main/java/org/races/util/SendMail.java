package org.races.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SendMail {
	
	/**
	  * Send a single email.
	  */
	private static Log log = LogFactory.getLog(SendMail.class);
	
	public String forgotPassword(String to_Email_Id,String email_message)
	{
		return sendEmail(to_Email_Id,email_message,"FORGOT PASSWORD");
	}
	
	public String sendEmail(String to_Email_Id,String email_message,String Subject){
	Session mailSession = createSmtpSession();
	mailSession.setDebug (true);

	try {
	    Transport transport = mailSession.getTransport ();

	    MimeMessage message = new MimeMessage (mailSession);

	    message.setFrom (new InternetAddress ("shashi.fengshui@gmail.com"));
	    message.setSubject(Subject);
	    message.setContent ("<h1>"+email_message+"</h1>", "text/html");
	    message.addRecipient (Message.RecipientType.TO, new InternetAddress (to_Email_Id));

	    transport.connect ();
	    transport.sendMessage (message, message.getRecipients (Message.RecipientType.TO));  
	}
	catch (MessagingException e) {
	    System.err.println("Cannot Send email");
	    log.fatal("Sorry !! Cannot Send Email : "+e.getMessage());
	    return "Sorry !! Cannot Send Email : "+e.getMessage();
	     
	}
	return "Mail Sent Successfully !! Please Check Your E-Mail Id";
	}

	private Session createSmtpSession() {
	final Properties props = new Properties();
	props.setProperty ("mail.host", "smtp.gmail.com");
	props.setProperty("mail.smtp.auth", "true");
	props.setProperty("mail.smtp.port", "" + 587);
	props.setProperty("mail.smtp.starttls.enable", "true");
	props.setProperty ("mail.transport.protocol", "smtp");
	// props.setProperty("mail.debug", "true");

	return Session.getDefaultInstance(props, new javax.mail.Authenticator() {
	  protected PasswordAuthentication getPasswordAuthentication() {
	    return new PasswordAuthentication("shashi.fengshui@gmail.com", "tommy312");
	  }
	});
	}


}
