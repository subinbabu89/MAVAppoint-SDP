package uta.mav.appoint;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.email.Email;
import uta.mav.appoint.email.RandomPasswordGenerator;
import uta.mav.appoint.helpers.EncryptionDecryptionAES;
import uta.mav.appoint.helpers.HashingClass;
import uta.mav.appoint.login.Department;
import uta.mav.appoint.login.StudentUser;
// sahana
import uta.mav.appoint.login.GuestUser;
import uta.mav.appoint.login.ProspectiveStudent;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; // test comment
	private ArrayList<Department> departments;
	private ArrayList<String> majors;
	private ArrayList<String> minors;//Added by Maithili
	private GuestUser guestUser;
	private ProspectiveStudent prospectiveStudent;//sahana
	private String email, phoneNumber, encryptedPassword;//
	int noOfCAPSAlpha = 1;
	int noOfDigits = 1;
	int noOfSplChars = 1;
	int minLen = 8;
	int maxLen = 12;
	HttpSession session;
	private ArrayList<String> majors1;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession();

		ArrayList<String> degreeType = new ArrayList<>();
		degreeType.add("Bachelor");
		degreeType.add("Master");
		degreeType.add("Doctorate");
		session.setAttribute("degreeType", degreeType);
		try {
			DatabaseManager dbm = new DatabaseManager();
			departments = dbm.getDepartments();
			session.setAttribute("departments", departments);

			// get majors from database
			majors = departments.get(0).getMajors();
			minors = departments.get(0).getMajors();
			session.setAttribute("major", majors);
			session.setAttribute("minor", minors);//Added by Maithili
		} catch (Exception e) {
			System.out.println(e + " RegisterServlet");
		}

		session.setAttribute("message", "");

		request.setAttribute("includeHeader", "templates/header.jsp");
		request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				String userType = request.getParameter("user_type");
		if (!Boolean.valueOf(request.getParameter("submitted"))) {
			session = request.getSession();
			String[] tempIndex = request.getParameterValues("drp_department");
			int[] departmentIndex1 = new int[tempIndex.length];
			for(int i = 0; i <tempIndex.length;i++)
			{ 
			   departmentIndex1[i] = Integer.parseInt(tempIndex[i]);
			   System.out.println("selected dept is "+departmentIndex1[i]);
			}
			session.setAttribute("tempDepts", tempIndex);
		
			ArrayList<Department> selectedDepartment = new ArrayList<Department>();
			selectedDepartment.clear();
			majors.clear();
			for (int m =0; m < departmentIndex1.length;m++){
				selectedDepartment.add(departments.get(departmentIndex1[m]));
				
				majors.addAll(selectedDepartment.get(m).getMajors());
				System.out.println("majors added are "+selectedDepartment.get(m).getMajors());
			}
			
			session.setAttribute("departments", departments);
			session.setAttribute("major", majors);
			session.setAttribute("minor", minors);//Added by Maithili
			session.setAttribute("user_type", userType);

			request.setAttribute("includeHeader", "templates/header.jsp");
			request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request, response);
		} else {
			session.setAttribute("user_type", userType);
			if(request.getParameter("user_type").toString().equals("guest_user")){
				createGuestUser(request,response);
			}else if(request.getParameter("user_type").toString().equals("prospective_student")){
				createProspectiveStudent(request, response);
			}else if(request.getParameter("user_type").toString().equals("current_student")){
			StudentUser studentUser = new StudentUser();
			String role = "student";
			studentUser.setRole(role);

			try {
				String email = request.getParameter("emailAddress");
				if (!email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
					System.out.println("Email Address Invalid");
					session.setAttribute("message", "Email Address Invalid");
					request.setAttribute("error", "Unable to add user");
					request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request, response);
				}
				studentUser.setEmail(email);

				char[] pswd = RandomPasswordGenerator.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits,
						noOfSplChars);

				String password = new String(pswd);
				System.out.println("password:::::: "+password);
				studentUser.setPassword(HashingClass.hashPassword(password));

				String phone_num = request.getParameter("phone_num");
				if (!phone_num.matches("^\\d{3}-\\d{3}-\\d{4}")) {
					System.out.println("Phone Number Invalid");
					session.setAttribute("message", "Phone Number Invalid");
					request.setAttribute("error", "Unable to add user");
					request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request, response);
				}
				studentUser.setPhoneNumber(phone_num);

				if (!request.getParameter("student_Id").matches("^100\\d{7}")
						&& !request.getParameter("student_Id").matches("^6000\\d{6}")) {
					System.out.println("Student ID Invalid");
					session.setAttribute("message", "Student ID Invalid");
					request.setAttribute("error", "Unable to add user");
					request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request, response);
				}
				
				String student_Id = request.getParameter("student_Id");

				String encryptedStudentID = EncryptionDecryptionAES.encrypt(student_Id);
				
				studentUser.setStudentId(encryptedStudentID);

				System.out.println("Last Name Initial: " + request.getParameter("drp_last_name_initial"));
				String lastNameInitial = request.getParameter("drp_last_name_initial");
				studentUser.setLastNameInitial(lastNameInitial);
