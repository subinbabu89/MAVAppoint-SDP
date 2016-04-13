package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;

import java.sql.SQLException;

import uta.mav.appoint.login.*;

public class DeleteAdvisor extends SQLCmd{

	private Integer userId;
	private Boolean b;
	
	public DeleteAdvisor(Integer userId){
		this.userId = userId;
		b = false;
	}
	
	@Override
	public void queryDB() {
		try{
			System.out.println("in the befor command");

			String command = "DELETE FROM user WHERE userId=?";

			PreparedStatement statement = conn.prepareStatement(command);

			statement.setInt(1,userId);

			statement.executeUpdate();

			b = true;
		}
		catch(SQLException sqe){

			System.out.println(sqe.toString());

		}
		
	}

	@Override
	public void processResult() {
		result.add(b);
	}

}
