package org.races.model;

import java.util.ArrayList;
import java.util.List;

public class OfferSheetDetails {
	int offerid;
	String customername;
	String address;
	String taluk;
	String district;
	String customerid;
	String financedetails;
	String contactno;
	double loanamount;
	double marginamount;
	String otherdetails;
	String offerdetails;
	String serviceEngineerCode;
	String date;
	List<MMDetails> mmDetailsList = new ArrayList<MMDetails>();
	String from;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}	
	public int getOfferid() {
		return offerid;
	}
	public void setOfferid(int offerid) {
		this.offerid = offerid;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTaluk() {
		return taluk;
	}
	public void setTaluk(String taluk) {
		this.taluk = taluk;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCustomerid() {
		return customerid;
	}
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	public String getFinancedetails() {
		return financedetails;
	}
	public void setFinancedetails(String financedetails) {
		this.financedetails = financedetails;
	}
	public double getLoanamount() {
		return loanamount;
	}
	public void setLoanamount(double loanamount) {
		this.loanamount = loanamount;
	}
	public double getMarginamount() {
		return marginamount;
	}
	public void setMarginamount(double marginamount) {
		this.marginamount = marginamount;
	}
	public String getOtherdetails() {
		return otherdetails;
	}
	public void setOtherdetails(String otherdetails) {
		this.otherdetails = otherdetails;
	}
	public String getOfferdetails() {
		return offerdetails;
	}
	public void setOfferdetails(String offerdetails) {
		this.offerdetails = offerdetails;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<MMDetails> getMmDetailsList() {
		return mmDetailsList;
	}
	public void setMmDetailsList(List<MMDetails> mmDetailsList) {
		this.mmDetailsList = mmDetailsList;
	}
	public String getContactno() {
		return contactno;
	}
	public void setContactno(String contactno) {
		this.contactno = contactno;
	}
	public String getServiceEngineerCode() {
		return serviceEngineerCode;
	}
	public void setServiceEngineerCode(String serviceEngineerCode) {
		this.serviceEngineerCode = serviceEngineerCode;
	}
	

}
