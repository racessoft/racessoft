package org.races.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.races.model.AssociateDaDetails;
import org.races.model.AssociateSalaryDetails;
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
import org.races.model.ServiceActual;
import org.races.model.ServiceUsageTrendChart;
import org.races.model.SoldSpares;
import org.races.model.SparesCountMetrics;
import org.races.model.SparesDetails;
import org.races.model.SparesSaleBySE;
import org.races.model.UserDetails;
import org.races.model.CustomerDetails;
import org.races.model.ValidityCheck;
import org.races.model.serviceDetails; 

/**
 * @author sashikumarshanmugam
 *
 */
/**
 * @author sashikumarshanmugam
 * 
 */
public interface RacesDao {

	public UserDetails getLoginDetails(String userName, String password);

	public UserDetails getEmailId(String userName);

	public void insertData(Object[] objects);

	public void insertSpareDetails(Object[] obj);

	public void insertServiceData(Object[] objects);

	public void insertCustomerData(List<CustomerDetails> listOfCustomers);

	public List<ServiceActual> getServiceList(String serviceCutoff);

	public List<String> getChasisNoList(String serviceCutoff);

	public Object getServiceDetailByName(String serviceName,
			final String chasisNumber);

	public String getCustomerDetails_byChasisNo(String chasisNumber);

	public Object getCustomerDetails(String ChasisNo);

	public List<serviceDetails> getServiceDetails(String ChasisNo);

	public List<serviceDetails> getServiceDetailsByName(String ChasisNo,
			String service);

	public Object getServiceDetail(String ChasisNo);

	public List<PendingService> getFirstServicePendinglist(int fromMonth,
			int toMonth, int fromYear, int toYear);

	public List<PendingService> getSecondServicePendinglist(int fromMonth,
			int toMonth, int fromYear, int toYear);

	public List<PendingService> getThirdServicePendinglist(int fromMonth,
			int toMonth, int fromYear, int toYear);

	public List<PendingService> getFourthServicePendinglist(int fromMonth,
			int toMonth, int fromYear, int toYear);

	public List<PendingService> getFifthServicePendinglist(int fromMonth,
			int toMonth, int fromYear, int toYear);

	public List<PendingService> getSixthServicePendinglist(int fromMonth,
			int toMonth, int fromYear, int toYear);

	public List<PendingService> getSeventhServicePendinglist(int fromMonth,
			int toMonth, int fromYear, int toYear);

	public List<PendingService> getEigthServicePendinglist(int fromMonth,
			int toMonth, int fromYear, int toYear);

	public Object[] getDataForChart();

	public List<PendingService> getPendingServicesBySelectedMonth(
			ReportFilter reportFilter);

	public List<EmployeeDetails> getEmployeeID();
	public List<org.races.model.DaDetails> getDADetails();

	/**
	 * Gets the List of spare code and cost available in races.
	 * 
	 * @return List of Spare code and costs available in races.
	 */
	List<SoldSpares> getSparesDetails();

	/**
	 * Add the details of job card
	 * 
	 * @param jobcardDetails
	 *            - like jobcard number, chasisnumber. etc.,
	 * @return message.
	 */
	String addJobcardDetail(JobCardDetails jobcardDetails);

	/**
	 * Get the details for particular jobcard number.
	 * 
	 * @param jobcardDetail
	 *            - jobcardnumber.
	 */
	String getReportForParticularJobcard(JobCardDetails jobcardDetails);

	/**
	 * Update the details for particular jobcard number.
	 * 
	 * @param jobcardDetail
	 *            - jobcardnumber.
	 */
	String updateJobcard(JobCardDetails jobcardDetails);

	Boolean deleteSparesForJobcard(JobCardDetails jobcardDetails);

	/**
	 * Fetches the List of pending services ( <= given date).
	 * 
	 * @param jobcardDetail
	 *            - jobcardnumber.
	 */
	List<PendingService> getCompleteServicePendinglist(String choosenDate);

