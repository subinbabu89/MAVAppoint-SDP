package uta.mav.appoint;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.AppointmentType;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.email.Email;
import uta.mav.appoint.login.LoginUser;
/**
 * Servlet implementation class ChangePasswordServlet
 */
@WebServlet("/NotifyEmailServlet")
public class NotifyEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
	String header;
	String emailnotification;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		session = request.getSession();
		LoginUser user = (LoginUser)session.getAttribute("user");
		if (user == null){
			user = new LoginUser();
			session.setAttribute("user", user);
		}
		try{
			header = "templates/" + user.getHeader() + ".jsp";
			DatabaseManager dbm = new DatabaseManager();
			emailnotification =dbm.getNotification(user);
			System.out.println("emailnotification before    "+emailnotification);
			session.setAttribute("emailnotification", emailnotification);
			System.out.println("emailnotification doget    "+emailnotification);
		}
		catch(Exception e){
			header = "templates/header.jsp";
			System.out.println(e);
		}
		
		request.setAttribute("includeHeader", header);
		System.out.println("emailnotification before request dispatcher    "+emailnotification);
		request.getRequestDispatcher("/WEB-INF/jsp/views/notifyEmail.jsp").forward(request,response);
		System.out.println("emailnotification after request dispatcher    "+emailnotification);
		return;
	}
	/*session = request.getSession();
	LoginUser user = (LoginUser)session.getAttribute("user");
	if (user == null){
		user = new LoginUser();
		session.setAttribute("user", user);
	}
		try{
				header = "templates/" + user.getHeader() + ".jsp";
				DatabaseManager dbm = new DatabaseManager();
				ArrayList<AppointmentType> ats = dbm.getAppointmentTypes(user.getPname());
				session.setAttribute("appointmenttypes", ats);	
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
	request.setAttribute("includeHeader", header);
	request.getRequestDispatcher("/WEB-INF/jsp/views/customize.jsp").forward(request,response);
}*/
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		LoginUser user = (LoginUser)session.getAttribute("user");
		System.out.println("somethin");
		try{
			
			String en=request.getParameter("emailnotification");
			if(en==null){
				en = "false";
			}
			
			System.out.println("something en"+en);
			user.setEmailNotification(en);
			session.setAttribute("emailnotification", en);
			 
			
			try{
				DatabaseManager dbm = new DatabaseManager();
				if (dbm.notifyEmail(user,en)){
//					String msg = "notification is changed."; 
//					user.setMsg(msg);
//					PrintWriter out = response.getWriter();
//					out.write(msg);
//					out.flush();
//					out.close();
//					response.setContentType("text/plain");
//					response.setHeader("Cache-Control", "no-cache");
//					response.setHeader("Pragma", "no-cache");
//					response.setCharacterEncoding("UTF-8");
					
					response.setHeader("Refresh", "2; URL=notifyEmail");
					request.getRequestDispatcher("/WEB-INF/jsp/views/success.jsp").forward(request,response);
					
				//	session.setAttribute("user", user);
					//session.setAttribute("message", "Notification changed");
					//request.setAttribute("includeHeader", header);
				//	request.getRequestDispatcher("/WEB-INF/jsp/views/notifyEmail.jsp").forward(request,response);
					 
					/*out.write(msg);
					out.flush();
					out.close();*/
					
				}
				else{
							
					user.setMsg("is not changed ");		
					String msg = user.getMsg();
					PrintWriter out = response.getWriter();	
					
/*					session.setAttribute("user", user);
					session.setAttribute("message", "Notification changed");
					request.setAttribute("includeHeader", header);
					request.getRequestDispatcher("/WEB-INF/jsp/views/notifyEmail.jsp").forward(request,response);*/
					
					out.write(msg);			
					out.flush();	
					out.close();
					
				
				}
			}
			catch(Exception e){
				user.setMsg("Unable to change..");
			}
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		request.setAttribute("includeHeader", header);
	//	request.getRequestDispatcher("/WEB-INF/jsp/views/notifyEmail.jsp").forward(request, response);
	}
}