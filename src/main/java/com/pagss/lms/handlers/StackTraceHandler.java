/**
 * 
 */
package com.pagss.lms.handlers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author John
 * 
 */
public class StackTraceHandler {
	
	/**
	 * Purpose: A custom exception stack trace handler to print the output on
	 * the IDE during the development stage and also to simplify the logged date
	 * of the exception.
	 * @param exception
	 */
	public static void printStackTrace (Exception exception) {
		System.out.println(new SimpleDateFormat("MMM d, yyyy H:mm:ss").format(Calendar.getInstance().getTime())
				+ " :: " + exception.getMessage());
		exception.printStackTrace();
	}
}
