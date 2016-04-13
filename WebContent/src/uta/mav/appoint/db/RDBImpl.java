package uta.mav.appoint.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import uta.mav.appoint.PrimitiveTimeSlot;
import uta.mav.appoint.TimeSlotComponent;
import uta.mav.appoint.beans.AllocateTime;
import uta.mav.appoint.beans.Appointment;
import uta.mav.appoint.beans.AppointmentType;
import uta.mav.appoint.beans.CreateAdvisorBean;
import uta.mav.appoint.beans.GetSet;
import uta.mav.appoint.beans.RegisterBean;
import uta.mav.appoint.db.command.*;
import uta.mav.appoint.flyweight.TimeSlotFlyweightFactory;
import uta.mav.appoint.helpers.TimeSlotHelpers;
import uta.mav.appoint.login.*;

public class RDBImpl implements DBImplInterface{

	public Connection connectDB(){
		try
	    {
	    Class.forName("com.mysql.jdbc.Driver").newInstance();
	    String jdbcUrl = "jdbc:mysql://localhost:3306/MavAppointDB2S";
	    String userid = "team2Spring";
	    String password = "er1ja@18xs@33";
	    Connection conn = DriverManager.getConnection(jdbcUrl,userid,password);
	    return conn;
	    }
	    catch (Exception e){
	        System.out.println(e.toString());
	    }
	    return null;
	}
	
			
	//user login checking, check username and password against database
	//then return role if a match is found
	//using command pattern
	public LoginUser checkUser(GetSet set) throws SQLException{
		LoginUser user = null;
		try{
			SQLCmd cmd = new CheckUser(set.getEmailAddress(), set.getPassword());
			cmd.execute();
			System.out.println("Result = "+cmd.getResult());
			user = (LoginUser)(cmd.getResult()).get(0);
			
		}
		catch(Exception e){
			System.out.println(e+" -- FOUND IN -- "+this.getClass().getSimpleName());
		}
		return user;
	}
	
	public Boolean updateAppointment(Appointment a){
		
		try{
			SQLCmd cmd = new UpdateAppointment(a);
			cmd.execute();
			System.out.println("Finished update");

			System.out.println("Found id " + a.getAppointmentId());
			cmd = new GetAppointmentById(a);
			cmd.execute();
			System.out.println("Finished getting appointment");
			
			return true;
		}
		catch(Exception e){
			System.out.println(e+"-- Found in -- "+ this.getClass().getSimpleName());
		}
		return false;
	}
	
	public Boolean createStudent(StudentUser studentUser){
		try{
			SQLCmd cmd = new CreateUser(studentUser);
			cmd.execute();
			System.out.println("Created User"+cmd.getResult());
			
			cmd = new CreateStudent(studentUser);
			cmd.execute();
			System.out.println(cmd.getResult());
			return (Boolean)cmd.getResult().get(0);
		}
		catch(Exception e){
			System.out.println(e+" -- Found In -- "+this.getClass().getName());
			return false;
		}
	}
	
	//using command pattern
	public ArrayList<String> getAdvisors() throws SQLException{
		ArrayList<String> arraylist = new ArrayList<String>();
		try{
			SQLCmd cmd = new GetAdvisors();
			cmd.execute();
			ArrayList<Object> tmp = cmd.getResult();
			for (int i=0;i<tmp.size();i++){
				arraylist.add(((String)tmp.get(i)));
			}
		}
		catch(Exception sq){
			System.out.printf(sq.toString());
		}
		return arraylist;
	}
	
