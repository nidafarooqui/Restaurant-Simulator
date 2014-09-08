package Restaurant;
// A menu of item including price and category
public class Item implements Comparable{
	String 			itemID; 					// the item id
	double 			price; 						// the price of the dish
	String 			category;					// the category the dish is in, can be STARTER, MAIN, DESSERT or DRINK
	String 			name;						// the name of the dish
	int 			quantity = 0;				// the quantity of the dish
	int 			completedQuantity = 0;		// the quantity that already completed 
	boolean 		itemStatus = false;			// the item status set to true after the quantity reach the required quantity
	int 			tabNum = 0;					// which table its belong to (this value will be same with which order it comes from)
	boolean 		delivered = false;		
	
	/***
	 * Item:
	 * constructor, use when first read the data from text
	 * @param id
	 * @param name
	 * @param category
	 * @param price
	 */
	public Item(String id,String name, String category,double price){
		this.itemID = id;
		this.price = price;
		this.category = category;
		this.name = name;
	}
	/***
	 * Item:
	 * constructor, use when item has a quantity in the order
	 * @param id
	 * @param name
	 * @param category
	 * @param price
	 * @param quantity
	 */
	public Item(String id,String name, String category,double price,int quantity){
		
		//check if the id or name is empty
		if(name.trim().length()==0 || id.trim().length()== 0) 
		{ 
			throw new IllegalStateException( "Cannot have blank id or name"); 
		}
		this.itemID = id.trim();
		
		//check if id is in 6 digits
		if (id.length()!=6)
		{
			throw new IllegalStateException( "ID must be in 6 digits"); 
		}
		//check if the id is a numeric value
		if(!(id.matches(".*\\d.*")))
		{
			throw new NumberFormatException("The quantity " +quantity + " must be in numeric values");
		}
		//check if price is negative
		if(price<0)
		{
			throw new IllegalArgumentException(" The price must be a positive number");
		}
		this.price = price;
		this.category = category.trim();
		this.name = name.trim();
		//check if quantity is negative
		if(quantity<0)
		{
			throw new IllegalArgumentException(" The quantity must be a positive number");
		}
		this.quantity = quantity;
	}
	
	/***
	 * Item:
	 * Display the item detail use for testing
	 * @return the item id, price and quantity in string
	 */
	public String itemDetail(){
		return this.itemID+" "+this.name+" "+this.price+" "+this.quantity;
	}
	
	/***
	 * Item:
	 * get item id when reading order into system,it needs to compare with the id that shown in the order list in order to get correct item.
	 * @return the item id
	 */
	public String getItemID(){
		return this.itemID;
	}
	
	/**
	 * Item:
	 * sets the quantity of the orders
	 * @param quantity: the quantity to be set, passed as a string value
	 */
	public void setQuantity(String quantity){
		//check if the quantity is a numeric value
		if(!(quantity.matches(".*\\d.*")))
		{
			throw new NumberFormatException("The quantity " +quantity + " must be in numeric values");
		}
		this.quantity = Integer.parseInt(quantity);
	}
	
	/**
	 * Item:
	 * sets the quantity
	 * @param quantity the quantity to be set passed as an integer value
	 */
	public void setQuantity(int quantity){
		this.quantity = quantity;
	}
	
	/**
	 * Item:
	 * gets the name of the dish
	 * @return the dish name
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Item:
	 * gets the category of the dish. It can be either STARTER, MAIN, DRINK or DESSERT
	 * @return the category of the dish
	 */
	public String getCategory(){
		return this.category;
	}
	
	/**
	 * Item:
	 * gets the quantity of the dishes ordered
	 * @return the quantity of dishes
	 */
	public int getQuantity(){
		return this.quantity;
	}
	
	/**
	 * Item:
	 * increases the number of dishes to be ordered
	 * @param i the number of dishes to be added to the quantity of dishes ordered
	 */
	public void increaseQuantity(int i){
		//check if the integer value to be added is negative
		if(i<0)
		{
			throw new IllegalArgumentException(" The quantity to be increased must be a positive number");
		}
		//add the the number to be increased 'i' to the quantity
		this.quantity = this.quantity + i;
	}
	
	
	public boolean checkDelivery(){
		if(this.itemStatus){
			return this.delivered;
		}else return false;
	}
	
	
	
	/**
	 * Item:
	 * gets the status of item
	 * @return the item status
	 */
	public boolean getItemStatus(){
		return this.itemStatus;
	}
	
	/**
	 * Item:
	 * gets the completed quantity of item
	 * @return
	 */
	public int getCompletedQuantity(){
		return this.completedQuantity;
	}
	
	
	/**
	 * Item:
	 * This function will increase the completed item quantity by 1
	 * and only work when the value is valid
	 * If the completed quantity reach the required quantity
	 * it will change the item status to true
	 */
	public void increaseCompletedQuantity(){
		if(this.completedQuantity +1 <=this.quantity){
			this.completedQuantity+=1;
			if(this.completedQuantity == this.quantity){
				this.itemStatus = true;
			}
		}
	}
	
	public void itemDelivered(){
		this.delivered = true;
	}
	
	
	/**
	 * Item:
	 * This function will check the quantity and item status
	 * if the item reach the required but status has not been set to true
	 * it will set the status to true and return the status of item
	 * @return
	 */
	public boolean checkItemStatus(){
		if(this.completedQuantity == this.quantity){
			this.itemStatus = true;
			return this.itemStatus;
		}else {
			return false;
		}
	}
	
	
	public void setTableNumber(int i){
		this.tabNum = i;
	}
	
	public int getTableNumber(){
		return this.tabNum;
	}
	
	public String kitchenInfo(){
		return  String.format("%-10s", this.itemID)+
				String.format("%-70s", this.name)+
			    String.format("%-10s", this.completedQuantity)+
				String.format("%-10s", this.quantity)+
				String.format("%-5s", this.tabNum);
	}
	
	/**
	 * Item:
	 * gets the price of the item
	 * @return the item price
	 */
	public double getItemPrice(){
		return this.price;
	}
	
	/**
	 * Item:
	 * calculates the total price of the dish with respect to the number of dishes ordered
	 * @return the total price of the dishes ordered
	 */
	public double getTotalPrice(){
		return this.quantity*this.price;
	}
	@Override
	public int compareTo(Object arg0) {
		Item o = null;
		if(arg0 instanceof Order){
			o = (Item)arg0;
		}

		return 0;
	}
	
	
}
