package org.races.model;

public class SparesDiffReport {
	String sparesDescription;
	String sparesCode;
	int noOfSparesSold;
	Double revenue;

	public String getSparesDescription() {
		return sparesDescription;
	}

	public void setSparesDescription(String sparesDescription) {
		this.sparesDescription = sparesDescription;
	}

	public String getSparesCode() {
		return sparesCode;
	}

	public void setSparesCode(String sparesCode) {
		this.sparesCode = sparesCode;
	}

	public int getNoOfSparesSold() {
		return noOfSparesSold;
	}

	public void setNoOfSparesSold(int noOfSparesSold) {
		this.noOfSparesSold = noOfSparesSold;
	}

	public Double getRevenue() {
		return revenue;
	}

	public void setRevenue(Double revenue) {
		this.revenue = revenue;
	}

}