	public ArrayList<TimeSlotComponent> getAdvisorSchedule(String name){
		ArrayList<TimeSlotComponent> array = new ArrayList<TimeSlotComponent>();
		try {
			Connection conn = this.connectDB();
			PreparedStatement statement;
			if (name.equals("all")){
			String command = "SELECT pname,date,start,end,id FROM user,Advising_Schedule,User_Advisor "
							+ "WHERE user.userid=User_Advisor.userid AND user.userid=Advising_Schedule.userid AND studentId is null";
			statement = conn.prepareStatement(command);
			}
			else{
				String command = "SELECT pname,date,start,end,id FROM USER,Advising_Schedule,User_Advisor "
								+ "WHERE USER.userid=User_Advisor.userid AND USER.userid=Advising_Schedule.userid AND USER.userid=Advising_Schedule.userid AND User_Advisor.pname=? AND studentId is null";
				statement = conn.prepareStatement(command);
				statement.setString(1,name);
			}	
			ResultSet res = statement.executeQuery();
			while(res.next()){
				//Use flyweight factory to avoid build cost if possible
				PrimitiveTimeSlot set = (PrimitiveTimeSlot)TimeSlotFlyweightFactory.getInstance().getFlyweight(res.getString(1)+"-"+res.getString(2),res.getString(3));
				set.setName(res.getString(1));
				set.setDate(res.getString(2));
				set.setStartTime(res.getString(3));
				set.setEndTime(res.getString(4));
				set.setUniqueId(res.getInt(5));
				array.add(set);
			}
			array = TimeSlotHelpers.createCompositeTimeSlot(array);
			conn.close();
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
		return array;
	}

	public ArrayList<TimeSlotComponent> getAdvisorSchedules(ArrayList<AdvisorUser> advisorUsers){
		ArrayList<TimeSlotComponent> timeSlots = new ArrayList<TimeSlotComponent>();
		try {
			Connection conn = this.connectDB();
			PreparedStatement statement;
			for(int i=0; i<advisorUsers.size(); i++)
			{
				String command = "SELECT pname,date,start,end,id FROM USER,Advising_Schedule,User_Advisor "
								+ "WHERE USER.userid=User_Advisor.userid AND USER.userid=Advising_Schedule.userid AND USER.userid=Advising_Schedule.userid AND User_Advisor.pname=? AND studentId is null";
				statement = conn.prepareStatement(command);
				statement.setString(1,advisorUsers.get(i).getPname());
				ResultSet res = statement.executeQuery();
				while(res.next()){
					//Use flyweight factory to avoid build cost if possible
					PrimitiveTimeSlot set = (PrimitiveTimeSlot)TimeSlotFlyweightFactory.getInstance().getFlyweight(res.getString(1)+"-"+res.getString(2),res.getString(3));
					set.setName(res.getString(1));
					set.setDate(res.getString(2));
					set.setStartTime(res.getString(3));
					set.setEndTime(res.getString(4));
					set.setUniqueId(res.getInt(5));
					timeSlots.add(set);
				}
			}	
			timeSlots = TimeSlotHelpers.createCompositeTimeSlot(timeSlots);
			conn.close();
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
		return timeSlots;
	}

	public Boolean createAppointment(Appointment appointment, String email){
		Boolean result = false;
		int student_id = 0;
		int advisor_id = 0;
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT userid from user where email=?";
			statement=conn.prepareStatement(command);
			statement.setString(1,email);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				student_id = rs.getInt(1);
			}
			command = "SELECT userid FROM User_Advisor WHERE User_Advisor.pname=?";
			statement=conn.prepareStatement(command);
			statement.setString(1, appointment.getPname());
			rs = statement.executeQuery();
			while(rs.next()){
				advisor_id = rs.getInt(1);
			}
			//check for slots already taken
			command = "SELECT COUNT(*) FROM Advising_Schedule WHERE userid=? AND date=? AND start=? AND end=? AND studentId is not null";
			statement = conn.prepareStatement(command);
			statement.setInt(1, advisor_id);
			statement.setString(2, appointment.getAdvisingDate());
			statement.setString(3, appointment.getAdvisingStartTime());
			statement.setString(4, appointment.getAdvisingEndTime());
			rs = statement.executeQuery();
			while(rs.next()){
				if (rs.getInt(1) < 1){
					command = "INSERT INTO Appointments (id,advisor_userid,student_userid,date,start,end,type,studentId,description,student_email,student_cell)"
							+"VALUES(?,?,?,?,?,?,?,?,?,?,?)";
					statement = conn.prepareStatement(command);
					statement.setInt(1, appointment.getAppointmentId());
					statement.setInt(2,advisor_id);
					statement.setInt(3,student_id);
					statement.setString(4,appointment.getAdvisingDate());
					statement.setString(5,appointment.getAdvisingStartTime());
					statement.setString(6,appointment.getAdvisingEndTime());
					statement.setString(7,appointment.getAppointmentType());
					statement.setInt(8,Integer.parseInt(appointment.getStudentId()));
					statement.setString(9,appointment.getDescription());
					statement.setString(10,email);
					statement.setString(11,appointment.getStudentPhoneNumber());
					
					System.out.println("Update about to execute");
					statement.executeUpdate();
					System.out.println("Should have set "+appointment.getStudentPhoneNumber());
					System.out.println("Update should have executed");
					
					command = "UPDATE Advising_Schedule SET studentId=? where userid=? AND date=? and start >= ? and end <= ?";
					statement=conn.prepareStatement(command);
					statement.setInt(1,Integer.parseInt(appointment.getStudentId()));
					statement.setInt(2, advisor_id);
					statement.setString(3, appointment.getAdvisingDate());
					statement.setString(4, appointment.getAdvisingStartTime());
					statement.setString(5, appointment.getAdvisingEndTime());
					statement.executeUpdate();
					result = true;
				}
			}
			conn.close();
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
		return result;
	}

	public ArrayList<Object> getAppointments(AdvisorUser user){
		ArrayList<Object> Appointments = new ArrayList<Object>();
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
//			String command = "SELECT User_Advisor.pname,User_Advisor.email,date,start,end,type,id,Appointments.description,studentId,Appointments.student_email FROM USER,Appointments,User_Advisor "
					String command = "SELECT User_Advisor.pname,User.email,date,start,end,type,id,Appointments.description,studentId,Appointments.student_email,Appointments.student_cell FROM USER,Appointments,User_Advisor "
						+ "WHERE USER.email=? AND user.userid=Appointments.advisor_userid AND User_Advisor.userid=Appointments.advisor_userid";
			statement = conn.prepareStatement(command);
			statement.setString(1, user.getEmail());
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				Appointment set = new Appointment();
				set.setPname(rs.getString(1));
				set.setAdvisorEmail(rs.getString(2));
				set.setAdvisingDate(rs.getString(3));
				set.setAdvisingStartTime(rs.getString(4));
				set.setAdvisingEndTime(rs.getString(5));
				set.setAppointmentType(rs.getString(6));
				set.setAppointmentId(rs.getInt(7));
				set.setDescription(rs.getString(8));
				set.setStudentId(rs.getString(9));
				set.setStudentEmail(rs.getString(10));
				set.setStudentPhoneNumber(rs.getString(11));
				Appointments.add(set);
			}
			conn.close();
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
		
		return Appointments;
	}

