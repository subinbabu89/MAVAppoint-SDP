package uta.mav.appoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.AllocateTime;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.email.RandomPasswordGenerator;
import uta.mav.appoint.helpers.TimeSlotHelpers;
import uta.mav.appoint.login.LoginUser;
import uta.mav.appoint.visitor.AllocateTimeVisitor;
import uta.mav.appoint.visitor.ManageTimeSlotVisitor;
import uta.mav.appoint.visitor.Visitor;

public class EditAdvisingScheduleServlet extends HttpServlet{
	HttpSession session;
	LoginUser user;
	AllocateTime at;
	String header, repeat;
	int noOfCAPSAlpha = 1;
	int noOfDigits = 1;
	int noOfSplChars = 1;
	int minLen = 20;
	int maxLen = 40;
	
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		user = (LoginUser)session.getAttribute("user");
		repeat = request.getParameter("repeatManage");
		
		if (user != null){
			header = "templates/" + user.getHeader() + ".jsp";
		try{
			at = new AllocateTime();
			at.setDate(request.getParameter("Date"));
			at.setStartTime(request.getParameter("StartTime2"));
			at.setEndTime(request.getParameter("EndTime2"));
			at.setEmail(user.getEmail()); //using pname to find correct advisor instead of email
			
			//delete data
			Visitor v = new ManageTimeSlotVisitor();
			user.accept(v,at);
			
			//insert data
			insertSchedule(request, response);
			
			request.setAttribute("response",user.getMsg());
		}
			catch(Exception e){
				System.out.printf(e.toString());
			}
		}
		else{
				header = "templates/header.jsp";
		}
		request.setAttribute("includeHeader", header);
	//	request.getRequestDispatcher("/WEB-INF/jsp/views/login.jsp").forward(request, response);
	
	}
		private void insertSchedule(HttpServletRequest request, HttpServletResponse response) {
			
			int rep;
			try{
				rep = Integer.parseInt(repeat);
			}
			catch(Exception e){
				rep = 0;
			}
			
			try{
							char[] tempValue = RandomPasswordGenerator.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits,noOfSplChars);
							String firstHash = tempValue.toString();
							String previousHash = "0"; 
							
							DatabaseManager dbm = new DatabaseManager(); 
							dbm.addTimeSlot(at,previousHash,firstHash);
							String msg = user.getMsg();
							response.setContentType("text/plain");
							response.setHeader("Cache-Control", "no-cache");
							response.setHeader("Pragma", "no-cache");
							response.setCharacterEncoding("UTF-8");
							PrintWriter out = response.getWriter();
							out.write(msg);
							out.flush();
							out.close();
							
							for (int i=0;i<rep;i++){
								char[] tempValue2 = RandomPasswordGenerator.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits,noOfSplChars);
								String nextHash = tempValue2.toString();
								
								at.setDate(TimeSlotHelpers.addDate(at.getDate(),1));
								dbm.addTimeSlot(at,firstHash,nextHash);
								msg = user.getMsg();
								response.setContentType("text/plain");
								response.setHeader("Cache-Control", "no-cache");
								response.setHeader("Pragma", "no-cache");
								response.setCharacterEncoding("UTF-8");
								out = response.getWriter();
								out.write(msg);
								out.flush();
								out.close();
								}
							}
						catch(Exception e){
							System.out.printf("Servlet error: " + e.toString());
						}
			
		}
}
