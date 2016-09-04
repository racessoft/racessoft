package org.races.dao;
 import java.text.SimpleDateFormat;     
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.races.model.PendingService;
import org.races.model.ServiceActual;
import org.races.model.CustomerDetails;
import org.races.model.serviceDetails; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
@Controller
public class daoImpl1 { 

	
	@Autowired
	RacesDaoImpl ts; 
	
    SimpleDateFormat ft = 
 	       new SimpleDateFormat ("yyyy-MM-dd");
	
	public static void main(String[] args) {
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("test.xml"); 
		 daoImpl1 di = new daoImpl1();
	     
		 AutowireCapableBeanFactory acbf = context.getAutowireCapableBeanFactory();
		 acbf.autowireBeanProperties(di,acbf.AUTOWIRE_BY_TYPE,true);
		 acbf.initializeBean(di, "daoimpl"); 
	      
	      if(di.ts == null)
	      {
	    	  System.out.println("NULL");
	      } 
		   System.out.println("***********************************************************");
	      
	      
	      try{
	    	  
	    	// Object[] objects = di.customerData.readExcelSheet("/Volumes/Sashi/races_customer/data used in db/customerdetails.xls");
	    	// List<customerDetails> listOfCustomers = (List<customerDetails>) objects[0];
	    	// System.out.println("LIST OF CUSTOMERS : " + listOfCustomers.size());
	    	// di.ts.insertCustomerData(listOfCustomers);
	    	  
	    	//  Object[] objects = di.read_excel.readExcelSheet("/Volumes/Sashi/races_customer/data used in db/service data for db/service_Apr12-mar13.xls");
	    	  
	    	 
	    	  
	    	  //di.ts.insertData(objects);
	    	  
	    	 
	    	  
	    	  //Object[] objects = di.read_Spares_Details.readExcelSheet("/Volumes/Sashi/races_customer/spare parts details/JD Parts list.xls");
	    	  //di.ts.insertSpareDetails(objects);
	    	  
	    	  
	    	  
/*	    	  List<PendingService> pendingListTotal = new ArrayList<PendingService>();
	      List<PendingService> pendingListFirst = di.ts.getFirstServicePendinglist(11,11,2012,2012);
	      List<PendingService> pendingListSecond = di.ts.getSecondServicePendinglist(11,11,2012,2012);
	      List<PendingService> pendingListThird = di.ts.getThirdServicePendinglist(11,11,2012,2012);
	      List<PendingService> pendingListFourth = di.ts.getFourthServicePendinglist(11,11,2012,2012);
	      List<PendingService> pendingListFifth = di.ts.getFifthServicePendinglist(11,11,2012,2012);
	      List<PendingService> pendingListSixth = di.ts.getSixthServicePendinglist(11,11,2012,2012);
	      List<PendingService> pendingListSeventh = di.ts.getSeventhServicePendinglist(11,11,2012,2012);
	      List<PendingService> pendingListEigth = di.ts.getEigthServicePendinglist(11,11,2012,2012);
	 	      
	      pendingListTotal.addAll(pendingListFirst);
	      pendingListTotal.addAll(pendingListSecond);
	      pendingListTotal.addAll(pendingListThird);
	      pendingListTotal.addAll(pendingListFourth);
	      pendingListTotal.addAll(pendingListFifth);
	      pendingListTotal.addAll(pendingListSixth);
	      pendingListTotal.addAll(pendingListSeventh);
	      pendingListTotal.addAll(pendingListEigth);
	      
	      System.out.println("COUNT OF TOTAL PENDING :: "+pendingListTotal.size());
	      
	      di.printListOfPendingService(pendingListTotal);*/
	    	  
	    	  
	    	  
	      
	      }
	      catch(Exception e)
	      {
	    	  System.out.println("exception at dao impl:"+e.getMessage());
	      }
 	      System.out.println("***********************************************************");
	      
	      
	      
 	}
	
	public void getServiceDatesByDays(Date dateOfSale,int count)
	{
	
		try
		{
			System.out.println(ft.format(dateOfSale));
			String[] dateDetails = (ft.format(dateOfSale)).split("-");
			Calendar c1 = Calendar.getInstance();
			c1.set(Integer.parseInt(dateDetails[0]),(Integer.parseInt(dateDetails[1])-1),Integer.parseInt(dateDetails[2]));
			c1.add(Calendar.DATE,20);
			System.out.println("Date + 20 days is : "+ ft.format(c1.getTime()));
		}
		catch(Exception ex)
		{
			System.out.println("Exception :: "+ex);
		}
	} 
	
