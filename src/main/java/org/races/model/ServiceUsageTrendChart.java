package org.races.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceUsageTrendChart implements Comparable<ServiceUsageTrendChart>{
	String monthyear;
	long noOfServices;

	public String getMonthyear() {
		return monthyear;
	}

	public void setMonthyear(String monthyear) {
		this.monthyear = monthyear;
	}

	public long getNoOfServices() {
		return noOfServices;
	}

	public void setNoOfServices(long noOfServices) {
		this.noOfServices = noOfServices;
	}

	@Override
	public int compareTo(ServiceUsageTrendChart o) {
		System.out.println("ServiceUsageTrendChart compareTo called ....");
		if (getMilliSecFromDate("01-"+this.getMonthyear()) < getMilliSecFromDate("01-"+o.getMonthyear()))
			return -1;
		else if (getMilliSecFromDate("01-"+this.getMonthyear()) > getMilliSecFromDate("01-"+o.getMonthyear()))
			return 1;
		else
			return 0;
	}
	public long getMilliSecFromDate(String inputDate){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = new Date();
		try {
			 date = sdf.parse(inputDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date.getTime();
	}

}
