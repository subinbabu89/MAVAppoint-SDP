package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uta.mav.appoint.beans.Appointment;

public class UpdateAppointment extends SQLCmd{
	String description;
	String studentid;
	int id;
	Boolean b = false;
	
	public UpdateAppointment(Appointment a){
		description = a.getDescription();
		//studentid = a.getStudentId();// commented to fix student ID bug on 30th March 2016 by Maithili
		id = a.getAppointmentId();
	}
	
	public void queryDB(){
		try{
			// commented to fix student ID bug on 30th March 2016 by Maithili
			//String command = "UPDATE appointments SET description=?,studentId=? where id=?";
			String command = "UPDATE appointments SET description=? where id=?";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setString(1, description);
			// commented to fix student ID bug on 30th March 2016 Maithili
		//	statement.setString(2, studentid);
			statement.setInt(2, id);
			statement.executeUpdate();
			b=true;
		}
		catch(SQLException sq){
			System.out.println(sq.toString());
		}
	}
	
	public void processResult(){
		result.add(b);
	}

}
