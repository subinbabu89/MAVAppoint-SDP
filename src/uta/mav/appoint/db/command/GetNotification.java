package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uta.mav.appoint.login.LoginUser;

public class GetNotification extends SQLCmd {

	private Integer userId;
	private String en;
	private Boolean b;
	LoginUser user;

	public GetNotification(LoginUser user, Integer userId) {
		this.userId = userId;
		this.user = user;

	}

	@Override
	public void queryDB() {
		try {
			System.out.println("GetNotif class...."+userId);
			String command = "SELECT IFNULL(notification, "+"\"false\""+") from user where userId=?";
			System.out.println("222222GetNotif class....");
			PreparedStatement statement = conn.prepareStatement(command);
			System.out.println("33333GetNotif class....");
			statement.setInt(1, userId);
			res = statement.executeQuery();
			System.out.println("4444444GetNotif class...."+res.toString());
		} catch (SQLException sqe) {
			System.out.println("getnotif exceptopn   "+sqe.toString());
		}
	}

	@Override
	public void processResult() {
		try {
			//String emailnotification;

			
			//System.out.println("emailnotification1111    "+emailnotification);
			//user.setEmailNotification(emailnotification);
			System.out.println("emailnotification    ");
			while (res.next()){
			result.add(res.getString(1));
			}
		} catch (SQLException sq) {
			System.out.println("Process recult excepton   "+sq.toString());
		}

	}

}
