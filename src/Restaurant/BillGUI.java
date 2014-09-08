package Restaurant;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class BillGUI extends JPanel{
	
	    protected static JTextArea textArea;
	    private final static String newline = "\n";
	    String temp;
	 
	    public BillGUI(String str) {
	        super(new GridBagLayout());
	 
	        this.temp = str;
	        textArea = new JTextArea(5, 20);
	        textArea.setEditable(false);
	        JScrollPane scrollPane = new JScrollPane(textArea);
	 
	        //Add Components to this panel.
	        GridBagConstraints c = new GridBagConstraints();
	        c.gridwidth = GridBagConstraints.REMAINDER;
	 
	        c.fill = GridBagConstraints.HORIZONTAL;
	 
	        c.fill = GridBagConstraints.BOTH;
	        c.weightx = 1.0;
	        c.weighty = 1.0;
	        add(scrollPane, c);
	    }

	   
	 
	    /**
	     * Create the GUI and show it.  
	     */
	    public void createAndShowGUI() {
	        //Create and set up the window.
	        JFrame frame = new JFrame("Bill");
	        
			frame.setTitle("Restaurant Management System"); 
			frame.setSize(600,500);
			frame.setLocationRelativeTo(null); //Initialize in the center of screen


	        setLayout(null); 
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
	        //Add contents to the window.
	        frame.add(new BillGUI(temp));

	        textArea.append(temp+ newline);

	        
	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	        
	    }
}
