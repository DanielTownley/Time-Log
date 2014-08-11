package com.townley.timeenough.execution;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.townley.timeenough.util.*;
public class InputBarPanel extends JPanel
{
	Session session;
	Connection conn; // Connection to db that gets data from this panel.
	DefaultTableModel tm;
    JTextField dateField = new JTextField(8);     // initialize to current date	
    JTextField startTimeField = new JTextField(8);// initialize this to end time of last entry
    JTextField endTimeField = new JTextField(8);  // initialize this to current time.
    JTextField typeField = new JTextField(12); 
    JTextArea commentArea = new JTextArea(16, 8);
    
    JPanel tempJ = this;  // A reference to this object that can be passed to inner classes.
    
    final String UI_TIME_ERROR = "Sorry, the time you entered doesn't exist";
    private final SimpleDateFormat TIME_DISPLAY_FORMAT = new SimpleDateFormat( "HH:mm");
    
    /**
     * A Hopefully convenient constructor that gives this window a db to write to.
     * @param conn A Connection to the database to which this window should write. 
     */
    
    public InputBarPanel(Session session)
    {
    	String APPEND_BUTTON_STRING = new String("Append");
    	Dimension D = new Dimension(99999, 60);
    	// can we create a db?
    	this.session = session;
		this.conn = session.getConnection();
	
		try
		{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			System.out.println("Success!");
		}
		catch(java.lang.ClassNotFoundException e)
		{
			System.err.print("ClassNotFoundException");
			System.err.println(e.getMessage());
		}
		
		// Innitialize Textfields
	
		// a Button.
		JButton appendBlock = new JButton(APPEND_BUTTON_STRING);

		// Initialize the panel's properties
		this.setLayout( new FlowLayout() );
		this.setPreferredSize(D);
		this.setMaximumSize(D);
	        
		// Set the behavior of the appendBlock button by giving it an anonymous actionlistenter.
		appendBlock.addActionListener( new AddButtonListener() );
	
		// Add text fields and buttons
		this.add(typeField);
		this.add(dateField);
		this.add(startTimeField);
		this.add(endTimeField);
		
		this.add(appendBlock);
		
		this.add(commentArea);
	
	

    }
    
    private class AddButtonListener implements ActionListener
    {	
    	// These are the formats SDF's the user must use. 
	    SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
	    SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
	    
	    public void actionPerformed(ActionEvent e)
	    { 	    	
		    // get the strings to be added to the database...
		    String dateStr = dateField.getText();
		    String startTimeStr = startTimeField.getText();
		    String endTimeStr = endTimeField.getText();
		    	   
		    // Date to indicate the start day (00:00 on that day)
		    Date date = null;
		    
		    // Start and end times for the event: represented as times on Jun 1 1970
		    // to facilitate arithmetic. 
		    Date startTime = null;
		    Date endTime = null;

		    // Time zone to be assigned to Date date.
		    TimeZone timeZone = TimeZone.getDefault();

		    // set time zones. since sdf1 is already offset by virtue of being adjusted 
		    // to the local time, sdf 2, which holds only hours from Unix Epoch 0, should
		    // not be changed.
			sdf1.setTimeZone(timeZone);
			sdf2.setTimeZone( TimeZone.getTimeZone("UTC") );
			
		    // Using validation functions, initialize the three Date objects from 
			// the strings entered in the fields.
			date = Validation.validDate(dateStr, sdf1);
			startTime = Validation.validDate(startTimeStr, sdf2);
			endTime = Validation.validDate(endTimeStr, sdf2);
			
		    if(date == null || startTime == null || endTime == null)
		    {	
		    	// Tell the user they entered an invalid time. Which they did. 
		    	startTimeField.setText(UI_TIME_ERROR);		    	
		    }
		    else
		    {	
		    	// Calculate the startSecond and duration of the block, and write them to the db.
		    	long startSecond = ( startTime.getTime() / 1000l ) + ( date.getTime() / 1000l ) ; 		    	
		    	int duration = (int) ( endTime.getTime() - startTime.getTime() ) / 1000;	    			    			    	

		    	// Add a new row to the database.
		    	addDBRow( startSecond, duration, typeField.getText(), commentArea.getText() );
		    	
		    	// Also add this data to the table model in the same session
				// without reading the DB.
				String[] rowData = new String[4];
				rowData[0] = typeField.getText();
				rowData[1] = TIME_DISPLAY_FORMAT.format( startSecond * 1000 );
				rowData[2] = TIME_DISPLAY_FORMAT.format( ( startSecond + duration ) * 1000 );
				rowData[3] = commentArea.getText();
				
				// With a single line of code, we add a row to the table!
				session.getViewPannel().getTableModel().addRow(rowData);	    	
		    }
	    }

		private void addDBRow(long startSecond, int duration, String title, String comment) 
		{
			// allocate a statement object
			Statement stmt = null;
			try 
			{
				stmt = conn.createStatement();
			} 
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
					    	
			try 
			{
				// Insert UI'd data to DB.
				stmt.executeUpdate("INSERT into records values ("
						+ "DEFAULT, "
						+ String.valueOf(startSecond) + ", " 
						+ String.valueOf(duration) + ", "
						+ title + ", "
						+ comment + ")" );
				
				ResultSet results = stmt.executeQuery("SELECT * FROM records");				
			}
			catch (SQLException e1) 
			{
				
				e1.printStackTrace();
			}
		}	
    }
    
}
 