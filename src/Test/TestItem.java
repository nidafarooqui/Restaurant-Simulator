package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import Restaurant.Item;

public class TestItem {

	@Test(expected = IllegalStateException.class)
	public void testItemDetail() throws Exception {
		//check if item id  is less 6 digits
		 String expected1 = "1234567PepperoniPizza113";
		 String message1 = "Failed for item id = 1234567";
		 Item item = new Item("1234567","Pepperoni Pizza","MAIN", 11.00, 3);
	     String actual1 = item.itemDetail();
		 assertEquals(message1, expected1, actual1);
		 
		 //check for item id = null
		 String expected2 = "BeefPizza113";
		 String message2 = "Failed for item id = null";
		 Item item2 = new Item("","Beef Pizza","MAIN", 11.00, 3);
	     String actual2 = item2.itemDetail();
		 assertEquals(message2, expected2, actual2);
		 
		
	}
	
	@Test(expected = NumberFormatException.class)
	public void testSetQuantity() throws Exception {
		
		//check for the quantity value passed as non numeric value
		 int expected1 = 3;
		 String message1 = "Failed for quantity = abc";
		 String quantity = " abc ";
		 Item item = new Item("123456","Pepperoni Pizza","MAIN", 11.00, 3);
		 item.setQuantity(quantity);
	     int actual1 = item.getQuantity();
	     
		 assertEquals(message1, expected1, actual1);
	}
	@Test(expected =IllegalArgumentException.class)
	public void testIncreaseQuantity() throws Exception{
		
		//check for addition between quantity and the number to be increased
		int expected1 = 7;
		 String message1 = "Failed for item quantity = 3";
		 Item item = new Item("123456","Pepperoni Pizza","MAIN", 11.00, 3);
	    item.increaseQuantity(4);
	    int actual1 = item.getQuantity();
		 assertEquals(message1, expected1, actual1);
		 
		 //check if quantity to be increased is negative
		 int expected2 = -1;
		 String message2 = "Failed for item quantity = -4";
		 Item item2 = new Item("123456","Pepperoni Pizza","MAIN", 11.00, 3);
	    item.increaseQuantity(-4);
	    int actual2 = item2.getQuantity();
		 assertEquals(message2, expected2, actual2);
	}
	
	
	@Test
	public void testgetTotalPrice() {
		
		//check for total price multiplication
		 double expected1 = 33.00;
		 String message1 = "Failed for price = 11.00 and quantity = 3";
		 Item item = new Item("123456","Pepperoni Pizza","MAIN", 11.00, 3);
	     double actual1 =  item.getTotalPrice();
	     assertEquals(expected1, actual1, 0.01);
	}
	
	@Test(expected =IllegalArgumentException.class)
	public void testgetItemPrice() throws Exception{
		//check if price is negative
		double expected1 =-4.00;
		 String message1 = "Failed for price = -4.00";
		 Item item = new Item("123456","Pepperoni Pizza","MAIN", -4.00, 3);
	     double actual1 =  item.getItemPrice();
	     assertEquals(expected1, actual1, 0.01);
		
	}
	
	@Test(expected =IllegalArgumentException.class)
	public void testgetQuantity() throws Exception{
		//check if quantity is negative
		int expected1 =-3;
		 String message1 = "Failed for quantity = -3";
		 Item item = new Item("123456","Pepperoni Pizza","MAIN", 14.00, -3);
	     int actual1 =  item.getQuantity();
	     assertEquals( message1,expected1, actual1);
		
	}
	@Test(expected = IllegalStateException.class)
	public void testgetItemID() throws Exception {
		//check for id greater than 6 digits
		 String expected1 = "1234567";
		 String message1 = "Failed for item id = 1234567";
		 Item item = new Item("1234567","Pepperoni Pizza","MAIN", 11.00, 3);
	     String actual1 = item.getItemID();
		 assertEquals(message1, expected1, actual1);
		 
		 //check for id = null
		 String expected2 = "";
		 String message2 = "Failed for item id = null";
		 Item item2 = new Item("","Beef Pizza","MAIN", 11.00, 3);
	     String actual2 = item2.getItemID();
		 assertEquals(message2, expected2, actual2);
	}
	@Test(expected = NumberFormatException.class)
	public void testgetItemIDformat() throws Exception {
		 //check if id is non numeric
		 String expected3 = "abc$&*";
		 String message3 = "Failed for item id = abc$&*";
		 Item item3 = new Item("abc$&*","Beef Pizza","MAIN", 11.00, 3);
	     String actual3 = item3.getItemID();
		 assertEquals(message3, expected3, actual3);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testgetName() throws Exception {	 
		 //check for dish name = null
		 String expected3 = "";
		 String message3 = "Failed for name = ";
		 Item item3 = new Item("123456","","MAIN", 11.00, 3);
	     String actual3 = item3.getName();
		 assertEquals(message3, expected3, actual3);
	}
}
