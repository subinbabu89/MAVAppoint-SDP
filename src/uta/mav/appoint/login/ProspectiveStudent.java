package uta.mav.appoint.login;

import java.util.ArrayList;

import uta.mav.appoint.visitor.Visitor;

public class ProspectiveStudent extends LoginUser{
	private String studentId;
	private String 	phoneNumber;
	private String	lastNameInitial;
	public static final String role = "prospective";

	public ProspectiveStudent(String em){
		super(em);
	}
	
	public String getRole() {
		return role;
	}

	public ProspectiveStudent(){
		super();
	}

	@Override
	public String getHeader(){
		return "student_header";
	}
	
	@Override
	public void accept(Visitor v){
		v.check(this);
	}
	
	@Override
	public ArrayList<Object> accept(Visitor v, Object o){//allow javabean to be passed in
		return v.check(this,o);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLastNameInitial() {
		return lastNameInitial;
	}

	public void setLastNameInitial(String lastNameInitial) {
		this.lastNameInitial = lastNameInitial;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
}
