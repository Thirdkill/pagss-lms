package com.pagss.lms.core;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.pagss.lms.handlers.StackTraceHandler;

public abstract class JdbcDao {

	protected NamedParameterJdbcTemplate jdbcTemplate;
	@Value("${amazon-s3.bucketname}")
	protected String bucketName;
	
	@Autowired
	protected void createdTemplate(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	/**
	 * Purpose: Get the string value and returns empty value if null. 
	 * @param string
	 * @return
	 */
	protected static String getStringValue (String string) {
		return (string!=null && !string.trim().equalsIgnoreCase("") ) ? string.trim() : "";
	}	
	
	/**
	 * Purpose: Returns timestamp as string value, and it 
	 * handles the format of the given timestamp and returns empty if null.
	 * Description: Use this if you want the given format.
	 * @param timestamp
	 * @param timeFlag - if true "M/d/yyyy H:mm:ss"; if false "M/d/yyyy".
	 * @return
	 */
	protected static String getDateValue (Timestamp timestamp, boolean timeFlag) {
		SimpleDateFormat dateFormatter;
		if (timeFlag) {
			dateFormatter = new SimpleDateFormat("M/d/yyyy H:mm:ss");
		} else {
			dateFormatter = new SimpleDateFormat("M/d/yyyy");
		}
		return (timestamp!=null) ? dateFormatter.format(timestamp) : "";
	}
	
	/**
	 * Purpose: Returns timestamp as string value.
	 * Description: Use this if you want to have a custom format.
	 * @param timestamp
	 * @param format
	 * @return
	 */
	protected static String getDateValue (Timestamp timestamp, String format) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
		return (timestamp!=null) ? dateFormatter.format(timestamp) : "";
	}
	
	/**
	 * Purpose: Returns the date value as string.
	 * Description: Use this if you want to change the original date format
	 * to new date format.
	 * @param date
	 * @param originalFormat
	 * @param newFormat
	 * @return
	 */
	protected static String getDateValue (String date, String originalFormat, String newFormat) {
		SimpleDateFormat dateParser = new SimpleDateFormat(originalFormat);
		SimpleDateFormat dateFormatter = new SimpleDateFormat(newFormat);
		try {
			return (date == null || date.trim().isEmpty()) ? "" : dateFormatter.format(dateParser.parse(date));
		} catch (ParseException pe) {
			StackTraceHandler.printStackTrace(pe);
			return "";
		}
	}
	
	/**
	 * Purpose: Handle the incoming null values of date to
	 * be inserted into database with data type Date.
	 * @param date
	 * @return String
	 */
	protected static String setDateValue (String date, String originalFormat, String newFormat) {
		SimpleDateFormat dateParser = new SimpleDateFormat(originalFormat);
		SimpleDateFormat dateFormatter = new SimpleDateFormat(newFormat);
		try {	
			return (date!="" && date!=null) ? dateFormatter.format(dateParser.parse(date)) : null;
		} catch (ParseException pe) {
			StackTraceHandler.printStackTrace(pe);
			return null;
		}
	}
}
