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

import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.email.Email;
import uta.mav.appoint.login.AdvisorUser;
import uta.mav.appoint.login.Department;
import uta.mav.appoint.login.LoginUser;

/**
 * Servlet implementation class DeleteAdvisorServlet
 */
@WebServlet("/DeleteAdvisorServlet")
public class DeleteAdvisorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Department> departments;
    private HttpSession session;   
    private String header;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAdvisorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser)session.getAttribute("user");
		
		try {
			DatabaseManager dbm = new DatabaseManager();
			departments = dbm.getDepartments();
			session.setAttribute("departments", departments);
		} catch(Exception e){
			System.out.println(e+" RegisterServlet");
		}
		
		if (user == null){
				user = new LoginUser();
				session.setAttribute("user", user);
				response.sendRedirect("/WEB-INF/jsp/views/login.jsp");		
		}
		else{
			try{
				header = "templates/" + user.getHeader() + ".jsp";
			}
			catch(Exception e){
				System.out.printf(e.toString());
			}
		}
		
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/delete_advisor.jsp").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoginUser user = (LoginUser)session.getAttribute("user");
		try{
			
			String advisorEmail=(String)request.getParameter("emailAddress");
			
			
			try{
				DatabaseManager dbm = new DatabaseManager();
				if (dbm.deleteAdvisor(advisorEmail)){
					user.setMsg("Advisor account is deleted.");
					
					String msg = user.getMsg();
					
					response.setContentType("text/plain");
					response.setHeader("Cache-Control", "no-cache");
					response.setHeader("Pragma", "no-cache");
					response.setCharacterEncoding("UTF-8");
					PrintWriter out = response.getWriter();
					
					String msgSub = "Mavappoint User Information";

					String msgText ="An advisor account has been deleted for your email address! ";
			            	
					String toEmail = advisorEmail;
					
					Email newMail = new Email(msgSub, msgText, toEmail);
					newMail.sendMail();
					out.write(msg);
					out.flush();
					out.close();
					
				}
				else{
							
					user.setMsg("Advisor can not be deleted ");		
					String msg = user.getMsg();
					PrintWriter out = response.getWriter();	
					out.write(msg);			
					out.flush();	
					out.close();
					
				
				}
			}
			catch(Exception e){
				user.setMsg("Unable to delete advisor..");
			}
		}
		catch(Exception e){
			System.out.println(e.toString());
		}

		request.setAttribute("includeHeader", header);
		//request.getRequestDispatcher("/WEB-INF/jsp/views/delete_advisor.jsp").forward(request, response);
	}

}
