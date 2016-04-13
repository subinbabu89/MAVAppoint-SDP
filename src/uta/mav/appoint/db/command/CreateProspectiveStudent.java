package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uta.mav.appoint.login.*;

public class CreateProspectiveStudent  extends SQLCmd{

	private Integer userid;
	private String student_Id;
	private Integer degree_type;
	private String phone_num;
	private String last_name_initial;
	private Boolean b;
	
	public CreateProspectiveStudent(ProspectiveStudent prospectiveStudent){
		userid = prospectiveStudent.getUserId();
		student_Id = prospectiveStudent.getStudentId();
		degree_type = prospectiveStudent.getDegType();
		phone_num = prospectiveStudent.getPhoneNumber();
		last_name_initial = prospectiveStudent.getLastNameInitial();
		b = false;
	}
	
	@Override
	public void queryDB() {
		try{
			String command = "INSERT INTO User_ProspectiveStudent (userid,student_Id,degree_type,phone_num,last_name_initial) "
								+"values(?,?,?,?,?)";
			PreparedStatement statement = conn.prepareStatement(command);
			int i=1;
			statement.setInt(i,userid);
			i++;
			statement.setString(i,student_Id);
			i++;
			statement.setInt(i,degree_type);
			i++;
			statement.setString(i,phone_num);
			i++;
			statement.setString(i,last_name_initial);
			statement.executeUpdate();
			b = true;
		}
		catch(SQLException sqe){
			System.out.println(sqe.toString()+"RegisterInitialStudent");
		}
	}

	@Override
	public void processResult() {
		System.out.println("Created "+result);
		result.add(b);
	}

}