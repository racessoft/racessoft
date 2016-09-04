package org.races.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.races.Constants.ReportConstants;
import org.races.dao.RacesDao;
import org.races.model.AssociateSalaryDetails;
import org.races.model.CustomerDetails;
import org.races.model.CustomerDetailsForExport;
import org.races.model.DaDetails;
import org.races.model.EmployeeDetails;
import org.races.model.EnquiryDetails;
import org.races.model.FeedbackDetails;
import org.races.model.FeedbackUiDetails;
import org.races.model.JobCardDetails;
import org.races.model.OfferSheetDetails;
import org.races.model.PendingService;
import org.races.model.ReportFilter;
import org.races.model.SalaryDetails;
import org.races.model.ServiceChart;
import org.races.model.ServiceUsageTrendChart;
import org.races.model.SoldSpares;
import org.races.model.SparesCountMetrics;
import org.races.model.SparesDetails;
import org.races.model.SparesSaleBySE;
import org.races.model.UserDetails;
import org.races.model.ValidityCheck;
import org.races.util.DateUtil;
import org.races.util.Encryptor;
import org.races.util.ExportFilterValue;
import org.races.util.FormatType;
import org.races.util.JasperUtil;
import org.races.util.QueryList;
import org.races.util.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
public class RacesServiceImpl implements RacesService {

	@Autowired
	RacesDao daoObject;
	@Autowired
	SendMail sendMail;
	@Autowired
	JasperUtil jasperUtil;
	@Autowired
	QueryList queryList;
	@Autowired
	Encryptor encryptData;
	@Autowired
	DateUtil dateUtil;
	@Value("${model.5045D.mapping}")
	private String model5045DMapping;
	@Value("${model.5050D.mapping}")
	private String model5050DMapping;
	@Value("${model.5042D.mapping}")
	private String model5042DMapping;

	/**
	 * Class path where the usage trend chart is stored.
	 */
	@Value("${usageTrend.chart.location}")
	private String usageTrendFile;

	/**
	 * Absolute path where the JASPER HTML exporter puts its image file into
	 * disk.
	 */
	@Value("${usageTrend.image.location}")
	public String userTrendImage;

	/**
	 * JASPER file identifier to read file from class path. This .jasper file is
	 * used to generate usage trend chart.
	 */
	@Value("${usageTrend.chart.jasper}")
	public String usageTrendIdentifier;

	/**
	 * JASPER file identifier to read file from class path. This .jasper file is
	 * used to generate pending service report.
	 */
	@Value("${pendingReport.jasper}")
	public String pendingReport;

	/**
	 * JASPER file identifier to read file from class path. This .jasper file is
	 * used to generate pending service report.
	 */
	@Value("${pendingReportExport.jasper}")
	public String pendingReportExport;

	/**
	 * Export Files location where the exported files are saved.
	 */
	@Value("${exportFolder.district.location}")	
	public String districtwiseExportLocation;
	/**
	 * Export Files location where the exported files are saved.
	 */
	@Value("${exportFolder.taluk.location}")	
	public String talukwiseExportLocation;
	/**
	 * JASPER file location, used to generate failure report to be shown in snap
	 * shot and usage trend chart location on any failures.
	 */
	@Value("${dashboard.failurereport.jasper}")
	public String failureReport;

	@Value("${exportFolder.location}")
	private String exportLocation;

	private static Logger log = Logger.getLogger(RacesServiceImpl.class);
	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd--hh-mm-ss");
	SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public String getAuthenticationValue(String userName, String password) {
		String encryptedPassword ="";
		try {
			 encryptedPassword = encryptData.encryptText(password);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UserDetails user_detail = daoObject.getLoginDetails(userName, encryptedPassword);
		try {
			if (user_detail != null) {
				if (user_detail.getLoginType() != null) {
					log.info("obtained the login type");
					return user_detail.getLoginType().toUpperCase();
				} else {
					log.info("obtained the login type : FAIL");
					return "FAIL";
				}
			} else {
				log.info("obtained the login type : user details id NULL");
				return "FAIL";
			}
		} catch (NullPointerException nullException) {
			System.out.println("USER DETAILS IS NULL");
			log.debug(" user details is NULL");
			return "FAIL";
		}
	}

	@Override
	public String getForgotPassword(String userName) {
		UserDetails userdetails = daoObject.getEmailId(userName);
		String message = sendMail(userdetails.getEmail_id(),
				userdetails.getPassword());
		return message;
	}

	@Override
	public String sendMail(String email_id, String password) {
		// TODO Auto-generated method stub
		String message = sendMail.forgotPassword(email_id, password);
		return message;
	}

	/**
	 * Saves the created usage trend chart into required '.png' image format.
	 */
	private void saveUsageTrend() {
		log.info("Saving usage trend chart as .png image.");
		System.out.println("Saving usage trend chart as .png image.");
		String jasperImage = ReportConstants.USER_HOME + userTrendImage;
		System.out.println("jasperImage loaction : " + jasperImage);
		File imageFile = new File(jasperImage);
		if (imageFile.exists()) {
			String usageTrendImage = ReportConstants.USER_HOME + usageTrendFile;
			System.out.println("usageTrendImage location : " + usageTrendImage);
			File usageTrendImageFile = new File(usageTrendImage);
			// Deletes the old file.
			if (usageTrendImageFile.exists()) {
				boolean isDeleted = usageTrendImageFile.delete();
				log.debug("usageTrendFile deleted status: " + isDeleted);
			}
			boolean isRenamed = imageFile.renameTo(new File(usageTrendImage));
			log.debug("usageTrendFile reNamed status: " + isRenamed);
		}
		log.info("Uage trend chart saved.");
	}

	/**
	 * Creates usage trend chart for ALUM Dash board. Values are pulled from DB
	 * and filled into a JASPER chart and exported as HTML. The exporter is
	 * instructed to save the image file in a local disk location.
	 * 
	 * Stored image is the required chart as file. Later the file is changed
	 * into respective .png format and stored in the same location.
	 * 
	 * @param formatType
	 *            - Type of the file (a PDF, HTML, etc) to be created.
	 * @return byte array of the usage trend chart.
	 */
	@Override
	public ByteArrayOutputStream createUsageTrend(FormatType formatType) {
		// log.info("Entering create usage trend service.");
		System.out.println("Entering create usage trend service.");
		ByteArrayOutputStream jasperByteArray = null;

		if (jasperByteArray == null) {
			// Gets JASPER file for generating usage trend chart as input
			// stream.
			InputStream usageTrendStream = this.getClass().getResourceAsStream(
					usageTrendIdentifier);
			// Gets usage trend data from DB.
			try {
				Object[] objectList = daoObject.getDataForChart();
				List<ServiceChart> usageTrendList = getServiceChartDao(
						(LinkedHashMap) objectList[0],
						(LinkedHashMap) objectList[1]);
				System.out.println("usageTrendList SIZE : "
						+ usageTrendList.size());
				if (usageTrendList != null && !usageTrendList.isEmpty()) {
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("Servicechartdata", usageTrendList);
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							usageTrendStream, parameters,
							new JRBeanCollectionDataSource(usageTrendList));

					jasperByteArray = jasperUtil.jasperPrintToByteArray(
							jasperPrint, formatType);
					if (formatType == FormatType.IMAGE) {
						// Saves usage trend chart as .png image.
						saveUsageTrend();
					}
					System.out.println("Size Of : jasperByteArray "
							+ jasperByteArray.size());
				} else {
					log.debug("Jasper Error.... !!! usageTrendList NULL or Empty");
					System.out
							.println("Jasper Error.... !!! usageTrendList NULL or Empty");
					jasperByteArray = getFailureReport("No Records Found");
				}
			} catch (JRException jasperException) {
				log.error("Excpetion while filling jasper report",
						jasperException);
			} catch (CannotGetJdbcConnectionException dataAccessException) {
				jasperByteArray = getFailureReport("Connection failure");
			} finally {
				closeInputStream(usageTrendStream);
			}
		}
		log.info("Exiting create usage trend service.");
		return jasperByteArray;
	}
	
