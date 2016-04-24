package uta.mav.appoint.db.command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import uta.mav.appoint.login.LoginUser;

public class ResetPassword extends SQLCmd {
	private LoginUser loginUser;
	private String email;
	private String password;
	private int userId;
	
	public ResetPassword(LoginUser loginUserParam) {
		// TODO Auto-generated constructor stub
		this.loginUser = loginUserParam;
		this.email = loginUserParam.getEmail();
		this.password = loginUserParam.getPassword();
		this.userId = loginUserParam.getUserId();
	}


	@Override
	public void queryDB() {
		// TODO Auto-generated method stub
		int  resty = 0;
			int validated = 0;
			String cmd1 = "UPDATE user SET password = ?, validated = ? where userId = ?";
			
			System.out.println(cmd1);
			System.out.println(this.password);
			
			
			try {
				PreparedStatement statement = conn.prepareStatement(cmd1);
				statement.setString(1,password);
				statement.setInt(2,validated);
				statement.setInt(3,userId);
				resty = statement.executeUpdate();
				System.out.println(resty);
			} catch (SQLException e) {
				// TODO Auto-generated catch blocks
				e.printStackTrace();
			}
			System.out.println(resty);
	}

	@Override
	public void processResult() {
		// TODO Auto-generated method stub
		System.out.println("Hopefully done changing the password.");
	}

}
