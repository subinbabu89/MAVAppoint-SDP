package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uta.mav.appoint.beans.AllocateTime;
import uta.mav.appoint.helpers.TimeSlotHelpers;


public class AddTimeSlot extends SQLCmd{
	String date;
	String starttime;
	String endtime;
	int userid;
	int count;
	String msg;
	
	
	public AddTimeSlot(AllocateTime at,int id){
		date = at.getDate();
		starttime = at.getStartTime();
		endtime = at.getEndTime();
		userid = id;
		count = TimeSlotHelpers.count(at.getStartTime(),at.getEndTime());
		msg = "Unable to add time slot.";
	}
	
	@Override
	public void queryDB(){
		int sessionID;
		try{
			String command = "INSERT INTO ADVISING_SCHEDULE (date,start,end,studentid,userid) VALUES(?,?,?,null,?)";
			PreparedStatement statement = conn.prepareStatement(command);
			for (int i=0;i<count;i++){
				statement.setString(1,date);
				statement.setString(2,TimeSlotHelpers.add(starttime,i));
				statement.setString(3,TimeSlotHelpers.add(starttime,i+1));
				statement.setInt(4,userid);
				statement.executeUpdate();
				System.out.println(statement.toString());
				sessionID= getTimeSlotId(userid,TimeSlotHelpers.add(starttime,i),date);
				insertIntoSchedule(userid,sessionID);
			}
			msg = "Time slot has been added.";
		}
		catch(Exception e){
			System.out.printf("Add Time Slot error : " + e.toString());
		}
	}
	
	@Override
	public void processResult(){
		try{
			result.add((Object)msg);
		}
		catch(Exception e){
			System.out.printf("Add Time Slot processResult error: " + e.toString());
		}
	}
	
	public int getTimeSlotId(int userid, String starttime, String date){
		PreparedStatement statement;
		int sessionID =0;
		ResultSet rs1;
		int slotid = 0;
		try{
			System.out.println("start time "+starttime);
			String fetchSlotID = "Select id from ADVISING_SCHEDULE where userid=? and start=? and date=?";
			statement = conn.prepareStatement(fetchSlotID);
			statement.setInt(1, userid);
			statement.setString(2, starttime);
			statement.setString(3, date);
			System.out.println("select session stmt "+statement);
			rs1 = statement.executeQuery();
			
			while(rs1.next()){
				System.out.println("time slot id fetched");
				slotid = rs1.getInt(1);
			}
			System.out.println(slotid);
			sessionID= getSessionId(slotid);
			
			}
		catch(Exception e){
			System.out.printf("Unable to fetch timeslot Id: " + e.toString());
		}
		return sessionID;
	}
	
	public int getSessionId(int slotid){
		PreparedStatement statement;
		ResultSet rs2;
		int sessionID=0;
		try {
			String insertIntoSession = "INSERT INTO advising_session (`slotId`) VALUES (?);";
			statement = conn.prepareStatement(insertIntoSession);
			statement.setInt(1, slotid);
			System.out.println("Insert session table stmt "+statement);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.printf("Unable to insert slot Id: " + e.toString());
		}
		
		//select session id
		try {
			String selectSessionId = "SELECT sessionId FROM advising_session where slotId =?;";
			statement = conn.prepareStatement(selectSessionId);
			statement.setInt(1, slotid);
			System.out.println("select sessionID stmt "+statement);
			rs2 = statement.executeQuery();
			 
			while(rs2.next()){
				System.out.println("session id fetched");
				sessionID = rs2.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.printf("Unable to get Session Id: " + e.toString());
		}
		
		return sessionID;
	}
	
	public void insertIntoSchedule(int userid,int sessionID){
		
		try{
			String command = "INSERT INTO schedule (`userId`,`session_id`) VALUES(?,?);";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setInt(1,userid);
			statement.setInt(2,sessionID);
			statement.executeUpdate();
			System.out.println(statement.toString());
		}
		catch(Exception e){
			System.out.printf("schedule table insertion error : " + e.toString());
		}
		
		
	}
	
	

}