	public List<ServiceChart> getServiceChartData(){
		List<ServiceChart> usageTrendList = new ArrayList<ServiceChart>();
		try{
			Object[] objectList = daoObject.getDataForChart();
			 usageTrendList = getServiceChartDao(
					(LinkedHashMap) objectList[0],
					(LinkedHashMap) objectList[1]);
		}catch(Exception ex){
			log.info("Exception at getting the chart data : "+ex);
			ex.printStackTrace();
		}
		return usageTrendList;
	}
	public List<ServiceUsageTrendChart> getServiceUsageChartData(){
		List<ServiceUsageTrendChart> usageTrendList = new ArrayList<ServiceUsageTrendChart>();
		try{
			usageTrendList = daoObject.getServiceUsageChartData();
			Collections.sort(usageTrendList, new SizeComparator());
			 
		}catch(Exception ex){
			log.info("Exception at getting the chart data : "+ex);
			ex.printStackTrace();
		}
		return usageTrendList;
	} 
	private List<ServiceChart> getServiceChartDao(
			LinkedHashMap pendingServices, LinkedHashMap completedServices) {
		List<ServiceChart> ServiceChartList = new ArrayList<ServiceChart>();

		try {
			Set pendingSeviceSet = pendingServices.keySet();
			Set completedServiceSet = completedServices.keySet();

			Object[] pendingSeviceKeyArray = pendingSeviceSet.toArray();
			Object[] completedSeviceKeyArray = completedServiceSet.toArray();

			System.out.println("pendingSeviceSet :" + pendingSeviceSet.size());
			System.out.println("completedServiceSet : "
					+ completedServiceSet.size());

			Iterator pendingSeviceIterator = pendingSeviceSet.iterator();
			Iterator comletedSeviceIterator = completedServiceSet.iterator();

			if (pendingSeviceSet.size() > completedServiceSet.size()) {
				int i = 0;
				int j = 0;
				ServiceChart serviceChart;
				try {
					while (pendingSeviceIterator.hasNext()) {
						serviceChart = new ServiceChart();
						pendingSeviceIterator.next();
						serviceChart.setDuration(pendingSeviceKeyArray[i]
								.toString());
						serviceChart.setPending_count(Integer
								.parseInt(pendingServices
										.get(pendingSeviceKeyArray[i])
										.toString().trim()));
						if (completedServices
								.containsKey(pendingSeviceKeyArray[i])) {
							serviceChart.setCompleted_count(Integer
									.parseInt(completedServices
											.get(pendingSeviceKeyArray[i])
											.toString().trim()));
						} else {
							serviceChart.setCompleted_count(Integer
									.parseInt("0"));
						}
						ServiceChartList.add(serviceChart);
						i++;
					}
				} catch (Exception e) {
					System.out.println("pending > completed : " + e);
				}
			} else if (pendingSeviceSet.size() < completedServiceSet.size()) {
				try {
					int i = 0;
					int j = 0;
					ServiceChart serviceChart;
					while (comletedSeviceIterator.hasNext()) {
						serviceChart = new ServiceChart();
						comletedSeviceIterator.next();
						if (completedSeviceKeyArray[i] != null) {
							serviceChart.setDuration(completedSeviceKeyArray[i]
									.toString());

							serviceChart.setCompleted_count(Integer
									.parseInt(completedServices
											.get(completedSeviceKeyArray[i])
											.toString().trim()));

							System.out
									.println("1st IF : No Data For PendingService !!!!");

							if (pendingServices
									.containsKey(completedSeviceKeyArray[i])) {
								serviceChart
										.setPending_count(Integer
												.parseInt(pendingServices
														.get(completedSeviceKeyArray[i])
														.toString().trim()));
								System.out
										.println("2nd IF : No Data For PendingService !!!!");
							} else {
								serviceChart.setPending_count(Integer
										.parseInt("0"));
							}
							ServiceChartList.add(serviceChart);
							i++;
						}
					}
				} catch (Exception e) {
					System.out.println("pending < completed : " + e);
				}

			} else {
				try {
					int i = 0;
					int j = 0;
					ServiceChart serviceChart;
					while (pendingSeviceIterator.hasNext()) {
						serviceChart = new ServiceChart();
						pendingSeviceIterator.next();
						serviceChart.setDuration(pendingSeviceKeyArray[i]
								.toString());
						serviceChart.setPending_count(Integer
								.parseInt(pendingServices
										.get(pendingSeviceKeyArray[i])
										.toString().trim()));
						if (completedServices
								.containsKey(pendingSeviceKeyArray[i])) {
							serviceChart.setCompleted_count(Integer
									.parseInt(completedServices
											.get(pendingSeviceKeyArray[i])
											.toString().trim()));
						} else {
							serviceChart.setCompleted_count(Integer
									.parseInt("0"));
						}
						ServiceChartList.add(serviceChart);
						i++;
					}
				} catch (Exception ex) {
					System.out.println("Exception at : pending = actual : "
							+ ex);
				}
			}
		} catch (Exception ex) {
			System.out.println("Exception in getting Chart Pojo :" + ex);
		}
		System.out.println("Size of ServiceChartList : "
				+ ServiceChartList.size());
		return ServiceChartList;
	}

