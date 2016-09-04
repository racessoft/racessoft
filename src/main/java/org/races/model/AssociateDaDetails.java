package org.races.model;

public class AssociateDaDetails {
	String datype;
	String eid;
	String gendate;
	int noofdays;
	double amount;
	double daperday;

	public String getDatype() {
		return datype;
	}

	public void setDatype(String datype) {
		this.datype = datype;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getGendate() {
		return gendate;
	}

	public void setGendate(String gendate) {
		this.gendate = gendate;
	}

	public int getNoofdays() {
		return noofdays;
	}

	public void setNoofdays(int noofdays) {
		this.noofdays = noofdays;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getDaperday() {
		return daperday;
	}

	public void setDaperday(double daperday) {
		this.daperday = daperday;
	}

}
