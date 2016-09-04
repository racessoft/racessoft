package org.races.model;

import java.util.List;

public class JobCardDetails {
	String branch;
	String dateOfComplaint;
	String chasisNumber;
	String natureOfComplaint;
	String actionTaken;
	int hours;
	String closureDate;
	String serviceComplaint;
	float labourAmount;
	int jobcardNumber;
	String from;
	float totalSpareCost;
	float overallCost;
	String receiptNumber;
	String serviceEngineerCode;
	private String serviceType;
	private boolean cascadeData;
	List<SoldSpares> spareDetails;
	public boolean isCascadeData() {
		return cascadeData;
	}

	public void setCascadeData(boolean cascadeData) {
		this.cascadeData = cascadeData;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceEngineerCode() {
		return serviceEngineerCode;
	}

	public void setServiceEngineerCode(String seviceEngineerCode) {
		this.serviceEngineerCode = seviceEngineerCode;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public float getOverallCost() {
		return overallCost;
	}

	public void setOverallCost(float overallCost) {
		this.overallCost = overallCost;
	}

	public float getTotalSpareCost() {
		return totalSpareCost;
	}

	public void setTotalSpareCost(float totalSpareCost) {
		this.totalSpareCost = totalSpareCost;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<SoldSpares> getSpareDetails() {
		return spareDetails;
	}

	public void setSpareDetails(List<SoldSpares> spareDetails) {
		this.spareDetails = spareDetails;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getDateOfComplaint() {
		return dateOfComplaint;
	}

	public void setDateOfComplaint(String dateOfComplaint) {
		this.dateOfComplaint = dateOfComplaint;
	}

	public String getChasisNumber() {
		return chasisNumber;
	}

	public void setChasisNumber(String chasisNumber) {
		this.chasisNumber = chasisNumber;
	}

	public String getNatureOfComplaint() {
		return natureOfComplaint;
	}

	public void setNatureOfComplaint(String natureOfComplaint) {
		this.natureOfComplaint = natureOfComplaint;
	}

	public String getActionTaken() {
		return actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public String getClosureDate() {
		return closureDate;
	}

	public void setClosureDate(String closureDate) {
		this.closureDate = closureDate;
	}

	public String getServiceComplaint() {
		return serviceComplaint;
	}

	public void setServiceComplaint(String serviceComplaint) {
		this.serviceComplaint = serviceComplaint;
	}

	public float getLabourAmount() {
		return labourAmount;
	}

	public void setLabourAmount(float labourAmount) {
		this.labourAmount = labourAmount;
	}

	public int getJobcardNumber() {
		return jobcardNumber;
	}

	public void setJobcardNumber(int jobcardNumber) {
		this.jobcardNumber = jobcardNumber;
	}

}
