package uta.mav.appoint;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.Appointment;
import uta.mav.appoint.beans.AppointmentType;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.email.Email;
import uta.mav.appoint.helpers.EncryptionDecryptionAES;
import uta.mav.appoint.login.LoginUser;

public class ScheduleAppointmentServlet extends HttpServlet {
	private static final long serialVersionUID = -5925080374199613248L;
	HttpSession session;
	String header;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser) session.getAttribute("user");
		if (user == null) {
			user = new LoginUser();
		}
		try {
			header = "templates/" + user.getHeader() + ".jsp";
			int id = Integer.parseInt(request.getParameter("id1"));
			ArrayList<TimeSlotComponent> array = (ArrayList<TimeSlotComponent>) session.getAttribute("schedules");
			DatabaseManager dbm = new DatabaseManager();
			ArrayList<AppointmentType> ats = dbm.getAppointmentTypes(request.getParameter("pname"));
			session.setAttribute("timeslot", array.get(id));
			session.setAttribute("appointmenttypes", ats);
		} catch (Exception e) {
			System.out.printf(e.toString());
		}
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/schedule_appointment.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession();
		try {
			Appointment a = new Appointment();
			LoginUser user = (LoginUser) session.getAttribute("user");
			/* Hien */
			a.setStudentPhoneNumber(request.getParameter("phoneNumber"));

			String student_Id = request.getParameter("studentid");
			System.out.println("student_Id::: " + student_Id);
			String encryptedStudentID = EncryptionDecryptionAES.encrypt(student_Id);
			System.out.println("encryptedStudentID::: " + encryptedStudentID);

			a.setAppointmentId(Integer.parseInt(request.getParameter("id2")));
			a.setStudentId(encryptedStudentID);
			a.setDescription(request.getParameter("description"));
			a.setAppointmentType(request.getParameter("apptype"));
			a.setPname(request.getParameter("pname"));
			a.setDescription(request.getParameter("description"));
			int d = Integer.parseInt(request.getParameter("duration"));
			String[] parts = (request.getParameter("start")).split(" ");
			a.setAdvisingDate(parts[3] + "-" + convertDate(parts[1]) + "-" + parts[2]);
			parts = parts[4].split(":");
			a.setAdvisingStartTime(parts[0] + ":" + parts[1]);
			a.setAdvisingEndTime(addTime(parts[0], parts[1], d));
			String email = request.getParameter("email");
			DatabaseManager dbm = new DatabaseManager();

			String appDate = a.getAdvisingDate();
			String sTime = a.getAdvisingStartTime();
			String eTime = a.getAdvisingEndTime();
			String stuEmail = request.getParameter("stuEmail");
			String advEmail = request.getParameter("advisor_email");

			// Hien (right above dbm.create...)
			System.out.println("Hien sent " + a.getStudentPhoneNumber());

			Boolean result = dbm.createAppointment(a, email);
			System.out.println("before big if !!!!!!!!!!!!!!!!!...");

			if (result == true) {
				response.setHeader("Refresh", "2; URL=index");
				request.getRequestDispatcher("/WEB-INF/jsp/views/success.jsp").forward(request, response);
				String sub = "Appointment set for " + a.getAdvisingDate();
				String mess = ",\nAn appointment has been set for " + a.getAdvisingDate() + " at "
						+ a.getAdvisingStartTime() + " - " + a.getAdvisingEndTime() + "\nAppoint ID: "
						+ a.getAppointmentId();
				Email newMail = new Email(sub, mess, email);

				/*
				 * AppointmentEmail ae = new
				 * AppointmentEmail(appDate,sTime,eTime,stuEmail,advEmail);
				 */

				System.out.println("before if !!!!!!!!!!!!!!!!!...");

				DatabaseManager dbm2 = new DatabaseManager();
				String emailnotification = dbm2.getNotification(user);
				System.out.println("emailnotification in scheduleappnt ...    " + emailnotification);
				session.setAttribute("emailnotification", emailnotification);

				// String en = (String)
				// session.getAttribute("emailnotification");
				System.out.println("emailnotification error ...>>>>" + emailnotification);
				if (emailnotification.equalsIgnoreCase("true")) {
					System.out.println("Sending email...");
					newMail.sendMail();

					/* ae.sendEmail(); */
				} else {
					System.out.println("else in email...");
					// newMail.sendMail();
				}
			} else {
				System.out.println("in  else !!!!!!!!!!!!!!!!!...");

				response.setHeader("Refresh", "2; URL=advising");
				request.getRequestDispatcher("/WEB-INF/jsp/views/failure.jsp").forward(request, response);
			}
		} catch (Exception e) {
			System.out.printf("EmailNotificationException " + e.toString());
		}
	}

	public String addTime(String hour, String minute, int add) {
		String result = "";
		try {
			int h = Integer.parseInt(hour);
			int m = Integer.parseInt(minute);
			if (m + add >= 60) {
				m = m + add - 60;
				h++;
			} else {
				m = m + add;
			}
			result = h + ":" + m;
		} catch (Exception e) {

		}
		return result;
	}

	public String convertDate(String d) {
		if (d.equals("Jan")) {
			return "1";
		}
		if (d.equals("Feb")) {
			return "2";
		}
		if (d.equals("Mar")) {
			return "3";
		}
		if (d.equals("Apr")) {
			return "4";
		}
		if (d.equals("May")) {
			return "5";
		}
		if (d.equals("Jun")) {
			return "6";
		}
		if (d.equals("Jul")) {
			return "7";
		}
		if (d.equals("Aug")) {
			return "8";
		}
		if (d.equals("Sep")) {
			return "9";
		}
		if (d.equals("Oct")) {
			return "10";
		}
		if (d.equals("Nov")) {
			return "11";
		}
		if (d.equals("Dec")) {
			return "12";
		}
		return null;
	}

}
