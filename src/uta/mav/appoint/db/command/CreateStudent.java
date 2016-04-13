package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uta.mav.appoint.login.*;

public class CreateStudent  extends SQLCmd{

	private Integer userid;
	private String student_Id;
	private Integer degree_type;
	private String phone_num;
	private String last_name_initial;
	private String last_name;
	private Boolean b;
	
	public CreateStudent(StudentUser studentUser){
		userid = studentUser.getUserId();
		student_Id = studentUser.getStudentId();
		degree_type = studentUser.getDegType();
		phone_num = studentUser.getPhoneNumber();
		last_name_initial = studentUser.getLastNameInitial();
		last_name=studentUser.getLastName();
		b = false;
	}
	
	@Override
	public void queryDB() {
		try{
			//updated by Maithili
			String command = "INSERT INTO User_Student (userid,student_Id,degree_type,phone_num,last_name_initial,last_name) "
								+"values(?,?,?,?,?,?)";
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
			i++;
			statement.setString(i,last_name);//Added by Maithili
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