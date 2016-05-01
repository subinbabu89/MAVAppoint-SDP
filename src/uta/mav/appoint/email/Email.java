package uta.mav.appoint.email;

import javax.mail.*;
import javax.mail.internet.*;

import uta.mav.appoint.SendEmailServlet;

import javax.activation.*;
import java.util.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class Email {
	private String subject;
	private String text;
	private String toEmail;
	String filename = "config.properties";
	Properties properties = new Properties();
	InputStream inputStream = null;
	
	public Email(String sub, String txt, String destEmail)
	{
		subject = sub;
		text = txt;
		toEmail = destEmail;
		//toEmail = "mavappoint.donotreply@gmail.com";
	}
	public void sendMail()
	{
		
		inputStream = SendEmailServlet.class.getClassLoader().getResourceAsStream(filename);
		if(inputStream==null){
	            System.out.println("Sorry, unable to find " + filename);
		    return;
		}

		try {
			properties.load(inputStream);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final String user = properties.getProperty("mail.smtp.user");
        final String pass = properties.getProperty("mail.smtp.password");
 
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
 
        Session session = Session.getInstance(properties,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
          });
 
        try {
 
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mavappoint.donotreply@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(text);
 
            Transport.send(message);
 
            System.out.println("Done211313" +toEmail);
 
        } 
		catch (MessagingException e) {
            throw new RuntimeException(e);
        }
	}
}
