///Added by Maithili

package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddMinorsByUserId extends SQLCmd {
	private Integer userId;
	private ArrayList<String> minors;
	
	Boolean b;
	
	public AddMinorsByUserId(Integer userId, ArrayList<String> minors){
		this.userId = userId;
		this.minors = minors;
		b = false;
	}
	
	public void queryDB(){
		try{
			String command = "INSERT INTO minor_user (name, userId) VALUES ";
			for(int minorIndex = 0; minorIndex < minors.size(); minorIndex++)
			{
				if(minorIndex>0)
					command += ", ";
				command += " (?, ?)";
			}
			PreparedStatement statement = conn.prepareStatement(command);
			for(int minorIndex = 0; minorIndex < minors.size(); minorIndex++)
			{
				System.out.println("Want to add :"+minors.get(minorIndex)+":");
				statement.setString(minorIndex*2+1,minors.get(minorIndex));
				statement.setInt(minorIndex*2+2,userId);
			}

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
