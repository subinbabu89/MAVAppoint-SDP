package uta.mav.appoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.CreateAdvisorBean;
import uta.mav.appoint.login.AdvisorUser;
import uta.mav.appoint.login.Department;
import uta.mav.appoint.login.LoginUser;
import uta.mav.appoint.visitor.AppointmentVisitor;
import uta.mav.appoint.visitor.Visitor;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.email.Email;
import uta.mav.appoint.email.RandomPasswordGenerator;
import uta.mav.appoint.helpers.HashingClass;

/*
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.*;
*/

/**
 * Servlet implementation class ViewAppointmentServlet
 */
public class CreateAdvisorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<Department> departments;
	private HttpSession session;
	private String header;
	int noOfCAPSAlpha = 1;
	int noOfDigits = 1;
	int noOfSplChars = 1;
	int minLen = 8;
	int maxLen = 12;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser) session.getAttribute("user");

		try {
			DatabaseManager dbm = new DatabaseManager();
			departments = dbm.getDepartments();
			session.setAttribute("departments", departments);
		} catch (Exception e) {
			System.out.println(e + " RegisterServlet");
		}

		if (user == null) {
			user = new LoginUser();
			session.setAttribute("user", user);
			response.sendRedirect("/WEB-INF/jsp/views/login.jsp");
			return;
		} else {
			try {
				header = "templates/" + user.getHeader() + ".jsp";
			} catch (Exception e) {
				System.out.printf(e.toString());
			}
		}

		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/create_advisor.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LoginUser user = (LoginUser) session.getAttribute("user");
		try {
			AdvisorUser advisorUser = new AdvisorUser();
			advisorUser.setEmail((String) request.getParameter("emailAddress"));
			char[] pswd = RandomPasswordGenerator.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits,
					noOfSplChars);

			String password = new String(pswd);
			System.out.println("password:::::: "+password);
			advisorUser.setPassword(HashingClass.hashPassword(password));
			advisorUser.setNotification("day");
			advisorUser.setPname((String) request.getParameter("pname"));

			ArrayList<String> departmentsSelected = new ArrayList<String>();
			String departmentFound = departments.get(Integer.valueOf(request.getParameter("drp_department"))).getName();
			departmentsSelected.add(departmentFound);
			advisorUser.setDepartments(departmentsSelected);

			advisorUser.setMajors(departments.get(Integer.valueOf(request.getParameter("drp_department"))).getMajors());
			advisorUser.setMajors(new ArrayList<String>());

			advisorUser.setNameLow("A");
			advisorUser.setNameHigh("Z");
			advisorUser.setDegType(7);
			//advisorUser.setIsLead(Integer.valueOf(request.getParameter("isLead")));
			advisorUser.setIsLead(0);
			try {
				DatabaseManager dbm = new DatabaseManager();
				if (dbm.createAdvisor(advisorUser)) {
					user.setMsg("Advisor account created with password sent to advisor's e-mail.");

					String msg = user.getMsg();

					response.setContentType("text/plain");
					response.setHeader("Cache-Control", "no-cache");
					response.setHeader("Pragma", "no-cache");
					response.setCharacterEncoding("UTF-8");
					PrintWriter out = response.getWriter();

					String msgSub = "Mavappoint User Information";

					String msgText = "An advisor account has been created for your email address! Login to change your password. Your login information is:"
							+ "\nUsername: " + advisorUser.getPname() + "\npassword: \"" + password
							+ "\" " + "\nMavAppoint";
					String toEmail = advisorUser.getEmail();

					Email newMail = new Email(msgSub, msgText, toEmail);
					newMail.sendMail();
					out.write(msg);
					out.flush();
					out.close();

				} else {
					user.setMsg("Advisor cannot be created");
					String msg = user.getMsg();
					PrintWriter out = response.getWriter();
					out.write(msg);
					out.flush();
					out.close();

				}
			} catch (Exception e) {
				user.setMsg("Unable to create advisor..");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/create_advisor.jsp").forward(request, response);
	}
}
