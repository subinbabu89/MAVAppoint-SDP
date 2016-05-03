package uta.mav.appoint.email;

import java.rmi.server.UID;
import java.util.Properties;

import javax.activation.DataHandler;
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
import javax.mail.util.ByteArrayDataSource;

public class AppointmentEmail {
	
	private String to, advisor_email, starttime, endtime, dateR;
	public AppointmentEmail(String appDate,String sTime, String eTime, String stuEmail, String advEmail){
		to = stuEmail;
		advisor_email = advEmail;
		starttime = sTime;
		endtime = eTime;
		dateR = appDate;
		
	}
	
	
	
	public  void sendEmail(){
	
	try {  
		String from = "maverickappointments@gmail.com";
		String pw = "gue#212!ns";
		String host = "smtp.gmail.com";
		String port = "465";
		/*String to = request.getParameter("student_email");
		String advisor_email = request.getParameter("advisor_email");
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String dateR = request.getParameter("date");*/
		//
		System.out.println("to"+to);
		System.out.println("Hello :P ");
		System.out.println("date"+dateR);
		System.out.println("advisor_email"+advisor_email);
		//convert start and end times to correct format
		
		String[] start  = starttime.split(":");
		String[] end  = endtime.split(":");
		String[] date  = dateR.split("-");
		starttime = date[0]+date[1]+date[2]+"T"+start[0]+start[1];
		endtime = date[0]+date[1]+date[2]+"T"+end[0]+end[1];
		
		System.out.println("starttime "+starttime);
		System.out.println("endtime "+endtime);

		
		String description = "This is a meeting request from Maverick Appointments. Please do not reply to this email.";
		String summary = "Maverick Appointments Advising Meeting Request";
		UID uid = new UID();
		Properties properties = System.getProperties();
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.host",host);
		properties.put("mail.smtp.user",from);
		properties.put("mail.smtp.password",pw);
		properties.put("mail.smtp.port",port);
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.socketFactory.port","465");
		properties.put("mail.smtp.socketFactory.class",
						"javax.net.ssl.SSLSocketFactory");
			
		Session session = Session.getDefaultInstance(properties,
				new javax.mail.Authenticator(){
					protected PasswordAuthentication getPasswordAuthentication(){
						return new PasswordAuthentication("maverickappointments@gmail.com","gue#212!ns");
					}
		});
	
		// Define message   
		MimeMessage message = new MimeMessage(session);  
		message.addHeaderLine("method=REQUEST");  
		message.addHeaderLine("charset=UTF-8");   
		message.addHeaderLine("component=VEVENT");   
		message.setFrom(new InternetAddress(from));   
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		
		message.setSubject("Meeting Request from Maverick Appointments");  
		StringBuffer sb = new StringBuffer();    
		StringBuffer buffer = sb.append(
				"BEGIN:VCALENDAR\r\n"+
						"PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\r\n"+  
						"VERSION:2.0\r\n" +  
						"METHOD:REQUEST\r\n" +
						"BEGIN:VTIMEZONE\r\n"+
							"TZID:Central Standard Time\r\n"+
						
							"BEGIN:DAYLIGHT\r\n"+
								"DTSTART:"+starttime+"\r\n"+
									"RRULE:FREQ=YEARLY;BYDAY=2SU;BYMONTH=3\r\n"+
									"TZOFFSETFROM:-0600\r\n"+
									"TZOFFSETTO:-0500\r\n"+    /*edit here the timezone*/
							"END:DAYLIGHT\r\n"+
							"BEGIN:STANDARD\r\n"+
								"DTSTART:"+starttime+"\r\n"+
								"RRULE:FREQ=YEARLY;BYDAY=1SU;BYMONTH=11\r\n"+
								"TZOFFSETFROM:-0500\r\n"+
								"TZOFFSETTO:-0600\r\n"+
							"END:STANDARD\r\n"+
							
						"END:VTIMEZONE\r\n"+
						"BEGIN:VEVENT\r\n" +  
							"ATTENDEE;RSVP=TRUE:MAILTO:" + to + "\r\n" +   
							"ORGANIZER:MAILTO:" + "maverickappointments@gmail.com" + "\r\n" +   
							"DTSTART;TZID=Central Standard Time:" + starttime + "00\r\n" +   
							"DTEND;TZID=Central Standard Time:" + endtime + "00\r\n" +   
							"LOCATION:Advisor Office\r\n" +  
							"TRANSP:OPAQUE\r\n" +  
							"SEQUENCE:0\r\n" +  
							"UID:"+uid+"\r\n"+ 
							"DTSTAMP:20160423T220900\r\n" +   
							"CATEGORIES:Meeting\r\n" +  
							"DESCRIPTION: " + description + "\r\n" +  
							"SUMMARY: " + summary + "\r\n" +   
							"PRIORITY:1\r\n" +  
							"CLASS:PUBLIC\r\n" +   
							"BEGIN:VALARM\r\n" +   
								"TRIGGER:PT1440M\r\n" +  
								"ACTION:DISPLAY\r\n" +   
								"DESCRIPTION:Reminder\r\n" +  
							"END:VALARM\r\n" +  
						"END:VEVENT\r\n" +
				"END:VCALENDAR");  // Create the message part  
   
			BodyPart messageBodyPart = new MimeBodyPart();   
   // Fill the message    
   messageBodyPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");  
   messageBodyPart.setHeader("Content-ID","calendar_message");   
   messageBodyPart.setDataHandler(new DataHandler( new ByteArrayDataSource(buffer.toString(), "text/calendar")));//very important  
   // Create a Multipart    
   Multipart multipart = new MimeMultipart("alternative");    
   // Add part one   
   multipart.addBodyPart(messageBodyPart);      
   // Put parts in message  
   message.setContent(multipart);    
   // send message   
   Transport.send(message);  
  } 
	catch (MessagingException me) {  
   me.printStackTrace();   
  } 
	catch (Exception ex) {  
   ex.printStackTrace();   
  }
	
	}
	
}
