package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utilities for server and client
 * @author Haijun Su
 * Date Dec 4, 2014
 *
 */
public class Utilities {

	public static String dateFormat = "MM/dd/yyyy";
	private static SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	
	/**
	 * Format date for output
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return sdf.format(date);
	}
	
	/**
	 * Parse date from input
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String date) throws ParseException {
		return sdf.parse(date);
	}
	
	
}
