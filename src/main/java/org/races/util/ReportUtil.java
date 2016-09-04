package org.races.util;

/**
 * Report util class contains some basic operations.
 * 
 */

public class ReportUtil {

	/**
	 * It will return true if the given string is not empty and not null.
	 * 
	 * @param value
	 *            - value
	 * @return true or false.
	 */
	public boolean isNotNullNotEmpty(String value) {
		boolean test = false;
		if ( !"".equals(value) && value != null) {
			test = true;
		}
		return test;
	}
}