//				Added by Maithili
				studentUser.setLastName(request.getParameter("student_lastname"));

				Integer degree_type = Integer.valueOf(request.getParameter("drp_degreeType"));
				studentUser.setDegType(degree_type);
				
				//edit below
				ArrayList<String> departmentsSelected = new ArrayList<String>();
				String[] tempdepts=(String[]) session.getAttribute("tempDepts");
				
				for(String currentDept : tempdepts){
					departmentsSelected.add(departments.get(Integer.parseInt(currentDept)).getName());
				}
				session.removeAttribute("tempDepts");
				
				
//				for(int i=0;i<tempdepts.length();i++)
//				{
//				String department = departments.get(Integer.valueOf(tempdepts.charAt(i))).toString();
//				departmentsSelected.add(department);
//				}
				
				studentUser.setDepartments(departmentsSelected);
				//
				
				System.out.println("param " + request.getParameter("drp_major").toString());
				ArrayList<String> majorsSelected = new ArrayList<String>();
				String majorFound = majors.get(Integer.valueOf(request.getParameter("drp_major")));
				majorsSelected.add(majorFound);
				studentUser.setMajors(majorsSelected);

				//Added by Maithili
				ArrayList<String> minorsSelected = new ArrayList<String>();
				String minorFound = minors.get(Integer.valueOf(request.getParameter("drp_minor")));
				minorsSelected.add(minorFound);
				studentUser.setMinors(minorsSelected);
				
				
				DatabaseManager dbm = new DatabaseManager();
				if (dbm.createStudent(studentUser)) {
					Email userEmail = new Email("MavAppoint Account Created",
							"Your account for MavAppoint has been created! Your account information is:\n" + "Role: "
									+ role + "\n" + "Password: " + password,
							email);
					userEmail.sendMail();
					session.setAttribute("message", "Account Created! Please check your e-mail for a new password.");
				} else {

					session.setAttribute("message", "Account could not be created");
				}
			} catch (Exception e) {
				System.out.println(e + " RegisterServlet");
			}

			request.setAttribute("includeHeader", "templates/header.jsp");
			request.getRequestDispatcher("/WEB-INF/jsp/views/login.jsp").forward(request, response);
		}
	}
	}
//
public void createGuestUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	guestUser = new GuestUser();
	guestUser.setRole(guestUser.role);
	email = request.getParameter("emailAddress");
	phoneNumber = request.getParameter("phone_num");
	
	try {
		if(verifyEmail(request)){
			guestUser.setEmail(email);
			encryptedPassword = encryptPassword();
			guestUser.setPassword(HashingClass.hashPassword(encryptedPassword));
		}else{
			request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request, response);
		}
		if(verifyPhoneNumber(request)){
			guestUser.setPhoneNumber(phoneNumber);
		}else{
			request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request, response);
		}
		guestUser.setLastNameInitial(request.getParameter("drp_last_name_initial"));
		guestUser.setDegType(Integer.valueOf(request.getParameter("drp_degreeType")));
		guestUser.setDepartments(getDepartments(request));
		guestUser.setMajors(getMajors(request));
		updateGuestDatabase(encryptedPassword);
		
	} catch (ServletException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}catch (SQLException e) {
		e.printStackTrace();
	}	
	request.setAttribute("includeHeader", "templates/header.jsp");
	request.getRequestDispatcher("/WEB-INF/jsp/views/login.jsp").forward(request, response);
}

