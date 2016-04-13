package uta.mav.appoint.helpers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashingClass {
	public static BCryptPasswordEncoder passwordEncoder;
	
	static{
		passwordEncoder = new BCryptPasswordEncoder();	
	}
	
	public static String hashPassword(String password) {
	    
	    String hashedPassword = passwordEncoder.encode(password);
	    return hashedPassword;
	} 

}
