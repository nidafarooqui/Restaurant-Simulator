package Restaurant;

import Errors.ErrorInputException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JOptionPane;


public class Manager {
	private Menu menu;
	private OrderManager ordermanager;
	private IO io;
	private Set report = new HashSet<Item>();
	
	public Manager(){
		
		this.menu = new Menu();
		this.ordermanager = new OrderManager();
		this.io = new IO();
		this.setOrders();
	}
	
	/**
	 * To  (formating) frequency report string,
	 * The basic report calculation will be done in order manager class.
	 * @return Return the full report in string 
	 */
	public String printFrequencyReport(){
		//System.out.println(this.report.size());
		this.report = this.ordermanager.reportGenerate(this.menu.transformDataStructure());
		
		String s="FREQUENCY REPORT"+"\r\n"+"==================================================================================================\r\n";
		String u="\r\n\r\nDISHES NOT ORDERED"+"\r\n"+"==================================================================================================\r\n";
		
		//iterate through each item and if the quantity of dish is not 0 print it
		for(Iterator iter = this.report.iterator(); iter.hasNext(); ) 
		{ 
			Item t = (Item) iter.next();
			
			//check if the quantity is 0 print the report under the FREQUENCY REPORT heading
			if (t.quantity !=0)
			{
		    s+= String.format("%-7s", t.itemID)+String.format("%-80s", t.name)+String.format("%-10s", t.quantity+"")+"\r\n";
			}
			  //if the quantity of the dish is 0 mention it under Dishes Not Ordered
			else
			{
				u+=String.format("%-7s", t.itemID)+String.format("%-80s", t.name)+"\r\n";
			}
		}
		//Optional out put to console
		//System.out.println(s+u);
		return s+u;
	}
	
	
	/**
	 * the interface for GUI class
	 * @return the io object
	 */
	public IO getIO(){
		return this.io;
	}
	
	/**
	 * print the menu to a text file by calling the menu's print menu method
	 * @return the menu as string
	 */
	public String printMenu(){
		return menu.printMenu();
	}
	
	/**
	 * prints the receipt which is the bill, to a text file by calling the order manager's printAllReceipt method
	 * @return the receipt as string
	 */
	public String printReciept(){
		return this.ordermanager.printAllReceipt();
	}
	
	/**
	 * finds the table number and shows the bill for that table in a GUI
	 * @param s The table number the user entered in the GUI
	 * @throws ErrorInputException if the table number is not a numeric value
	 */
	public String searchTable(String s) throws ErrorInputException{
		//check if the table number is not a numeric value
		if(!s.trim().matches(".*\\d.*")){
			throw new ErrorInputException("Table number must be in digits");
		}
		//find the table number and display its bill
		BillGUI gui = new BillGUI(this.ordermanager.searchOrderByTableID(s));
		gui.createAndShowGUI();
		//System.out.println(this.ordermanager.searchOrderByTableID(s));
		return "";
		
	}
	
	/**
	 * searches for the order number and displays the bill for that order number in a GUI
	 * @param s the order number that the user enters to a GUI
	 * @returns an empty string
	 * @throws ErrorInputException if the order number is not numeric or in 8 digits
	 */
	public String searchOrder(String s) throws ErrorInputException{
		//check if order number is numeric or in 8 digits
		if(!s.trim().matches(".*\\d.*") && s.length()!=8){
			throw new ErrorInputException("Order number must be numeric and in 8 digits");
		}
		//find the order number and display its bill
		BillGUI gui = new BillGUI(this.ordermanager.searchOrderByOrderID(s));
		gui.createAndShowGUI();
		return "";
	}
	
	
	/**
	 * this function will be called when system creates this object in the constructor
	 * it will read the orders from the order.text file and store it
	 */
	private void setOrders(){
		this.ordermanager.setOrders(this.io.readOrderTxt());
		
		
	}
	
	/**
	 * this function will build a random order and send to the Simulator queue list/
	 */
	public void GenerateOrders(){
		ArrayList<Item> items = new ArrayList<Item>();
		int number = Simulator.getInstance().getRandom().nextInt(10)+5;
		for(int i = 0; i<number;i++){
			Item temp = this.menu.getRandomItem();
			//System.out.println("Random Item:"+temp.itemDetail());
			temp.setQuantity(Simulator.getInstance().getRandom().nextInt(3)+1);
			items.add(temp);
		}
		this.ordermanager.addOrder(Simulator.getInstance().getRandom().nextInt(10)+1, items);
	}
	
}
