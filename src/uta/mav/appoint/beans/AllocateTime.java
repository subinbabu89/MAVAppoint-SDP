package uta.mav.appoint.beans;

import java.io.Serializable;

public class AllocateTime implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1319063849195059654L;
	private String date;
	private String startTime;
	private String endTime;
	private String email;
	private String oldDate;
	private String oldStartTime;
	private String oldEndTime;

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public String getOldDate() {
		return oldDate;
	}

	public void setOldDate(String oldDate) {
		this.oldDate = oldDate;
	}

	public String getOldEndTime() {
		return oldEndTime;
	}

	public String getOldStartTime() {
		return oldStartTime;
	}

	public void setOldEndTime(String oldEndTime) {
		this.oldEndTime = oldEndTime;
	}

	public void setOldStartTime(String oldStartTime) {
		this.oldStartTime = oldStartTime;
	}
}
