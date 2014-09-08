package Restaurant;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JOptionPane;

public class OrderManager{
	
	// Keep for stage 2
	ArrayList<Order> completedOrder;
	ArrayList<Order> cancledOrder;
	
	
	// the current use list
	ArrayList<Order> restaurantOrders;
	
	/**
	 * generates an order number and adds the order to the uncompleted order list
	 * @param tnum the table number
	 * @param items the list of items in the array list
	 */
	public void addOrder(int tnum,ArrayList<Item> items)
	{
		//this.uncompletedOrder.add(new Order(this.generateOrderNumber(),tnum,items));
		this.restaurantOrders.add(new Order(this.generateOrderNumber(),tnum,items));
		//check the status of the order
		//this.checkOrderStatus();
		this.orderToTable(this.restaurantOrders.get(this.restaurantOrders.size()-1));
	}
	
	/**
	 * private method
	 * use to generate an order number when system try to adding a new order in to the list;
	 * @return the order number generated in string format
	 */
	private String generateOrderNumber(){
		//int n = this.cancledOrder.size()+this.completedOrder.size()+this.uncompletedOrder.size();
		int n = restaurantOrders.size()+1;
	    String str = String.format("%08d", n);     
		return str;
	}
	
	/**
	 * set the orders to the Order list
	 * @param order the list of orders
	 */
	public void setOrders(ArrayList<Order> order){
		this.restaurantOrders = order;
		this.orderToTable();
	}
	
	
	public void orderToTable(){
		for(Order o : this.restaurantOrders){
			if(o.getOrderStatus()== OrderStatus.Uncompleted){
				Simulator.getInstance().addTable(new Table(o));
				//Simulator.getInstance().logWorker("The order(" + o.orderNum +") has take a Table: " +o.tableNum);
				Simulator.getInstance().logWorker("The order(" + o.orderNum +") has take a Table: " +o.tableNum,'0');
			}
		}
		
	}
	public void orderToTable(Order o){
		if(o.getOrderStatus()== OrderStatus.Uncompleted){
			Simulator.getInstance().addTable(new Table(o));
			//Simulator.getInstance().logWorker("The order(" + o.orderNum +") has take a Table: " +o.tableNum);
			Simulator.getInstance().logWorker("The order(" + o.orderNum +") has take a Table: " +o.tableNum,'0');
		}
	}
	
	
	/**
	 * Search order with a given order Number, it catch user input in string type
	 * @param s Order string
	 * @return the full receipt string 
	 */
	public String searchOrderByOrderID(String s){
		String temp = "";
		//iterate through the restaurantOrders list to find the order number
		for(Order o:restaurantOrders)
		{
			//if found, print the bill for that order
			 if(o.orderNum.equals(s))
			 {
				 temp += o.printReceipt();
			 }
		}
		 //else display a message to the system stating order number not found
		 if(temp=="")
		 {
			 System.out.println("Order number not found");
		 }
		return temp;
	}
	
	/**
	 * Search order with a given table Number, it catch user input in string type
	 * In the system we assume that table can be any integer number
	 * @param s Table number String
	 * @return the full receipt string 
	 */
	public String searchOrderByTableID(String s)
	{
		//iterate through the list of restaurant orders and print receipt if table number is found
		String temp = "";
		for(Order o:restaurantOrders)
		//check if table number exists
		{
			 if(o.tableNum == Integer.valueOf(s)){
				 temp += o.printReceipt();
			 }
		}
		 //if not print a message to the system saying table nuber not found
		 if(temp == "")
		 {
			 System.out.println("Table number not found");
		 }
		return temp;
	}
	
	/**
	 * To generate a full report string including all the order receipt
	 * @return
	 */
	public String printAllReceipt()
	{
		//print the entire bill for all the orders i.e all the tables
		String temp = "";
		for(Order o: restaurantOrders)
		{
			temp += o.printReceipt();
		}
		return temp;
	}
	
	/**
	 * To calculate item frequency and save in a hash set;
	 * @param s A empty hash set should be send in;
	 * @return The method returns a full item list corresponding to what in the menu;
	 */
	public Set reportGenerate(Set s){
		
		// traverse all orders to calculate the frequency
		for(Order o:restaurantOrders){
			// Traverse all the items in one order to get the quantity
			for(Item i:o.getItems())
			{
				for(Iterator iter = s.iterator(); iter.hasNext(); ) 
				{ 
					Item temp = (Item)(iter.next());
					//if item id found, add the quantity to be increased to the quantity
				    if(temp.getItemID().equals(i.getItemID()))
				    {
				    	temp.increaseQuantity(i.getQuantity());
				    }
				}
			}
		}
		return s;
	}
	
	/***
	 * this method may use in the second stage during threading;
	 * checks the order status whether it is complete, cancelled or incomplete
	 */
	public void checkOrderStatus()
	{	
		/*//traverse through the list of all the incomplete orders still to be completed
		for(Order w:this.uncompletedOrder)
		{
			//check if the ordered list is  not empty
			if(!this.uncompletedOrder.isEmpty())
			{
				//check if the status of the order is complete
				if(w.getOrderStatus().equals(OrderStatus.Completed))
				{
					//add it to the complete ordered list and remove it from the incomplete list
					this.completedOrder.add(w);
					this.uncompletedOrder.remove(w);
				}
				//check if the order is cancelled
				if(w.getOrderStatus().equals(OrderStatus.Canceled))
				{
					//put the order to the cancelled order list and remove it from the incomplete ordered list
					this.cancledOrder.add(w);
					this.uncompletedOrder.remove(w);
				}
			}
		}
		//traverse through the list of all the complete orders
		for(Order w:this.completedOrder)
		{
			//if the list is not empty check if it is cancelled or complete
			if(!this.completedOrder.isEmpty())
			{
				//if the status in incomplete add it to the incomplete list and remove it from the completed list
				if(w.getOrderStatus().equals(OrderStatus.Uncompleted))
				{
					this.uncompletedOrder.add(w);
					this.completedOrder.remove(w);
				}
				// if the status is cancelled, add it to the cancelled list and remove it from the completed list
				if(w.getOrderStatus().equals(OrderStatus.Canceled)){
					this.cancledOrder.add(w);
					this.completedOrder.remove(w);
				}
			}
		}
		//traverse through the list of all the cancelled orders
		for(Order w:this.cancledOrder)
		{
			//check if the cancelled order is empty
			if(!this.cancledOrder.isEmpty())
			{
				//if the order status is incomplete, add to the incomplete list and remove it from the cancelled order list
				if(w.getOrderStatus().equals(OrderStatus.Uncompleted))
				{
					this.uncompletedOrder.add(w);
					this.cancledOrder.remove(w);
				}
				// if the order status is complete then add it to the complete list and remove it from the cancelled list
				if(w.getOrderStatus().equals(OrderStatus.Completed))
				{
					this.completedOrder.add(w);
					this.cancledOrder.remove(w);
				}
			}
		}*/
	}	
}

