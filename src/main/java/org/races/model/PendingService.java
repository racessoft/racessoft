package org.races.model;

import java.util.Date;

 
public class PendingService {

	String serviceName;
	String customerDetails;
	String chasisNumber;
	Date actual_date_of_service;
	String district;
	String postOffice;
	String contactNumber;
	String taluk;

	
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
	public Date getActual_date_of_service() {
		return actual_date_of_service;
	}
	public void setActual_date_of_service(Date actual_date_of_service) {
		this.actual_date_of_service = actual_date_of_service;
	}

	public String getCustomerDetails() {
		return customerDetails;
	}
	public void setCustomerDetails(String customerDetails) {
		this.customerDetails = customerDetails;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getChasisNumber() {
		return chasisNumber;
	}
	public void setChasisNumber(String chasisNumber) {
		this.chasisNumber = chasisNumber;
	} 

 }
