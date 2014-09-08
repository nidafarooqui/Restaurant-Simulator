package Restaurant;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class IO {
	/**
	 * Relative directory
	 */
	String directory = "Data/" ;
	
	private TreeMap menu = new TreeMap();
	//Iterator iter = menu.keySet().iterator();
	
	    public IO() {
	    
	    }
	
	
	
	/***
	 * Create a txt file in the directory
	 * @param txtname the name of the txt file
	 */
	public void createText(String txtname)
	{
        String fileName = txtname+".txt"; 
        File f = new File(directory+fileName);
        //check if file exists
        if(f.exists()) {
        	JOptionPane.showMessageDialog(null,f.getAbsolutePath()+"\n"+f.getName()+"\n"+f.length(),"Path info",JOptionPane.QUESTION_MESSAGE);
        	return;
        	//else try to create a new file
        } else {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
            	JOptionPane.showMessageDialog(null,"Wrong Create","Create info",JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }
        }
    }
	
	/**
	 * To write a string to the text file
	 * @param s
	 * @param overwrite If true the function will overwrite the txt file with new sting, if false the method will adding new string and keep the old data
	 * @param fileName
	 */
	/**
	 * writes the report to the path specified
	 * @param filename the file name where the report will be written to
	 * @param report all the data in String that needs to be written into the file
	 */
	public void writeToFile(String filename,boolean overwrite, String report)
	{
		createText(filename);

		FileWriter fw;
		
		try
		{
			//write the report in to the file specified in 'path'
			fw= new FileWriter(this.directory+filename+".txt",overwrite);
			
			PrintWriter pw=new PrintWriter(fw);  
	        
	        pw.println(report);  
	        
	        pw.close();  
	        
	        fw.close();  
		}
		
		//if file is not found print the error message and exit
		catch(FileNotFoundException ex)
		{
			System.out.print(this.directory+filename+".txt" + " not found.");
			System.exit(0);
		}
		
		//for any other exception print the stack trace and exit
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * The method read the txt file from the project folder.
	 * @return This method return a treemap which will be cached in the menu class.
	 */
	public TreeMap readMenuTxt(){
		String tokens[];
		FileReader fr;
		TreeMap s = new TreeMap();
		// we catch the general exception which is file not found. in case of the program can't find the target txt file
		try {
			fr = new FileReader("Menu.txt");
			BufferedReader LoadDetail = new BufferedReader(fr);
			try {
				//looping the main IO stream in order to get all list of items.
				//It reads the text file line by line until it gets a null in the stream.
				do{
					String temp = LoadDetail.readLine();
					//check if entire field not empty
					if (temp!=null){
						tokens = temp.split(",");
						
						//an expression to check for double value in string
						String regExp = "[\\x00-\\x20]*[+-]?(((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*";
						//check if the number of comma separated values are 4
						if(tokens.length==0 || tokens.length<4 || tokens.length>4)
						{
							System.out.println("Data corrupted in the Menu.txt file. "
									+ "please check the number of comma separated values");
							return s;
						}
						//check if each comma separated value is not empty
						else if(tokens[0].trim().length()==0 || tokens[1].trim().length()==0 
								|| tokens[2].trim().length()==0 || tokens[3].trim().length()==0){
							
							System.out.println("One or more values missing the menu.txt file");
							return s;
						}
						//check if the item id is a numeric value
						else if(!(tokens[0].trim().matches(".*\\d.*")))
						{
							System.out.println("the item id is not in a numeric value in the Menu.txt file");
							return s;
						}
						//check for negative value in item id
						else if(Integer.parseInt(tokens[0]) < 0)
						{
							System.out.println("Item id in menu.txt cannot be negative");
							return s;
						}
						
						//check if dish name is non numeric
						else if(tokens[1].trim().matches(".*\\d.*"))
						{
							System.out.println("Dish name in the Menu.txt file cannot be numeric");
							return s;
						}
						//check if category name is non numeric
						else if(tokens[2].trim().matches(".*\\d.*"))
						{
							System.out.println("Category name in the Menu.txt file cannot be numeric");
							return s;
						}
						//check if the category names are correct
						else if(!(tokens[2].trim().equalsIgnoreCase("MAIN") || 
								tokens[2].trim().equalsIgnoreCase("STARTER") ||
								tokens[2].trim().equalsIgnoreCase("DESSERT") ||
								tokens[2].trim().equalsIgnoreCase("DRINK")))
								{
									System.out.println("The category of the comma separated values in menu.txt must be MAIN, STARTER, DESSERT or DRINK");
									return s;
								}
						//check if the price is in a double numeric value, excludes special doubles like NaN and Infinity
						else if(!(tokens[3].trim().matches(regExp)))
						{
							System.out.println("The price in menu.txt must be in 2 decimal place");
							return s;
						}
						//check if the price is non negative
						else if(Double.parseDouble(tokens[3]) < 0)
						{
							System.out.println("Price cannot be negative in menu.txt");
							return s;
						}
						//create an item object once all data has been validated from the menu.txt file
						Item i = new Item(tokens[0],tokens[1],tokens[2],Double.parseDouble(tokens[3]));
						
						//System.out.println(tokens[0]+"  "+tokens[1]+"  "+tokens[2]+"  "+Double.parseDouble(tokens[3]));
						s.put(tokens[0], i);
					}
					else{
						fr.close();
						this.menu = s;
						//System.out.println("Menu:"+this.menu.size());
						return s;
					}
				}while(true);
			}catch (FileNotFoundException e){
				e.printStackTrace();
			}catch (IOException e){
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	} 
	
	
	/**
	 * Read the order in to system
	 * @return
	 */
	public ArrayList<Order> readOrderTxt(){
		String tokens[],tokens1[];
		FileReader fr;
		
		ArrayList<Order> orders = new ArrayList<Order>();
		
		this.readMenuTxt();
		try {
			fr = new FileReader("Order.txt");
			BufferedReader LoadDetail = new BufferedReader(fr);
			try {
				do{
					//--------------------------  FIRST BLOCK
					//Read the first line of an order detail including the order number, table number, and order status 
					String temp = LoadDetail.readLine();
					
					//Because the last line in the order txt file is a null line.
					//If we read a null in the stream that means the end.
					if (temp!=null){
						tokens = temp.split(",");
						
						//check if the number of comma separated values are 4
						if(tokens.length==0 || tokens.length<4 || tokens.length>4)
						{
							System.out.println("Data corrupted in the Order.txt file. "
									+ "please check the number of comma separated values");
							return orders;
						}
						//check if the values are empty
						else if(tokens[0].trim().length()==0 || tokens[1].trim().length()==0 
								|| tokens[2].trim().length()==0){
							
							System.out.println("One or more values missing the Order.txt file");
							return orders;
						}
						//check if the order number is a numeric value
						else if(!(tokens[0].trim().matches(".*\\d.*")))
						{
							System.out.println("the order number is not in a numeric value in the Order.txt file");
							return orders;
						}
						//check for negative value in order number
						else if(Integer.parseInt(tokens[0]) < 0)
						{
							System.out.println("Order number in Order.txt cannot be negative");
							return orders;
						}
						//check if the table number is a numeric value
						else if(!(tokens[1].trim().matches(".*\\d.*")))
						{
							System.out.println("the table number is not in a numeric value in the Order.txt file");
							return orders;
						}
						//check for negative value in table number
						else if(Integer.parseInt(tokens[1]) < 0)
						{
							System.out.println("Table Number in Order.txt cannot be negative");
							return orders;
						}
						//check if order status is non numeric
						else if(tokens[2].trim().matches(".*\\d.*"))
						{
							System.out.println("Order status in the Menu.txt file cannot be numeric");
							return orders;
						}
						//check if the category names are correct
						else if(!(tokens[2].trim().equalsIgnoreCase("COMPLETED") || tokens[2].trim().equalsIgnoreCase("UNCOMPLETED")|| tokens[2].trim().equalsIgnoreCase("CANCELED")))
								{
									System.out.println("The order status of the comma separated values in order.txt must say completed");
									return orders;
								}
						
						OrderStatus os;
						Object key;
						
						//The reason why we have a array list was because an order contains a list of items
						ArrayList<Item> items = new ArrayList<Item>();
						do{
							temp = LoadDetail.readLine();
							
							//The order in txt file is ending with      -END-
							//The string means that the end of the current order, if a null string is the next the reading is end otherwise its continue.
							if (temp.equals("-END-")){
								break;
							}else{
								tokens1 = temp.split(",");
								
								//check if the number of comma separated values are 2
								if(tokens1.length==0 || tokens1.length<2 || tokens1.length>2 || tokens1.equals("-END-"))
								{
									System.out.println("Data corrupted in the Order.txt file. "
											+ "please check the number of comma separated values");
									return orders;
								}
								//check if the values are empty
								else if(tokens1[0].trim().length()==0 || tokens1[1].trim().length()==0)
								{
									System.out.println("One or more values missing the Order.txt file");
									return orders;
								}
								//check if the item id is a numeric value
								else if(!(tokens1[0].trim().matches(".*\\d.*")))
								{
									System.out.println("the item id is not in a numeric value in the Order.txt file");
									return orders;
								}
								//check for negative value in item id
								else if(Integer.parseInt(tokens1[0]) < 0)
								{
									System.out.println("Item id in Order.txt cannot be negative");
									return orders;
								}
								//check if the quantity is a numeric value
								else if(!(tokens1[1].trim().matches(".*\\d.*")))
								{
									System.out.println("the quantity is not in a numeric value in the Order.txt file");
									return orders;
								}
								//check for negative value in quantity
								else if(Integer.parseInt(tokens1[1]) < 0)
								{
									System.out.println("Quantity in Order.txt cannot be negative");
									return orders;
								}
								
								Iterator<String> iter = menu.keySet().iterator();
								while(iter.hasNext()){
									Item t = (Item)menu.get(iter.next());									
									if(tokens1[0].equals(t.getItemID())){
										t.setQuantity(tokens1[1]);
										items.add(t);
									}
								}
								
							}
							
						}while(true);	
						
						//create an object o from the order class and store the data read after validation into the object o
						Order o = new Order(tokens[0], Integer.parseInt(tokens[1]), items);
						if(tokens[2].equals("Completed")){
							o.orderCompleted();
						}else if(tokens[2].equals("Uncompleted")){
							o.orderUncompleted();
						}else if(tokens[2].equals("Canceled")){
							o.orderCancled();
						}
						//read the price of the dish and send it to the set discount function
						o.setDiscount(Double.valueOf(tokens[3]));
						//System.out.println(o.printReceipt());
						//System.out.println(orders.size());
						orders.add(o);
						
					}else{
						fr.close();
	
						return orders;
					}
				}while(true);
			}catch (FileNotFoundException e){
				e.printStackTrace();
			}catch (IOException e){
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	} 
	
	
		   
	
	
}

