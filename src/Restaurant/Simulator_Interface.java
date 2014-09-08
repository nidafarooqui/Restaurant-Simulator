package Restaurant;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Simulator_Interface extends JFrame implements Runnable{
	
	/**
	 * Silulator:
	 * The two interface will share one manager class
	 * because all data read from txt file will be in only one manager class
	 * and from this object interface can actually use all the resources from existing class
	 */
	Manager MANAGER;
	
	IO io = new IO();
	
	
	
	Thread InterfaceThread;
	//-----------------GUI ----------------------
	JPanel _panel = new JPanel();
	ButtonGroup bg=new ButtonGroup();

	//Simulator
	JLabel label_Main = new JLabel("Simulator");
	JLabel label_Main_info = new JLabel(String.format("%-10s", "Item ID")+String.format("%-10s", "Completed")+String.format("%-10s", "Quantity")+String.format("%-10s", "State"));
	JTextArea textArea_Main = new JTextArea();
	
	JLabel label_TableQueue = new JLabel("Table Queue");
	JTextArea textArea_TableQueue = new JTextArea();
	
	JLabel label_DeliveryQueue = new JLabel("Delivery Queue");
	JTextArea textArea_DeliveryQueue = new JTextArea();
	
	JLabel label_SimulatorInfo = new JLabel("Simulator Log");
	JTextPane textPane_SimulatorInfo = new JTextPane();
	
	JCheckBox checkBox_GenerateOrder = new JCheckBox("Generate Orders");
	JCheckBox checkBox_MadChef = new JCheckBox("Mad Chef");
	JButton button_GenerateOnce = new JButton("Generate one");
	
	
	//Kitchen
	JLabel label_1 = new JLabel("Chef 1");
	JLabel label_2 = new JLabel("Chef 2");
	JLabel label_3 = new JLabel("Chef 3");
	JLabel label_4 = new JLabel("Chef 4");
	
	JTextArea textArea_1 = new JTextArea();
	JTextArea textArea_2 = new JTextArea();
	JTextArea textArea_3 = new JTextArea();
	JTextArea textArea_4 = new JTextArea();
	
	JLabel label_5 = new JLabel("Waiter 1");
	JLabel label_6 = new JLabel("Watier 2");
	
	JTextArea textArea_5 = new JTextArea();
	JTextArea textArea_6 = new JTextArea();
	
	//-----------------GUI ----------------------
	
	public Simulator_Interface (int chef,Manager m){
		try{
			this.MANAGER = m;
			
			//create the user interface
			setTitle("Restaurant Simulator System"); 
			setSize(1000,710);
			setLocationRelativeTo(null); //Initialize in the center of screen
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close event

	        setLayout(null); 
		
	        _panel.setLocation(0, 0);
	        _panel.setSize(1000,710);
	        _panel.setLayout(null);        
	        
	        this.label_Main.setBounds(20, 0, 220, 30);
	        this.label_Main_info.setBounds(20,15,220,30);
	        this.textArea_Main.setBounds(20, 40, 220, 240);
	        this.label_TableQueue.setBounds(20, 273, 220, 30);
	    	this.textArea_TableQueue.setBounds(20, 295, 220, 80);
	    	
	    	this.label_DeliveryQueue .setBounds(330, 0, 150, 30);
	    	this.textArea_DeliveryQueue.setBounds(330, 25, 150, 260);
	    	
	    	
	    	this.label_SimulatorInfo.setBounds(20, 370, 320, 30);
	    	this.textPane_SimulatorInfo.setBounds(20, 395, 620, 430);
	                
	        this.label_1.setBounds(670, 0, 300, 30);
	        this.label_2.setBounds(670, 220, 300, 30);
	        this.label_3.setBounds(670, 440, 300, 30);
	        
	        this.textArea_1.setBounds(670, 25, 300, 200);
	        this.textArea_2.setBounds(670, 245, 300, 200);
	        this.textArea_3.setBounds(670, 465, 300, 200);	        
	        
	        this.checkBox_GenerateOrder.setBounds(240, 350, 150, 30);
	        this.checkBox_MadChef.setBounds(400, 350, 150, 30);
	        this.button_GenerateOnce.setBounds(240, 320, 150, 30);
	        
	        
	    	this.label_5.setBounds(500, 0, 150, 30);
	    	this.label_6.setBounds(500, 180, 150, 30);
	    	
	    	this.textArea_5.setBounds(500, 25, 150, 150);
	    	this.textArea_6.setBounds(500, 205, 150, 150);
	        
	        
	        // when user decided to close the window save all log data to a txt file.
	        WindowListener listener = new WindowAdapter() {
	        
	            // This method is called when the user clicks the close button
	            public void windowClosing(WindowEvent evt) {
	                io.writeToFile(Simulator.getInstance().getTime(new SimpleDateFormat("dd_HH_mm_ss"))+" Log", false, Simulator.getInstance().getLog());
	        
	                // By default, nothing happens when the user clicks the close button.
	                // To close the frame, see e565 
	            }
	        
	        };
	        
	        
	        
	        this.checkBox_MadChef.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent e){
	        		if(checkBox_MadChef.isSelected()){
	        			Simulator.getInstance().getKitchen().startMad();
	        		}else{
	        			Simulator.getInstance().getKitchen().endMad();
	        		}
	        	}
	        });
	        
	        this.button_GenerateOnce.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent e){
	        		MANAGER.GenerateOrders();
	        	}
	        });
	        

	        
	        // Register the listener with the frame
	        this.addWindowListener(listener);
	        
	        
	        this._panel.add(label_Main);
	        this._panel.add(this.label_Main_info);
	        this._panel.add(textArea_Main);
	        this._panel.add(label_TableQueue);
	        this._panel.add(textArea_TableQueue);
	        this._panel.add(label_SimulatorInfo);
	        this._panel.add(new JScrollPane(textPane_SimulatorInfo));
	        this._panel.add(textPane_SimulatorInfo);
	        this._panel.add(label_DeliveryQueue);
	        this._panel.add(textArea_DeliveryQueue);
	        
	        
	        this._panel.add(label_1);
	        this._panel.add(label_2);
	        this._panel.add(label_3);
	        
	        this._panel.add(textArea_1);
	        this._panel.add(textArea_2);
	        this._panel.add(textArea_3);
	        
	        this._panel.add(label_5);
	        this._panel.add(label_6);
	        
	        this._panel.add(textArea_5);
	        this._panel.add(textArea_6);
	        
	        
	        this._panel.add(checkBox_GenerateOrder);
	        this._panel.add(checkBox_MadChef);
	        this._panel.add(button_GenerateOnce);
	        
	        this.add(_panel);
	        this.setVisible(true);
	        
	        
	        //to regerist a thread in Simulator
			Simulator.getInstance().SETINTERFACE(this);
			
			// Start all threads in the Simulator
	        Simulator.getInstance().ThreadStart(this);
	        
	        this.InterfaceThread = new Thread(this);
	        this.InterfaceThread.start();
		}catch(Exception e){
			
		}
	}
	
	
	/**
	 * this function insert a string and custom text colour into textPane
	 * @param text the string you want to insert 
	 * @param textColour the color
	 */
	public void insertDocument(String text , Color textColour)//
	{
	  SimpleAttributeSet set = new SimpleAttributeSet();
	  StyleConstants.setForeground(set, textColour);//
	  StyleConstants.setFontSize(set, 12);//
	  Document doc = textPane_SimulatorInfo.getStyledDocument();
	  try
	  {
	   doc.insertString(0, text, set);//
	  }
	  catch (BadLocationException e)
	  {
	  }
	}

	
	/**
	 * we actually 
	 */
	@Override
	public void run() {
		while (true){
		
		// to get simulator information (what order is in queue; what items in the order)
		this.label_Main.setText("Simulator Thread:"+Simulator.getInstance().getSimularotThreadInfo()+"\r\n");
		this.textArea_Main.setText(Simulator.getInstance().getSimulatorInfo());
		
		// gets what table is actually wating to join the queue
		this.textArea_TableQueue.setText(Simulator.getInstance().getTableQueueInfo());
		this.label_SimulatorInfo.setText("Simulator Log  "+ "Refresh (Interface):"+this.InterfaceThread.isAlive() + "  r:" +Simulator.getInstance().getRandom().nextInt(15));
		
		this.textArea_DeliveryQueue.setText(Simulator.getInstance().getDeliveryInfo());
		
		
		// to get progress from chef
		this.label_1.setText("Chef 1: "+Simulator.getInstance().getChefThreadInfo(0));
		this.textArea_1.setText(Simulator.getInstance().getKitchen().getChefInfo(0));
		this.label_2.setText("Chef 2: "+Simulator.getInstance().getChefThreadInfo(1));
		this.textArea_2.setText(Simulator.getInstance().getKitchen().getChefInfo(1));
		this.label_3.setText("Chef 3: "+Simulator.getInstance().getChefThreadInfo(2));
		this.textArea_3.setText(Simulator.getInstance().getKitchen().getChefInfo(2));
		
		
		this.label_5.setText("Waiter 1: "+Simulator.getInstance().getWaiterThreadInfo(0));
		this.textArea_5.setText(Simulator.getInstance().getKitchen().getWaiterInfo(0));
		this.label_6.setText("Waiter 2: "+Simulator.getInstance().getWaiterThreadInfo(1));
		this.textArea_6.setText(Simulator.getInstance().getKitchen().getWaiterInfo(1));
		
		// randomly generate order
		if(this.checkBox_GenerateOrder.isSelected()){
			if(5>Simulator.getInstance().getRandom().nextInt(100)){
				this.MANAGER.GenerateOrders();
			}
		}
		
		try {
			Thread.sleep(150L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}
	
}
