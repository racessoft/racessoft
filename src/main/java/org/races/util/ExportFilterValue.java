/**
 * 
 */
package org.races.util;

/**
 * @author sashikumars
 *
 */
public enum ExportFilterValue {

    /**
     * For Taluk
     */
    TALUK("TALUK"),

    /**
     * failure of job
     */
    DISTRICT("DISTRICT"),

    /**
     * Unable to find job status
     */
    DEFAULT("DEFAULT");
    
    /**
     * status of the job
     */
    private String filterType;

    /**
     * Constructor overloaded with JobStatusType argument
     * 
     * @param jobStatus
     *            - Name of the Job status
     * 
     */
    private ExportFilterValue(String filterType) {
        this.filterType = filterType;
    }

    /**
     * Used to get status of the job
     * 
     * @return jobStatus - jobStatus
     */
    public String getName() {
        return filterType;
    }
}
