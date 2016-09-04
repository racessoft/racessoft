package org.races.model;

import java.util.List;

public class AssociateSalaryDetails {
	String eid;
	String assocName;	
	double baseSalary;
	double leaveCount;
	int noOfDaysInMonth;
	int noOfWorkingDaysInMonth;	
	double noOfDaysWorked;
	double dailyAllowance;
	double totalSalary; 
	String message;
	String from;
	String salaryDate;
	String daDetailsString;
	List<AssociateDaDetails> associateDaDetails;

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(double baseSalary) {
		this.baseSalary = baseSalary;
	}


	public double getLeaveCount() {
		return leaveCount;
	}

	public void setLeaveCount(double leaveCount) {
		this.leaveCount = leaveCount;
	}

	public double getDailyAllowance() {
		return dailyAllowance;
	}

	public void setDailyAllowance(double dailyAllowance) {
		this.dailyAllowance = dailyAllowance;
	}

	public double getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(double totalSalary) {
		this.totalSalary = totalSalary;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public double getNoOfDaysWorked() {
		return noOfDaysWorked;
	}

	public void setNoOfDaysWorked(double noOfDaysWorked) {
		this.noOfDaysWorked = noOfDaysWorked;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<AssociateDaDetails> getAssociateDaDetails() {
		return associateDaDetails;
	}

	public void setAssociateDaDetails(List<AssociateDaDetails> associateDaDetails) {
		this.associateDaDetails = associateDaDetails;
	}

	public String getAssocName() {
		return assocName;
	}

	public void setAssocName(String assocName) {
		this.assocName = assocName;
	}

	public String getSalaryDate() {
		return salaryDate;
	}

	public void setSalaryDate(String salaryDate) {
		this.salaryDate = salaryDate;
	}

	public int getNoOfDaysInMonth() {
		return noOfDaysInMonth;
	}

	public void setNoOfDaysInMonth(int noOfDaysInMonth) {
		this.noOfDaysInMonth = noOfDaysInMonth;
	}

	public int getNoOfWorkingDaysInMonth() {
		return noOfWorkingDaysInMonth;
	}

	public void setNoOfWorkingDaysInMonth(int noOfWorkingDaysInMonth) {
		this.noOfWorkingDaysInMonth = noOfWorkingDaysInMonth;
	}

	public String getDaDetailsString() {
		return daDetailsString;
	}

	public void setDaDetailsString(String daDetailsString) {
		this.daDetailsString = daDetailsString;
	}

}
