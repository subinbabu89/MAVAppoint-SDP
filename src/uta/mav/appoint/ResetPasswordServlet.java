package uta.mav.appoint;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.email.Email;
import uta.mav.appoint.email.RandomPasswordGenerator;
import uta.mav.appoint.helpers.HashingClass;
import uta.mav.appoint.login.LoginUser;

public class ResetPasswordServlet extends HttpServlet{
	HttpSession session;
	int noOfCAPSAlpha = 1;
	int noOfDigits = 1;
	int noOfSplChars = 1;
	int minLen = 8;
	int maxLen = 12;
	LoginUser loginUser;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		session = req.getSession();
		DatabaseManager dbm = new DatabaseManager();
		String email = req.getParameter("emailAddress");
		if (!email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
			System.out.println("Email Address Invalid");
			session.setAttribute("message", "Email Address Invalid");
			req.setAttribute("error", "Cannot Change Password");
			req.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(req, resp);
		}
		// Rudy - Create a login user and set the various attributes to it. 
		
		loginUser = new LoginUser();
		// set email to login User
		loginUser.setEmail(email);
		Boolean checkIfExist = dbm.checkUserExist(loginUser);
		// if true , its a new user!
		if (checkIfExist){
			System.out.println("The User Doesnt Exist");
			session.setAttribute("accMessage", "Password cannot be reset! User is not a registered user.");
		}else {
			// This has to be done only if the user exist.
			// generate random password
			char[] pswd = RandomPasswordGenerator.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits,
					noOfSplChars);
			String password = new String(pswd);
			System.out.println("password:::::: "+password);
			// set newly create hashed password to the login user.
			loginUser.setPassword(HashingClass.hashPassword(password));
			// database call to reset password.
			Boolean result = dbm.resetPassword(loginUser);
			System.out.println("Result is" + result);
			Email userEmail = new Email("Password Reset Mail",
					"Your  Password has been reset. Your temporary password is "+ password, email);
			userEmail.sendMail();
			session.setAttribute("accMessage", "Password has been reset successfully. Check your email for the password");
			
		}
		
		
		
		
		req.setAttribute("includeHeader", "templates/header.jsp");
		req.getRequestDispatcher("/WEB-INF/jsp/views/index.jsp").forward(req, resp);
	
	}

}
