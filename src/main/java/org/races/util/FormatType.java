/**
 * 
 */
package org.races.util;

/**
 * @author sashikumarshanmugam
 *
 */
public enum FormatType {
	


    /**
     * successful completion of job
     */
    PDF("PDF"),

    /**
     * failure of job
     */
    HTML("HTML"),

    /**
     * Unable to find job status
     */
    CSV("CSV"),

    /**
     * Byte array format.
     */
    IMAGE("IMAGE");

    /**
     * status of the job
     */
    private String formatType;

    /**
     * Constructor overloaded with JobStatusType argument
     * 
     * @param jobStatus
     *            - Name of the Job status
     * 
     */
    private FormatType(String formatType) {
        this.formatType = formatType;
    }

    /**
     * Used to get status of the job
     * 
     * @return jobStatus - jobStatus
     */
    public String getName() {
        return formatType;
    }

}
