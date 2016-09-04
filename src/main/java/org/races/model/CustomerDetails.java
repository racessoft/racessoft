package org.races.model;

 
public class CustomerDetails {
	
	int customerId;
	public String getNameaddr() {
		return Nameaddr;
	}
	public void setNameaddr(String nameaddr) {
		Nameaddr = nameaddr;
	}
	String from;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	String customerDetails;
	String Nameaddr;
	String chasisNumber;
	String engineNumber;
	String dateOFsale;
	String installedDate;
	String district; 
	String postOffice;
	String contactNumber;
	String taluk;
	String pincode;
	String serviceEngineerCode;
	boolean autoincrement;
	String message = "Failure";
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isAutoincrement() {
		return autoincrement;
	}
	public void setAutoincrement(boolean autoincrement) {
		this.autoincrement = autoincrement;
	}
	public String getServiceEngineerCode() {
		return serviceEngineerCode;
	}
	public void setServiceEngineerCode(String serviceEngineerCode) {
		this.serviceEngineerCode = serviceEngineerCode;
	}
	String registeredBranch;
	String delivaryBranch;
	
	
	public String getRegisteredBranch() {
		return registeredBranch;
	}
	public void setRegisteredBranch(String registeredBranch) {
		this.registeredBranch = registeredBranch;
	}
	public String getDelivaryBranch() {
		return delivaryBranch;
	}
	public void setDelivaryBranch(String delivaryBranch) {
		this.delivaryBranch = delivaryBranch;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getPostOffice() {
		return postOffice;
	}
	public void setPostOffice(String postOffice) {
		this.postOffice = postOffice;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getTaluk() {
		return taluk;
	}
	public void setTaluk(String taluk) {
		this.taluk = taluk;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerDetails() {
		return customerDetails;
	}
	public void setCustomerDetails(String customerDetails) {
		this.customerDetails = customerDetails;
	}
	public String getChasisNumber() {
		return chasisNumber;
	}
	public void setChasisNumber(String chasisNumber) {
		this.chasisNumber = chasisNumber;
	}
	public String getEngineNumber() {
		return engineNumber;
	}
	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	} 
	public String getDateOFsale() {
		return dateOFsale;
	}
	public void setDateOFsale(String dateOFsale) {
		this.dateOFsale = dateOFsale;
	}
	public String getInstalledDate() {
		return installedDate;
	}
	public void setInstalledDate(String installedDate) {
		this.installedDate = installedDate;
	} 
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
}
