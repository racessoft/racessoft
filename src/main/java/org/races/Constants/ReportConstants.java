package org.races.Constants;

public class ReportConstants {
 
    /**
     * Object name which holds current system date to be displayed in dash board
     * main page.
     */
    public static final String CURRENT_DATE = "currentDate";

 
    /**
     * Object name which holds from page to be displayed in alum page.
     */
    public static final String FROM = "from";

    /**
     * View name for ALUM Dash board main page.
     */
    public static final String DASHBOARD_VIEW = "dashboard";
    
    /**
     * spare cost detail object.
     */
    public static final String SPARE_DETAIL = "spareCostDetails";

    /**
     * Date format to be displayed in alum metrics report page.
     */
    public static final String DATE_FORMAT_WITH_YEAR = "MMM dd, yyyy";

    /**
     * Duration interval to be displayed in alum metrics report.
     */
    public static final String DURATION_INTERVAL = "durationInterval";

    /**
     * Print and export page does not have link option.
     */
    public static final String EXPORT_REPORT = "forExport";
    
    /**
     * Message parameter for JASPER print.
     */
    public static final String MSG_PARAM = "message";
    
    /**
     * Print and export page does not have link option.
     */
    public static final String MONTHLY_REPORT = "monthlyReport";
    
    /**
     * from navigation link option.
     */
    public static final String NAVIGATE_LINK = "leftPane";
    
    /**
     * Print and export page does not have link option.
     */
    public static final String DURATION_REPORT = "durationReport";
    
    /**
     * Add job card report page option.
     */
    public static final String ADD_JOBCARD_REPORT ="addJobCardReport";
    /**
     * Add job card report page option.
     */
    public static final String ADD_SALARY_DETAILS ="addassocsalarydetails";
    /**
     * Add job card report page option.
     */
    public static final String ADD_SALARY_DATA ="addassocsalarydata";  
    /**
     * Add job card report page option.
     */
    public static final String ADD_DA_DATA ="addDAdata";      
    
    /**
     * Update job card report page option.
     */
    public static final String UPDATE_JOBCARD_REPORT ="updateJobCardReport";
    /**
     * Update Assoc Salary page option.
     */
    public static final String UPDATE_ASSOC_SALARY ="updateassocsalarydetails";  
    /**
     * Update Assoc Salary page option.
     */
    public static final String UPDATE_ASSOC_SALARY_DATA ="retreiveassocsalarydata"; 
    /**
     * Update Assoc Salary page option.
     */
    public static final String UPDATE_ASSOC_SALARY_DETAILS ="retreiveassocsalarydetails"; 
    
    
    
    /**
     * Update Assoc Salary page option.
     */
    public static final String UPDATE_DA_DATA ="updateDAdetails";
    /**
     * Add FeedBack report page option.
     */
    public static final String ADD_FEEDBACK_REPORT ="addFeedBackReport";
    
    /**
     * Update FeedBack report page option.
     */
    public static final String UPDATE_FEEDBACK_REPORT ="updateFeedBackReport";

    /**
     * Add job card report page option.
     */
    public static final String ADD_CUSTOMER ="addCustomer";
    
    /**
     * Update job card report page option.
     */
    public static final String UPDATE_CUSTOMER ="updateCustomer";
    /**
     * Update job card report page option.
     */
    public static final String ADD_OFFER_SHEET ="addoffersheetdetails";
    
    /**
     * Add job card report page option.
     */
    public static final String ADD_SPARES ="addSpares";
    
    /**
     * Update job card report page option.
     */
    public static final String UPDATE_SPARES ="updateSpares";  

    /**
     * Add job card report page option.
     */
    public static final String ADD_ASSOC ="addAssoc";
    
    /**
     * Update job card report page option.
     */
    public static final String UPDATE_ASSOC ="updateAssoc";  
    

    /**
     * Run reports have link option.
     */
    public static final String LINK = "link";

    /**
     * It's a from view for Dash board page.
     */
    public static final String DASHBOARD = "dashboard";

    /**
     * Type of the image used.
     */
    public static final String IMAGE_TYPE = "image/png";

    /**
     * Image URI for JASPER fill manager, to name the generated image.
     */
    public static final String IMAGE_URI = "image?image=";

    /**
     * Format name of the image used.
     */
    public static final String FORMAT_TYPE = "png";

    
    
    /**
     * View name for Articles and Research Area page.
     */
    public static final String EXCEPTION_PAGE = "exceptionpage";

    /**
     * View name for Articles and Research Area page.
     */
    public static final String ERROR_MESSAGE = "errorMessage";
 
    /**
     * Object name which holds HTML String from JASPER report.
     */
    public static final String JASPER_STRING = "htmlString";
    /**
     * View name for ALUM Metrics page
     */
    public static final String REPORT_ALUM_METRICS = "usagemetrics";

    /**
     * Character Set type for byte array.
     */
    public static final String CHAR_SET = "UTF-8";

    /**
     * Content type for HTML format.
     */
    public static final String HTML_CONTENT_TYPE = "application/html";

    /**
     * Content type for PDF format.
     */
    public static final String PDF_CONTENT_TYPE = "application/pdf";

    /**
     * Content type for CSV format.
     */
    public static final String CSV_CONTENT_TYPE = "application/msexcel";

    /**
     * Header information of exporting file.
     */
    public static final String HEADER_INFO = "Content-disposition";

    /**
     * Header information for PDF export file.
     */
    public static final String PDF_HEADER_INFO = "attachment; filename=report.pdf";

    /**
     * Header information for HTML export file.
     */
    public static final String HTML_HEADER_INFO = "attachment; filename=report.html";

    /**
     * Header information for CSV export file.
     */
    public static final String CSV_HEADER_INFO = "attachment; filename=report.csv";

    /**
     * Header for exporting usage trend chart into PDF. Export type as
     * attachment and the name of the PDF is specified here.
     */
    public static final String USAGE_TREND_HEADER = "attachment; filename=Report.pdf";

    /**
     * Header for exporting usage snapshot into PDF. Export type as attachment
     * and the name of the PDF is specified here.
     */
    public static final String USAGE_SNAPSHOT_HEADER = "attachment; filename=Report.pdf";

    /**
     * CSV exporter parameter for JASPER print.
     */
    public static final String CSV_EXPORTER_PARAM = "csvfile/file.csv";

    
    /**
     * Stores current users home directory. This aids us to find every users
     * home directory irrespective to operating system to create a storage
     * temporary directory.
     */
    public static final String USER_HOME = System.getProperty("user.home");

 
    /**
     * CSS header inclusion for reports page.
     */
    public static final String CSS_HEADER = "<html><head><link rel='stylesheet' type='text/css' href='";

    /**
     * HTML header inclusion for reports page.
     */
    public static final String HTML_HEADER = "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\" />";

    /**
     * CSS footer inclusion for reports page.
     */
    public static final String CSS_FOOTER = "/css/export.css'/>";

    /**
     * Body inclusion for reports page.
     */
    public static final String BODY_HEADER = "</html><body>";

    /**
     * HTML footer inclusion for reports page.
     */
    public static final String HTML_FOOTER = "</body></html>";

    /**
     * Table data source parameter for JASPER report.
     */
    public static final String TABLE_DATASOURCE_PARAM = "TableDataSource";
    
    /**
     * Hyphen for date format.
     */
    public static final String HYPHEN = " - ";

    /**
     * Comma used to separate the article id's.
     */
    public static final String COMMA = ",";

    /**
     * Colon to separate the collection from article id.
     */
    public static final String COLON = ":";

 }
