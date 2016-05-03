package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uta.mav.appoint.beans.AllocateTime;
import uta.mav.appoint.email.RandomPasswordGenerator;
import uta.mav.appoint.helpers.TimeSlotHelpers;


public class AddTimeSlot extends SQLCmd{
	String date;
	String starttime;
	String endtime;
	int userid;
	int count;
	String msg;
	String previousHash;
	String sessionHash;
	
	
	
	
	public AddTimeSlot(AllocateTime at,int id, String hash1, String hash2){
		date = at.getDate();
		starttime = at.getStartTime();
		endtime = at.getEndTime();
		userid = id;
		count = TimeSlotHelpers.count(at.getStartTime(),at.getEndTime());
		msg = "Unable to add time slot.";
		previousHash = hash1;
		sessionHash = hash2;
	}
	
	@Override
	public void queryDB(){
			
		try{
			String command = "INSERT INTO ADVISING_SCHEDULE (`userId`,`date`,`start`,`end`,`studentId`,`session_id`)VALUES(?,?,?,?,null,?);";
			PreparedStatement statement = conn.prepareStatement(command);
			for (int i=0;i<count;i++){
				statement.setInt(1,userid);
				statement.setString(2,date);
				statement.setString(3,TimeSlotHelpers.add(starttime,i));
				statement.setString(4,TimeSlotHelpers.add(starttime,i+1));
				statement.setString(5,sessionHash);
				statement.executeUpdate();
				System.out.println(statement.toString());
			}
			mapSchedule();
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
	
	public void mapSchedule(){
		
		try{
			String command = "INSERT INTO map_schedules (`prHash`,`crHash`) VALUES (?,?);";
			PreparedStatement statement = conn.prepareStatement(command);
			
			statement.setString(1,previousHash);
			statement.setString(2,sessionHash);
			statement.executeUpdate();
			System.out.println(statement.toString());
		}
		catch(Exception e){
			System.out.printf("Add Time Slot error : " + e.toString());
		}
		
		
		
	}

}
