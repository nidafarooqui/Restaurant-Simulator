package Test;
import java.util.ArrayList;

import Restaurant.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestOrder {

	@Test(expected = NumberFormatException.class)
	public void testgetOrderNumber()  throws Exception {
	  //check if order number is numeric
		String expected1 = "abc$&*";
		ArrayList<Item> items= new ArrayList<Item>();
		Item i = new Item("123456","Pepperoni Pizza","MAIN", 11.00, 3);
		items.add(i);
		String message1 = "Failed for order number = abc$&*";
		Order order1= new Order ("abc$&#^&",2, items);
		String actual1 = order1.getOrderNumber();
		assertEquals(message1, expected1, actual1);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testgetOrderNumberlength() throws Exception {
		//check for order number is less than 8 digits
		 String expected1 = "1234567";
		 ArrayList<Item> item= new ArrayList<Item>();
		 Item i = new Item("123456","Pepperoni Pizza","MAIN", 11.00, 3);
		 item.add(i);
		 String message1 = "Failed for order number = 1234567";
		 Order order1= new Order ("1234567",2, item);
		 String actual1 = order1.getOrderNumber();
		 assertEquals(message1, expected1, actual1);
	}
		
		@Test(expected = IllegalStateException.class)
		public void testgetOrderNumber2() throws Exception {	
		//check if order number is empty or null
		String expected2 = "";
		ArrayList<Item> items2= new ArrayList<Item>();
		Item i2 = new Item("123456","Pepperoni Pizza","MAIN", 11.00, 3);
		items2.add(i2);
		String message2 = "Failed for order number = ";
		Order order2= new Order ("",2, items2);
		String actual2 = order2.getOrderNumber();
		assertEquals(message2, expected2, actual2);
		}
	
	@Test(expected = IllegalStateException.class)
	public void testgetTableNumber() throws Exception {
	  
		//check if table number is negative
		int expected1 = -3;
		ArrayList<Item> items= new ArrayList<Item>();
		Item i = new Item("123456","Pepperoni Pizza","MAIN", 11.00, 3);
		items.add(i);
		String message1 = "Failed for table number = -3";
		Order order1= new Order ("00000004",-3, items);
		int actual1 = order1.getTableNumber();
		assertEquals(message1, expected1, actual1);	
	}
	@Test(expected = NullPointerException.class)
	public void testgetItems() throws Exception {
	  
		//check if array list item is null
		ArrayList<Item> expected1 = null;
		ArrayList<Item> items= new ArrayList<Item>();
		String message1 = "Failed for ArrayList<Item> items = null";
		Order order1= new Order ("00000004",3, items);
		ArrayList<Item> actual1 = order1.getItems();
		assertEquals(message1, expected1, actual1);	
	}
	
	@Test(expected = IllegalStateException.class)
	public void testOrderNumberlength() throws Exception {
		//check for order number is greater than 8 digits
		 String expected1 = "1234567890";
		 ArrayList<Item> item= new ArrayList<Item>();
		 Item i = new Item("1234567890","Pepperoni Pizza","MAIN", 11.00, 3);
		 item.add(i);
		 String message1 = "Failed for order number = 1234567";
		 Order order1= new Order ("1234567890",2, item);
		 String actual1 = order1.getOrderNumber();
		 assertEquals(message1, expected1, actual1);
	}
	
}


