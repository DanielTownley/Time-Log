package com.townley.timeenough.execution;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

class ViewPanel extends JPanel
{
	private Session session;
	private DBTableModel dbtm = null;
	private Connection conn;
	private final SimpleDateFormat TIME_DISPLAY_FORMAT = new SimpleDateFormat( "HH:mm");
	
	ViewPanel(Session session)
	{
		this.session = session;
		final int ROWS = 1;
		final int COLUMNS = 1;
		String querry = "SELECT * FROM records";
		
		ResultSet results = null;
		Statement stmt = null;
		
		this.setLayout(new GridLayout(ROWS, COLUMNS));
		
		this.conn = session.getConnection();				
		
		// Attempt to create a statement to interface with the conn.
		try 
		{
			stmt = conn.createStatement();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JTable table = null;
		JScrollPane scrollPane = null;

		// try to get results and display them in the spread.
		try 
		{	
			results = stmt.executeQuery(querry);
			String[] headings = {"ID", "Start Time", "Duration"};
			
			
			// Set Model to be an instance of
			//the non-editable subclass, DBTableModel.
			dbtm = new DBTableModel();
			dbtm.setColumnCount(4);

			table = new JTable(dbtm);
			scrollPane = new JScrollPane(table);
			
			//Render the DB.
			while( results.next() )
			{	
				// For each row of the query results, add a row to the JTable.
				String[] rowData = new String[3];
				
				// render the date as 
					
				rowData[0] = results.getString(4);
				
				rowData[1] = TIME_DISPLAY_FORMAT.format( results.getLong(2) * 1000 );

				rowData[2] = TIME_DISPLAY_FORMAT.format( ( results.getLong(2) 
						+ results.getLong(3) ) * 1000 );
							
				rowData[3] = results.getString(5);
				
				dbtm.addRow(rowData);
			}
			this.add(scrollPane);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public class DBTableModel extends DefaultTableModel 
	{
		public boolean isCellEditable(int row, int column)
		{  
	          return false;  
	    }

	}
	
	public DefaultTableModel getTableModel()
	{
		return this.dbtm;
	}
	
	
}

