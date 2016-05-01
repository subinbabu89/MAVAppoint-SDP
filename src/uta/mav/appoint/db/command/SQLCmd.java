package uta.mav.appoint.db.command;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
/*
 * SQLCmd -> implements command and template patterns
 */
public abstract class SQLCmd {
	ArrayList<Object> result = new ArrayList<Object>();
	ResultSet res;
	Connection conn;
	public ArrayList<Object> getResult(){return result;};
	public abstract void queryDB();
	public abstract void processResult();
	
	String filename = "config.properties";
	Properties properties = new Properties();
	InputStream inputStream = null;
	public void execute(){
		try{
			connectDB();
			queryDB();
			processResult();
			disconnectDB(); 
		}
		catch(Exception e){
			disconnectDB();
		}
	}
	
	public void connectDB(){
		try
	    {
	    Class.forName("com.mysql.jdbc.Driver").newInstance();
	    
	 
		inputStream = SQLCmd.class.getClassLoader().getResourceAsStream(filename);
		if(inputStream==null){
	            System.out.println("Sorry, unable to find " + filename);
		    return;
		}

		properties.load(inputStream);
	    
	    conn = DriverManager.getConnection( properties.getProperty("jdbcUrl"), properties.getProperty("userid"), properties.getProperty("databasePass"));
	    }
	    catch (Exception e){
	        System.out.println(e.toString());
	    }
	}
	
	public void disconnectDB(){
		try{
			conn.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}
