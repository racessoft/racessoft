package org.races.service;

import java.io.ByteArrayOutputStream; 
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.races.model.AssociateSalaryDetails;
import org.races.model.CustomerDetails;
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
import org.races.model.SparesCountMetrics;
import org.races.model.SparesDetails;
import org.races.model.SparesSaleBySE;
import org.races.model.ValidityCheck;
import org.races.util.FormatType;

public interface RacesService {

	public String getAuthenticationValue(String userName,String password);
	public String getForgotPassword(String userName);
	public String sendMail(String email_id,String password);
	ByteArrayOutputStream createUsageTrend(FormatType formatType);
	ByteArrayOutputStream getFailureReport(String failureMessage);
 	ByteArrayOutputStream getPendingReport(ReportFilter reportFilter);
 	public String getReportExportStatus(ReportFilter reportFilter);
 	public List<ServiceChart> getServiceChartData();
 	
    /**
     * Get the Spares Details such as spare id and spare cost
     *  
     * @return Spares map.
     */
    LinkedHashMap<String, Float> getSparesDetails();
    String addJobcardDetail(JobCardDetails jobcardDetail);
    String updateJobCardDetails(JobCardDetails jobcardDetail);
    
    /**
     * Get the details for particular jobcard number.
     * @param jobcardDetail - jobcardnumber.
     */
    String getReportForParticularJobcard(JobCardDetails jobcardDetails);
	List<EmployeeDetails> getEmployeeID();
	
	String addCustomerDetails(CustomerDetails customerDetails);
	String updateCustomerDetails(CustomerDetails customerDetails);
	CustomerDetails getCustomerDetails(String chasisNumber, CustomerDetails customerDetails);
	SparesDetails getSparesDetails(String chasisNumber, SparesDetails sparesDetails);
	public List<SparesCountMetrics> getSparesCountMetrics(int noOfMonths);
	public List<SparesSaleBySE> getSparesSaleBySE(int noOfMonths);
	String addSparesDetails(SparesDetails sparesDetails);
	public String updateSparesDetails(SparesDetails sparesDetails);
	String addAssocDetails(EmployeeDetails assocDetails);
	public String updateAssocDetails(EmployeeDetails assocDetails);
	public EmployeeDetails getAssocDetails(String assocId,
			EmployeeDetails employeeDetails);
	public String updateFeedbackDetails(FeedbackDetails feedbackDetails);
	public Map<String, Object> getFeedbackDetails(String jobCardNumber,
			FeedbackDetails employeeDetails,String uiCheck) ;
	public String addFeedbackDetails(FeedbackDetails feedbackDetails) ;
	public FeedbackDetails getFeedbackDetailsForId(int feedBackId,
			FeedbackDetails employeeDetails) ; 
	public List<ServiceUsageTrendChart> getServiceUsageChartData();
	public ValidityCheck isJobcardAvailable(String jobCardNumber);
	public List<Object> getVehicleSoldYears();
	public Map<String, Object> getCustomerDetails(String query,String diffCheck,int year,int month);
	public int getNoOfWorkingDays(String startDate,String endDate);
	public List<DaDetails> getDADetails();
	public double getAssociateBaseSalary(String eid,String queryDate);
	public int getNoOfWorkingDaysForCurrentMonth();
	public int getNoOfPublicHolidaysForCurrentMonth();
	public String getCurrentMonthYear();
	public String addAssocSalaryDetail(AssociateSalaryDetails associateSalaryDetails) ;
	public int getNoOfDaysForCurrentMonth();
	public AssociateSalaryDetails getAssocSalaryDetails(String assocId,AssociateSalaryDetails associateSalaryDetails,String queryDate);
	public String updateAssocSalaryDetail(AssociateSalaryDetails associateSalaryDetails) ;
	public List<Object> getSalaryYears();
	public Map<String, Object> getAssociateSalaryDetails(String query,String diffCheck,int year,int month);
	public Map<String, Object> getAssociateLeaveDetails(String query,String diffCheck,int year,int month); 
	public Map<Object,Object> getAddSalaryDateDetails(String queryDate);
	public String addAssocSalaryData(SalaryDetails associateSalaryDetails);
	public String addDaData(DaDetails daDetails);
	
	public List<String> getEmployeeIDString();
	public List<Object> getSalaryDetailsYears();
	public Map<String, Object> getAssociateCompleteSalaryDetails(String query) ;
	public String updateAssocSalaryDetails(SalaryDetails assocDetails);
	public SalaryDetails fetchAssocSalaryData(String eid,String queryDate);
	public String addEnquiryDetail(EnquiryDetails enquiryDetails);
	int getNoOfPublicHolidaysForSpecificMonth(String startDate,String endDate);
	public String addOfferSheetDetails(OfferSheetDetails offerSheetDetails);
}