public void createProspectiveStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	prospectiveStudent = new ProspectiveStudent();
	prospectiveStudent.setRole(prospectiveStudent.role);
	email = request.getParameter("emailAddress");
	phoneNumber = request.getParameter("phone_num");
	
	try {
		if(verifyEmail(request)){
			prospectiveStudent.setEmail(email);
			encryptedPassword = encryptPassword();
			prospectiveStudent.setPassword(HashingClass.hashPassword(encryptedPassword));
		}else{
			request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request, response);
		}
		if(verifyPhoneNumber(request)){
			prospectiveStudent.setPhoneNumber(phoneNumber);
		}else{
			request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request, response);
		}
		if (!request.getParameter("student_Id").matches("^100\\d{7}")
				&& !request.getParameter("student_Id").matches("^6000\\d{6}")) {
			System.out.println("Student ID Invalid");
			session.setAttribute("message", "Student ID Invalid");
			request.setAttribute("error", "Unable to add user");
			request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request, response);
		}
		
		String student_Id = request.getParameter("student_Id");

		String encryptedStudentID = EncryptionDecryptionAES.encrypt(student_Id);
		
		prospectiveStudent.setStudentId(encryptedStudentID);
		
		prospectiveStudent.setLastNameInitial(request.getParameter("drp_last_name_initial"));
		prospectiveStudent.setDegType(Integer.valueOf(request.getParameter("drp_degreeType")));
		prospectiveStudent.setDepartments(getDepartments(request));
		prospectiveStudent.setMajors(getMajors(request));
		updateProspectiveStudentDatabase(encryptedPassword);
		
	} catch (ServletException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}catch (SQLException e) {
		e.printStackTrace();
	}catch (Exception e) {
		e.printStackTrace();
	}
	request.setAttribute("includeHeader", "templates/header.jsp");
	request.getRequestDispatcher("/WEB-INF/jsp/views/login.jsp").forward(request, response);
}

public void updateGuestDatabase(String encryptedPassword) throws SQLException{
	DatabaseManager dbm = new DatabaseManager();
	if (dbm.createGuestUser(guestUser)) {
		Email userEmail = new Email("MavAppoint Account Created",
				"Your account for MavAppoint has been created! Your account information is:\n" + "Role: "
						+ guestUser.getRole() + "\n" + "Password: " + encryptedPassword,
				email);
		userEmail.sendMail();
		session.setAttribute("message", "Account Created! Please check your e-mail for a new password.");
	}else 
		session.setAttribute("message", "Account could not be created");
}

public void updateProspectiveStudentDatabase(String encryptedPassword2) throws SQLException{
	DatabaseManager dbm = new DatabaseManager();
	if (dbm.createProspectiveStudent(prospectiveStudent)) {
		Email userEmail = new Email("MavAppoint Account Created",
				"Your account for MavAppoint has been created! Your account information is:\n" + "Role: "
						+ prospectiveStudent.getRole() + "\n" + "Password: " + encryptedPassword2,
				email);
		userEmail.sendMail();
		session.setAttribute("message", "Account Created! Please check your e-mail for a new password.");
	}else 
		session.setAttribute("message", "Account could not be created");
}

public ArrayList<String> getMajors(HttpServletRequest request) {
	ArrayList<String> majorsSelected = new ArrayList<String>();
	String majorFound = majors.get(Integer.valueOf(request.getParameter("drp_major")));
	majorsSelected.add(majorFound);
	return majorsSelected;		
}

public ArrayList<String> getDepartments(HttpServletRequest request) {
	ArrayList<String> departmentsSelected = new ArrayList<String>();
	String[] tempdepts=(String[]) session.getAttribute("tempDepts");

	for(String currentDept : tempdepts){
		departmentsSelected.add(departments.get(Integer.parseInt(currentDept)).getName());
	}
	session.removeAttribute("tempDepts");
	return departmentsSelected;
}

public boolean verifyPhoneNumber(HttpServletRequest request) {
	if (!phoneNumber.matches("^\\d{3}-\\d{3}-\\d{4}")) {
		System.out.println("Phone Number Invalid");
		session.setAttribute("message", "Phone Number Invalid");
		request.setAttribute("error", "Unable to add user");
		return false;
	}
	return true;
}

public String encryptPassword() {
	char[] pswd = RandomPasswordGenerator.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits,
			noOfSplChars);
	String password = new String(pswd);
	return password;
}

public boolean verifyEmail(HttpServletRequest request){
	if (!email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
		System.out.println("Email Address Invalid");
		session.setAttribute("message", "Email Address Invalid");
		request.setAttribute("error", "Unable to add user");
		return false;
	}
	return true;
}


}