	public void getPendingServiceList(serviceDetails sd,List<PendingService> pss,String chasisNumber,String serviceName,daoImpl1 di)
	{
		PendingService ps = new PendingService();
 		try{

		if(sd != null)
			{
				if(sd.getJobcardNo().length() == 0)
				{
				 	ps.setChasisNumber(sd.getChasisNumber());
					ps.setServiceName(sd.getServiceName()); 
					ps.setCustomerDetails(di.ts.getCustomerDetails_byChasisNo(sd.getChasisNumber()));
					pss.add(ps);
				}
			}
		else
		{
			//System.out.println("SD IS NULL....");
			System.out.println("DATA FOR NULL IS :"+chasisNumber);
			ps.setChasisNumber(chasisNumber);
			ps.setServiceName(serviceName); 
			ps.setCustomerDetails(di.ts.getCustomerDetails_byChasisNo(chasisNumber)); 
			pss.add(ps);
		}
 		}
		catch(Exception e)
		{
			System.out.println("Exception at getPendingServiceList : "+e);
		}
 		
		
	}
	


	public void gettodoService(daoImpl1 di,List<ServiceActual> sdList)
	{
		List<PendingService> serviceList = new ArrayList<PendingService>();
		for(ServiceActual sa : sdList)
		{
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		      List<serviceDetails> servicedetailsList= (ArrayList<serviceDetails>)di.ts.getServiceDetails(sa.getChasisnumber());
		      System.out.println("SERVICE DETAILS LIST : "+servicedetailsList.size());
		      for(serviceDetails sd : servicedetailsList )
		      {
		    	  System.out.println("****************");
		    	  System.out.println("Chasis no : "+sd.getChasisNumber());
		    	  System.out.println("****************");
		    	  System.out.println("JOBCARD NO : "+sd.getJobcardNo());
		    	  System.out.println("Service date : "+sd.getServiceDate());
		    	  System.out.println("Service Name : "+sd.getServiceName()); 
		      } 
		}
		
	} 
	
	public void printListOfPendingService(List<PendingService> ps)
	{
		for(PendingService pss : ps)
		{
			System.out.println(" CHASIS NO : "+pss.getChasisNumber()+" Contact Details : "+pss.getCustomerDetails().trim()+" DATE : "+pss.getActual_date_of_service()+" Service Name : "+pss.getServiceName());
			System.out.println("\n");
		}
	}
	
	 	
 
	 	
	 	
}


/*
 
 	     //di.ts.insertData(new Object[]{1,"test","chasisNO","engine no ","12-09-09","17-09-09"}); 
	     //Object[] objects = di.read_excel.readExcelSheet("/Volumes/Sashi/races_customer/fulldatalatest.xls");
	     //Object[] objects = di.read_excel.readExcelSheet("/Volumes/Sashi/races_customer/test10data.xls");
	  
	  	      //di.ts.insertData(objects);

	      System.out.println("DATTE : "+di.ft.format(dd));

	      List<ServiceActual> serviceactualList= (ArrayList<ServiceActual>)di.ts.getServiceList(di.ft.format(dd));
	      System.out.println("SERVICE ACTUAL LIST : "+serviceactualList.size());
	      for(ServiceActual sa : serviceactualList )
	      {
	    	  System.out.println("CHASIS NO : "+sa.getChasisnumber());
	    	  
	      }
	      
	      di.gettodoService(di,serviceactualList);

	  

	public void gettodoService1(daoImpl di,List<ServiceActual> sdList,String dateOfSearch)
	{
		int monthOfSearch = Integer.parseInt(dateOfSearch.substring(5,7));
		System.out.println("Month to Check Service Pending : "+monthOfSearch);
		
		List<PendingService> serviceList = new ArrayList<PendingService>();
		List<Integer> monthList = new ArrayList<Integer>();
		for(ServiceActual sa : sdList)
		{
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		      List<serviceDetails> servicedetailsList= (ArrayList<serviceDetails>)di.ts.getServiceDetails(sa.getChasisnumber());
		      PendingService ps = new PendingService();
  
		      System.out.println("SERVICE DETAILS LIST : "+servicedetailsList.size());
		      for(serviceDetails sd : servicedetailsList )
		      {
		    	  if(Integer.parseInt(sd.getServiceDate().substring(5,7)) != monthOfSearch)
		    	  {
		    		  monthList.add(Integer.parseInt(sd.getServiceDate().substring(5,7)));
		    	  }
		    	  if(!monthList.contains(Integer.parseInt(dateOfSearch.substring(5,7))))
		    	  { 
	 	    		  ps.setChasisNumber(sd.getChasisNumber());
		    	  }
		      }
		      serviceList.add(ps);
			
		}
		
	}


*/
