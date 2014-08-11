package com.townley.timeenough.execution;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JPanel;

import com.townley.timeenough.util.*;
/**  
 * 
 * @author danieltownley
 * 
 */
public class Main
{
	
	
	public static String dbURL = new String("jdbc:derby:scheddb;create=true");
	// TODO Open or create a new database
	
    public static void main(String[] args)
    {
    	
    	// This string creates the table to store a series of times...
    	final String TABLES_IF_NEEDED = "CREATE TABLE records (" 
    			+ "ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
    			+ "TIMESTAMP BIGINT NOT NULL, "
    			+ "DURATION INT NOT NULL, "
    			+ "TYPE VARCHAR(32), "
    			+ "DESCRIPTION VARCHAR(140)"
    			+ ")";
    			
    			
    	Connection conn = null;
    		    
    	// Attempt to connect to the create and connect to database.	

		conn = innitializeDB(dbURL, TABLES_IF_NEEDED);
		
		
		Session session = new Session(conn);
    	
    }
	private static Connection innitializeDB(String URL, final String TABLES)
	{
		Statement stmt = null;
		
		Connection conn = null;
		
		try 
		{	
			conn = DriverManager.getConnection(URL);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			stmt = conn.createStatement();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			stmt.execute(TABLES);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return conn;
		
	}
}