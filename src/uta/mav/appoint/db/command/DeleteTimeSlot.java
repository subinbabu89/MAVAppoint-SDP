package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import uta.mav.appoint.beans.AllocateTime;

public class DeleteTimeSlot extends SQLCmd {
	String date;
	String start;
	String end;
	String pname;
	int userId;
	Boolean b = false;
	ArrayList<String> ListOfSessions = new ArrayList<String>();
	String tempCrHash = null;
	String tempPrHash = null;

	public DeleteTimeSlot(AllocateTime at, int userid) {
		date = at.getOldDate();
		start = at.getOldStartTime();
		end = at.getEndTime();
		pname = at.getEmail();
		userId = userid;
	}

	public void queryDB() {

		processHash();
		deleteSlots();
		/*
		 * try{
		 * 
		 * String command =
		 * "DELETE a FROM advising_schedule a JOIN User_Advisor b ON a.userid=b.userid WHERE date=? AND start >=? AND end <=?"
		 * +"AND b.pname=?"; PreparedStatement statement =
		 * conn.prepareStatement(command); statement.setString(1,date);
		 * statement.setString(2,start); statement.setString(3,end);
		 * statement.setString(4,pname); statement.executeUpdate(); b = true; }
		 * catch(SQLException sqe){ System.out.println(sqe.toString()); }
		 */
	}

	public void processResult() {
		result.add(b);
	}

	private ArrayList<String> processHash() {
		// TODO Auto-generated method stub

		tempCrHash = fetchCrHash();
		tempPrHash = getPrHash(tempCrHash);
		chechkRoot();

		return ListOfSessions;
	}

	private void chechkRoot() {
		// TODO Auto-generated method stub

		if (tempPrHash.equals("0")) {
			System.out.println("get cr hashes based on  temp crhash value");
			getListofHashes(tempCrHash);
		} else {
			System.out.println("get cr hashes based on  temp prhash value");
			getListofHashes(tempPrHash);
		}

	}

	public String fetchCrHash() {
		try {
			String command = "SELECT `session_id` FROM advising_schedule WHERE userId=? AND date=? AND start =? ";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setInt(1, userId);
			statement.setString(2, date);
			statement.setString(3, start);
			System.out.println(statement.toString());
			res = statement.executeQuery();
			while (res.next()) {
				System.out.println("CR hash captured " + res.getString(1));
				return res.getString(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		}
		return null;

	}

	public String getPrHash(String tempCrHash) {
		try {
			String prCommand = "SELECT `prHash` FROM `map_schedules` where `crHash` = ?";
			PreparedStatement statement = conn.prepareStatement(prCommand);
			statement.setString(1, tempCrHash);
			System.out.println(statement.toString());
			res = statement.executeQuery();

			while (res.next()) {
				tempPrHash = res.getString(1);
				System.out.println(" tempPrHash " + tempPrHash);
			}
			return tempPrHash;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		}

		return tempPrHash;
	}

	public void getListofHashes(String hashValue) {
		String localHash = null;
		try {
			String cmd = "SELECT `crHash` FROM `map_schedules` where `prHash` = ?";
			PreparedStatement cmdStmnt = conn.prepareStatement(cmd);
			cmdStmnt.setString(1, hashValue);
			System.out.println(cmdStmnt.toString());
			res = cmdStmnt.executeQuery();

			ListOfSessions.add(hashValue);
			while (res.next()) {
				ListOfSessions.add(res.getString(1));
				System.out.println("list values " + res.getString(1));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		}

	}

					
	public void deleteSlots(){
			for (Iterator iterator = ListOfSessions.iterator(); iterator.hasNext();) {
				String str = (String) iterator.next();
				deleteFromAdvisingSchedule(str);
				deleteFromMapSchedules(str);
				
			}
			b = true;
	}

	public void deleteFromAdvisingSchedule(String str){
		
		try{
			String deleteCommand = "DELETE FROM advising_schedule WHERE session_id=?";
			PreparedStatement deleteStatement = conn.prepareStatement(deleteCommand); 
			deleteStatement.setString(1,str);
			deleteStatement.executeUpdate();
			System.out.println("delete stmt1 "+deleteStatement.toString());
			
		} catch (SQLException sqe) {
			System.out.println(sqe.toString());
		}
		
	}
	
public void deleteFromMapSchedules(String str){
		
		try{
			String deleteCommand = "DELETE FROM map_schedules WHERE crHash=? ";
			PreparedStatement deleteStatement = conn.prepareStatement(deleteCommand); 
			deleteStatement.setString(1,str);
			deleteStatement.executeUpdate();
			System.out.println("delete stmt2 "+deleteStatement.toString());
		} catch (SQLException sqe) {
			System.out.println(sqe.toString());
		}
		
	}
	
}
