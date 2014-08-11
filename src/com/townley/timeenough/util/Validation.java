package com.townley.timeenough.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.DocumentListener;


public class Validation
{

	
	/**
	 * @param JTextField field: a JTextField under considderation
	 * This function allows the user to enter only 6 or 8 numbers when a text field is 
	 * selected. 
	 * @return a boolean indicating whether or not the input field contains a validly formatted
	 * date.
	 */
	public static boolean validDate(JTextField field)
	
	{
		String date = field.getText();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		Date testDate = null;
		
		try
		{
			testDate = sdf1.parse(date);
		}
		catch (Exception e)
		{
			return false;
		}
		if( !sdf1.format(testDate).equals( date ) )
		{
			return false;
		}
		field.setText( sdf1.format( testDate ) );
		return true;
	}
/**
 * 	
 * @param date a string representing a date
 * @return a Date representing the string if the string is validly formatted, otherwise null.
 */
	public static Date validDate(String date, SimpleDateFormat sdf1)
	{
		
		Date testDate = null;
		
		try
		{
			testDate = sdf1.parse(date);
		}
		catch (Exception e)
		{
			return null;
		}
		if( !sdf1.format(testDate).equals( date ) )
		{
			return null;
		}
		return testDate;
	}
	
	/**
	 * 
	 * @param Field a text field containing a possibly valid time.
	 * @return Whether the time is valid.
	 */
	public static boolean validTime(JTextField field)
	{
		return false;
	}
}