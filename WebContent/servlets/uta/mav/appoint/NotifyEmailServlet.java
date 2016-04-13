package uta.mav.appoint;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.LoginUser;
/**
 * Servlet implementation class ChangePasswordServlet
 */
@WebServlet("/NotifyEmailServlet")
public class NotifyEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
	String header;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		session = request.getSession();
		
		try{
			LoginUser user = (LoginUser)session.getAttribute("user");
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
		}
		catch(Exception e){
			header = "templates/header.jsp";
			System.out.println(e);
		}
		session.setAttribute("message", "");
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/notifyEmail.jsp").forward(request,response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
}
}