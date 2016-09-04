package org.races.model;

import org.races.util.ExportFilterValue;
import org.races.util.FormatType;

public class ReportFilter {
	String forCurrentMonth;
	String fromDate;
	String toDate;
	FormatType formatType;
	String from;
	String navLink;
	String serviceList;
	ExportFilterValue exportTypeFilter;
	String talukName;
	String districtName;
	String employeeId;
	String employeeName;
	String reportType;
	String taluk;
	String district; 
	String yearOfSale; 
	String month; 
	String eid;
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

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getForCurrentMonth() {
		return forCurrentMonth;
	}

	public void setForCurrentMonth(String forCurrentMonth) {
		this.forCurrentMonth = forCurrentMonth;
	}

	public String getServiceList() {
		return serviceList;
	}

	public void setServiceList(String serviceList) {
		this.serviceList = serviceList;
	}

	public String getNavLink() {
		return navLink;
	}

	public void setNavLink(String navLink) {
		this.navLink = navLink;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public FormatType getFormatType() {
		return formatType;
	}

	public void setFormatType(FormatType formatType) {
		this.formatType = formatType;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public ExportFilterValue getExportTypeFilter() {
		return exportTypeFilter;
	}

	public void setExportTypeFilter(ExportFilterValue exportTypeFilter) {
		this.exportTypeFilter = exportTypeFilter;
	}

	public String getTalukName() {
		return talukName;
	}

	public void setTalukName(String talukName) {
		this.talukName = talukName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getYearOfSale() {
		return yearOfSale;
	}

	public void setYearOfSale(String yearOfSale) {
		this.yearOfSale = yearOfSale;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}
	

}
