package uta.mav.appoint.db.command;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class NotifyEmail extends SQLCmd {
	private Integer userId;
	private String en;
	private Boolean b;
	public NotifyEmail(Integer userId, String en) {
		this.userId = userId;
		this.en = en;
		b = false;
	}
	@Override
	public void queryDB() {
		
		
		try {
			System.out.println("in the befor command add noti");
			String command = "UPDATE user SET `notification`=? WHERE  userId=?";
			//String command = "INSERT into user (userId, notification)" +" values)(?,?) " ;
			PreparedStatement statement = conn.prepareStatement(command);
			System.out.println("en   ++++++++++   "+en);
			statement.setString(1, en);
			statement.setInt(2, userId);
			statement.executeUpdate();
			b = true;
		} catch (SQLException sqe) {
			System.out.println(sqe.toString());
		}
	}
	@Override
	public void processResult() {
		result.add(b);
	}
}