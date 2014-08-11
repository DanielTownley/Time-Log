package com.townley.timeenough.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 
 * @author danieltownley
 *
 */
public class Parser

{
	static final SimpleDateFormat DFM = new SimpleDateFormat("MM/dd/yyyy");
	
	/**
	 * @param String date: a string in format "MM/dd/yyyy" representing a date.
	 * @return A long corresponding to the Unix time stamp of that day at 5:00 GMT.
	 */
	public static long timeFromDate(String date)

	{	
		// TODO Get the local time zone offset in seconds.
		
		
		try 
		{
			// get the local timezone and apply it to the Simple Date Format.
			TimeZone timeZone = TimeZone.getDefault();
			DFM.setTimeZone(timeZone);
			
			// get the date entered by this user as a Unix time stamp.
			return DFM.parse(date).getTime() / 1000;
		} 
		catch (ParseException e) 
		{
			// Dust off your bell-bottoms.
			e.printStackTrace();
			return 0l;
		}
	}
	
	/**
	 * @param date: a Date in format "MM/dd/yyyy" representing a date.
	 * @return A long corresponding to the Unix time stamp of that day at 5:00 GMT.
	 */
	public static long timeFromDate(Date date)

	{	
		// TODO Get the local time zone offset in seconds.
		return date.getTime() / 1000;
	}
	
	
	/**
	 * 
	 * @param time1 the first time in a range.
	 * @param time2 the second time in a range.
	 * @return the number of seconds between these two times
	 */
	public static int secondsFromTimes(String time1, String time2)
	{
		/**
		 * TODO: write method to convert from time strings to seconds 
		 */
		
		// TODO: convert the times to milliseconds
		
		// TODO: get the difference in seconds between the two times and 
		
		
		return -1;
	}
	
	
}