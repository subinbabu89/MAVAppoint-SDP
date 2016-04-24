package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uta.mav.appoint.login.LoginUser;

public class CheckIfUserExist extends SQLCmd {
	LoginUser loginUser;
	private int x;
	
	
	
	public CheckIfUserExist(LoginUser loginUser) {
		super();
		this.loginUser = loginUser;
	}

	@Override
	public void queryDB() {
		// TODO Auto-generated method stub
		
		String sql = "SELECT userId FROM user where email = ?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, loginUser.getEmail());
			res = preparedStatement.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void processResult() {
		try{
			while (res.next()){
				result.add(res.getInt(1));
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

}
