package org.races.model;

import java.util.Date;

public class serviceDetails {
	public String getChasisNumber() {
		return chasisNumber;
	}
	public void setChasisNumber(String chasisNumber) {
		this.chasisNumber = chasisNumber;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
 
	public String getJobcardNo() {
		return jobcardNo;
	}
	public void setJobcardNo(String jobcardNo) {
		this.jobcardNo = jobcardNo;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public String getCostCharged() {
		return CostCharged;
	}
	public void setCostCharged(String costCharged) {
		CostCharged = costCharged;
	}
	String chasisNumber;
	String serviceName;
	public String getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}
	String serviceDate;
	String jobcardNo;
	int hours;
	String CostCharged;
	

}
