package com.townley.timeenough.execution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame
{
    // A constructor without parameters, used when the program starts.
    public MainWindow()
	{
	    Container contentPane = this.getContentPane();

	    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
	    
	    // Adds a window listener that shuts down the program when the window closes.
	    addWindowListener(new WindowAdapter()
		{
		    @Override
			public void windowClosing(WindowEvent e)
		    {
			System.out.println("Bye");
			System.exit(0);
		    }
		});
	}
		
    @Override public void paint(Graphics g)
	{
	    super.paint(g);
	}
}