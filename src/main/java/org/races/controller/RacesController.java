package org.races.controller;

import java.io.BufferedInputStream; 
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.races.Constants.ReportConstants;
import org.races.model.AssociateDaDetails;
import org.races.model.AssociateSalaryDetails;
import org.races.model.CustomerDetails;
import org.races.model.DaDetails;
import org.races.model.EmployeeDetails;
import org.races.model.FeedbackDetails;
import org.races.model.JobCardDetails;
import org.races.model.LeaveDetails;
import org.races.model.OfferSheetDetails;
import org.races.model.ReportFilter;
import org.races.model.SalaryDetails;
import org.races.model.ServiceChart;
import org.races.model.ServiceUsageTrendChart;
import org.races.model.SoldSpares;
import org.races.model.SparesCountMetrics;
import org.races.model.SparesDetails;
import org.races.model.SparesSaleBySE;
import org.races.model.ValidityCheck;
import org.races.service.RacesService;
import org.races.util.ExportUtil;
import org.races.util.FormatType;
import org.races.util.ExportFilterValue;
import org.races.util.QueryList;
import org.races.util.ReportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class RacesController {
	@Autowired
	RacesService racesService;
	
	@Autowired
	ReportUtil reportUtil;
	
	@Autowired
	ExportUtil expUtil ;
	/**
	 * class path name of usageTrendChart.jasper(a '.jasper' file to generate
	 * usage trend chart).
	 */
	@Value("${usageTrend.chart.location}")
	private String usageTrendFile;

	@Autowired
	QueryList queryList;

	private static Logger log = Logger.getLogger(RacesController.class);
	
	String reportString = null;

	@RequestMapping("getReport.do")
	public ModelAndView getReport(HttpServletRequest request,
			HttpServletResponse response, ReportFilter reportFilter) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		try {
			System.out.println("@ controller.....");
			log.debug("Entered getReport ..");
			log.debug("Current Date" + reportFilter.getForCurrentMonth());
			if(reportFilter.getExportTypeFilter() == null || reportFilter.getExportTypeFilter().equals(""))
			{
				log.debug("Value of Export Filter : NULL .... so setting it to default.....");
				reportFilter.setExportTypeFilter(ExportFilterValue.DEFAULT);
			}
			String from = reportFilter.getFrom();
			Map<String, String> exportType = queryList.getExportType();
			if (reportFilter.getToDate() != null) {
				reportFilter.setNavLink(null);
			}
			reportFilter.setFormatType(FormatType.HTML);
			if ("construction".equals(reportFilter.getFrom())) {
				mav = new ModelAndView("construction");
			} else {
				byte[] reportArray = racesService
						.getPendingReport(reportFilter).toByteArray();
				reportString = new String(reportArray, ReportConstants.CHAR_SET);
				mav = new ModelAndView("racesreport");
			}
			mav.addObject("exportType", exportType);
			mav.addObject("reportString", reportString);
			mav.addObject("fromParam", from);
			String username = session.getAttribute("username").toString();
			mav.addObject("username", username);
			reportFilter.setFormatType(FormatType.PDF);
		} catch (UnsupportedEncodingException e) {
			mav = new ModelAndView("errorreport");
			mav.addObject("ExceptionMessage", e);
		}catch (java.lang.NullPointerException e) {
			mav = new ModelAndView("errorreport");
			mav.addObject("ExceptionMessage", "Sorry, Session TimeOut Please Login Again !!!.");
		}catch (Exception e) {
			mav = new ModelAndView("errorreport");
			mav.addObject("ExceptionMessage", e);
		}

		return mav;
	}
	@RequestMapping("getCustomerReport.do")
	public ModelAndView getCustomerReport(HttpServletRequest request,
			HttpServletResponse response, ReportFilter reportFilter) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		try {
			System.out.println("@ controller.....");
			log.debug("Entered getReport ..");
			log.debug("Current Date" + reportFilter.getForCurrentMonth());
			if(reportFilter.getExportTypeFilter() == null || reportFilter.getExportTypeFilter().equals(""))
			{
				log.debug("Value of Export Filter : NULL .... so setting it to default.....");
				reportFilter.setExportTypeFilter(ExportFilterValue.DEFAULT);
			}
			String from = reportFilter.getFrom();
			Map<String, String> exportType = queryList.getExportType();
			List<String> months = queryList.getMonth();
			List<Object> vehicleYears = racesService.getVehicleSoldYears();
			vehicleYears.add("---"); 
			
			if (reportFilter.getToDate() != null) {
				reportFilter.setNavLink(null);
			}
			reportFilter.setFormatType(FormatType.HTML);
			if ("construction".equals(reportFilter.getFrom())) {
				mav = new ModelAndView("construction");
			}else if("customerByTaluk".equals(reportFilter.getFrom())){
				mav = new ModelAndView("customerdetailsreportbytaluk");
				List<String> taluk = queryList.getTaluk();

				taluk.add("---");
				mav.addObject("taluks", taluk);
				byte[] reportArray = racesService
						.getPendingReport(reportFilter).toByteArray();
				reportString = new String(reportArray, ReportConstants.CHAR_SET);
				
			} 
			else if("customerByDistrict".equals(reportFilter.getFrom())){
				mav = new ModelAndView("customerdetailsreportbydistrict");
				List<String> districts = queryList.getDistricts();
				districts.add("---");
				mav.addObject("districts", districts);
				byte[] reportArray = racesService
						.getPendingReport(reportFilter).toByteArray();
				reportString = new String(reportArray, ReportConstants.CHAR_SET);
				
			}else {
				byte[] reportArray = racesService
						.getPendingReport(reportFilter).toByteArray();
				reportString = new String(reportArray, ReportConstants.CHAR_SET);
				mav = new ModelAndView("customerdetailsreportbydistrict");
			}
			
			mav.addObject("exportType", exportType);
			mav.addObject("months", months);
			mav.addObject("vehicleYears", vehicleYears);
			mav.addObject("reportString", reportString);
			mav.addObject("fromParam", from);
			String username = session.getAttribute("username").toString();
			mav.addObject("username", username);
			reportFilter.setFormatType(FormatType.PDF);
		} catch (UnsupportedEncodingException e) {
			mav = new ModelAndView("errorreport");
			mav.addObject("ExceptionMessage", e);
		}

		return mav;
	}
	@RequestMapping("jobCardReport.do")
	public ModelAndView addJobCardReport(HttpServletRequest request,
			HttpServletResponse response, JobCardDetails jobCardDetails) {
		ModelAndView mav = null;

		try {
			System.out.println("@ controller.....");
			log.debug("Entered jobCardReport ..");
			String from = jobCardDetails.getFrom();
			// reportFilter.setFormatType(FormatType.HTML);
			LinkedHashMap<String, Float> spareDetail = racesService
					.getSparesDetails();
			List<String> branches = queryList.getBranches();
			List<EmployeeDetails> employee = racesService.getEmployeeID();
			List<String> serviceNames = queryList.getServiceNames();
			HttpSession session = request.getSession(true);
			String username = session.getAttribute("username").toString();

			if (ReportConstants.ADD_JOBCARD_REPORT.equals(from)) {
				mav = new ModelAndView("jobcard");
				mav.addObject(ReportConstants.SPARE_DETAIL, spareDetail);
				mav.addObject("branches", branches);
				mav.addObject("EmployeeDetails", employee);
				mav.addObject("serviceNames", serviceNames);
			} else if (ReportConstants.UPDATE_JOBCARD_REPORT.equals(from)) {
				mav = new ModelAndView("retreivejobcard");
			}
			mav.addObject("fromParam", from);
			mav.addObject("username", username);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}

		return mav;
	}

	@RequestMapping("addJobcardReport.do")
	public ModelAndView getJobCardReport(HttpServletRequest request,
			HttpServletResponse response, JobCardDetails jobCardDetails) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString();

		try {
			System.out.println("@ controller.....");
			log.debug("Entered addJobcardReport ..");
			// reportFilter.setFormatType(FormatType.HTML);
			LinkedHashMap<String, Float> spareDetail = racesService
					.getSparesDetails();
			List<SoldSpares> soldDetails = (List<SoldSpares>) jobCardDetails
					.getSpareDetails();
			List<String> serviceNames = queryList.getServiceNames();
			int count = soldDetails.size();
			log.debug("size of sold detail: " + count);
			String from = jobCardDetails.getFrom();
			List<String> branches = queryList.getBranches();
			List<EmployeeDetails> employee = racesService.getEmployeeID();
			String message = racesService.addJobcardDetail(jobCardDetails);

			if (message.equals("success")
					&& ReportConstants.ADD_JOBCARD_REPORT.equals(from)) {
				mav = new ModelAndView("successjobcard");
				mav.addObject(ReportConstants.SPARE_DETAIL, spareDetail);
				mav.addObject("spareDetail", soldDetails);
				// mav.addObject("count", count);
				mav.addObject("successmessage", "successfully saved");
				//mav.addObject("updateFeedBackDetailsString","addfeedBackDetails.do?navLink=leftPane&from=addFeedBackReport&jobcardNumber="+jobCardDetails.getJobcardNumber()+"&chasisNumber="+jobCardDetails.getChasisNumber());
			} else {
				mav = new ModelAndView("successjobcard");
				mav.addObject(ReportConstants.SPARE_DETAIL, spareDetail);
				mav.addObject("spareDetail", soldDetails);
				// mav.addObject("count", count);
				mav.addObject("failuremessage",
						"Detail is not saved due to duplication of job card number");
			}
			mav.addObject("serviceNames", serviceNames);
			mav.addObject("username", username);
			mav.addObject("branches", branches);
			mav.addObject("EmployeeDetails", employee);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}

		return mav;
	}
	

	@RequestMapping("updateJobcardReport.do")
	public ModelAndView getJobCardReportForUpdate(HttpServletRequest request,
			HttpServletResponse response, JobCardDetails jobCardDetails) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString();
		try {
			System.out.println("@ controller.....");
			log.debug("Entered Update JobCard ..");
			// reportFilter.setFormatType(FormatType.HTML);
			LinkedHashMap<String, Float> spareDetail = racesService
					.getSparesDetails();
			log.debug("GET JOBCARD DETAILS FOR ::: ->"
					+ jobCardDetails.getJobcardNumber() + "<-");
			// racesService.getReportForParticularJobcard(jobCardDetails);
			// String message = racesService.addJobcardDetail(jobCardDetails);
			List<SoldSpares> soldDetails = (List<SoldSpares>) jobCardDetails
					.getSpareDetails();
			String message = racesService.updateJobCardDetails(jobCardDetails);
			String from = jobCardDetails.getFrom();

			int count = soldDetails.size();
			log.debug("size of sold detail: " + count);
			List<String> branches = queryList.getBranches();
			List<EmployeeDetails> employee = racesService.getEmployeeID();
			// String message = racesService.addJobcardDetail(jobCardDetails);
			List<String> serviceNames = queryList.getServiceNames();
			if (message.equals("success")
					&& ReportConstants.UPDATE_JOBCARD_REPORT.equals(from)) {
				mav = new ModelAndView("updatesuccessjobcard");
				mav.addObject(ReportConstants.SPARE_DETAIL, spareDetail);

				mav.addObject("spareDetail", soldDetails);
				mav.addObject("count", count);

				mav.addObject("successmessage", "successfully Updated");
			} else {
				mav = new ModelAndView("updatejobcard");
				mav.addObject(ReportConstants.SPARE_DETAIL, spareDetail);

				mav.addObject("spareDetail", soldDetails);
				mav.addObject("count", count);

				mav.addObject("failuremessage",
						"Detail is not Updated due to duplication of job card number");
			}
			mav.addObject("branches", branches);
			mav.addObject("serviceNames", serviceNames);
			mav.addObject("EmployeeDetails", employee);
			mav.addObject("username", username);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}
	
	@RequestMapping("getTalukData.do")
	public void getTalukData(HttpServletRequest request,
			HttpServletResponse response){
		try{
			
			List<String> taluk = queryList.getTaluk();
			
			
			HashMap<Object,Object> dats = new HashMap<Object,Object>(); 
			
			dats.put("talukData", taluk);
			Gson gson = new Gson();
			System.out.println(gson.toJson(dats));
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		    out.write(gson.toJson(dats).getBytes());
		    response.setContentType("application/json");
		    response.setContentLength(out.size());
		    response.getOutputStream().write(out.toByteArray());
		    response.getOutputStream().flush();
			
		}catch(Exception ex){
			log.debug("Exception @ getSparesSaleBySE",ex);
		}
	}
	@RequestMapping("getDistrictData.do")
	public void getDistrictData(HttpServletRequest request,
			HttpServletResponse response){
		try{
			HashMap<Object,Object> dats = new HashMap<Object,Object>();
			List<String> district = queryList.getDistricts();
			
			dats.put("districtData", district);
			Gson gson = new Gson();
			System.out.println(gson.toJson(dats));
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		    out.write(gson.toJson(dats).getBytes());
		    response.setContentType("application/json");
		    response.setContentLength(out.size());
		    response.getOutputStream().write(out.toByteArray());
		    response.getOutputStream().flush();
			
		}catch(Exception ex){
			log.debug("Exception @ getSparesSaleBySE",ex);
		}
	}

	@RequestMapping("fetchJobcardReport.do")
	public ModelAndView getJobCardReportForParticularRecord(
			HttpServletRequest request, HttpServletResponse response,
			JobCardDetails jobCardDetails) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString();

		try {
			System.out.println("@ controller.....");
			log.debug("Entered Fetch JobCard ..");
			// reportFilter.setFormatType(FormatType.HTML);
			LinkedHashMap<String, Float> spareDetail = racesService
					.getSparesDetails();
			log.debug("GET JOBCARD DETAILS FOR ::: ->"
					+ jobCardDetails.getJobcardNumber() + "<-");
			String message = racesService
					.getReportForParticularJobcard(jobCardDetails);
			String from = jobCardDetails.getFrom();
			List<String> serviceNames = queryList.getServiceNames();
			List<SoldSpares> soldDetails = (List<SoldSpares>) jobCardDetails
					.getSpareDetails();
			int count = soldDetails.size();
			log.debug("size of sold detail: " + count);
			List<String> branches = queryList.getBranches();
			List<EmployeeDetails> employee = racesService.getEmployeeID();
			
			
		/*	for(EmployeeDetails ed : employee){
				if(jobCardDetails.getServiceEngineerCode().equalsIgnoreCase(ed.getEmployeeId())){
					log.info("JobcardNumber : "+jobCardDetails.getJobcardNumber()+" EmployeeName : "+ed.getEmployeeName());
					jobCardDetails.setServiceEngineerCode(ed.getEmployeeName());
				}
			}
		*/	
			if (message.equals("success")
					&& ReportConstants.UPDATE_JOBCARD_REPORT.equals(from)) {
				mav = new ModelAndView("updatejobcard");
				mav.addObject(ReportConstants.SPARE_DETAIL, spareDetail);

				mav.addObject("spareDetail", soldDetails);
				mav.addObject("count", count);

				// mav.addObject("successmessage", "successfully Updated");
			} else {
				mav = new ModelAndView("retreivejobcard");
				mav.addObject(ReportConstants.SPARE_DETAIL, spareDetail);

				mav.addObject("spareDetail", soldDetails);
				mav.addObject("count", count);

				mav.addObject("failuremessage", "No records Found");
			}
			mav.addObject("branches", branches);
			mav.addObject("serviceNames", serviceNames);
			mav.addObject("EmployeeDetails", employee);
			mav.addObject("username", username);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}


	@RequestMapping("rerunReport.do")
	public ModelAndView rerunEventReport(HttpServletRequest request,
			HttpServletResponse response, ReportFilter reportFilter) {
		long time = System.currentTimeMillis();
		ModelAndView mav = new ModelAndView(ReportConstants.EXCEPTION_PAGE);
		try {
			log.debug("Entering rerun Event report,"
					+ " getting new report for user search : " + time);
			// reportFilter.setFrom(ReportConstants.LINK);
			// reportFilter.setForCurrentMonth("2008-02-01");
			if(reportFilter.getExportTypeFilter() == null || reportFilter.getExportTypeFilter().equals(""))
			{
				log.debug("Value of Export Filter : NULL .... so setting it to default.....");
				reportFilter.setExportTypeFilter(ExportFilterValue.DEFAULT);
			}
			reportFilter.setFormatType(FormatType.HTML);
			byte[] byteArray = getEventReportBytes(reportFilter);
			String eventReport = new String(byteArray, ReportConstants.CHAR_SET);
			response.setCharacterEncoding(ReportConstants.CHAR_SET);
			response.setContentType(ReportConstants.HTML_CONTENT_TYPE);
			response.getWriter().write(eventReport);

		} catch (UnsupportedEncodingException encodingException) {
			log.error("UnsupportedEncodingException while getting reports.",
					encodingException);

		} catch (IOException ioException) {
			log.error("IO Exception while getting report for event.",
					ioException);
			mav.addObject(ReportConstants.ERROR_MESSAGE,
					"IO Exception while getting report for event.");
			return mav;
		}
		return null;
	}

	@RequestMapping("racesHome.do")
	public ModelAndView doAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		String message = null;
		ModelAndView mav = null;
		try {
			mav = new ModelAndView("raceshome");
			HttpSession session = request.getSession(true);
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			System.out.println("@ Authentication.....username" + username);
			log.debug("Authentication.....");
			message = racesService.getAuthenticationValue(username, password);
			if (message == "FAIL") {
				mav = new ModelAndView("index");
				mav.addObject("message", "No User Found");
			} else {
				session.setAttribute("username", username);
				session.setAttribute("loginType", message);
				mav.addObject("userinfo", username);
			}
			session.setAttribute("loginType", message);
		} catch (org.springframework.jdbc.CannotGetJdbcConnectionException e) {
			mav = new ModelAndView("error");
			mav.addObject("message", "DB Connection Failed");
			mav.addObject("ExceptionMessage", e);
		} catch (java.lang.NullPointerException e) {
			mav = new ModelAndView("error");
			mav.addObject("ExceptionMessage", "Sorry, Session TimeOut Please Login Again !!!.");
		}catch (Exception e) {
			mav = new ModelAndView("error");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}
	@RequestMapping("servicedashboard.do")
	public ModelAndView doAuthentication(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("fromServiceDashboard") String fromServiceDashboard) {
		String message = null;
		ModelAndView mav = null;
		try {
			
			if("dashboardOne".equals(fromServiceDashboard))
				{	System.out.println("dashboardOne .... mav ");
					mav = new ModelAndView("servicedashboard");
				}
			else
			{
				System.out.println("Other .... mav ");
				mav = new ModelAndView("servicedashboard");
			}

			HttpSession session = request.getSession(true);
			String username = session.getAttribute("username").toString();
			System.out.println("@ Authentication.....username" + username); 
		} catch (org.springframework.jdbc.CannotGetJdbcConnectionException e) {
			mav = new ModelAndView("error");
			mav.addObject("message", "DB Connection Failed");
			mav.addObject("ExceptionMessage", e);
		} catch (java.lang.NullPointerException e) {
			mav = new ModelAndView("error");
			mav.addObject("ExceptionMessage", "Sorry, Session TimeOut Please Login Again !!!.");
		}catch (Exception e) {
			mav = new ModelAndView("error");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}	

	@RequestMapping("homePage.do")
	public ModelAndView homePage(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = null;
		String username = null,loginType=null;
		HttpSession session = request.getSession(true);
		try {
			mav = new ModelAndView("raceshome");
			username = session.getAttribute("username").toString();
			loginType = session.getAttribute("loginType").toString();
			
		} catch (java.lang.NullPointerException e) {
			mav = new ModelAndView("error");
			mav.addObject("ExceptionMessage", "Sorry, Session TimeOut Please Login Again !!!.");
		}catch (Exception e) {
			mav = new ModelAndView("error");
			mav.addObject("ExceptionMessage", e);
		}
		mav.addObject("username", username);
		mav.addObject("loginType", loginType);
		return mav;
	}
	@RequestMapping("employeeByLocation.do")
	public ModelAndView employeeData(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = null;
		String username = null;
		HttpSession session = request.getSession(true);
		try {
			mav = new ModelAndView("raceshome");
			username = session.getAttribute("username").toString();
		} catch (java.lang.NullPointerException e) {
			mav = new ModelAndView("error");
			mav.addObject("ExceptionMessage", "Sorry, Session TimeOut Please Login Again !!!.");
		}catch (Exception e) {
			mav = new ModelAndView("error");
			mav.addObject("ExceptionMessage", e);
		}
		mav.addObject("username", username);
		return mav;
	}

	@RequestMapping("racesLogin.do")
	public ModelAndView loadLoginpage(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("@ Loading login page.....");
		return new ModelAndView("index");
	}

	@RequestMapping("logout.do")
	public ModelAndView loadlogoutpage(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("@ Loading logout page.....");
		HttpSession session = request.getSession(true);
		session.invalidate();
		return new ModelAndView("logout");
	}

	@RequestMapping("forgotPassword")
	public ModelAndView forgotPassword() {
		log.debug("Forgot Password .....");
		System.out.println("@ Forgot Password.....");
		racesService.getForgotPassword("");
		return new ModelAndView("test");
	}

	@RequestMapping("createUsageForService.do")
	public void createUsageForService(HttpServletRequest request,
			HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		log.debug("Creating Sales Report.--------" + startTime);
		try {
			HashMap<Object,Object> dats = new HashMap<Object,Object>();
			List<ServiceUsageTrendChart> datas = racesService.getServiceUsageChartData();
			
			Object[] contentData = new Object[datas.size()]; 
			for(int i=(datas.size()-1);i>=0;i--){ 
				Object[] innerContentData = new Object[2]; 
				innerContentData[0] = datas.get(i).getMonthyear();
				innerContentData[1] = datas.get(i).getNoOfServices(); 
				contentData[i] = innerContentData; 
				}
			dats.put("contentData", contentData);
			dats.put("title", "Last Six Months Services ");
			dats.put("xtitle", "Month");
			dats.put("subtitle", "Source: Races");
			dats.put("ytitle", "No Of Services Done"); 
			Gson gson = new Gson();
			System.out.println(gson.toJson(dats));
			
			
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    out.write(gson.toJson(dats).getBytes());
		    response.setContentType("application/json");
		    response.setContentLength(out.size());
		    response.getOutputStream().write(out.toByteArray());
		    response.getOutputStream().flush();
			
			//return gson.toJson(dats);
			}catch (Exception ioException) {
			log.error("IO exception has occurred.", ioException);
		}
		
		log.debug("Done! Creating Sales Report.-------- ");
		//return "";
	}
	//TODO: Dashboard Report
	@RequestMapping("createSalesReport.do")
	public void createUsageTrend(HttpServletRequest request,
			HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		log.debug("Creating Sales Report.--------" + startTime);
		try {
			HashMap<Object,Object> dats = new HashMap<Object,Object>();
			List<ServiceChart> datas = racesService.getServiceChartData();
			String[] categories = new String[datas.size()];
			Integer[] soldData = new Integer[datas.size()];
			Integer[] committedData = new Integer[datas.size()];
			for(int i=(datas.size()-1);i>=0;i--){
				categories[i]=datas.get(i).getDuration();
				soldData[i]=datas.get(i).getCompleted_count();
				committedData[i]=datas.get(i).getPending_count();
			}
			
			dats.put("categories", categories);
			dats.put("soldData", soldData);
			dats.put("committedData", committedData);
			dats.put("xtitle", "Service Report");
			dats.put("subtitle", "Source: Races");
			dats.put("ytitle", "No Of Sales");
			dats.put("AseriesData", "Services Completed");
			dats.put("BseriesData", "Services Pending"); 
			Gson gson = new Gson();
			System.out.println(gson.toJson(dats));
			
			
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    out.write(gson.toJson(dats).getBytes());
		    response.setContentType("application/json");
		    response.setContentLength(out.size());
		    response.getOutputStream().write(out.toByteArray());
		    response.getOutputStream().flush();
			
			//return gson.toJson(dats);
		} catch (Exception ioException) {
			log.error("IO exception has occurred.", ioException);
		}
		
		log.debug("Done! Creating Sales Report.-------- ");
		//return "";
	}
	
	@RequestMapping("getServiceDashboardMetricsOne.do")
	public void getServiceDashboardMetricsOne(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("fromDate") String fromDate,@RequestParam("toDate") String toDate,@RequestParam("metricsType") String metricsType) {
		long startTime = System.currentTimeMillis();
		log.debug("Creating Sales Report.--------" + startTime);
		try {
			HashMap<Object,Object> dats = new HashMap<Object,Object>();
			List<ServiceChart> datas = racesService.getServiceChartData();
			String[] categories = new String[datas.size()];
			Integer[] soldData = new Integer[datas.size()];
			Integer[] committedData = new Integer[datas.size()];
			for(int i=(datas.size()-1);i>=0;i--){
				categories[i]=datas.get(i).getDuration();
				soldData[i]=datas.get(i).getCompleted_count();
				committedData[i]=datas.get(i).getPending_count();
			}
			
			dats.put("categories", categories);
			dats.put("soldData", soldData);
			dats.put("committedData", committedData);
			dats.put("xtitle", "Service Report");
			dats.put("subtitle", "Source: Races");
			dats.put("ytitle", "No Of Sales");
			dats.put("AseriesData", "Services Completed");
			dats.put("BseriesData", "Services Pending"); 
			Gson gson = new Gson();
			System.out.println(gson.toJson(dats));
			
			
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    out.write(gson.toJson(dats).getBytes());
		    response.setContentType("application/json");
		    response.setContentLength(out.size());
		    response.getOutputStream().write(out.toByteArray());
		    response.getOutputStream().flush();
			
			//return gson.toJson(dats);
		} catch (Exception ioException) {
			log.error("IO exception has occurred.", ioException);
		}
		
		log.debug("Done! Creating Sales Report.-------- ");
		//return "";
	}	

	@RequestMapping("getSalesReportChart.do")
	public ModelAndView getServiceReportChart(HttpServletRequest request,
			HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		log.debug("Entering getImage to get Sales report chart image.----"
				+ startTime);
		response.setContentType(ReportConstants.IMAGE_TYPE);
		ModelAndView mav = new ModelAndView(ReportConstants.EXCEPTION_PAGE);
		try {
			String usageTrendChart = ReportConstants.USER_HOME + usageTrendFile;
			InputStream inputStream = new BufferedInputStream(
					new FileInputStream(usageTrendChart));
			ImageIO.write(ImageIO.read(inputStream),
					ReportConstants.FORMAT_TYPE, response.getOutputStream());
			inputStream.close();
			log.debug("Writing image into output stream.");
		} catch (FileNotFoundException filesException) {
			log.error("Image not found in the respective location.",
					filesException);
		} catch (IOException ioException) {
			log.error("Unable to write image.", ioException);
			mav.addObject(ReportConstants.ERROR_MESSAGE,
					"IO Exception while getting report for event.");
			return mav;
		}
		return null;
	}

	@RequestMapping("createServiceReport.do")
	public void createServiceReport(HttpServletRequest request,
			HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		log.debug("Creating Service Report.--------" + startTime);
		System.out.println("createServiceReport.do --- Started");
		try {
			response.setContentType(ReportConstants.HTML_CONTENT_TYPE);
			response.getOutputStream().write(
					racesService.createUsageTrend(FormatType.IMAGE)
							.toByteArray());
			response.flushBuffer();
		} catch (IOException ioException) {
			log.error("IO exception has occurred.", ioException);
		} catch (java.lang.NullPointerException NPE) {
			System.out.println("Exception at createServiceReport.do : " + NPE);
		}
		log.debug("Done! Creating Service Report.-------- ");
		System.out.println("createServiceReport.do ----- Completed");
	}

	@RequestMapping("getServiceReportChart.do")
	public ModelAndView getUsageTrendChart(HttpServletRequest request,
			HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		log.debug("Entering getImage to get Service report chart image.----"
				+ startTime);
		System.out.println("getServiceReportChart.do : STARTED");
		response.setContentType(ReportConstants.IMAGE_TYPE);
		ModelAndView mav = new ModelAndView(ReportConstants.EXCEPTION_PAGE);
		try {
			String usageTrendChart = ReportConstants.USER_HOME + usageTrendFile;
			InputStream inputStream = new BufferedInputStream(
					new FileInputStream(usageTrendChart));
			ImageIO.write(ImageIO.read(inputStream),
					ReportConstants.FORMAT_TYPE, response.getOutputStream());
			inputStream.close();
			log.debug("Writing image into output stream.");
			System.out.println("Writing image into output stream.");
		} catch (FileNotFoundException filesException) {
			log.error("Image not found in the respective location.",
					filesException);
		} catch (IOException ioException) {
			log.error("Unable to write image.", ioException);
			mav.addObject(ReportConstants.ERROR_MESSAGE,
					"IO Exception while getting report for event.");
			return mav;
		}
		return null;
	}
	 

	@RequestMapping("exportReport.do")
	public ModelAndView exportEventReport(ReportFilter reportFilter,
			HttpServletRequest request, HttpServletResponse response) {
		String message = null;
		ModelAndView mav = new ModelAndView();
		long startTime = System.currentTimeMillis();
		log.debug("Entering into exportReportForEvent() method -----"
				+ startTime);
		try {
			FormatType format = reportFilter.getFormatType();
			String headerInfo = getHeaderInfo(format);
			log.debug("Value of Export Filter : "+reportFilter.getExportTypeFilter());
			
			if(reportFilter.getExportTypeFilter() == null || reportFilter.getExportTypeFilter().equals(""))
			{
				log.debug("Value of Export Filter : NULL .... so setting it to default.....");
				reportFilter.setExportTypeFilter(ExportFilterValue.DEFAULT);
			}
			// reportFilter.setForCurrentMonth("2008-02-01");
			if (reportUtil.isNotNullNotEmpty(reportFilter.getToDate())) {
				reportFilter.setNavLink(null);
			}
			String contentType = getContentType(format);
			
			// Getting event report from service based on user selection.
			// reportFilter.setFrom(ReportConstants.EXPORT_REPORT);
			byte[] eventReportBytes;
			
			if(reportFilter.getExportTypeFilter() == ExportFilterValue.DEFAULT)
			{
				response.setHeader(ReportConstants.HEADER_INFO, headerInfo);
				response.setContentType(contentType);
				eventReportBytes = getEventReportBytes(reportFilter);
				switch (format) {
				case HTML:
					response.getOutputStream().write(eventReportBytes);
					break;
				case PDF:
					response.getOutputStream().write(eventReportBytes);
					break;
				case CSV:
					response.getOutputStream().write(239);
					response.getOutputStream().write(187);
					response.getOutputStream().write(191);
					response.getOutputStream().write(eventReportBytes);
					break;
				default:
					break;
				}
			}
			else// This happens when the export is based on Taluk/district
			{
				message = getExportReportStatus(reportFilter);
				Map<String, String> exportType = queryList.getExportType();
				mav.setViewName("racesreport");
				mav.addObject("successmessage", message);
				mav.addObject("exportType", exportType);
				mav.addObject("reportString", reportString);
			}
			
		} catch (IOException ioException) {
			message = "failure";			
			mav.addObject("failuremessage", message);
			log.error("IO Exception while exporting event report.", ioException);
		}
		return mav;
	}

	@RequestMapping("printReport.do")
	public void printEventReport(ReportFilter reportFilter,
			HttpServletRequest request, HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		log.debug("Entering print event report.------" + startTime);
		// reportFilter.setForCurrentMonth("2008-02-01");
		reportFilter.setFormatType(FormatType.HTML);
		// reportFilter.setFrom(ReportConstants.EXPORT_REPORT);
		ByteArrayOutputStream eventReportStream;
		// report selection based on events
		if (reportUtil.isNotNullNotEmpty(reportFilter.getToDate())) {
			reportFilter.setNavLink(null);
		}
		eventReportStream = getEventReport(reportFilter);
		try {
			response.setContentType(ReportConstants.HTML_CONTENT_TYPE);
			response.setHeader(ReportConstants.HEADER_INFO,
					ReportConstants.HTML_HEADER_INFO);
			response.setCharacterEncoding(ReportConstants.CHAR_SET);
			response.getWriter().write(
					new String(eventReportStream.toByteArray(),
							ReportConstants.CHAR_SET));
		} catch (IOException ioException) {
			log.error("IO Exception while printing event report. ", ioException);
		}
	}

	@RequestMapping("customerReport.do")
	public ModelAndView getcustomerDetails(HttpServletRequest request,
			HttpServletResponse response, CustomerDetails customerDetails) {
		ModelAndView mav = null;
		try {
			HttpSession session = request.getSession(true);
			String from = customerDetails.getFrom();
			String username = session.getAttribute("username").toString();

			List<EmployeeDetails> employee = racesService.getEmployeeID();
			List<String> taluk = queryList.getTaluk();
			List<String> district = queryList.getDistricts();
			List<String> registeredBranches = queryList.getRegisteredBranch();
			List<String> deliveryBranches = queryList.getDeliverydBranch();
			System.out.println("CUSTOMER DETAILS REPORT");
			System.out.println("taluk : " + taluk.toString());
			System.out.println("district : " + district.toString());
			System.out.println("registeredBranches : "
					+ registeredBranches.toString());
			System.out.println("deliveryBranches : "
					+ deliveryBranches.toString());
			System.out.println("employee size : " + employee.size());

			if (ReportConstants.ADD_CUSTOMER.equals(from)) {
				mav = new ModelAndView("addcustomerdetails");
			} else if (ReportConstants.UPDATE_CUSTOMER.equals(from)) {
				mav = new ModelAndView("retreivecustomerdetails");
			}
			mav.addObject("EmployeeDetails", employee);
			mav.addObject("districts", district);
			mav.addObject("taluks", taluk);
			mav.addObject("registeredBranches", registeredBranches);
			mav.addObject("deliveryBranches", deliveryBranches);
			mav.addObject("username", username);

			mav.addObject("fromParam", from);
			mav.addObject("username", username);

		} catch (java.lang.NullPointerException e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", "Sorry, Session TimeOut Please Login Again !!!.");
		}catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}
	@RequestMapping("sparesReport.do")
	public ModelAndView getsparesDetails(HttpServletRequest request,
			HttpServletResponse response, SparesDetails sparesDetails) {
		ModelAndView mav = null;
		try {
			System.out.println("sparesReport.do Called....");
			HttpSession session = request.getSession(true);
			String from = sparesDetails.getFrom();
			String username = session.getAttribute("username").toString();

			/*
			List<EmployeeDetails> employee = racesService.getEmployeeID();
			List<String> taluk = queryList.getTaluk();
			List<String> district = queryList.getDistricts();
			List<String> registeredBranches = queryList.getRegisteredBranch();
			List<String> deliveryBranches = queryList.getDeliverydBranch();
			
			System.out.println("CUSTOMER DETAILS REPORT");
			System.out.println("taluk : " + taluk.toString());
			System.out.println("district : " + district.toString());
			System.out.println("registeredBranches : "
					+ registeredBranches.toString());
			System.out.println("deliveryBranches : "
					+ deliveryBranches.toString());
			System.out.println("employee size : " + employee.size());
			 */
			if (ReportConstants.ADD_SPARES.equals(from)) {
				mav = new ModelAndView("addsparesdetails");
			} else if (ReportConstants.UPDATE_SPARES.equals(from)) {
				mav = new ModelAndView("retreivesparesdetails");
			}
			/*mav.addObject("districts", district);
			mav.addObject("taluks", taluk);
			mav.addObject("registeredBranches", registeredBranches);
			mav.addObject("deliveryBranches", deliveryBranches);
			*/
			mav.addObject("username", username);

			mav.addObject("fromParam", from);
			//mav.addObject("EmployeeDetails", employee);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}
	@RequestMapping("feedbackReport.do")
	public ModelAndView getFeedBackDetails(HttpServletRequest request,
			HttpServletResponse response,  FeedbackDetails feedbackDetails) {
		ModelAndView mav = null;
		try {
			
			System.out.println("feedbackReport.do Called....");
			List<EmployeeDetails> employee = racesService.getEmployeeID();
			List<String> statusValue = new ArrayList<String>();
			statusValue.add("Resolved");
			statusValue.add("Not-Resolved");
			HttpSession session = request.getSession(true);
			String from = feedbackDetails.getFrom();
			String username = session.getAttribute("username").toString();
			if (ReportConstants.ADD_FEEDBACK_REPORT.equals(from)) {
				mav = new ModelAndView("addfeedbackdetails");
			} else if (ReportConstants.UPDATE_FEEDBACK_REPORT.equals(from)) {
				mav = new ModelAndView("retreivefeedbackdetails");
			}
			mav.addObject("username", username);
			mav.addObject("statusValues", statusValue);
			mav.addObject("EmployeeDetails", employee);
			mav.addObject("fromParam", from);
			//mav.addObject("EmployeeDetails", employee);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}
	@RequestMapping("assocReport.do")
	public ModelAndView getAssocDetails(HttpServletRequest request,
			HttpServletResponse response, EmployeeDetails employeeDetails) {
		ModelAndView mav = null;
		try {
			System.out.println("assocReport.do Called....");
			HttpSession session = request.getSession(true);
			List<EmployeeDetails> employee = racesService.getEmployeeID();
			String from = employeeDetails.getFrom();
			String username = session.getAttribute("username").toString();
			if (ReportConstants.ADD_ASSOC.equals(from)) {
				mav = new ModelAndView("addassocdetails");
			} else if (ReportConstants.UPDATE_ASSOC.equals(from)) {
				mav = new ModelAndView("retreiveassocdetails");
			}
			mav.addObject("username", username);
			mav.addObject("fromParam", from);
			mav.addObject("EmployeeDetails", employee);
			//mav.addObject(employeeDetails);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}	
	@RequestMapping("fetchCustomerDetail.do")
	public ModelAndView fetchCustomerDetails(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("chasisNumber") String chasis,
			CustomerDetails customerDetails) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString();
		List<String> taluk = queryList.getTaluk();
		List<String> district = queryList.getDistricts();
		List<String> registeredBranches = queryList.getRegisteredBranch();
		List<String> deliveryBranches = queryList.getDeliverydBranch();
		List<EmployeeDetails> employee = racesService.getEmployeeID();
		System.out.println("FETCH CUSTOMER DETAILS");
		System.out.println("taluk : " + taluk.toString());
		System.out.println("district : " + district.toString());
		System.out.println("registeredBranches : "
				+ registeredBranches.toString());
		System.out.println("deliveryBranches : " + deliveryBranches.toString());
		System.out.println("employee size : " + employee.size());

		try {
			// customerDetails = new CustomerDetails();
			customerDetails = racesService.getCustomerDetails(chasis,
					customerDetails);

			if ("success".equals(customerDetails.getMessage())) {
				mav = new ModelAndView("updatecustomerdetails");
				mav.addObject("successmessage", "successfully fetched");
				mav.addObject("districts", district);
				mav.addObject("taluks", taluk);
				mav.addObject("registeredBranches", registeredBranches);
				mav.addObject("deliveryBranches", deliveryBranches);
				mav.addObject("EmployeeDetails", employee);
			} else {
				mav = new ModelAndView("retreivecustomerdetails");
				mav.addObject("failuremessage", "No details found");
			}
			mav.addObject("username", username);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}
	@RequestMapping("fetchSparesDetail.do")
	public ModelAndView fetchSparesDetail(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("sparesCode") String sparesCode,
			SparesDetails sparesDetails) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString();
		List<String> obsoleteValue = new ArrayList<String>();
		obsoleteValue.add("YES");
		obsoleteValue.add("NO");
		try {
			// customerDetails = new CustomerDetails();
			sparesDetails = racesService.getSparesDetails(sparesCode,
					sparesDetails);

			if ("success".equals(sparesDetails.getMessage())) {
				mav = new ModelAndView("updatesparesdetails");
				mav.addObject("successmessage", "successfully fetched");
				mav.addObject("obsoleteValues", obsoleteValue); 
			} else {
				mav = new ModelAndView("retreivesparesdetails");
				mav.addObject("failuremessage", "No details found");
			}
			mav.addObject("username", username);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}
	@RequestMapping("fetchFeedbackDetail.do")
	public ModelAndView fetchFeedbackDetail(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("feedbackid") String feedbackid,
			FeedbackDetails feedbackDetails) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString();
		List<EmployeeDetails> employee = racesService.getEmployeeID();
		List<String> statusValue = new ArrayList<String>();
		feedbackDetails = racesService.getFeedbackDetailsForId(Integer.parseInt(feedbackid),feedbackDetails);
		statusValue.add("Resolved");
		statusValue.add("Not-Resolved");
		try { 

			if ("success".equals(feedbackDetails.getMessage())) {
				mav = new ModelAndView("updatefeedbackdetails");
				mav.addObject("successmessage", "successfully fetched");
				mav.addObject("statusValues", statusValue); 
			} else {
				mav = new ModelAndView("listfeedbackdetails");
				mav.addObject("failuremessage", "No details found");
			}
			mav.addObject("username", username);
			mav.addObject("EmployeeDetails", employee);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}
//TODO : use this to check...
	@RequestMapping("fetchAssocDetail.do")
	public ModelAndView fetchAssocDetail(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("employeeId") String employeeId,
			EmployeeDetails employeeDetails) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString();
		try { 
			 employeeDetails = racesService.getAssocDetails(employeeId, employeeDetails);
			if ("success".equals((String)employeeDetails.getMessage())) {
				mav = new ModelAndView("updateassocdetails");
				mav.addObject("successmessage", "successfully fetched"); 
			} else {
				mav = new ModelAndView("retreiveassocdetails");
				mav.addObject("failuremessage", "No details found");
			}
			mav.addObject("username", username); 
			mav.addObject("jobcardNumberForAjax", employeeId);
			//mav.addObject("employeeDetails",gson.toJson(employeeDetails).replaceAll("\"","\\\""));
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}
	@RequestMapping("getFeedBackDatas.do")
	public void getFeedBackDatas(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("jobcardNumber") String jobcardNumber){
		try{
			System.out.println("getFeedBackDatas.do called for : "+jobcardNumber );
			Map<String, Object> employeeDetails = racesService.getFeedbackDetails(jobcardNumber,new FeedbackDetails(),"UI");
			Gson gson = new Gson();
			System.out.println(gson.toJson(employeeDetails)); 
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		    out.write(gson.toJson(employeeDetails).getBytes());
		    response.setContentType("application/json");
		    response.setContentLength(out.size());
		    response.getOutputStream().write(out.toByteArray());
		    response.getOutputStream().flush();
			
		}catch(Exception ex){
			log.debug("Exception @ getSparesCountMetrics",ex);
		}
	}
	@RequestMapping("getCustomerDatas.do")
	public void getCustomerDatas(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("queryName") String queryName,@RequestParam("yearData") String yearData,@RequestParam("monthData") String monthData,@RequestParam("diffData") String diffData){
		try{
			System.out.println("getCustomerDatas.do called for : queryName : "+queryName+" yearData :"+yearData+" diffData: "+diffData);
			if("---".equals(yearData))
			{
				yearData = "0";
			}
			if("---".equals(monthData))
			{
				monthData = "0";
			}
			Map<String, Object> employeeDetails = racesService.getCustomerDetails(queryName,diffData,Integer.parseInt(yearData),Integer.parseInt(monthData));
			Gson gson = new Gson();
			System.out.println(gson.toJson(employeeDetails)); 
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		    out.write(gson.toJson(employeeDetails).getBytes());
		    response.setContentType("application/json");
		    response.setContentLength(out.size());
		    response.getOutputStream().write(out.toByteArray());
		    response.getOutputStream().flush();
			
		}catch(Exception ex){
			log.debug("Exception @ getSparesCountMetrics",ex);
		}
	}
	@RequestMapping("validateJobcardAvailability.do")
	public void validateJobcardAvailability(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("jobcardNumber") String jobcardNumber){
		try{
			System.out.println("validateJobcardAvailability.do called for : "+jobcardNumber );
			ValidityCheck jobcardValidity = racesService.isJobcardAvailable(jobcardNumber);
			Map<String, Object> datas = new HashMap<String, Object>();
			datas.put("dataList", jobcardValidity);
			Gson gson = new Gson();
			System.out.println(gson.toJson(datas)); 
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		    out.write(gson.toJson(datas).getBytes());
		    response.setContentType("application/json");
		    response.setContentLength(out.size());
		    response.getOutputStream().write(out.toByteArray());
		    response.getOutputStream().flush();
			
		}catch(Exception ex){
			log.debug("Exception @ getSparesCountMetrics",ex);
		}
	}
	@RequestMapping("fetchTotalWorkingDays.do")
	public void fetchTotalWorkingDays(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate){
		try{
			log.info("fetchTotalWorkingDays api called ... : startDate : "
					+ startDate + " endDate : " + endDate);
			Map<String, Object> datas = new HashMap<String, Object>();
			Gson gson = new Gson();			
			int noOfWorkingDays = racesService.getNoOfWorkingDays(startDate,
					endDate);
			System.out.println("No Of Working Days : "+noOfWorkingDays);
			System.out.println(gson.toJson(noOfWorkingDays+""));
			datas.put("noOfWorkingDays", noOfWorkingDays); 
			System.out.println(gson.toJson(datas)); 
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		    out.write(gson.toJson(datas).getBytes());
		    response.setContentType("application/json");
		    response.setContentLength(out.size());
		    response.getOutputStream().write(out.toByteArray());
		    response.getOutputStream().flush();
			
		}catch(Exception ex){
			log.debug("Exception @ getSparesCountMetrics",ex);
		}
	}

	@RequestMapping("addassocsalarydetails.do")
	public ModelAndView addAssociateSalary(HttpServletRequest request,
			HttpServletResponse response, AssociateSalaryDetails associateSalaryDetails) {
		ModelAndView mav = null;
		try {
			List<EmployeeDetails> employee = racesService.getEmployeeID();
			List<DaDetails> daDetails = racesService.getDADetails();
			System.out.println("@ controller addassocsalarydetails.do .....");
			log.debug("Entered addassocsalarydetails.do ..");
			String from = associateSalaryDetails.getFrom();
			HttpSession session = request.getSession(true);
			String username = session.getAttribute("username").toString(); 

			String message = "success";
			if("addassocsalarydetails".equals(from)){
				mav = new ModelAndView("addassocsalarydetails");
			}
			else{
				mav = new ModelAndView("updateassocsalarydetails");
			}
			if ("success".equals(message)) {
				mav.addObject("successmessage", "successfully saved");
			} else {
				mav.addObject("failuremessage",
						"unsuccessful due to duplication of Spares Code");
			}
			mav.addObject("fromParam", from);
			mav.addObject("username", username); 
			mav.addObject("EmployeeDetails", employee);
			mav.addObject("daDetails", daDetails);
			
			mav.addObject("noOfWorkingDays", racesService.getNoOfWorkingDaysForCurrentMonth());
			mav.addObject("noOfPublicHolidays", racesService.getNoOfPublicHolidaysForCurrentMonth());
			mav.addObject("currentMonthYear", racesService.getCurrentMonthYear()); 
			
			
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}

		return mav;
	}		

	@RequestMapping("getAddSalaryDateDetails.do")
	public void getAddSalaryDateDetails(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("monthYear") String monthYear){
		try{
			String dateDetails = "01/"+monthYear;
			Map<Object,Object> dats = new HashMap<Object,Object>(); 
			
			dats= racesService.getAddSalaryDateDetails(dateDetails); 
			 
			Gson gson = new Gson();
			System.out.println(gson.toJson(dats));
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		    out.write(gson.toJson(dats).getBytes());
		    response.setContentType("application/json");
		    response.setContentLength(out.size());
		    response.getOutputStream().write(out.toByteArray());
		    response.getOutputStream().flush();
			
		}catch(Exception ex){
			log.debug("Exception @ getAddSalaryDateDetails",ex);
		}
	}	
	@RequestMapping("getAssociateBaseSalary.do")
	public void getAssociateBaseSalary(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("eid") String eid,@RequestParam("monthYear") String salaryMonth){
		double baseSalary=0.0;
		try{
			HashMap<Object,Object> dats = new HashMap<Object,Object>(); 
			if(salaryMonth==null || "".equals(salaryMonth)){
				 baseSalary= racesService.getAssociateBaseSalary(eid,null);
			}else
			{
				 baseSalary= racesService.getAssociateBaseSalary(eid,"01/"+salaryMonth);	
			}
			 
			
			dats.put("baseSalary", baseSalary);
			Gson gson = new Gson();
			System.out.println(gson.toJson(baseSalary));
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		    out.write(gson.toJson(dats).getBytes());
		    response.setContentType("application/json");
		    response.setContentLength(out.size());
		    response.getOutputStream().write(out.toByteArray());
		    response.getOutputStream().flush();
		}catch(Exception ex){
			log.debug("Exception @ getAssociateBaseSalary",ex);
		}
	}	
	// .jsp
	@RequestMapping("getSalaryEntryPage.do")
	public ModelAndView getSalaryEntryPage(HttpServletRequest request,
			HttpServletResponse response, AssociateSalaryDetails associateSalaryDetails) {
		ModelAndView mav = null;

		try {
			System.out.println("@ controller.....");
			log.debug("Entered jobCardReport ..");
			List<EmployeeDetails> employee = racesService.getEmployeeID();
			List<DaDetails> daDetails = racesService.getDADetails();
			String from = associateSalaryDetails.getFrom();
			List<AssociateDaDetails> daMappedDetails =new ArrayList<AssociateDaDetails>();
			for(DaDetails daData:daDetails){
				AssociateDaDetails aDaData = new AssociateDaDetails();
				aDaData.setDatype(daData.getDaType());
				aDaData.setDaperday(daData.getAmount());
				aDaData.setGendate(daData.getAsOnDate());
				daMappedDetails.add(aDaData);
			}
			
			associateSalaryDetails.setAssociateDaDetails(daMappedDetails);
			// reportFilter.setFormatType(FormatType.HTML); 
			HttpSession session = request.getSession(true);
			String username = session.getAttribute("username").toString();

			if (ReportConstants.ADD_SALARY_DETAILS.equals(from)) {
				mav = new ModelAndView("addassocsalarydetails"); 
				mav.addObject("EmployeeDetails", employee); 
				mav.addObject("daDetails", daDetails);
				
				mav.addObject("daMappedDetails", daMappedDetails);
				
				mav.addObject("noOfWorkingDays", racesService.getNoOfWorkingDaysForCurrentMonth());
				mav.addObject("noOfPublicHolidays", racesService.getNoOfPublicHolidaysForCurrentMonth());
				mav.addObject("currentMonthYear", racesService.getCurrentMonthYear()); 
				mav.addObject("noOfDaysInCurrentMonth", racesService.getNoOfDaysForCurrentMonth());
			} else if (ReportConstants.UPDATE_ASSOC_SALARY.equals(from)) {
				
				mav = new ModelAndView("retreiveassocsalarydetails");
				mav.addObject("EmployeeDetails", employee); 
			}
			mav.addObject("fromParam", from);
			mav.addObject("username", username);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}

		return mav;
	}
	// TODO : Complete this service
	@RequestMapping("getSalaryDataEntryPage.do")
	public ModelAndView getSalaryDataEntryPage(HttpServletRequest request,
			HttpServletResponse response, SalaryDetails salaryDetails) {
		ModelAndView mav = null;
		try {
			System.out.println("@ controller.....");
			log.debug("Entered getSalaryDataEntryPage ..");
			List<EmployeeDetails> employee = racesService.getEmployeeID(); 
			String from = salaryDetails.getFrom(); 
			// reportFilter.setFormatType(FormatType.HTML); 
			HttpSession session = request.getSession(true);
			String username = session.getAttribute("username").toString();

			if (ReportConstants.ADD_SALARY_DATA.equals(from)) {
				mav = new ModelAndView("addassocsalarydata"); 
				mav.addObject("EmployeeDetails", employee);
			} else if (ReportConstants.UPDATE_ASSOC_SALARY_DATA.equals(from)) { 
				mav = new ModelAndView("retreiveassocsalarydata");
				mav.addObject("EmployeeDetails", employee); 
			}else if (ReportConstants.UPDATE_ASSOC_SALARY_DETAILS.equals(from)) { 
				mav = new ModelAndView("updateassocsalarydetails");
				mav.addObject("EmployeeDetails", employee); 
			}
			mav.addObject("fromParam", from);
			mav.addObject("username", username);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}

		return mav;
	}	
	@RequestMapping("addAssocSalaryData.do")
	public ModelAndView insertAssocSalaryData(HttpServletRequest request,
			HttpServletResponse response, SalaryDetails salaryDetails) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString();

		try {
			System.out.println("@ controller.....");
			log.debug("Entered addAssocSalaryData ..");
			// reportFilter.setFormatType(FormatType.HTML); 
			String from = salaryDetails.getFrom(); 
			List<EmployeeDetails> employee = racesService.getEmployeeID(); 
			String message ="";
			if(ReportConstants.ADD_SALARY_DATA.equals(from)){ 
				 message = racesService.addAssocSalaryData(salaryDetails);
				 if (message.equals("success")
							&& ReportConstants.ADD_SALARY_DATA.equals(from)) {
						mav = new ModelAndView("addassocsalarydata"); 
						mav.addObject("successmessage", "successfully saved");
						salaryDetails = new SalaryDetails();
						salaryDetails.setFrom(from);
						mav.addObject("fromParam", "addassocsalarydata");
					} else {
						mav = new ModelAndView("addassocsalarydata"); 
						mav.addObject("failuremessage",
								"Salary Details Already Exixts FOr this Month...");
						salaryDetails = new SalaryDetails();
						salaryDetails.setFrom(from);
						mav.addObject("fromParam", "updateassocsalarydetails");
					}
				 
			}else if(ReportConstants.UPDATE_ASSOC_SALARY_DETAILS.equals(from) || "retreiveassocsalarydata".equals(from) || "updateassocsalarydata".equals(from)){ 
				message = racesService.updateAssocSalaryDetails(salaryDetails);
				 if ("retreiveassocsalarydata".equals(from)) {
						mav = new ModelAndView("retreiveassocsalarydata"); //Checkit
						//mav.addObject("successmessage", "successfully saved");
						//salaryDetails = new SalaryDetails();
						//salaryDetails.setFrom(from);
					} else if("updateassocsalarydata".equals(from) && "success".equals(message)){
						mav = new ModelAndView("addassocsalarydata"); 
						mav.addObject("successmessage",
								"successfully saved");
						salaryDetails = new SalaryDetails();
						salaryDetails.setFrom(from);
					}else {
						mav = new ModelAndView("addassocsalarydata"); 
						mav.addObject("failuremessage",
								"Update Failed.. Please Try Again...");
						salaryDetails = new SalaryDetails();
						salaryDetails.setFrom(from);
					}
				 
			} 
			mav.addObject("username", username);
			mav.addObject("EmployeeDetails", employee); 
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}

		return mav;
	}	

	@RequestMapping("addAssocDAData.do")
	public ModelAndView insertDAData(HttpServletRequest request,
			HttpServletResponse response, DaDetails daDetails) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString();

		try {
			System.out.println("@ controller.....");
			log.debug("Entered addAssocSalaryData ..");
			// reportFilter.setFormatType(FormatType.HTML); 
			String from = daDetails.getFrom(); 
			List<EmployeeDetails> employee = racesService.getEmployeeID(); 
			String message ="";
			if(ReportConstants.ADD_SALARY_DATA.equals(from)){ 
				 message = racesService.addDaData(daDetails);
				 if (message.equals("success")
							&& ReportConstants.ADD_SALARY_DATA.equals(from)) {
						mav = new ModelAndView("addDAdata"); 
						mav.addObject("successmessage", "successfully saved");
						daDetails = new DaDetails();
						daDetails.setFrom(from);
						mav.addObject("fromParam", "addDAdata");
					} else {
						mav = new ModelAndView("addassocsalarydata"); 
						mav.addObject("failuremessage",
								"Salary Details Already Exixts FOr this Month...");
						daDetails = new DaDetails();
						daDetails.setFrom(from);
						mav.addObject("fromParam", "updateDAdetails");
					}
				 
			}else if(ReportConstants.UPDATE_DA_DATA.equals(from)){ 
				// message = racesService.updateAssocSalaryDetail(associateSalaryDetails);
				 if (message.equals("success")
							&& ReportConstants.UPDATE_DA_DATA.equals(from)) {
						mav = new ModelAndView("addassocsalarydata"); //Checkit
						mav.addObject("successmessage", "successfully saved");
						daDetails = new DaDetails();
						daDetails.setFrom(from);
					} else {
						mav = new ModelAndView("addassocsalarydata"); 
						mav.addObject("failuremessage",
								"Update Failed.. Please Try Again...");
						daDetails = new DaDetails();
						daDetails.setFrom(from);
					}
				 
			} 
			mav.addObject("username", username);
			mav.addObject("EmployeeDetails", employee); 
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}

		return mav;
	}
	@RequestMapping("addAssocSalaryDetails.do")
	public ModelAndView insertAssocSalaryDetails(HttpServletRequest request,
			HttpServletResponse response, AssociateSalaryDetails associateSalaryDetails) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString();

		try {
			System.out.println("@ controller.....");
			log.debug("Entered addJobcardReport ..");
			// reportFilter.setFormatType(FormatType.HTML);
			List<AssociateDaDetails> daMappedDetails =new ArrayList<AssociateDaDetails>();
			String from = associateSalaryDetails.getFrom(); 
			List<EmployeeDetails> employee = racesService.getEmployeeID();
			List<DaDetails> daDetails = racesService.getDADetails();
			for(DaDetails daData:daDetails){
				AssociateDaDetails aDaData = new AssociateDaDetails();
				aDaData.setDatype(daData.getDaType());
				aDaData.setDaperday(daData.getAmount());
				aDaData.setGendate(daData.getAsOnDate());
				daMappedDetails.add(aDaData);
			}
			String message ="";
			if(ReportConstants.ADD_SALARY_DETAILS.equals(from)){
				associateSalaryDetails.setNoOfWorkingDaysInMonth(racesService.getNoOfWorkingDaysForCurrentMonth());
				 message = racesService.addAssocSalaryDetail(associateSalaryDetails);
				 if (message.equals("success")
							&& ReportConstants.ADD_SALARY_DETAILS.equals(from)) {
						mav = new ModelAndView("addassocsalarydetails"); 
						mav.addObject("successmessage", "successfully saved");
						associateSalaryDetails = new AssociateSalaryDetails();
						associateSalaryDetails.setFrom(from);
						mav.addObject("fromValue", "addassocsalarydetails");
					} else {
						mav = new ModelAndView("addassocsalarydetails"); 
						mav.addObject("failuremessage",
								"Salary Details Already Exixts FOr this Month...");
						associateSalaryDetails = new AssociateSalaryDetails();
						associateSalaryDetails.setFrom(from);
						mav.addObject("fromValue", "updateassocsalarydetails");
					}
				 
			}else if(ReportConstants.UPDATE_ASSOC_SALARY.equals(from)){
				associateSalaryDetails.setNoOfWorkingDaysInMonth(racesService.getNoOfWorkingDaysForCurrentMonth());
				 message = racesService.updateAssocSalaryDetail(associateSalaryDetails);
				 if (message.equals("success")
							&& ReportConstants.UPDATE_ASSOC_SALARY.equals(from)) {
						mav = new ModelAndView("addassocsalarydetails"); //Checkit
						mav.addObject("successmessage", "successfully saved");
						associateSalaryDetails = new AssociateSalaryDetails();
						associateSalaryDetails.setFrom(from);
					} else {
						mav = new ModelAndView("retreiveassocsalarydetails"); 
						mav.addObject("failuremessage",
								"Update Failed.. Please Try Again...");
						associateSalaryDetails = new AssociateSalaryDetails();
						associateSalaryDetails.setFrom(from);
					}
				 
			} 
			mav.addObject("username", username);
			mav.addObject("EmployeeDetails", employee);
			mav.addObject("daDetails", daDetails);
			mav.addObject("daMappedDetails", daMappedDetails);			
			mav.addObject("noOfWorkingDays", racesService.getNoOfWorkingDaysForCurrentMonth());
			mav.addObject("noOfDaysInCurrentMonth", racesService.getNoOfDaysForCurrentMonth());
			mav.addObject("noOfPublicHolidays", racesService.getNoOfPublicHolidaysForCurrentMonth());
			mav.addObject("currentMonthYear", racesService.getCurrentMonthYear()); 
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}

		return mav;
	}

	@RequestMapping("fetchAssocSalaryDetail.do")
	public ModelAndView fetchAssocSalaryDetail(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("employeeId") String employeeId,
			@RequestParam("salaryMonth") String salaryMonth,
			AssociateSalaryDetails associateSalaryDetails) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString();
		List<DaDetails> daDetails = racesService.getDADetails();
		List<EmployeeDetails> employee = racesService.getEmployeeID();		
		List<AssociateDaDetails> daMappedDetails = new ArrayList<AssociateDaDetails>();	
		List<AssociateDaDetails> tempdaMappedDetails = new ArrayList<AssociateDaDetails>();	
		for (DaDetails daData : daDetails) {
			AssociateDaDetails aDaData = new AssociateDaDetails();
			aDaData.setDatype(daData.getDaType());
			aDaData.setDaperday(daData.getAmount());
			aDaData.setGendate(daData.getAsOnDate());
			daMappedDetails.add(aDaData);
		}

		try { 
			//	05/2016
			associateSalaryDetails = racesService.getAssocSalaryDetails(employeeId, associateSalaryDetails,"01/"+salaryMonth);
			
			if (associateSalaryDetails.getAssociateDaDetails().size() == 0) { 
				associateSalaryDetails.setAssociateDaDetails(daMappedDetails);
			}else
			{
				for (AssociateDaDetails daData : daMappedDetails) {
					for(AssociateDaDetails availabledaData :associateSalaryDetails.getAssociateDaDetails()){
						if(!(availabledaData.getDatype().equals(daData.getDatype()))){
							tempdaMappedDetails.add(daData);
						}else{
							/* ignore Scenario*/
						}
						
					}
				}
				associateSalaryDetails.getAssociateDaDetails().addAll(tempdaMappedDetails);
			}
			
			if ("success".equals(associateSalaryDetails.getMessage())) {
				mav = new ModelAndView("addassocsalarydetails"); // also checkit
				mav.addObject("successmessage", "successfully fetched"); 
			} else {
				mav = new ModelAndView("retreiveassocsalarydetails");
				mav.addObject("failuremessage", "No details found");
			}
			mav.addObject("username", username); 
			mav.addObject("jobcardNumberForAjax", employeeId);
			mav.addObject("EmployeeDetails", employee);
			mav.addObject("daDetails", daDetails);
			mav.addObject("fromValue", "updateassocsalarydetails");
			
			mav.addObject("daMappedDetails", daMappedDetails);			
			mav.addObject("noOfWorkingDays", racesService.getNoOfWorkingDaysForCurrentMonth());
			mav.addObject("noOfDaysInCurrentMonth", racesService.getNoOfDaysForCurrentMonth());
			mav.addObject("noOfPublicHolidays", racesService.getNoOfPublicHolidaysForCurrentMonth());
			mav.addObject("currentMonthYear", racesService.getCurrentMonthYear()); 
			//mav.addObject("employeeDetails",gson.toJson(employeeDetails).replaceAll("\"","\\\""));
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}	
	
	/*
	@RequestMapping("fetchAssocSalaryData.do")
	public ModelAndView fetchAssocSalaryData(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("employeeId") String employeeId,
			@RequestParam("salaryMonth") String salaryMonth,
			SalaryDetails salaryDetails) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString();
		List<DaDetails> daDetails = racesService.getDADetails();
		List<EmployeeDetails> employee = racesService.getEmployeeID();
		try{
			salaryDetails= racesService.fetchAssocSalaryData(employeeId, "01/"+salaryMonth);
			if(salaryDetails.getAsOnDate()!=null)
			{
			//mav = new ModelAndView("updateassocsalarydata");
				mav = new ModelAndView("addassocsalarydata");
				
			mav.addObject("username", username); 
			mav.addObject("jobcardNumberForAjax", employeeId);
			mav.addObject("EmployeeDetails", employee);
			mav.addObject("fromParam", "updateassocsalarydetails");	
			mav.addObject("successmessage", "successfully fetched..!!!"); 
			}else
			{
				mav = new ModelAndView("updateassocsalarydata");
				mav.addObject("username", username); 
				mav.addObject("jobcardNumberForAjax", employeeId);
				mav.addObject("EmployeeDetails", employee);
				mav.addObject("daDetails", daDetails);
				mav.addObject("fromParam", "updateassocsalarydetails");	
				mav.addObject("successmessage", "No Data Found !!!"); 
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
			
		}
	return mav;
	}
	*/
	//TODO: Complete this Api
	@RequestMapping("fetchAssocSalaryData.do")
	public ModelAndView fetchAssocSalaryData(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("employeeId") String employeeId,
			@RequestParam("salaryMonth") String salaryMonth,
			SalaryDetails salaryDetails) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		
		String username = session.getAttribute("username").toString();
		List<EmployeeDetails> employee = racesService.getEmployeeID();
		try { 
			salaryDetails= racesService.fetchAssocSalaryData(employeeId, "01/"+salaryMonth);
			if ("success".equals((String)salaryDetails.getMessage())) {
				mav = new ModelAndView("addassocsalarydata");
				mav.addObject("EmployeeDetails", employee);
				mav.addObject("successmessage", "successfully fetched"); 
			} else {
				mav = new ModelAndView("addassocsalarydata");
				mav.addObject("EmployeeDetails", employee);
				mav.addObject("failuremessage", "No details found");
			}
			mav.addObject("username", username); 
			mav.addObject("jobcardNumberForAjax", employeeId);
			//mav.addObject("employeeDetails",gson.toJson(employeeDetails).replaceAll("\"","\\\""));
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}	
	@RequestMapping("getSalaryReport.do")
	public ModelAndView getSalaryReport(HttpServletRequest request,
			HttpServletResponse response, ReportFilter reportFilter) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		try {
			System.out.println("@ controller.....");
			log.debug("Entered getReport ..");
			log.debug("Current Date" + reportFilter.getForCurrentMonth());
			if(reportFilter.getExportTypeFilter() == null || reportFilter.getExportTypeFilter().equals(""))
			{
				log.debug("Value of Export Filter : NULL .... so setting it to default.....");
				reportFilter.setExportTypeFilter(ExportFilterValue.DEFAULT);
			}
			String from = reportFilter.getFrom();
			Map<String, String> exportType = queryList.getExportType();
			List<String> months = queryList.getMonth();
			List<Object> vehicleYears = racesService.getSalaryYears();
			List<EmployeeDetails> employee = racesService.getEmployeeID();				
			vehicleYears.add("---"); 
			months.add("---"); 
			EmployeeDetails dummy = new EmployeeDetails();
			dummy.setEmployeeName("---");
			dummy.setEmployeeId("---");
			employee.add(dummy);
			
			if (reportFilter.getToDate() != null) {
				reportFilter.setNavLink(null);
			}
			reportFilter.setFormatType(FormatType.HTML);
			if ("construction".equals(reportFilter.getFrom())) {
				mav = new ModelAndView("construction");
			}else if("salaryByYear".equals(reportFilter.getFrom())){
				mav = new ModelAndView("assocdetailssalaryreport");
				List<String> taluk = queryList.getTaluk();

				taluk.add("---");
				mav.addObject("taluks", taluk);
				byte[] reportArray = racesService
						.getPendingReport(reportFilter).toByteArray();
				reportString = new String(reportArray, ReportConstants.CHAR_SET);
			}else if("completeSalaryListByYear".equals(reportFilter.getFrom())){
				mav = new ModelAndView("assocdetailssalarylistreport");
				List<String> taluk = queryList.getTaluk();
				taluk.add("---");
				mav.addObject("salaryYears", racesService.getSalaryDetailsYears());
				mav.addObject("taluks", taluk);
				byte[] reportArray = racesService
						.getPendingReport(reportFilter).toByteArray();
				reportString = new String(reportArray, ReportConstants.CHAR_SET);
				
			}   else if("assocleavereport".equals(reportFilter.getFrom())){
				mav = new ModelAndView("assocdetailssalaryreport");
				List<String> taluk = queryList.getTaluk();

				taluk.add("---");
				mav.addObject("taluks", taluk);
				byte[] reportArray = racesService
						.getPendingReport(reportFilter).toByteArray();
				reportString = new String(reportArray, ReportConstants.CHAR_SET);
			}
			
			mav.addObject("exportType", exportType);
			mav.addObject("months", months);
			mav.addObject("vehicleYears", vehicleYears);
			mav.addObject("reportString", reportString);
			mav.addObject("EmployeeDetails", employee);			
			mav.addObject("fromParam", from);
			String username = session.getAttribute("username").toString();
			mav.addObject("username", username);
			reportFilter.setFormatType(FormatType.PDF);
		} catch (UnsupportedEncodingException e) {
			mav = new ModelAndView("errorreport");
			mav.addObject("ExceptionMessage", e);
		}

		return mav;
	}
		@RequestMapping("getLeaveReport.do")
		public ModelAndView getLeaveReport(HttpServletRequest request,
				HttpServletResponse response, ReportFilter reportFilter) {
			ModelAndView mav = null;
			HttpSession session = request.getSession(true);
			try {
				System.out.println("@ controller.....");
				log.debug("Entered getReport ..");
				log.debug("Current Date" + reportFilter.getForCurrentMonth());
				if(reportFilter.getExportTypeFilter() == null || reportFilter.getExportTypeFilter().equals(""))
				{
					log.debug("Value of Export Filter : NULL .... so setting it to default.....");
					reportFilter.setExportTypeFilter(ExportFilterValue.DEFAULT);
				}
				String from = reportFilter.getFrom();
				Map<String, String> exportType = queryList.getExportType();
				List<String> months = queryList.getMonth();
				List<Object> vehicleYears = racesService.getSalaryYears();
				List<EmployeeDetails> employee = racesService.getEmployeeID();				
				vehicleYears.add("---"); 
				months.add("---"); 
				EmployeeDetails dummy = new EmployeeDetails();
				dummy.setEmployeeName("---");
				dummy.setEmployeeId("---");
				employee.add(dummy);
				
				if (reportFilter.getToDate() != null) {
					reportFilter.setNavLink(null);
				}
				reportFilter.setFormatType(FormatType.HTML);
				if ("construction".equals(reportFilter.getFrom())) {
					mav = new ModelAndView("construction");
				}else if("assocleavereport".equals(reportFilter.getFrom())){
					mav = new ModelAndView("assocleavereport"); 
					byte[] reportArray = racesService
							.getPendingReport(reportFilter).toByteArray();
					reportString = new String(reportArray, ReportConstants.CHAR_SET);
				}  
				
				mav.addObject("exportType", exportType);
				mav.addObject("months", months);
				mav.addObject("vehicleYears", vehicleYears);
				mav.addObject("reportString", reportString);
				mav.addObject("EmployeeDetails", employee);			
				mav.addObject("fromParam", from);
				String username = session.getAttribute("username").toString();
				mav.addObject("username", username);
				reportFilter.setFormatType(FormatType.PDF);
			} catch (UnsupportedEncodingException e) {
				mav = new ModelAndView("errorreport");
				mav.addObject("ExceptionMessage", e);
			}

			return mav;
		}	
		@RequestMapping("getLeaveTrend.do")
		public ModelAndView getLeaveTrend(HttpServletRequest request,
				HttpServletResponse response, ReportFilter reportFilter) {
			ModelAndView mav = null;
			HttpSession session = request.getSession(true);
			try {
				System.out.println("@ controller.....");
				log.debug("Entered getReport ..");
				log.debug("Current Date" + reportFilter.getForCurrentMonth());
				if(reportFilter.getExportTypeFilter() == null || reportFilter.getExportTypeFilter().equals(""))
				{
					log.debug("Value of Export Filter : NULL .... so setting it to default.....");
					reportFilter.setExportTypeFilter(ExportFilterValue.DEFAULT);
				}
				String from = reportFilter.getFrom();
				Map<String, String> exportType = queryList.getExportType();
				List<String> months = queryList.getMonth();
				List<Object> vehicleYears = racesService.getSalaryYears();
				List<EmployeeDetails> employee = racesService.getEmployeeID();				
				
				if (reportFilter.getToDate() != null) {
					reportFilter.setNavLink(null);
				}
				reportFilter.setFormatType(FormatType.HTML);
				if ("construction".equals(reportFilter.getFrom())) {
					mav = new ModelAndView("construction");
				}else if("assocleavetrend".equals(reportFilter.getFrom())){
					mav = new ModelAndView("assocleavetrend"); 
				/*	byte[] reportArray = racesService
							.getPendingReport(reportFilter).toByteArray();
					reportString = new String(reportArray, ReportConstants.CHAR_SET);
					*/
					
				}  
				
				mav.addObject("exportType", exportType);
				mav.addObject("months", months);
				mav.addObject("vehicleYears", vehicleYears);
				mav.addObject("reportString", reportString);
				mav.addObject("EmployeeDetails", employee);			
				mav.addObject("fromParam", from);
				String username = session.getAttribute("username").toString();
				mav.addObject("username", username);
				reportFilter.setFormatType(FormatType.PDF);
			} 
			/*catch (UnsupportedEncodingException e) {
				mav = new ModelAndView("errorreport");
				mav.addObject("ExceptionMessage", e);
			}*/
			catch (Exception e) {
				mav = new ModelAndView("errorreport");
				mav.addObject("ExceptionMessage", e);
			}

			return mav;
		}		 
	@RequestMapping("getSalaryDatas.do")
	public void getSalaryDatas(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("queryName") String queryName,@RequestParam("yearData") String yearData,@RequestParam("monthData") String monthData,@RequestParam("diffData") String diffData){
		try{
			System.out.println("getSalaryDatas.do called for : queryName : "+queryName+" yearData :"+yearData+" diffData: "+diffData);
			if("---".equals(yearData))
			{
				yearData = "0";
			}
			if("---".equals(monthData))
			{
				monthData = "0";
			}
			Map<String, Object> employeeDetails = racesService.getAssociateSalaryDetails(queryName,diffData,Integer.parseInt(yearData),Integer.parseInt(monthData));
			Gson gson = new Gson();
			System.out.println(gson.toJson(employeeDetails)); 
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		    out.write(gson.toJson(employeeDetails).getBytes());
		    response.setContentType("application/json");
		    response.setContentLength(out.size());
		    response.getOutputStream().write(out.toByteArray());
		    response.getOutputStream().flush();
			
		}catch(Exception ex){
			log.debug("Exception @ getSalaryDatas",ex);
			ex.printStackTrace();
		}
	}
	@RequestMapping("getCompleteSalaryDatas.do")
	public void getCompleteSalaryDatas(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("yearData") String yearData){
		try{
			System.out.println("getCompleteSalaryDatas.do called for : "+" yearData :"+yearData);
			Map<String, Object> employeeDetails = racesService.getAssociateCompleteSalaryDetails(yearData);
			Gson gson = new Gson();
			System.out.println(gson.toJson(employeeDetails)); 
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		    out.write(gson.toJson(employeeDetails).getBytes());
		    response.setContentType("application/json");
		    response.setContentLength(out.size());
		    response.getOutputStream().write(out.toByteArray());
		    response.getOutputStream().flush();
			
		}catch(Exception ex){
			log.debug("Exception @ getSalaryDatas",ex);
			ex.printStackTrace();
		}
	}
	@RequestMapping("getLeaveDatas.do")
	public void getLeaveDatas(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("queryName") String queryName,@RequestParam("yearData") String yearData,@RequestParam("monthData") String monthData,@RequestParam("diffData") String diffData){
		try{
			SimpleDateFormat reqFormat = new SimpleDateFormat("MMM");
			SimpleDateFormat availableFormat = new SimpleDateFormat("yyyy-MM-dd");
			HashMap<Object,Object> dats = new HashMap<Object,Object>();
			System.out.println("getSalaryDatas.do called for : queryName : "+queryName+" yearData :"+yearData+" diffData: "+diffData);
			monthData = "0";
			Map<String, Object> leaveDetails = racesService.getAssociateLeaveDetails(queryName,diffData,Integer.parseInt(yearData),Integer.parseInt(monthData));
			List<LeaveDetails> associateLeaveDetails = (List<LeaveDetails>)leaveDetails.get("dataList");
			Integer[] noOfLeaves = new Integer[associateLeaveDetails.size()];
			Integer[] noOfWorkingDays = new Integer[associateLeaveDetails.size()];
			Integer[] noOfDaysInMonth = new Integer[associateLeaveDetails.size()];
			Integer[] noOfDaysWorked = new Integer[associateLeaveDetails.size()];
			String[] monthDetails = new String[associateLeaveDetails.size()];
			
			for(int i=(associateLeaveDetails.size()-1);i>=0;i--){
				noOfLeaves[i]=associateLeaveDetails.get(i).getNoOfLeaves();
				noOfWorkingDays[i]=associateLeaveDetails.get(i).getNoOfWorkingDays();
				noOfDaysInMonth[i]=associateLeaveDetails.get(i).getTotalNoOfDays();
				noOfDaysWorked[i]=associateLeaveDetails.get(i).getNoOfDaysWorked();
				monthDetails[i]=reqFormat.format(availableFormat.parse(associateLeaveDetails.get(i).getCalendarDetails())); 
			}
			dats.put("noOfLeaves", noOfLeaves);
			dats.put("noOfWorkingDays", noOfWorkingDays);
			dats.put("noOfDaysInMonth", noOfDaysInMonth);
			dats.put("noOfDaysWorked", noOfDaysWorked);
			dats.put("monthDetails", monthDetails); 
			Gson gson = new Gson();
			System.out.println(gson.toJson(dats)); 
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		    out.write(gson.toJson(dats).getBytes());
		    response.setContentType("application/json");
		    response.setContentLength(out.size());
		    response.getOutputStream().write(out.toByteArray());
		    response.getOutputStream().flush();
			
		}catch(Exception ex){
			log.debug("Exception @ getSalaryDatas",ex);
			ex.printStackTrace();
		}
	}	

	@RequestMapping("checkEmployeeIdAvailability.do")
	public void checkEmployeeIdAvailability(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("eid") String eid){
		try{
			HashMap<Object,Object> dats = new HashMap<Object,Object>(); 
			
			List<String> existingEmployeeIds= racesService.getEmployeeIDString();
			if(existingEmployeeIds.contains(eid)){
				dats.put("availability", "Change the Employee Id");
			}else
			{
				dats.put("availability", "This ID is Available");
			} 
			Gson gson = new Gson();
			System.out.println(gson.toJson(dats));
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		    out.write(gson.toJson(dats).getBytes());
		    response.setContentType("application/json");
		    response.setContentLength(out.size());
		    response.getOutputStream().write(out.toByteArray());
		    response.getOutputStream().flush();
			
		}catch(Exception ex){
			log.debug("Exception @ getAssociateBaseSalary",ex);
		}
	}	
	@RequestMapping("addCustomerDetails.do")
	public ModelAndView addCustomerDetails(HttpServletRequest request,
			HttpServletResponse response, CustomerDetails customerDetails) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString();
		List<EmployeeDetails> employee = racesService.getEmployeeID();
		List<String> taluk = queryList.getTaluk();
		List<String> district = queryList.getDistricts();
		List<String> registeredBranches = queryList.getRegisteredBranch();
		List<String> deliveryBranches = queryList.getDeliverydBranch();
		System.out.println("ADD CUSTOMER DETAILS");
		customerDetails.setAutoincrement(true);
		System.out.println("taluk : " + taluk.toString());
		System.out.println("district : " + district.toString());
		System.out.println("registeredBranches : "
				+ registeredBranches.toString());
		System.out.println("deliveryBranches : " + deliveryBranches.toString());

		try {
			String message = racesService.addCustomerDetails(customerDetails);
			mav = new ModelAndView("addcustomerdetails");
			if ("success".equals(message)) {
				mav.addObject("successmessage", "successfully saved");
			} else {
				mav.addObject("failuremessage",
						"unsuccessful due to duplication of chasis or engine number");
			}
			mav.addObject("districts", district);
			mav.addObject("taluks", taluk);
			mav.addObject("registeredBranches", registeredBranches);
			mav.addObject("deliveryBranches", deliveryBranches);
			mav.addObject("username", username);
			mav.addObject("EmployeeDetails", employee);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}
	@RequestMapping("addSparesDetails.do")
	public ModelAndView addSparesDetails(HttpServletRequest request,
			HttpServletResponse response, SparesDetails sparesDetails) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString();
		try {
			String message = racesService.addSparesDetails(sparesDetails);
			mav = new ModelAndView("addsparesdetails");
			if ("success".equals(message)) {
				mav.addObject("successmessage", "successfully saved");
			} else {
				mav.addObject("failuremessage",
						"unsuccessful due to duplication of Spares Code");
			}
			
			mav.addObject("username", username);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}
	
	
	@RequestMapping("addfeedBackDetails.do")
	public ModelAndView addFeedBackReport(HttpServletRequest request,
			HttpServletResponse response, FeedbackDetails feedbackDetails) {
		ModelAndView mav = null;
		try {
			List<EmployeeDetails> employee = racesService.getEmployeeID();
			System.out.println("@ controller.....");
			log.debug("Entered addfeedBackDetails.do ..");
			String from = feedbackDetails.getFrom();
			HttpSession session = request.getSession(true);
			String username = session.getAttribute("username").toString();
			List<String> statusValue = new ArrayList<String>();
			statusValue.add("Resolved");
			statusValue.add("Not-Resolved");

			String message = racesService.addFeedbackDetails(feedbackDetails);
			mav = new ModelAndView("addfeedbackdetails");
			if ("success".equals(message)) {
				mav.addObject("successmessage", "successfully saved");
			} else {
				mav.addObject("failuremessage",
						"unsuccessful due to duplication of Spares Code");
			}
			mav.addObject("fromParam", from);
			mav.addObject("username", username);
			mav.addObject("statusValues", statusValue);
			mav.addObject("EmployeeDetails", employee);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}

		return mav;
	}
	
	
	@RequestMapping("addAssocDetails.do")
	public ModelAndView addAssocDetails(HttpServletRequest request,
			HttpServletResponse response, EmployeeDetails employeeDetails) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString();
		try {
			String message = racesService.addAssocDetails(employeeDetails);
			mav = new ModelAndView("addassocdetails");
			if ("success".equals(message)) {
				mav.addObject("successmessage", "successfully saved");
			} else {
				mav.addObject("failuremessage",
						"unsuccessful due to duplication of Assoc Id");
			}
			
			mav.addObject("username", username);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}
	@RequestMapping("addOfferSheetDetails.do")
	public ModelAndView addOfferSheetDetails(HttpServletRequest request,
			HttpServletResponse response, OfferSheetDetails offerSheetDetails) {
		ModelAndView mav = null;
		HttpSession session = request.getSession(true);
		List<EmployeeDetails> employee = racesService.getEmployeeID();
		List<String> taluk = queryList.getTaluk();
		List<String> district = queryList.getDistricts();
		String username = session.getAttribute("username").toString();
		String from = offerSheetDetails.getFrom();
		try {
			if (ReportConstants.ADD_OFFER_SHEET.equals(from)) {
				mav = new ModelAndView("addoffersheetdetails");
				mav.addObject("EmployeeDetails", employee);
				mav.addObject("districts", district);
				mav.addObject("taluks", taluk);
			} else if (ReportConstants.UPDATE_CUSTOMER.equals(from)) {
				mav = new ModelAndView("retreivecustomerdetails");
			}
			//String message = racesService.addOfferSheetDetails(offerSheetDetails);
			String message = "success";
			if ("success".equals(message)) {
				mav.addObject("EmployeeDetails", employee);
				mav.addObject("districts", district);
				mav.addObject("taluks", taluk);
				//mav = new ModelAndView("addoffersheetdetails");
				mav.addObject("successmessage", "successfully saved");
			} else {
				mav.addObject("failuremessage",
						"unsuccessful due to duplication of Offer Sheet Id");
			}
			
			mav.addObject("username", username);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}	
	@RequestMapping("updateCustomerDetails.do")
	public ModelAndView updateCustomerDetails(HttpServletRequest request,
			HttpServletResponse response, CustomerDetails customerDetails) {
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString();
		List<EmployeeDetails> employee = racesService.getEmployeeID();
		List<String> taluk = queryList.getTaluk();
		List<String> district = queryList.getDistricts();
		List<String> registeredBranches = queryList.getRegisteredBranch();
		List<String> deliveryBranches = queryList.getDeliverydBranch();
		ModelAndView mav = null;
		try {

			String message = racesService
					.updateCustomerDetails(customerDetails);
			mav = new ModelAndView("updatecustomerdetails");
			if ("success".equals(message)) {
				mav.addObject("successmessage", "successfully saved");
			} else {
				mav.addObject("failuremessage",
						"unsuccessful due to duplication of chasis or engine number");
			}
			mav.addObject("districts", district);
			mav.addObject("taluks", taluk);
			mav.addObject("registeredBranches", registeredBranches);
			mav.addObject("deliveryBranches", deliveryBranches);
			mav.addObject("EmployeeDetails", employee);
			mav.addObject("username", username);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}

	@RequestMapping("updateSparesDetails.do")
	public ModelAndView updateSparesDetails(HttpServletRequest request,
			HttpServletResponse response, SparesDetails sparesDetails) {
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString(); 
		List<String> obsoleteValue = new ArrayList<String>();
		obsoleteValue.add("YES");
		obsoleteValue.add("NO");
		ModelAndView mav = null;
		try {
			String message = racesService
					.updateSparesDetails(sparesDetails);
			mav = new ModelAndView("updatesparesdetails");
			if ("success".equals(message)) {
				mav.addObject("successmessage", "successfully saved");
			} else {
				mav.addObject("failuremessage",
						"unsuccessful due to duplication of Spares Code");
			} 
			mav.addObject("obsoleteValues", obsoleteValue); 
			mav.addObject("username", username);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}
	@RequestMapping("getListOfFeedbacks.do")
	public ModelAndView getListOfFeedbacks(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("jobcardNumber") String jobcardNumber) {
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString(); 
		ModelAndView mav = null;
		try {
			//List<EmployeeDetails> employee = racesService.getEmployeeID();	
			mav = new ModelAndView("listfeedbackdetails");
			
			mav.addObject("username", username);
			mav.addObject("jobcardNumberForAjax", jobcardNumber);
			//mav.addObject("EmployeeDetails", employee);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}
	@RequestMapping("updateFeedbackDetails.do")
	public ModelAndView updateFeedbackDetails(HttpServletRequest request,
			HttpServletResponse response, FeedbackDetails feedbackDetails) {
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString(); 
		ModelAndView mav = null;
		try {
			List<EmployeeDetails> employee = racesService.getEmployeeID();	
		List<String> statusValue = new ArrayList<String>();
		statusValue.add("Resolved");
		statusValue.add("Not-Resolved");
			String message = racesService
					.updateFeedbackDetails(feedbackDetails);
			mav = new ModelAndView("updatefeedbackdetails");
			if ("success".equals(message)) {
				mav.addObject("successmessage", "successfully saved");
			} else {
				mav.addObject("failuremessage",
						"unsuccessful due to duplication of Feedback Details");
			} 
			mav.addObject("username", username);
			mav.addObject("statusValues", statusValue);
			mav.addObject("EmployeeDetails", employee);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}
	@RequestMapping("updateAssocDetails.do")
	public ModelAndView updateAssocDetails(HttpServletRequest request,
			HttpServletResponse response, EmployeeDetails employeeDetails) {
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString(); 
		List<String> obsoleteValue = new ArrayList<String>();
		obsoleteValue.add("YES");
		obsoleteValue.add("NO");
		ModelAndView mav = null;
		try {
			String message = racesService
					.updateAssocDetails(employeeDetails);
			mav = new ModelAndView("updateassocdetails");
			if ("success".equals(message)) {
				mav.addObject("successmessage", "successfully saved");
			} else {
				mav.addObject("failuremessage",
						"unsuccessful due to duplication of Employee Id");
			} 
			mav.addObject("obsoleteValues", obsoleteValue); 
			mav.addObject("username", username);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}
	@RequestMapping("updateAssocSalaryDetails.do")
	public ModelAndView updateAssocSalaryDetails(HttpServletRequest request,
			HttpServletResponse response, SalaryDetails associateSalaryDetails) {
		HttpSession session = request.getSession(true);
		String username = session.getAttribute("username").toString(); 
		List<EmployeeDetails> employee = racesService.getEmployeeID();	
		ModelAndView mav = null;
		try {
			String message = racesService
					.updateAssocSalaryDetails(associateSalaryDetails);
			mav = new ModelAndView("updateassocsalarydetails");
			if ("success".equals(message)) {
				mav.addObject("successmessage", "successfully saved");
			} else {
				mav.addObject("failuremessage",
						"unsuccessful due to duplication of Employee Id");
			} 
			mav.addObject("username", username);
			mav.addObject("EmployeeDetails", employee);
		} catch (Exception e) {
			mav = new ModelAndView("dataentryerror");
			mav.addObject("ExceptionMessage", e);
		}
		return mav;
	}	

	private byte[] getEventReportBytes(ReportFilter reportFilter) {
		log.debug("Requesting ervent report as stream.");
		// Getting event report as stream and returning as byte array.
		ByteArrayOutputStream eventReportStream = getEventReport(reportFilter);
		log.debug("Returning event report as byte array.");
		return eventReportStream.toByteArray();
	}

	private ByteArrayOutputStream getEventReport(ReportFilter reportFilter) {
		log.debug("Entering get Event report to decide and request service to create an event report.");
		ByteArrayOutputStream eventReportStream = null;
		// Report for research area is done here.
		log.debug("getTopArticlesForEventByResearchArea() called.");
		eventReportStream = racesService.getPendingReport(reportFilter);
		log.debug("Event report creation done.");
		return eventReportStream;
	}
	
	private String getExportReportStatus(ReportFilter reportFilter)
	{
		String message = "";
		message = racesService.getReportExportStatus(reportFilter); 
		return message;
	}

	private String getContentType(FormatType formatType) {
		String contentType = null;
		switch (formatType) {
		case PDF:
			contentType = ReportConstants.PDF_CONTENT_TYPE;
			break;
		case HTML:
			contentType = ReportConstants.HTML_CONTENT_TYPE;
			break;
		case CSV:
			contentType = ReportConstants.CSV_CONTENT_TYPE;
			break;
		default:
			break;
		}
		return contentType;
	}
	
	@RequestMapping("getSparesCountMetrics.do")
	public void getSparesCountMetrics(HttpServletRequest request,
			HttpServletResponse response){
		try{
			HashMap<Object,Object> dats = new HashMap<Object,Object>();
			int noOfMonths = (Integer)request.getAttribute("noOfMonths");
			List<SparesCountMetrics> sparesCountMetricsList= racesService.getSparesCountMetrics(noOfMonths);
			Gson gson = new Gson();
			System.out.println(gson.toJson(sparesCountMetricsList));
			dats.put("sparesCountMetricsList", sparesCountMetricsList);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		    out.write(gson.toJson(dats).getBytes());
		    response.setContentType("application/json");
		    response.setContentLength(out.size());
		    response.getOutputStream().write(out.toByteArray());
		    response.getOutputStream().flush();
			
		}catch(Exception ex){
			log.debug("Exception @ getSparesCountMetrics",ex);
		}
	}
	@RequestMapping("getSparesSaleBySE.do")
	public void getSparesSaleBySE(HttpServletRequest request,
			HttpServletResponse response){
		try{
			HashMap<Object,Object> dats = new HashMap<Object,Object>();
			int noOfMonths = (Integer)request.getAttribute("noOfMonths");
			
			List<SparesSaleBySE> sparesSaleBySEList= racesService.getSparesSaleBySE(noOfMonths); 
			
			dats.put("sparesSaleBySEList", sparesSaleBySEList);
			Gson gson = new Gson();
			System.out.println(gson.toJson(sparesSaleBySEList));
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		    out.write(gson.toJson(dats).getBytes());
		    response.setContentType("application/json");
		    response.setContentLength(out.size());
		    response.getOutputStream().write(out.toByteArray());
		    response.getOutputStream().flush();
			
		}catch(Exception ex){
			log.debug("Exception @ getSparesSaleBySE",ex);
		}
	}

	private String getHeaderInfo(FormatType formatType) {
		String headerInfo = null;
		switch (formatType) {
		case PDF:
			headerInfo = ReportConstants.PDF_HEADER_INFO;
			break;
		case HTML:
			headerInfo = ReportConstants.HTML_HEADER_INFO;
			break;
		case CSV:
			headerInfo = ReportConstants.CSV_HEADER_INFO;
			break;
		default:
			break;
		}
		return headerInfo;
	}

}
