package Restaurant;
import java.text.DecimalFormat;
import java.util.ArrayList;


//Class representing one line in the input text file
public class Order
{
	final String 			orderNum;						//the order number 
	int 					tableNum;						//the table number
	double 					orderdiscount = 0;				//the discount
	ArrayList<Item> 		orderedItem;					//the list of ordered items from the Item class
	private IO 				io = new IO();	
	private DecimalFormat 	df=  new DecimalFormat("#.00");
	
	OrderStatus status;
	/**
	 * the constructor to the Order class, checks for correct values and throws exceptions
	 * @param onum the order number
	 * @param tnum the table number
	 * @param items the item number
	 */
	public Order(String onum,int tnum, ArrayList<Item> items){
		//check if order number is blank
		if(onum.trim().length()==0)
		{
			throw new IllegalStateException( "Cannot have blank order number");
		}
		//check if  order number is in 8 digits
		if (onum.length()!=8)
		{
			throw new IllegalStateException( "ID must be in 8 digits"); 
		}
		//check if the order number is a numeric value
		if(!(onum.matches(".*\\d.*")))
		{
			throw new NumberFormatException("The order number " + onum + " must be in numeric values");
		}
		
		//check if table number is negative
		if(tnum<0)
		{
			throw new IllegalStateException(" The table number must be a positive number");
		}
		
		//check if items is null i.e. no items in the ArrayList of items
		if(items.isEmpty())
		{
			throw new NullPointerException("The items list is empty");
		}
		//trim any spaces and store it in the orderNum variable
		this.orderNum = onum.trim();
		this.tableNum = tnum;
		this.orderedItem = items;
		//order status is initialised as not complete
		this.orderUncompleted();
	}
	
	/**
	 * return the list of ordered items
	 * @return the items array list
	 */
	public ArrayList<Item> getItems(){
		return this.orderedItem;
	}
	
	/**
	 * gets the order number
	 * @return the order number
	 */
	public String getOrderNumber()
	{
		return this.orderNum;	
	}
	
	/**
	 * gets the table number
	 * @return the table number
	 */
	public int getTableNumber()
	{
		return this.tableNum;
	}
	
	/**
	 * set the order status to canceled
	 */
	public void orderCancled(){
		this.status = OrderStatus.Canceled;
	}

	/**
	 * sets the order status to not complete
	 */
	public void orderUncompleted(){
		this.status= OrderStatus.Uncompleted;
	}
	
	/**
	 * sets the order status to complete
	 */
	public void orderCompleted(){
		this.status= OrderStatus.Completed;
	}
	
	/**
	 * Receipt on screen
	 */
	public void orderCheckOut(){
		
	}
	
	/**
	 * sets the discount of the table
	 * @param discount the discount to be set
	 */
	public void setDiscount(double discount){
		//if the value of the discount is greater than 1 or less than 0 then there is no discount
		if(discount>1||discount<=0)
		{
			this.orderdiscount = 1;
		}
		//else the value of discount to be reduced from the total price is calculated
		else
		{
			this.orderdiscount = 1 -discount;
		}
	}
	
	/**
	 * format and generate the full receipt string
	 * print the bills and return it as string
	 * @return the bill as report in string format
	 */
	public String printReceipt(){
		String temp;
		double sum = 0;
		temp = 	"\r\n                              Receipt\r\n"+
				"==========================================================\r\n"+
				"Order Number:   "+String.format("%-10s", this.orderNum)+"\r\n"+
				"Table Number:   "+String.format("%-10s", this.tableNum)+"\r\n"+
				"Order Discount: "+String.format("%-10s", Double.valueOf(this.df.format((1-this.orderdiscount)*100)))+"% Off\r\n"+
				"Order Status:   "+String.format("%-10s", this.status)+"\r\n"+
				"Ordered Items:  "+String.format("%-10s", this.orderedItem.size())+"\r\n"+
				"----------------------------------------------------------\r\n"
				;
	
		for(Item s:this.orderedItem)
		{
			
			temp+=String.format("%-10s", s.itemID)+String.format("%-70s", s.name)+
					String.format("%-10s", s.quantity)+String.format("%-10s", Double.valueOf(s.price)*Integer.valueOf(s.quantity))+"\r\n";
			sum+= Double.valueOf(s.price)*Integer.valueOf(s.quantity);
		}
			
		temp += "----------------------------------------------------------\r\n"+ 
				"Total:              "+String.format("%-10s",  "£"+Double.valueOf(this.df.format(sum))+"")+"\r\n"+
				"The money you save: "+String.format("%-10s",  "£"+Double.valueOf(this.df.format(calculateDisount(sum)))+"")+"\r\n"+
				"The cost:           "+String.format("%-10s",  "£"+Double.valueOf(this.df.format(calculateTotalCost(sum)))+"")+"\r\n"+
				"========================Recepit End=======================\r\n\r\n";
		
		return temp;
	}
	
	/**
	 * to calculate how much money saved with discount
	 * @param s the total cost of order
	 * @return 
	 */
	public double calculateDisount(double s){
		return s- (s*this.orderdiscount);
	}
	
	/**
	 * To calculate the cost after take out the discount 
	 * @param s the total cost of order
	 * @return
	 */
	public double calculateTotalCost(double s){
		return s*this.orderdiscount;
	}
	
	
	public OrderStatus getOrderStatus(){
		return this.status;
	}
	
	public boolean checkOrderStatus(){
		boolean status = true;		
		for(Item i:this.orderedItem){
			status = (status && i.checkItemStatus()&&i.checkDelivery());
		}
		
		if(status){
			this.status = OrderStatus.Completed;
		}else if (!status){
			this.status = OrderStatus.Uncompleted;
		}
		return status;
	}



	
}
