package org.races.image;

public class BlankRemover
{
	/**
	 * Remove leading whitespace
	 * 
	 * @param p_sSource
	 * @return 
	 */
	public static String ltrim(String p_sSource)
	{
		return p_sSource.replaceAll("^\\s+","");
	}

	/**
	 * Remove trailing whitespace
	 * 
	 * @param p_sSource
	 * @return 
	 */
	public static String rtrim(String p_sSource)
	{
		return p_sSource.replaceAll("\\s+$","");
	}

	/**
	 * Replace multiple whitespaces between words with single blank
	 * 
	 * @param p_sSource
	 * @return 
	 */
	public static String itrim(String p_sSource)
	{
		return p_sSource.replaceAll("\\b\\s{2,}\\b"," ");
	}

	/**
	 * Remove all superfluous whitespaces in source string
	 * 
	 * @param p_sSource
	 * @return 
	 */
	public static String trim(String p_sSource)
	{
		return itrim(ltrim(rtrim(p_sSource)));
	}
	
	/**
	 * Remove leading and trailing whitespace
	 * 
	 * @param source
	 * @return 
	 */
	public static String lrtrim(String p_sSource)
	{
		return ltrim(rtrim(p_sSource));
	}
}
