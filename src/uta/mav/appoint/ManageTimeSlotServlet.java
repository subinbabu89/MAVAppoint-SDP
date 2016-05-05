package uta.mav.appoint;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.AllocateTime;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.AdvisorUser;
import uta.mav.appoint.login.LoginUser;
import uta.mav.appoint.visitor.AppointmentVisitor;
import uta.mav.appoint.visitor.ManageTimeSlotVisitor;
import uta.mav.appoint.visitor.Visitor;

public class ManageTimeSlotServlet extends HttpServlet {
	HttpSession session;
	String header;
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser)session.getAttribute("user");
		if (user != null){
			header = "templates/" + user.getHeader() + ".jsp";
		try{
			AllocateTime at = new AllocateTime();
			at.setDate(request.getParameter("Date"));
			at.setStartTime(request.getParameter("StartTime2"));
			at.setEndTime(request.getParameter("EndTime2"));
			at.setEmail(user.getEmail()); //using pname to find correct advisor instead of email
			at.setOldDate(request.getParameter("oldDate"));
			at.setOldStartTime(request.getParameter("oldStartTime2"));
			at.setOldEndTime(request.getParameter("oldEndTime2"));
			Visitor v = new ManageTimeSlotVisitor();
			user.accept(v,at);
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
}
