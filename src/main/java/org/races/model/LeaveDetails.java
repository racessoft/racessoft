package org.races.model;

public class LeaveDetails {
	String eid;
	String associateName;
	int noOfLeaves;
	int noOfWorkingDays;
	int noOfDaysWorked;
	int totalNoOfDays;
	String calendarDetails;

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getAssociateName() {
		return associateName;
	}

	public void setAssociateName(String associateName) {
		this.associateName = associateName;
	}

	public int getNoOfLeaves() {
		return noOfLeaves;
	}

	public void setNoOfLeaves(int noOfLeaves) {
		this.noOfLeaves = noOfLeaves;
	}

	public int getNoOfWorkingDays() {
		return noOfWorkingDays;
	}

	public void setNoOfWorkingDays(int noOfWorkingDays) {
		this.noOfWorkingDays = noOfWorkingDays;
	}

	public int getNoOfDaysWorked() {
		return noOfDaysWorked;
	}

	public void setNoOfDaysWorked(int noOfDaysWorked) {
		this.noOfDaysWorked = noOfDaysWorked;
	}

	public int getTotalNoOfDays() {
		return totalNoOfDays;
	}

	public void setTotalNoOfDays(int totalNoOfDays) {
		this.totalNoOfDays = totalNoOfDays;
	}

	public String getCalendarDetails() {
		return calendarDetails;
	}

	public void setCalendarDetails(String calendarDetails) {
		this.calendarDetails = calendarDetails;
	}

}
