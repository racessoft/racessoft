package org.races.util;
 
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
	public String getStringfromDate(java.sql.Date inputDate) {
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat to = new SimpleDateFormat("yyyy-MM-dd"); //2010-12-01
		
		String date = from.format(inputDate);  
		return date;
	}

	public String getDateforUI(String convertDate) {
		SimpleDateFormat from = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat to = new SimpleDateFormat("yyyy-MM-dd"); //2010-12-01
		java.util.Date dateToConvert = null;
		try {
			dateToConvert = from.parse(convertDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String date = to.format(dateToConvert);  
		return date;
	}

	public String getStringfromDateTimeForFeedBack(String inputDate) {
		
		SimpleDateFormat from = new SimpleDateFormat("MM/dd/yyyy hh:mm");
		SimpleDateFormat to = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //2010-12-01
		String toDateString ="";
		try{
		java.util.Date fromDate = from.parse(inputDate);
		 toDateString = to.format(fromDate);
		 System.out.println("Formatted Date time : "+toDateString);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return toDateString;
	}
	public String convertDateTimeToUI(String inputDate) {
		
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat to = new SimpleDateFormat("MM/dd/yyyy hh:mm"); //2010-12-01
		String toDateString ="";
		try{
		java.util.Date fromDate = from.parse(inputDate);
		 toDateString = to.format(fromDate);
		 System.out.println("Formatted Date time : "+toDateString);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return toDateString;
	}
	public long getTimeDiff(String startTime,String endTime) {
		
		SimpleDateFormat from = new SimpleDateFormat("MM/dd/yyyy hh:mm");
		long diffMinutes = 0;
		try{
		Long startTimeLong = from.parse(startTime).getTime();
		Long endTimeLong = from.parse(endTime).getTime();
		long diff = endTimeLong - startTimeLong;
		diffMinutes = diff / (60 * 1000);
		System.out.println("from.parse(startTime) : "+from.parse(startTime).toString());
		System.out.println("from.parse(endTime) : "+from.parse(endTime).toString());
		
		System.out.println("startTimeLong : "+startTimeLong);
		System.out.println("endTimeLong : "+endTimeLong);
		
		
		 System.out.println("Total Service time Taken (minutes) : "+diffMinutes);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return diffMinutes;
	}
	public int getWorkingDaysBetweenTwoDates(java.util.Date startDate, java.util.Date endDate,int noOfPublicHolidays) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);

		int workDays = 0;

		// Return 0 if start and end are the same
		if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
			return 0;
		}

		if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
			startCal.setTime(endDate);
			endCal.setTime(startDate);
		}

		do {
			// excluding start date 
			if(workDays==0){
				++workDays;
			}
			startCal.add(Calendar.DAY_OF_MONTH, 1);
			if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				++workDays;
			}
		} while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); // excluding
																			// end
																			// date
		
		 System.out.println("No Of Public Holidays : "+noOfPublicHolidays);
		 System.out.println("Total No Working Days : "+workDays);
		 System.out.println("Total No Working Days After public Holidays: "+(workDays-noOfPublicHolidays));
		 return (workDays-noOfPublicHolidays);
	}	
	
	public String getLastDayOfMonth() {
		java.util.Date today = new java.util.Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);

		java.util.Date lastDayOfMonth = calendar.getTime();

		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("Today            : " + sdf.format(today));
		System.out.println("Last Day of Month: " + sdf.format(lastDayOfMonth));
		return sdf.format(lastDayOfMonth);
	}
	public String getFirstDayOfMonth() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance();   // this takes current date
	    c.set(Calendar.DAY_OF_MONTH, 1);
	    System.out.println(c.getTime());       // this returns java.util.Date
	    String firstDateOfMonth = sdf.format(c.getTime());
	    System.out.println("First Day Of Month : "+firstDateOfMonth); 
		return firstDateOfMonth;
	}
	public String getLastDayOfSpecificMonth(String queryDate) throws ParseException {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date today = sdf.parse(queryDate);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);

		java.util.Date lastDayOfMonth = calendar.getTime();

		
		System.out.println("Today            : " + sdf.format(today));
		System.out.println("Last Day of Month: " + sdf.format(lastDayOfMonth));
		return sdf.format(lastDayOfMonth);
	}
	public String getFirstDayOfSpecificMonth(String queryDate) throws ParseException {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance();   // this takes current date
		c.setTime(sdf.parse(queryDate));
	    c.set(Calendar.DAY_OF_MONTH, 1);
	    System.out.println(c.getTime());       // this returns java.util.Date
	    String firstDateOfMonth = sdf.format(c.getTime());
	    System.out.println("First Day Of Month : "+firstDateOfMonth); 
		return firstDateOfMonth;
	}	
	public String getCurrentMonthYear() {
		java.util.Date today = new java.util.Date();

		DateFormat sdf = new SimpleDateFormat("yyyy-MMM"); 
		System.out.println("Current MonthYear: " + sdf.format(today));
		return sdf.format(today);
	}	
	public String getTodaysDateTime() {
		java.util.Date today = new java.util.Date();

		DateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss"); 
		//System.out.println("Current MonthYear: " + sdf.format(today));
		return sdf.format(today);
	}	
	public int getNoOfDaysInCurrentMonth(){
		java.util.Date today = new java.util.Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days;
	}
	public int getNoOfDaysInQueryMonth(java.util.Date queryDate){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(queryDate);
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days;
	}	
	public String getLastDayOfQueryMonth(java.util.Date queryDate) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(queryDate);

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);

		java.util.Date lastDayOfMonth = calendar.getTime();

		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("Today            : " + sdf.format(queryDate));
		System.out.println("Last Day of Month: " + sdf.format(lastDayOfMonth));
		return sdf.format(lastDayOfMonth);
	}	

	
}