	public ArrayList<Object> getAppointments(StudentUser user){
		ArrayList<Object> Appointments = new ArrayList<Object>();
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
//			String command = "SELECT User_Advisor.pname,User_Advisor.email,date,start,end,type,id,description,student_email FROM USER,Appointments,User_Advisor "
					String command = "SELECT User_Advisor.pname,User.email,date,start,end,type,id,description,student_email FROM USER,Appointments,User_Advisor "
						+ "WHERE USER.email=? AND user.userid=Appointments.student_userid AND User_Advisor.userid=Appointments.advisor_userid";
			statement = conn.prepareStatement(command);
			statement.setString(1, user.getEmail());
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				Appointment set = new Appointment();
				set.setPname(rs.getString(1));
				set.setAdvisorEmail(rs.getString(2));
				set.setAdvisingDate(rs.getString(3));
				set.setAdvisingStartTime(rs.getString(4));
				set.setAdvisingEndTime(rs.getString(5));
				set.setAppointmentType(rs.getString(6));
				set.setAppointmentId(rs.getInt(7));
				set.setDescription(rs.getString(8));
				set.setStudentId("Advisor only");
				set.setStudentEmail(rs.getString(9));
				Appointments.add(set);
			}
			conn.close();
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
		
		return Appointments;
	}

	public ArrayList<Object> getAppointments(AdminUser user){
		ArrayList<Object> Appointments = new ArrayList<Object>();
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
//			String command = "SELECT User_Advisor.pname,User_Advisor.email,date,start,end,type,id FROM Appointments INNER JOIN User_Advisor "
					String command = "SELECT User_Advisor.pname,User.email,date,start,end,type,id FROM Appointments INNER JOIN User_Advisor "
						+"WHERE User_Advisor.userid = Appointments.advisor_userid";
			statement = conn.prepareStatement(command);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				Appointment set = new Appointment();
				set.setPname(rs.getString(1));
				set.setAdvisorEmail(rs.getString(2));
				set.setAdvisingDate(rs.getString(3));
				set.setAdvisingStartTime(rs.getString(4));
				set.setAdvisingEndTime(rs.getString(5));
				set.setAppointmentType(rs.getString(6));
				set.setAppointmentId(rs.getInt(7));
				Appointments.add(set);
			}
			conn.close();
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
		
		return Appointments;
	}
	
	public Boolean cancelAppointment(int id){
		Boolean result = false;
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT count(*),date,start, end from Appointments where id=?";
			statement=conn.prepareStatement(command);
			statement.setInt(1,id);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				if (rs.getInt(1) == 1){
					command = "DELETE FROM Appointments where id=?";
					statement=conn.prepareStatement(command);
					statement.setInt(1, id);
					statement.executeUpdate();
					command = "UPDATE Advising_Schedule SET studentId=null where date=? AND start >=? AND end <=?";
					statement=conn.prepareStatement(command);
					statement.setString(1, rs.getString(2));
					statement.setString(2,rs.getString(3));
					statement.setString(3, rs.getString(4));
					statement.executeUpdate();
					result = true;
				}
			}
			conn.close();	
		}
		catch(SQLException e){
			System.out.printf("Error in Database: " + e.toString());
			return false;
		}
		return result;
	}
	
	public String addTimeSlot(AllocateTime at){
		SQLCmd cmd = new GetUserIDByEmail(at.getEmail());
		cmd.execute();
		int id = (int)cmd.getResult().get(0);
		cmd = new CheckTimeSlot(at,id);
		cmd.execute();
		if ((Boolean)cmd.getResult().get(0) == true){
			cmd = new AddTimeSlot(at,id);
			cmd.execute();
			return (String)cmd.getResult().get(0);
		}
		else{
			return "Unable to add time slot.";
		}
	}
	
	public AdvisorUser getAdvisor(String email){
		SQLCmd cmd = new GetUserIDByEmail(email);
		cmd.execute();
		Integer userId = (Integer)cmd.getResult().get(0);
		
		cmd = new GetAdvisorById(userId);
		cmd.execute();
		return (AdvisorUser)cmd.getResult().get(0);
	}
	
	public StudentUser getStudent(String email){
		SQLCmd cmd = new GetUserIDByEmail(email);
		cmd.execute();
		Integer userId = (Integer)cmd.getResult().get(0);
		
		cmd = new GetStudentById(userId);
		cmd.execute();
		return (StudentUser)cmd.getResult().get(0);
	}
	
	public AdminUser getAdmin(String email){
		SQLCmd cmd = new GetUserIDByEmail(email);
		cmd.execute();
		Integer userId = (Integer)cmd.getResult().get(0);
		
		cmd = new GetAdminById(userId);
		cmd.execute();
		return (AdminUser)cmd.getResult().get(0);
	}
	
	public FacultyUser getFaculty(String email){
		SQLCmd cmd = new GetUserIDByEmail(email);
		cmd.execute();
		Integer userId = (Integer)cmd.getResult().get(0);
		
		cmd = new GetFacultyById(userId);
		cmd.execute();
		return (FacultyUser)cmd.getResult().get(0);
	}
	
	public ArrayList<AppointmentType> getAppointmentTypes(String pname){
			ArrayList<AppointmentType> ats = new ArrayList<AppointmentType>();
			try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT type,duration,user.email FROM  Appointment_Types,User_Advisor,user WHERE Appointment_Types.userid=User_Advisor.userid AND User_Advisor.userid=user.userid AND User_Advisor.pname=?";
			statement = conn.prepareStatement(command);
			statement.setString(1,pname);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				AppointmentType at = new AppointmentType();
				at.setType(rs.getString(1));
				at.setDuration(rs.getInt(2));
				at.setEmail(rs.getString(3));
				ats.add(at);
			}
			conn.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return ats;
	
	}
	
	public Boolean deleteTimeSlot(AllocateTime at){
		Boolean b;
		SQLCmd cmd = new DeleteTimeSlot(at);
		cmd.execute();
		b = (Boolean)(cmd.getResult()).get(0);
		return b;
	}
	
	public Appointment getAppointment(String d, String e){
		Appointment app = null;
		try{
			SQLCmd cmd = new GetAppointment(d,e);
			cmd.execute();
			if (cmd.getResult().size() > 0){
				app = (Appointment)(cmd.getResult()).get(0);
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return app;
	}
	
	public Integer createUser(LoginUser loginUser){
		Integer userId = -1;
		try{
			SQLCmd cmd = new CreateUser(loginUser);
			cmd.execute();
			if ((Boolean)cmd.getResult().get(0)){
				cmd = new GetUserIDByEmail(loginUser.getEmail());
				cmd.execute();
				userId = (int)cmd.getResult().get(0);
			}
			else{
				System.out.println("User not created"+"RDBImpl");
			}
		}
		catch(Exception e){
			System.out.println(e+"RDBImpl");
		}

		return userId;
	}
	
	public Boolean createAdvisor(AdvisorUser advisorUser){
		advisorUser.setRole("advisor");
		
		try{
			SQLCmd cmd = new CreateUser(advisorUser);
			cmd.execute();
			cmd = new CreateAdvisor(advisorUser);
			cmd.execute();
			System.out.println("Created Advisor");
				return (Boolean)cmd.getResult().get(0);
		}
		catch(Exception e){
			System.out.println(e+this.getClass().getName());
			return false;
		}
	}
	
	public String addAppointmentType(AdvisorUser user, AppointmentType at){
		String msg = null;
		SQLCmd cmd = new GetUserIDByEmail(user.getEmail());
		cmd.execute();
		cmd = new AddAppointmentType(at, (int)cmd.getResult().get(0));
		cmd.execute();
		return (String)cmd.getResult().get(0);
	}
	
	//using command pattern
	public ArrayList<String> getDepartmentStrings() throws SQLException{
		ArrayList<String> arraylist = new ArrayList<String>();
		try{
			SQLCmd cmd = new GetDepartmentNames();
			cmd.execute();
			ArrayList<Object> tmp = cmd.getResult();
			for (int i=0;i<tmp.size();i++){
				arraylist.add(((String)tmp.get(i)));
			}
		}
		catch(Exception sq){
			System.out.printf(sq.toString());
		}
		return arraylist;
	}
	
	//using command pattern
	public ArrayList<String> getMajor() throws SQLException{
		ArrayList<String> arraylist = new ArrayList<String>();
		try{
			SQLCmd cmd = new GetMajors();
			cmd.execute();
			ArrayList<Object> tmp = cmd.getResult();
			for (int i=0;i<tmp.size();i++){
				arraylist.add(((String)tmp.get(i)));
			}
		}
		catch(Exception sq){
			System.out.printf(sq.toString());
		}
		return arraylist;
	}
	
	public ArrayList<AdvisorUser> getAdvisorsOfDepartment(String department) throws SQLException {
		
		ArrayList<AdvisorUser> advisorUsers = new ArrayList<AdvisorUser>();
		SQLCmd sqlCmd = new GetAdvisorIdsOfDepartment(department);
		sqlCmd.execute();
		System.out.println("User Ids "+sqlCmd.getResult());
		
		for(int i=0; i<sqlCmd.getResult().size(); i++)
		{
			Integer userId = Integer.valueOf((String)sqlCmd.getResult().get(i));
			System.out.println("User Id "+userId);
			SQLCmd sqlCmd2 = new GetAdvisorById(userId);
			sqlCmd2.execute();
			System.out.println("Advisor "+sqlCmd2.getResult());
			AdvisorUser advisorUser = (AdvisorUser)sqlCmd2.getResult().get(0);
			advisorUsers.add(advisorUser);
		}
		
		return advisorUsers;
	}
	
	public Boolean updateAdvisors(ArrayList<AdvisorUser> advisorUsers) throws SQLException {
		
		for(int i=0; i<advisorUsers.size(); i++)
		{
			SQLCmd sqlCmd = new UpdateAdvisor(advisorUsers.get(i));
			sqlCmd.execute();
		}
		
		return true;
	}
	
	public ArrayList<Department> getDepartments() throws SQLException {
		SQLCmd sqlCmd = new GetDepartmentNames();
		sqlCmd.execute();
		Department department;
		
		ArrayList<Department> departments = new ArrayList<Department>();
		for(int depIndex=0; depIndex<sqlCmd.getResult().size(); depIndex++)
		{
			String depName = (String)sqlCmd.getResult().get(depIndex);
			SQLCmd sqlCmd2 = new GetDepartmentByName(depName);
			sqlCmd2.execute();
		
			department = (Department)sqlCmd2.getResult().get(0);
			departments.add(department);
		}
		
		return departments;
	}
	
	public Department getDepartmentByName(String name) throws SQLException {
		SQLCmd sqlCmd2 = new GetDepartmentByName(name);
		sqlCmd2.execute();
		return (Department)sqlCmd2.getResult().get(0);
	}
	
	public Boolean updateUser(LoginUser loginUser) throws SQLException {
		SQLCmd sqlCmd = new UpdateUser(loginUser);
		sqlCmd.execute();
		return true;
	}
}

