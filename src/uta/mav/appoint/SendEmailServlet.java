package uta.mav.appoint;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SendEmailServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -969978370422916362L;
	String filename = "config.properties";
	Properties properties = new Properties();
	InputStream inputStream = null;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try{
		String to = (String)request.getParameter("to");
		String subject = (String)request.getParameter("subject");
		String body = (String)request.getParameter("body");
		
		inputStream = SendEmailServlet.class.getClassLoader().getResourceAsStream(filename);
		if(inputStream==null){
	            System.out.println("Sorry, unable to find " + filename);
		    return;
		}

		properties.load(inputStream);
			
		Session session = Session.getDefaultInstance(properties,
				new javax.mail.Authenticator(){
					protected PasswordAuthentication getPasswordAuthentication(){
						//modified to fix the bug : send email on clicking on 
						//"Email" button in appointments page - on March 30, 2016. by Sahana
						//return new PasswordAuthentication("maverickappointments@gmail.com","sFre192R!");
						return new PasswordAuthentication(properties.getProperty("mail.smtp.user"),properties.getProperty("mail.smtp.password"));
					}
		});
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
			javax.mail.Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(properties.getProperty("mail.smtp.user")));
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
			message.setText(body);
			message.setSubject(subject);
			Transport.send(message);
			out.write("Message sent successfully.");
		}
		catch(Exception mex){
			mex.printStackTrace();
		}
	}	
}