	/**
	 * Method to add New Customer
	 * 
	 * @param customerDetails
	 * @return String
	 */
	public String addCustomerDetails(CustomerDetails customerDetails);

	/**
	 * Method to update the details of the Customer.
	 * 
	 * @param customerDetails
	 * @return String
	 */
	public String updateCustomerDetails(CustomerDetails customerDetails);

	CustomerDetails getReportForParticularChasisNumber(String chasisNumber,
			CustomerDetails customerDetails);

	/**
	 * Method to return the details of the pending service based on the filter
	 * choosen.
	 * 
	 * @param reportFilter
	 * @return Map<String,List<PendingService>> with district/taluk as key and
	 *         corresponding list of pending services.
	 */
	public Map<String, ArrayList<PendingService>> getExportedStatus(
			ReportFilter reportFilter,Map<String, ArrayList<PendingService>> filterBasePojos);
	
	public List<SparesCountMetrics> getSparesSalesMetricsDetails(int noOfMonths);
	
	public List<SparesSaleBySE> getSparesSalesMetricsBySE(int noOfMonths);
	
	/**
	 * Method to add New Spares
	 * 
	 * @param customerDetails
	 * @return String
	 */
	public String addSparesDetails(SparesDetails sparesDetails);
	
	public String updateSparesDetails(SparesDetails sparesDetails);
	
	public SparesDetails getReportForParticularSparesCode(String sparesCode, final SparesDetails sparesDetails) ;
	
	public String addAssocDetails(EmployeeDetails assocDetails);
	
	public String updateAssocDetails(EmployeeDetails assocDetails);
	public EmployeeDetails getReportForParticularAssocId(String sparesCode,
			final EmployeeDetails assocDetails) ;
	public String updateFeedbackDetails(FeedbackDetails feedbackDetails) ;
	public Map<String, Object> getFeedbackForParticularJobCard(String jobCardNumber,
			final FeedbackDetails feedbackDetails,String uiCheck);
	public String addFeedbackDetails(FeedbackDetails feedbackDetails);
	public FeedbackDetails getFeedbackForParticularId(int feedbackId,
			final FeedbackDetails feedbackDetails);
	public List<ServiceUsageTrendChart> getServiceUsageChartData() ;
	public ValidityCheck getNoOfJobcard(String jobCardNumber) ;
	public List<Object> getVehicleSaleYears();
	public Map<String, Object> getCustomerDetails(String queryName,final String diffCheck,final Integer year,final Integer month) ;
	public int getNoOfPublicHolidays(String startDate, String endDate);
	public double getAssociateBaseSalary(String eid,String queryDate);
	public String insertAssocSalaryDetails(AssociateSalaryDetails associateSalaryDetails);
	public AssociateSalaryDetails getSalaryDetailsForParticularAssocId(final String eId,final AssociateSalaryDetails associateSalaryDetails,String queryDate);
	public String updateAssocSalaryDetails(final AssociateSalaryDetails associateSalaryDetails);
	public List<Object> getSalaryYears();
	public Map<String, Object> getAssociateSalaryDetails(String queryName,final String diffCheck,final Integer year,final Integer month);
	public Map<String, Object> getAssociateLeaveDetails(String queryName,final String diffCheck,final Integer year,final Integer month);
	public List<AssociateDaDetails> getDaStringForAssociate(String associateId,String salaryDate);
	public String insertAssocSalaryData(final SalaryDetails associateSalaryDetails);
	public List<String> getEmployeeIDString();
	public String addDaDetails(DaDetails daDetails);
	public List<AssociateDaDetails> getConsolidatedAssociateDADetails(String eId,String fetchDate); 
	public Map<String, Object> getConsolidatedSalaryDetailsByYear(String fetchYear);
	public List<Object> getSalaryDetailsYears();
	public String updateAssocSalaryDetails(SalaryDetails assocDetails);
	public SalaryDetails fetchAssocSalaryData(final String eid,final String queryDate);
	public String insertEnquiryDetails(EnquiryDetails enquiryDetails);
	public String insertOfferSheetDetails(final OfferSheetDetails offerSheetDetails);
}
