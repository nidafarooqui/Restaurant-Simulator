package Restaurant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Errors.ErrorInputException;

public class User_Interface extends JFrame{
	
	JPanel _panel = new JPanel();
	ButtonGroup bg=new ButtonGroup();
	JLabel Logo = new JLabel(new ImageIcon("Image/Untitled.png"));
	JRadioButton radio_Func_Ordernum = new JRadioButton("Order Number");
	JRadioButton radio_Func_Tablenum = new JRadioButton("Table Number");
	JButton button_Func_Menu  = new JButton("View Menu");
	JButton button_Func_Report  = new JButton("Print Report");
	JButton button_Func_Search  = new JButton("Search");
	JButton button_Func_Receipt  = new JButton("Table Summary");
	JButton button_Func_Simulator  = new JButton("Simulator");
	JTextField textField = new JTextField();
	
	private Manager manager = new Manager();
	private Simulator_Interface simulator;
	
	public User_Interface (){
		try{
			
			//create the user interface
			setTitle("Restaurant Management System"); 
			setSize(500,300);
			setLocationRelativeTo(null); //Initialize in the center of screen
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close event

	        setLayout(null); 

	        
	        _panel.setLocation(0, 0);
	        _panel.setSize(500,300);
	        _panel.setLayout(null);
	        
	        this.Logo.setBounds(0,0,500,150);
	        this.button_Func_Menu.setBounds(60,155,120,30);
	        this.button_Func_Report.setBounds(190,155,120,30);
	        this.button_Func_Receipt.setBounds(320,155,120,30);
	        this.textField.setBounds(60, 205, 120, 30);
	        this.button_Func_Search.setBounds(190,205,120,30);
	        this.radio_Func_Ordernum.setBounds(60, 235, 120, 30);
	        this.radio_Func_Tablenum.setBounds(190, 235, 120, 30);
	        this.button_Func_Simulator.setBounds(320, 205, 120, 30);
	        this.radio_Func_Tablenum.setSelected(true);
	        
	        bg.add(radio_Func_Ordernum);
	        bg.add(radio_Func_Tablenum);
	        
	        //check if the view menu button is pressed
	        this.button_Func_Menu.addActionListener(new ActionListener() 
	        {
	        	//if pressed, write the menu to a new file as menu.txt
		        public void actionPerformed(ActionEvent event) { 
		        	manager.getIO().writeToFile( "MENU", false, manager.printMenu());
	        	}
	        });
	        
	        //listen for any print report button presses
	        this.button_Func_Report.addActionListener(new ActionListener() 
	        {
		        //if the print report button is pressed write the entire report to the FINAL REPORT.txt file 
	        	public void actionPerformed(ActionEvent event) { 
		        	manager.getIO().writeToFile( "FINAL REPORT", false, manager.printMenu()+"\r\n\r\n\r\n"+
		        														manager.printFrequencyReport()+
		        														"\r\n\r\n\r\nTABLE SUMMARY\r\n"+manager.printReciept());
	        	}
	        });
	        
	        //listen for any button presses for the search button
	        this.button_Func_Search.addActionListener(new ActionListener() 
	        {
	       
		        public void actionPerformed(ActionEvent event) 
		        { 
		        	//if the search button is pressed,  and the radio button order number is selected
		        	//get the value from the text field and search for for that order nuber and display its bill in a GUI
		        	if(radio_Func_Ordernum.isSelected()){
		        		try {
							manager.searchOrder(textField.getText());
						} catch (ErrorInputException e) {
		
						}
		        		
		        	}
		        	//if the radio button table number is selected, get the value the user entered in the text field
		        	//search for the table number amongst the orders
		        	//and display the bill for that table number as a GUI
		        	else if (radio_Func_Tablenum.isSelected())
		        	{
		        		try {
							manager.searchTable(textField.getText());
						} catch (ErrorInputException e) {
							
			
						}
		        	}else{
		        		
		        	}
	        	}
	        });
	        
	        //listen for any button presses for the table summary button
	        this.button_Func_Receipt.addActionListener(new ActionListener() 
	        {
	        	//if the button is pressed, write the table summary to a table summary.txt file
		        public void actionPerformed(ActionEvent event) { 
		        	manager.getIO().writeToFile("Table Summary", false, "TABLE SUMMARY\r\n"+manager.printReciept());
		        	
	        	}
	        });
	        
	        
	        //listen for any button presses for the table summary button
	        this.button_Func_Simulator.addActionListener(new ActionListener() 
	        {
	        	//if the button is pressed, write the table summary to a table summary.txt file
		        public void actionPerformed(ActionEvent event) { 
		        	simulator = new Simulator_Interface(3,manager);
	        	}
	        });
	        
	        
	        
	        //add all the components of the GUI
	        this._panel.add(this.button_Func_Menu);
	        this._panel.add(this.button_Func_Report);
	        this._panel.add(Logo);
	        this._panel.add(textField);

	        this._panel.add(radio_Func_Ordernum);
	        this._panel.add(radio_Func_Tablenum);
	        this._panel.add(this.button_Func_Search);
	        this._panel.add(this.button_Func_Receipt);
	        this._panel.add(this.button_Func_Simulator);
	        
	        
	        this.add(_panel);
	        this.setVisible(true);
	        
		}catch(Exception e){
			
		}
	}
	
	
	


}
