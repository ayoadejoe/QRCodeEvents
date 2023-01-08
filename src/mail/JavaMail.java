package mail;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;  

public class JavaMail {

	public JavaMail(String domain, String fromEmail, String fromPassword, String text, String who, String toEmail) {
		  System.out.println("About to send an Email....");
		    String cc="admin@iq-joy.com";
		  
		   //Get the session object  
		   Properties props = new Properties();
		   props.put("mail.smtp.host", domain);
		   props.put("mail.smtp.auth", "true");
		     
		   Session session = Session.getDefaultInstance(props,  
		    new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {  
		    return new PasswordAuthentication(fromEmail,fromPassword);  
		      }  
		    });  
		  
		   //Compose the message  
		    try {  
		     MimeMessage message = new MimeMessage(session);  
		     message.setFrom(new InternetAddress(fromEmail));  
		     message.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));  
		     message.addRecipient(Message.RecipientType.BCC,new InternetAddress(cc));  
		     message.setSubject(who);  
		     message.setText(text); 
		     
		     BodyPart messageBodyPart = new MimeBodyPart(); 
		     messageBodyPart.setText(text);
		     
		     //attachment
		     MimeBodyPart attachmentPart = new MimeBodyPart();
		     attachmentPart.attachFile(new File("demo.png"));
		     
		     //add body parts
		     Multipart multipart = new MimeMultipart();
		     multipart.addBodyPart(messageBodyPart);
		     multipart.addBodyPart(attachmentPart);
		       
		    //send the message  
		     message.setContent(multipart);
		     Transport.send(message);
		  
		     System.out.println("message sent successfully...");  
		   
		     } catch (MessagingException | IOException e) {e.printStackTrace();}  
	}

	public static void main(String[] args) {
		new JavaMail("mail.iq-joy.com", "admin@iq-joy.com", "Genesis100%", "YOU ARE HAPPY", 
				"Joseph", "ayoadejoe@gmail.com");	//nogosafarmltd@outlook.com

	}

}
