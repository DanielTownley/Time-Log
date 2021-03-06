package com.townley.timeenough.execution;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Session 
{
	
	// Variables declared
	private MainWindow mainWin;
	private ViewPanel viewPanel;
	private InputBarPanel inputBar;
	private Connection conn;	
	private Statement statement;
	
	
	// Constants assigned
	public final String DB_URL = new String("jdbc:derby:scheddb;create=true");
	final String TABLES_IF_NEEDED = "CREATE TABLE records (" 
			+ "ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
			+ "TIMESTAMP BIGINT NOT NULL, "
			+ "DURATION INT NOT NULL)";
	
	public Session(Connection conn)
	{
		// Assign the connection passed as a parameter.
		this.conn = conn;
		
		// Innitialize the session's windows: 
		this.mainWin = new MainWindow();
		this.viewPanel = new ViewPanel(this);
		this.inputBar = new InputBarPanel(this);
		
		// Set up the windows.
		Dimension D = new Dimension(99999, 500);
	    viewPanel.setBackground(Color.RED);
	    viewPanel.setPreferredSize(D);
		mainWin.add(viewPanel);
		mainWin.add(inputBar);	
		mainWin.repaint();   
		mainWin.setVisible(true);
		
	}
	
	public MainWindow getMainWin()
	{
		return this.mainWin;
	}
	
	public ViewPanel getViewPannel()
	{
		return this.viewPanel;
	}
	
	public InputBarPanel getInputBar()
	{
		return this.inputBar;
	}
	
	public Connection getConnection()
	{
		return this.conn;
	}

	
}
