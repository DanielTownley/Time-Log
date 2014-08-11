package com.townley.timeenough.execution;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Session 
{
	
	// Variables declared
	private MainWindow mainWin;
	private recordViewPanel viewPanel;
	private NewBlockPanel inputBar;
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
		
		this.mainWin = new MainWindow(this);
		this.viewPanel = new recordViewPanel(this);
		
	}
	
	public MainWindow getMainWin()
	{
		return this.mainWin;
	}
	
	public recordViewPanel getViewPannel()
	{
		return this.viewPanel;
	}
	
	public Connection getConnection()
	{
		return this.conn;
	}

	
}
