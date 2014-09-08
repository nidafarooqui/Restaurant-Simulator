package Restaurant;

/**
 * The table will be an object that not actually use in the system
 * we made this class because in the reality the table will relate to an order.
 * 
 * In the very early we are trying to implement a Comparable interface which used for comparing two Table Object in a tree map
 * but we abandoned this idea because we are not using this Table class for any use. it is just a middle layer connecting two classes.
 * @author Sonne
 *
 */
public class Table implements Comparable{
	
	/*
	 * A table will be assigned an order
	 */
	private Order currentOrder = null;
	
	
	/**
	 * Table:
	 * When we initialize this object, we should give an order
	 * which means this table is currently being used.
	 * @param order
	 */
	public Table (Order order){
		this.currentOrder = order;
	}

	/**
	 * gets the assigned order
	 * @return 
	 */
	public Order getTableOrder(){
		return this.currentOrder;
	}
	
	/**
	 * Table:
	 * This function return the assigned order's status.
	 * @return
	 */
	public boolean checkTableStatus(){
		return this.currentOrder.checkOrderStatus();
	}

	@Override
	public int compareTo(Object arg0) {
		Order o = null;
		if(arg0 instanceof Order){
			o = (Order)arg0;
		}
		
		if(this.currentOrder.tableNum>o.tableNum){
			return 1;
		}
		else if(this.currentOrder.tableNum < o.tableNum){
			return -1;
		}
		
		
		// TODO Auto-generated method stub
		return 0;
	}

	
	
}