	/**
	 * Utility to close the given input stream.
	 * 
	 * @param inputStream
	 *            - input stream to be closed.
	 */
	private void closeInputStream(InputStream inputStream) {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException ioException) {
				log.error("IO Exception while closing the stream", ioException);
			}
		}
	}

	/**
	 * Gets the Top Search terms, will be displayed in report page. It contains
	 * Search term and count.
	 * 
	 * If searchReportList is empty or SQLException occurs while interacting
	 * with DB then error JASPER will be displayed.
	 * 
	 * @param reportFilter
	 *            - Has event details like search term.
	 * @return Byte Array of the event report generated.
	 */
	@Override
	// getTopSearchTopic
	public ByteArrayOutputStream getPendingReport(ReportFilter reportFilter) {
		log.debug("Entering Report Service to getPendingReport.");
		ByteArrayOutputStream reportByteArray = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			InputStream reportStream;
			List<PendingService> listOfPendingServices = null;

			if (("completeReport").equals(reportFilter.getFrom())) {
				listOfPendingServices = daoObject
						.getCompleteServicePendinglist(reportFilter.getToDate());
			} else {

				if (reportFilter.getExportTypeFilter() == ExportFilterValue.TALUK) {
					List talukList = queryList.getTaluk();

				} else if (reportFilter.getExportTypeFilter() == ExportFilterValue.DISTRICT) {
					List districtList = queryList.getDistricts();
				} else {
					listOfPendingServices = daoObject
							.getPendingServicesBySelectedMonth(reportFilter);
				}
			}
			if (listOfPendingServices.isEmpty()) {
				reportByteArray = getFailureReport("No Records Found");
			} else {

				if (reportFilter.getFormatType() == FormatType.PDF) {
					reportStream = this.getClass().getResourceAsStream(
							pendingReportExport);
					parameters.put("PendingReportdata",
							new JRBeanCollectionDataSource(
									listOfPendingServices));
					log.debug("Parameter Size : " + parameters.size());
					reportStream = this.getClass().getResourceAsStream(
							pendingReportExport);
					reportByteArray = getReportByteArray(reportStream,
							reportFilter, parameters);
					closeInputStream(reportStream);
				} else {
					reportStream = this.getClass().getResourceAsStream(
							pendingReport);
					parameters.put("PendingReportdata",
							new JRBeanCollectionDataSource(
									listOfPendingServices));
					log.debug("Parameter Size : " + parameters.size());
					reportStream = this.getClass().getResourceAsStream(
							pendingReport);
					reportByteArray = getReportByteArray(reportStream,
							reportFilter, parameters);
					closeInputStream(reportStream);
				}
			}
			log.debug("Existing getPendingReport service method.");
		} catch (Exception e) {
			log.error("SQLException occurred while calling from : " + e);
			// reportByteArray = getDBErrorReport(reportFilter,
			// ReportConstants.ERROR_MSG, parameters);
			log.info("Jasper with error message is displayed");
		}
		return reportByteArray;
	}

	/**
	 * Generates the failure report for the given failure message.
	 * 
	 * The report generated will be used in dash board main page, to announce
	 * the failures to users.
	 * 
	 * @param failureMessage
	 *            - Message to be displayed in report.
	 * @return Failure report with the given message.
	 */
	public ByteArrayOutputStream getFailureReport(String failureMessage) {
		log.info("Entering create usage trend service.");
		ByteArrayOutputStream reportByteArray = null;
		// Gets JASPER file for generating failure report as stream.
		InputStream failureReportStream = this.getClass().getResourceAsStream(
				failureReport);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("message", failureMessage);
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					failureReportStream, parameters, new JREmptyDataSource());
			reportByteArray = jasperUtil.jasperPrintToByteArray(jasperPrint,
					FormatType.HTML);
		} catch (JRException jasperException) {
			log.error("Excpetion while filling jasper report", jasperException);
		} finally {
			closeInputStream(failureReportStream);
		}
		log.info("Exiting create usage trend service.");
		return reportByteArray;
	}

	/**
	 * Get the Spares Details such as spare id and spare cost
	 * 
	 * @return Spares map.
	 */
	public LinkedHashMap<String, Float> getSparesDetails() {
		List<SoldSpares> spareDetails = null;

		spareDetails = daoObject.getSparesDetails();

		LinkedHashMap<String, Float> spareCostMap;
		spareCostMap = new LinkedHashMap<String, Float>();
		for (SoldSpares spareDetail : spareDetails) {
			spareCostMap.put(spareDetail.getSpareCode(), spareDetail.getCost());
		}
		return spareCostMap;

	}

	/**
	 * Gets the byte array for all the reports.
	 * 
	 * @param reportStream
	 *            - report Stream of the report.
	 * @param reportFilter
	 *            - Has event details like search term.
	 * @param parameters
	 *            - Report parameters.
	 * @return byte array of report.
	 */
	private ByteArrayOutputStream getReportByteArray(InputStream reportStream,
			ReportFilter reportFilter, Map<String, Object> parameters) {
		log.info("Entering into getReportByteArray service method.");
		ByteArrayOutputStream reportByteArray = null;
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, new JREmptyDataSource());
			reportByteArray = jasperUtil.jasperPrintToByteArray(jasperPrint,
					reportFilter.getFormatType());
			if (reportByteArray != null) {
				log.debug("ByteArray size : " + reportByteArray.size());
			} else {
				log.error("reportByteArray is NULL !!!!");
			}
		} catch (JRException jrException) {
			log.error("Jasper exception occured: ", jrException);
		} catch (Exception ex) {
			log.error("Exception at getReportByteArray : " + ex);
		}
		log.info("Existing into getReportByteArray service method.");
		return reportByteArray;
	}

	@Override
	public String addJobcardDetail(JobCardDetails jobcardDetail) {
		String message = null;
		try {
			message = daoObject.addJobcardDetail(jobcardDetail);
		} catch (Exception e) {
			log.debug("Exception ", e);
		}
		return message;
	}
	@Override
	public String addAssocSalaryDetail(AssociateSalaryDetails associateSalaryDetails) {
		String message = null;
		try {
			message = daoObject.insertAssocSalaryDetails(associateSalaryDetails);
		} catch (Exception e) {
			log.debug("Exception ", e);
		}
		return message;
	}
	@Override
	public String addEnquiryDetail(EnquiryDetails enquiryDetails) {
		String message = null;
		try {
			message = daoObject.insertEnquiryDetails(enquiryDetails);
		} catch (Exception e) {
			log.debug("Exception ", e);
		}
		return message;
	}	
	@Override
	public String addAssocSalaryData(SalaryDetails associateSalaryDetails) {
		String message = null;
		try {
			message = daoObject.insertAssocSalaryData(associateSalaryDetails);
		} catch (Exception e) {
			log.debug("Exception ", e);
		}
		return message;
	}	
	@Override
	public String updateAssocSalaryDetail(AssociateSalaryDetails associateSalaryDetails) {
		String message = null;
		try {
			message = daoObject.updateAssocSalaryDetails(associateSalaryDetails);
		} catch (Exception e) {
			log.debug("Exception ", e);
		}
		return message;
	}	
	@Override
	public String addFeedbackDetails(FeedbackDetails feedbackDetails) {
		String message = null;
		try {
			
			String serviceStartTime = dateUtil.getStringfromDateTimeForFeedBack(feedbackDetails.getServicestarttime());
			String serviceEndTime = dateUtil.getStringfromDateTimeForFeedBack(feedbackDetails.getServiceendtime());
			long timeTaken = dateUtil.getTimeDiff(feedbackDetails.getServicestarttime(), feedbackDetails.getServiceendtime());
			feedbackDetails.setServicestarttime(serviceStartTime);
			feedbackDetails.setServiceendtime(serviceEndTime);
			//long timeTaken = dateUtil.getTimeDiff(feedbackDetails.getServicestarttime(), feedbackDetails.getServiceendtime());
			feedbackDetails.setTimeSpentByEngineer(timeTaken);
			message = daoObject.addFeedbackDetails(feedbackDetails); 
			
		} catch (Exception e) {
			log.debug("Exception ", e);
		}
		return message;
	}
	@Override
	public int getNoOfDaysForCurrentMonth(){
		return dateUtil.getNoOfDaysInCurrentMonth();
	}
	/**
	 * Get the details for particular jobcard number.
	 * 
	 * @param jobcardDetail
	 *            - jobcardnumber.
	 */
	@Override
	public String getReportForParticularJobcard(JobCardDetails jobcardDetails) {
		String message = null;
		try {
			message = daoObject.getReportForParticularJobcard(jobcardDetails);
		} catch (Exception e) {
			log.debug("Exception ", e);
		}
		return message;
	}

	@Override
	public String updateJobCardDetails(JobCardDetails jobcardDetail) {
		String message = null;
		try {
			message = daoObject.updateJobcard(jobcardDetail);
		} catch (Exception e) {
			log.fatal(" Exception in update jobcard ... service layer " + e);
			return message;
		}
		return message;
	}

	@Override
	public List<EmployeeDetails> getEmployeeID() {
		List<EmployeeDetails> message = null;
		try {
			message = daoObject.getEmployeeID();
		} catch (Exception e) {
			log.fatal(" Exception in getEmployeeID ... service layer " + e);
			return message;
		}
		return message;
	} 
	@Override
	public List<String> getEmployeeIDString() {
		List<String> message = null;
		try {
			message = daoObject.getEmployeeIDString();
		} catch (Exception e) {
			log.fatal(" Exception in getEmployeeID ... service layer " + e);
			return message;
		}
		return message;
	} 	
	//
	@Override
	public List<DaDetails> getDADetails() {
		List<DaDetails> message = null;
		try {
			message = daoObject.getDADetails();
		} catch (Exception e) {
			log.fatal(" Exception in getDADetails ... service layer " + e);
			return message;
		}
		return message;
	}	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.races.service.RacesService#addCustomerDetails(org.races.model.
	 * CustomerDetails)
	 */
	@Override
	public String addCustomerDetails(CustomerDetails customerDetails) {
		String message = "Failure";
		try {
			if (daoObject != null) {
				System.out.println("DAO OBJECT IS NOT NULL");
				message = daoObject.addCustomerDetails(customerDetails);
			} else {
				log.fatal("DAO Object is NULL");
			}
		} catch (Exception exception) {
			log.fatal(" Exception in adding Customer ... service layer "
					+ exception);
		}
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.races.service.RacesService#updateCustomerDetails(org.races.model.
	 * CustomerDetails)
	 */
	@Override
	public String updateCustomerDetails(CustomerDetails customerDetails) {
		String message = null;
		try {
			message = daoObject.updateCustomerDetails(customerDetails);
		} catch (Exception exception) {
			log.fatal(" Exception in update Customer ... service layer "
					+ exception);
		}
		return message;
	}

	@Override
	public CustomerDetails getCustomerDetails(String chasisNumber,
			CustomerDetails customerDetails) {
		// String message = null;
		try {
			customerDetails = daoObject.getReportForParticularChasisNumber(
					chasisNumber, customerDetails);
		} catch (Exception e) {
			log.debug("Exception ", e);
		}
		return customerDetails;

	}
	@Override
	public List<SparesCountMetrics> getSparesCountMetrics(int noOfMonths) {
		List<SparesCountMetrics> datas = new ArrayList<SparesCountMetrics>();
		try {
			datas = daoObject.getSparesSalesMetricsDetails(noOfMonths);
		} catch (Exception e) {
			log.debug("Exception ", e);
		}
		return datas;

	}
	@Override
	public List<SparesSaleBySE> getSparesSaleBySE(int noOfMonths) {
		List<SparesSaleBySE> datas = new ArrayList<SparesSaleBySE>();
		try {
			datas = daoObject.getSparesSalesMetricsBySE(noOfMonths);
		} catch (Exception e) {
			log.debug("Exception ", e);
		}
		return datas;

	}
	@Override	
	public double getAssociateBaseSalary(String eid,String queryDate){
		double salary=0.0;
		try{
			salary = daoObject.getAssociateBaseSalary(eid,queryDate);
		}catch(Exception e) {
			log.debug("Exception ", e);
		}
		return salary;
	}
	@Override	
	public Map<Object,Object> getAddSalaryDateDetails(String queryDate){
		HashMap<Object,Object> dateDetails=new HashMap<Object,Object>();
		try{
			java.util.Date queryDateDetails = inputDateFormat.parse(queryDate);
			String lastDate = dateUtil.getLastDayOfQueryMonth(queryDateDetails);
			java.util.Date lastDateDetails = sqlDateFormat.parse(lastDate);
			//sqlDateFormat
			int noOfWorkingDays = 	getNoOfWorkingDays(sqlDateFormat.format(queryDateDetails),lastDate);
			int noOfDaysInQueryMonth = dateUtil.getNoOfDaysInQueryMonth(queryDateDetails);
			dateDetails.put("noOfWorkingDays", noOfWorkingDays);
			dateDetails.put("noOfDaysInQueryMonth", noOfDaysInQueryMonth);
			
		}catch(Exception e) {
			log.debug("Exception ", e);
		}
		return dateDetails;
	}	
	@Override
	public String getReportExportStatus(ReportFilter reportFilter) {
		// TODO Auto-generated method stub
		String message = "";
		Map<String, ArrayList<PendingService>> pendingServiceMap = new LinkedHashMap<String, ArrayList<PendingService>>();
		daoObject.getExportedStatus(reportFilter, pendingServiceMap);
		log.debug("pendingServiceMap -- " + pendingServiceMap.size());

		String folderName = ft.format((new Date()));
		log.debug("Folder Name : " + folderName);
		// String completePath = exportLocation;
		String completePath = "";
		if (reportFilter.getExportTypeFilter() == ExportFilterValue.DISTRICT) {
			completePath = ReportConstants.USER_HOME + districtwiseExportLocation;
			log.debug("CompletePath to export the files... : " + completePath);
			File exportDirectory = new File(completePath);
			if (!exportDirectory.exists()) {
				if (exportDirectory.mkdir()) {
					log.debug("District Folder created for Exporting the reports !!!");
				} else {
					log.debug("District Folder CANNOT BE created for Exporting the reports !!!");
				}
			}
			
		} else if (reportFilter.getExportTypeFilter() == ExportFilterValue.TALUK) {
			completePath = ReportConstants.USER_HOME + talukwiseExportLocation;
			log.debug("CompletePath to export the files... : " + completePath);
			File exportDirectory = new File(completePath);
			if (!exportDirectory.exists()) {
				if (exportDirectory.mkdir()) {
					log.debug("Taluk Folder created for Exporting the reports !!!");
				} else {
					log.debug("Taluk Folder CANNOT BE created for Exporting the reports !!!");
				}
			}
		}
		Set<String> keyList = pendingServiceMap.keySet();
		Iterator<String> filterIterator = keyList.iterator();
		InputStream reportStream;
		while (filterIterator.hasNext()) {
			String fileName = (String) filterIterator.next();
			List<PendingService> pendingServicePojo = pendingServiceMap
					.get(fileName);
			try {
				Map<String, Object> parameters = new HashMap<String, Object>();
				if (reportFilter.getFormatType() == FormatType.PDF) {
					reportStream = this.getClass().getResourceAsStream(
							pendingReportExport);
					log.fatal("For : " + fileName + " Size :"
							+ pendingServicePojo.size());
					parameters.put("PendingReportdata",
							new JRBeanCollectionDataSource(pendingServicePojo));
					log.debug("Parameter Size : " + parameters.size());
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							reportStream, parameters, new JREmptyDataSource());
					message = jasperUtil.exportFiles(completePath, fileName,
							FormatType.PDF, jasperPrint);
				} else {
					reportStream = this.getClass().getResourceAsStream(
							pendingReportExport);
					parameters.put("PendingReportdata",
							new JRBeanCollectionDataSource(pendingServicePojo));
					log.debug("Parameter Size : " + parameters.size());
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							reportStream, parameters, new JREmptyDataSource());
					message = jasperUtil.exportFiles(completePath, fileName,
							reportFilter.getFormatType(), jasperPrint);
				}
			} catch (Exception e) {
				log.debug("Exception in Service layer While Exporting : " + e);
			}
		}
		deleteDatalessFiles(new File(completePath));
		return message;
	}

	private void deleteDatalessFiles(File folderPath) {
		if (folderPath.isDirectory()) {
			String files[] = folderPath.list();
			for (String temp : files) {
				// construct the file structure
				File fileDelete = new File(folderPath, temp);
				if (fileDelete.length() == 0) {
					fileDelete.delete();
				}
			}
		}
	}

	@Override
	public String addSparesDetails(SparesDetails sparesDetails) {
		String message = "Failure";
		try {
			if (daoObject != null) {
				System.out.println("DAO OBJECT IS NOT NULL");
				message = daoObject.addSparesDetails(sparesDetails);
			} else {
				log.fatal("DAO Object is NULL");
			}
		} catch (Exception exception) {
			log.fatal(" Exception in adding Spares ... service layer "
					+ exception);
		}
		return message; 
	}

	@Override
	public SparesDetails getSparesDetails(String sparesCode,
			SparesDetails sparesDetails) {
		// String message = null;
		try {
			sparesDetails = daoObject.getReportForParticularSparesCode(
					sparesCode, sparesDetails);
		} catch (Exception e) {
			log.debug("Exception ", e);
		}
		return sparesDetails; 
	}
	@Override
	public EmployeeDetails getAssocDetails(String assocId,
			EmployeeDetails employeeDetails) {
		// String message = null;
		try {
			employeeDetails = daoObject.getReportForParticularAssocId(
					assocId, employeeDetails);
		} catch (Exception e) {
			log.debug("Exception ", e);
		}
		return employeeDetails; 
	}
	@Override
	public AssociateSalaryDetails getAssocSalaryDetails(String assocId,
			AssociateSalaryDetails associateSalaryDetails,String queryDate) {
		// String message = null;
		try {
			Date inputDate = inputDateFormat.parse(queryDate);
			associateSalaryDetails = daoObject.getSalaryDetailsForParticularAssocId(
					assocId, associateSalaryDetails,sqlDateFormat.format(inputDate));
		} catch (Exception e) {
			log.debug("Exception ", e);
		}
		return associateSalaryDetails; 
	}	 
	
	@Override
	public Map<String, Object> getFeedbackDetails(String jobCardNumber,
			FeedbackDetails employeeDetails,String uiCheck) {
		// String message = null;
		Map<String, Object> datas = new HashMap<String, Object>();
		try {
			 datas = daoObject.getFeedbackForParticularJobCard(
					jobCardNumber, employeeDetails,uiCheck);
		} catch (Exception e) {
			log.debug("Exception ", e);
		}
		return datas; 
	}
	@Override
	public Map<String, Object> getCustomerDetails(String query,String diffCheck,int year,int month) {
		// String message = null;
		Map<String, Object> datas = new HashMap<String, Object>();
		try { 
				 datas = daoObject.getCustomerDetails(query,diffCheck, year,month);
				 List<CustomerDetailsForExport> exportData = getExportCustomerData((List<CustomerDetails>) datas.get("dataList"));
				 datas.put("exportData",exportData);
				 datas.put("noOfCustomers", exportData.size());
				 //dataList
		} catch (Exception e) {
			log.debug("Exception ", e);
		}
		return datas; 
	}
	@Override
	public Map<String, Object> getAssociateSalaryDetails(String query,String diffCheck,int year,int month) {
		// String message = null;
		Map<String, Object> datas = new HashMap<String, Object>();
		try { 
			
				 datas = daoObject.getAssociateSalaryDetails(query,diffCheck, year,month);
				 List<AssociateSalaryDetails> assocSalDetailsList = new ArrayList<AssociateSalaryDetails>((List<AssociateSalaryDetails>)datas.get("dataList"));
				 datas.put("noOfRecords", assocSalDetailsList.size());
				 datas.put("toStoreTableJsonData", assocSalDetailsList.size());
				 
				 
				 //dataList
		} catch (Exception e) {
			log.debug("Exception ", e);
		}
		return datas; 
	}
	@Override
	public Map<String, Object> getAssociateCompleteSalaryDetails(String query) {
		// String message = null;
		Map<String, Object> datas = new HashMap<String, Object>();
		try { 
			
				 datas = daoObject.getConsolidatedSalaryDetailsByYear(query);
				 List<SalaryDetails> assocSalDetailsList = new ArrayList<SalaryDetails>((List<SalaryDetails>)datas.get("dataList"));
				 datas.put("noOfRecords", assocSalDetailsList.size());
				 datas.put("toStoreTableJsonData", assocSalDetailsList.size());
				 
				 
				 //dataList
		} catch (Exception e) {
			log.debug("Exception ", e);
		}
		return datas; 
	}	
	@Override
	public Map<String, Object> getAssociateLeaveDetails(String query,String diffCheck,int year,int month) {
		// String message = null;
		Map<String, Object> datas = new HashMap<String, Object>();
		try { 
				 datas = daoObject.getAssociateLeaveDetails(query,diffCheck, year,month);
				 datas.put("noOfRecords", ((List<AssociateSalaryDetails>)datas.get("dataList")).size());
				 //dataList
		} catch (Exception e) {
			log.debug("Exception ", e);
		}
		return datas; 
	}		
	private List<CustomerDetailsForExport> getExportCustomerData(List<CustomerDetails> customerDetails){
		List<CustomerDetailsForExport> customerDataForExport = new ArrayList<CustomerDetailsForExport>();
		for(CustomerDetails cd : customerDetails){
			CustomerDetailsForExport cef = new CustomerDetailsForExport();
			cef.setContactNumber(cd.getContactNumber());
			cef.setCustomerDetails(cd.getCustomerDetails());
			cef.setCustomerId(cd.getCustomerId());
			cef.setDateOFsale(cd.getDateOFsale());
			customerDataForExport.add(cef);
		}
		return customerDataForExport;
	}
	
	@Override
	public ValidityCheck isJobcardAvailable(String jobCardNumber) {
		// String message = null;
		String datas="Sorry, No/Multiple Such JOBCARD";
		int count=0;
		ValidityCheck validityCheck=null;
		try {
			validityCheck = daoObject.getNoOfJobcard(jobCardNumber);
			
			if(validityCheck==null){
				validityCheck = new ValidityCheck();
				validityCheck.setMessage(datas);
				validityCheck.setValid(false);
				return validityCheck;
			}else{
				datas="Valid JOBCARD"; 
				validityCheck.setMessage(datas);
				validityCheck.setValid(true);
			}
			 
		} catch (Exception e) {
			log.debug("Exception ", e);
		}
		return validityCheck; 
	}
	@Override
	public String updateSparesDetails(SparesDetails sparesDetails) {
		String message = null;
		try {
			message = daoObject.updateSparesDetails(sparesDetails);
		} catch (Exception exception) {
			log.fatal(" Exception in update Spares ... service layer "
					+ exception);
		}
		return message;
	}
	@Override
	public String updateFeedbackDetails(FeedbackDetails feedbackDetails) {
		String message = null;
		try {
			
			String serviceStartTime = dateUtil.getStringfromDateTimeForFeedBack(feedbackDetails.getServicestarttime());
			String serviceEndTime = dateUtil.getStringfromDateTimeForFeedBack(feedbackDetails.getServiceendtime());
			long timeTaken = dateUtil.getTimeDiff(feedbackDetails.getServicestarttime(), feedbackDetails.getServiceendtime());
			feedbackDetails.setServicestarttime(serviceStartTime);
			feedbackDetails.setServiceendtime(serviceEndTime);
			//long timeTaken = dateUtil.getTimeDiff(feedbackDetails.getServicestarttime(), feedbackDetails.getServiceendtime());
			feedbackDetails.setTimeSpentByEngineer(timeTaken);
			
			message = daoObject.updateFeedbackDetails(feedbackDetails);
		} catch (Exception exception) {
			log.fatal(" Exception in update Feedback ... service layer "
					+ exception);
		}
		return message;
	}
	@Override
	public String addAssocDetails(EmployeeDetails assocDetails) {
		String message = "Failure";
		try {
			if (daoObject != null) {
				System.out.println("DAO OBJECT IS NOT NULL");
				message = daoObject.addAssocDetails(assocDetails);
			} else {
				log.fatal("DAO Object is NULL");
			}
		} catch (Exception exception) {
			log.fatal(" Exception in adding Assoc ... service layer "
					+ exception);
			exception.printStackTrace();
		}
		return message; 
	 	}
	@Override
	public String addOfferSheetDetails(OfferSheetDetails offerSheetDetails) {
		String message = "Failure";
		try {
			if (daoObject != null) {
				System.out.println("DAO OBJECT IS NOT NULL");
				message = daoObject.insertOfferSheetDetails(offerSheetDetails);
			} else {
				log.fatal("DAO Object is NULL");
			}
		} catch (Exception exception) {
			log.fatal(" Exception in adding Offer Sheet Details ... service layer "
					+ exception);
			exception.printStackTrace();
		}
		return message; 
	 	}

	
	@Override	
	public String addDaData(DaDetails daDetails){
		String message = "Failure";
		try {
			if (daoObject != null) {
				System.out.println("DAO OBJECT IS NOT NULL");
				message = daoObject.addDaDetails(daDetails);
			} else {
				log.fatal("DAO Object is NULL");
			}
		} catch (Exception exception) {
			log.fatal(" Exception in adding DA ... service layer "
					+ exception);
		}
		return message;
	}	

	@Override
	public String updateAssocDetails(EmployeeDetails assocDetails) {
		String message = null;
		try {
			message = daoObject.updateAssocDetails(assocDetails);
		} catch (Exception exception) {
			log.fatal(" Exception in update Assoc ... service layer "
					+ exception);
		}
		return message;
	}
	@Override
	public String updateAssocSalaryDetails(SalaryDetails assocDetails) {
		String message = null;
		try {
			message = daoObject.updateAssocSalaryDetails(assocDetails);
		} catch (Exception exception) {
			log.fatal(" Exception in update Assoc Salary ... service layer "
					+ exception);
		}
		return message;
	}	
	@Override
	public SalaryDetails fetchAssocSalaryData(String eid,String queryDate) {
		SalaryDetails sd = new SalaryDetails();
		try {
			sd = daoObject.fetchAssocSalaryData(eid,queryDate);
		} catch (Exception exception) {
			log.fatal(" Exception in update Assoc Salary ... service layer "
					+ exception);
		}
		return sd;
	}		
	@Override
	public List<Object> getVehicleSoldYears() {
		List<Object> message = null;
		try {
			message = daoObject.getVehicleSaleYears();
		} catch (Exception exception) {
			log.fatal(" Exception in getVehicleSoldYears ... service layer "
					+ exception);
		}
		return message;
	}
	@Override
	public List<Object> getSalaryYears() {
		List<Object> message = null;
		try {
			message = daoObject.getSalaryYears();
		} catch (Exception exception) {
			log.fatal(" Exception in getSalaryYears ... service layer "
					+ exception);
		}
		return message;
	}
	@Override
	public List<Object> getSalaryDetailsYears(){
		List<Object> message = null;
		try {
			message = daoObject.getSalaryDetailsYears();
		} catch (Exception exception) {
			log.fatal(" Exception in getSalaryDetailsYears ... service layer "
					+ exception);
		}
		return message;
	}
	@Override
	public FeedbackDetails getFeedbackDetailsForId(int feedBackId,
			FeedbackDetails feedbackDetails) {
		try {
			
			feedbackDetails = daoObject.getFeedbackForParticularId(feedBackId,feedbackDetails);
			String datChange = dateUtil.getDateforUI(feedbackDetails.getDateOfFeedback());
			String startTime = dateUtil.convertDateTimeToUI(feedbackDetails.getServicestarttime());
			String endTime = dateUtil.convertDateTimeToUI(feedbackDetails.getServiceendtime());
			feedbackDetails.setServicestarttime(startTime);
			feedbackDetails.setServiceendtime(endTime);
			feedbackDetails.setDateOfFeedback(datChange);
			// TODO : reverse the starttime and the endtime as in the UI.
		} catch (Exception exception) {
			log.fatal(" Exception in fetch feedback ... service layer "
					+ exception);
		}
		
		return feedbackDetails;
	}
	@Override
	public int getNoOfWorkingDaysForCurrentMonth(){
		String startDate=dateUtil.getFirstDayOfMonth();
		String endDate=dateUtil.getLastDayOfMonth();
		int workingDays = getNoOfWorkingDays(startDate,endDate);
		log.info("No Of Working Days Between : startDate : "+startDate+" endDate : "+endDate+" is : "+workingDays);
		System.out.println("No Of Working Days Between : startDate : "+startDate+" endDate : "+endDate+" is : "+workingDays);
		return workingDays; 
	}
	@Override
	public String getCurrentMonthYear() {
		// TODO Auto-generated method stub
		String currentMonthYear="NA";
		try{
			 currentMonthYear=dateUtil.getCurrentMonthYear();
		}catch (Exception exception) {
			log.fatal(" Exception in getCurrentMonthYear ... service layer "
					+ exception);
		} 
		return currentMonthYear;
	}	
	@Override
	public int getNoOfPublicHolidaysForCurrentMonth(){
		int noOfPublicHolidays=0;
		String startDate=dateUtil.getFirstDayOfMonth();
		String endDate=dateUtil.getLastDayOfMonth();
		try {
				 noOfPublicHolidays = daoObject.getNoOfPublicHolidays(startDate, endDate);
			}catch (Exception exception) {
				log.fatal(" Exception in getNoOfPublicHolidaysForCurrentMonth ... service layer "
						+ exception);
			} 
			return noOfPublicHolidays;
	}
	@Override
	public int getNoOfPublicHolidaysForSpecificMonth(String startDate,String endDate){
		int noOfPublicHolidays=0; 
		try {
				 noOfPublicHolidays = daoObject.getNoOfPublicHolidays(startDate, endDate);
			}catch (Exception exception) {
				log.fatal(" Exception in getNoOfPublicHolidaysForCurrentMonth ... service layer "
						+ exception);
			} 
			return noOfPublicHolidays;
	}	
	@Override
	public int getNoOfWorkingDays(String startDate,String endDate) {
		int noOfWorkingDays =0;
		try {
			int noOfPublicHolidays = getNoOfPublicHolidaysForSpecificMonth(startDate,endDate); 
			
			noOfWorkingDays = dateUtil.getWorkingDaysBetweenTwoDates(sqlDateFormat.parse(startDate), sqlDateFormat.parse(endDate), noOfPublicHolidays);
		} catch (Exception exception) {
			log.fatal(" Exception in getNoOfWorkingDays ... service layer "
					+ exception);
		} 
		return noOfWorkingDays;
	}

		

}
 class SizeComparator implements Comparator<ServiceUsageTrendChart> {
	//getMilliSecFromDate("01-"+this.getMonthyear()) < getMilliSecFromDate("01-"+o.getMonthyear())
	@Override
	public int compare(ServiceUsageTrendChart tv1, ServiceUsageTrendChart tv2) {
		long tv1Size =getMilliSecFromDate("01-"+tv1.getMonthyear());
		long tv2Size = getMilliSecFromDate("01-"+tv2.getMonthyear());
 
		if (tv1Size > tv2Size) {
			return 1;
		} else if (tv1Size < tv2Size) {
			return -1;
		} else {
			return 0;
		}
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

	@Override
	public Comparator<ServiceUsageTrendChart> reversed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<ServiceUsageTrendChart> thenComparing(
			Comparator<? super ServiceUsageTrendChart> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> Comparator<ServiceUsageTrendChart> thenComparing(
			Function<? super ServiceUsageTrendChart, ? extends U> keyExtractor,
			Comparator<? super U> keyComparator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U extends Comparable<? super U>> Comparator<ServiceUsageTrendChart> thenComparing(
			Function<? super ServiceUsageTrendChart, ? extends U> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<ServiceUsageTrendChart> thenComparingInt(
			ToIntFunction<? super ServiceUsageTrendChart> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<ServiceUsageTrendChart> thenComparingLong(
			ToLongFunction<? super ServiceUsageTrendChart> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<ServiceUsageTrendChart> thenComparingDouble(
			ToDoubleFunction<? super ServiceUsageTrendChart> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	} 
}