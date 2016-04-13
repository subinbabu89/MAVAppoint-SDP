//Added by Maithili
package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;

import uta.mav.appoint.beans.AppointmentType;

public class UpdateAppointmentType extends SQLCmd {

    String type;
    int duration;
    int userid;
    String color;// Added by Maithili
    String resu;
    
    public UpdateAppointmentType(AppointmentType at,int id){
        type = at.getType();
        duration = at.getDuration();
        userid = id;
        color=at.getColortype();//at.getColortype(); //Added by Maithili
        resu = "";
    }
    
    @Override
    public void queryDB(){
        try{
            //Added by Maithili
            String command = "UPDATE Appointment_Types SET duration=?,colortype=? WHERE userid=? and type=?";
            PreparedStatement statement = conn.prepareStatement(command); 
            
            
            statement.setInt(1,duration);
            statement.setString(2, color);//Added by Maithili
            statement.setInt(3,userid);
            statement.setString(4,type);
            statement.executeUpdate();
            resu = "Appointment type updated.";
                        
            
            }
        catch (Exception e){
            System.out.println(e);    
        }
        
    }
    
    @Override
    public void processResult(){
        result.add(resu);
        
    }    


}