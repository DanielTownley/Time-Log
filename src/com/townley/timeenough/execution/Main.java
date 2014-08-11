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
    			+ "DURATION INT NOT NULL)";
    	
    	Connection conn  = null;
    	Statement stmt = null;
    	
    	
	    
    	// Attempt to connect to the create and connect to database.
		try 
		{
			conn = DriverManager.getConnection(dbURL);
			System.out.println("connected to db");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		// Attempt to assign statement to conn. 
		try 
		{
			stmt = conn.createStatement();
		}
		catch (SQLException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// If the database was just created, add to it an appropriate table.
		try
		{
			stmt.execute(TABLES_IF_NEEDED);
		}
		catch(SQLException e)
		{
			//e.printStackTrace();
		}
		
		// Now that that's all done, set up the window and panels.
		
		recordViewPanel spread = new recordViewPanel(conn);
	    NewBlockPanel addEvent = new NewBlockPanel(conn);
	    Dimension D = new Dimension(99999, 500);

	    // Configure the spreadsheet
	    spread.setBackground(Color.RED);
	    spread.setPreferredSize(D);
	    //spread.setMaximumSize(D);
	   
	    // give addEvent spreads table model, so it can add data to it "on the fly". 
	    // This, BTW, drastically limits db reads!
	    addEvent.addTableModel(spread.getTableModel());
	    
	   
    	MainWindow mainMenu = new MainWindow();
    	 	
    	mainMenu.add(spread);
	    mainMenu.add(addEvent);
	    
	    mainMenu.repaint();
	    
	    mainMenu.setVisible(true);
	    
	    
    	
    }
	private Connection innitializeDB(String URL, final String TABLES) throws SQLException
	{
		Statement stmt = null;
		
		Connection conn = null;
		
		conn = DriverManager.getConnection(URL);
		
		stmt = conn.createStatement();
		
		stmt.execute(TABLES);
		
		return conn;
		
	}
}