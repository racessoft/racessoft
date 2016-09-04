/** 
 * 
 */
package org.races.util;

import java.util.Date;

/**
 * @author sashikumarshanmugam
 *
 */
public class PendingReport {
	String chasisNumber;
	Date service_date;
	String customer_details;
	String service_name;
	public String getChasisNumber() {
		return chasisNumber;
	}
	public void setChasisNumber(String chasisNumber) {
		this.chasisNumber = chasisNumber;
	}
	public Date getService_date() {
		return service_date;
	}
	public void setService_date(Date service_date) {
		this.service_date = service_date;
	}
	public String getCustomer_details() {
		return customer_details;
	}
	public void setCustomer_details(String customer_details) {
		this.customer_details = customer_details;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	

}
