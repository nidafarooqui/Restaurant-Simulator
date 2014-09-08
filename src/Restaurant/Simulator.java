package Restaurant;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * A singleton class
 * All the runnable object will be assign a thread in this class
 * it does basic mechanism that transfer object from 
 * @author Sonne
 *
 */
class Simulator implements Runnable{
	
	
	private Date dt = new Date();
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	
	private Random r = new Random();
	
	// Static Instance
	private static Simulator instance = null;
	
	private Kitchen kitchen = new Kitchen();
	
	private ArrayList<Table> tableList = new ArrayList<Table>(); 
	private ArrayList<Table> tableList_Queue = new ArrayList<Table>(); 
	private ArrayList<Item> itemList = new ArrayList<Item>(); 
	
	//Log string
	private String log= "";
	
	//Thread for the simulation progress
	private Thread mainThread = new Thread(this);
	
	//Thread for kitchen
	private ArrayList<Thread> chefThread = new ArrayList<Thread>();
	private ArrayList<Thread> waiterThread = new ArrayList<Thread>();
	
	// Interface instance
	private Simulator_Interface THEINTERFACE = null;
	
	
	protected Simulator(){
	}
	
	/**
	 * Simulator:
	 * To set the interface instance
	 * so all the function in simulator will be able to access
	 * @param y
	 */
	public void SETINTERFACE(Simulator_Interface y){
		this.THEINTERFACE = y;
	}
	
	/**
	 * Simulator:
	 * This function will start all the threads that regested in the Simulator instance;
	 * @param j
	 */
	public void ThreadStart(Simulator_Interface j){
		this.chefThread.add(new Thread(this.kitchen.chefList.get(0)));
		this.chefThread.add(new Thread(this.kitchen.chefList.get(1)));
		this.chefThread.add(new Thread(this.kitchen.chefList.get(2)));
		
		this.waiterThread.add(new Thread(this.kitchen.waiterList.get(0)));
		this.waiterThread.add(new Thread(this.kitchen.waiterList.get(1)));
		this.mainThread.start();
		
		for(Thread i:chefThread){
			i.start();
		}
		for(Thread i:waiterThread){
			i.start();
		}
	}
	
	
	/**
	 * Simulator:
	 * To get Simulator instance
	 * @return
	 */
	static Simulator getInstance(){
		if(instance == null){
			instance = new Simulator();
		}
		return instance;
	}
	
	/**
	 * Simulator:
	 * return the simulator thread is alive information
	 * @return
	 */
	public boolean getSimularotThreadInfo(){
		return this.mainThread.isAlive();
	}
	
	
	public Random getRandom(){
		return r;
	}
	/**
	 * Simulator:
	 * return the chef thread is alive information
	 * @param index the index of chef in the list
	 * @return
	 */
	public boolean getChefThreadInfo(int index){
		return this.chefThread.get(index).isAlive();
	}
	
	
	public boolean getWaiterThreadInfo(int index){
		return this.waiterThread.get(index).isAlive();
	}
	
	
	
	//Kitchen Functions
	
	
	//Firstly the list of ordered item will come in to the list 
	/**
	 * Simulator:
	 * The table info will add to the table set
	 * @param t
	 */
	public void addTable(Table t){
		int temp= checkTableList(t);
		if(temp==0){
			this.tableList.add(t);
			//this.logWorker("The Table:"+t.getTableOrder().getTableNumber()+" Has been added to the Table List("+ this.tableList.size()+")");
			this.logWorker("The Table:"+t.getTableOrder().getTableNumber()+" Has been added to the Table List("+ this.tableList.size()+")",'g');
		}else if(temp==1){
			this.tableList_Queue.add(t);
			//this.logWorker("The Table:"+t.getTableOrder().getTableNumber()+" Has been added to the Table Queue List("+ this.tableList_Queue.size()+") Wating for the current table finish");
			this.logWorker("The Table:"+t.getTableOrder().getTableNumber()+" Has been added to the Table Queue List("+ this.tableList_Queue.size()+") Wating for the current table finish",'g');
		}else{
			
		}
	}
	
	/**
	 * Add an item to the item queue
	 * @param i
	 * @return
	 */
	public boolean addItem(Item i){
		if(i.checkItemStatus()){
			this.itemList.add(i);
			return true;
		}else return false;
	}
	
	/**
	 * to remove an item from item list
	 * @param i
	 */
	public void removeItem(Item i){
		this.itemList.remove(i);
	}
	
	
	/**
	 * Simulator:
	 * This method will check the table and in the list
	 * see if there is some duplicate e.g.
	 * 1.same table number but different order in the same time
	 * 2.the order is closed and has been completed
	 * 3.the order is cancelled
	 * @param t 
	 * @return if its okay return true else false
	 */
	private int checkTableList(Table t){
		//The table (order) state should be uncompleted
		if(t.getTableOrder().getOrderStatus()==OrderStatus.Uncompleted){
			
			
			if(this.tableList.isEmpty()){
				//Remove the completed table in the list
				for(Table temp:this.tableList){
					if(temp.getTableOrder().getOrderStatus()!= OrderStatus.Uncompleted);
					this.tableList.remove(temp);
					//this.logWorker("The Table:"+t.getTableOrder().getTableNumber()+" The Ordernumber:" + temp.getTableOrder().getOrderNumber() +" Is completed and has been removed from the list");
					this.logWorker("The Table:"+t.getTableOrder().getTableNumber()+" The Ordernumber:" + temp.getTableOrder().getOrderNumber() +" Is completed and has been removed from the list",'r');
				}
			}else{
				for(Table temp:this.tableList){
					if(temp.getTableOrder().getTableNumber()==(t.getTableOrder().getTableNumber())){
						//Put it in to the queue
						this.logWorker("The Table:" + t.getTableOrder().getTableNumber() +" Is valid table but current list is full. Ready to join the Table queue",'m');
						return 1;
					}
				}
				this.logWorker("The Table:" + t.getTableOrder().getTableNumber() +" Has been accept to join to the list",'b');
				return 0;
			}
			
			//To check the duplicate table number in the list
			//The return means adding that lead to a duplicate table number
			//In the program we assume that the table can be maximum at 10
			//When a new Table (Order) try to join the list it has to be not be same table number as which is in the list

			//Its okay just add it
			//this.logWorker("The Table:" + t.getTableOrder().getTableNumber() +" Has been accept to join to the list");
			this.logWorker("The Table:" + t.getTableOrder().getTableNumber() +" Has been accept to join to the list",'b');
			return 0;
		}else{
			//Skip this, because it might be a completed order or a cancelled order.
			//this.logWorker("The Table:" + t.getTableOrder().getTableNumber() +" Join to the list faild because it is a completed order or a cancelled order");
			this.logWorker("The Table:" + t.getTableOrder().getTableNumber() +" Join to the list faild because it is a completed order or a cancelled order",'o');
			return 2;
		}
	}
	
	
	/**
	 * Simulator:
	 * This function will check table queue and current processing list
	 * if any order have occupied with a specific table number
	 * the rest orders with same table number will not be allow to move to the processing list
	 * 
	 * Otherwise if the order in the table queue have a unique tale number that no order in the processing list has it
	 * the order will be move to the processing list
	 * 
	 */
	private void queueWorker(){
		if(!this.tableList_Queue.isEmpty()){
			for(Table t:this.tableList_Queue){
				boolean b = true;
				for(Table temp:this.tableList){
					
					if(temp.getTableOrder().getTableNumber()==(t.getTableOrder().getTableNumber())){
						b = false;
					}
				}
				if (b){
					if(this.checkTableList(t)==0){
						this.addTable(t);
						this.tableList_Queue.remove(t);
						return;
					}
				}
			}
		}
	}
	
	/**
	 * Simulator:
	 * This function will add String to the Simulator Interface
	 * @param s
	 * @param c
	 */
	public void logWorker(String s,char c){
		this.log = this.timeFormat.format(dt)+": "+ s+"\r\n"+this.log;
		if(this.THEINTERFACE!=null){
		switch(c){
			case 'b': {
				this.THEINTERFACE.insertDocument(this.timeFormat.format(dt)+": "+ s+"\r\n",Color.BLUE);
				break;
			}
			case 'g': {
				this.THEINTERFACE.insertDocument(this.timeFormat.format(dt)+": "+ s+"\r\n",Color.GREEN);
				break;
			}
			case 'r': {
				this.THEINTERFACE.insertDocument(this.timeFormat.format(dt)+": "+ s+"\r\n",Color.RED);
				break;
			}
			case 'o': {
				this.THEINTERFACE.insertDocument(this.timeFormat.format(dt)+": "+ s+"\r\n",Color.ORANGE);
				break;
			}
			case 'm': {
				this.THEINTERFACE.insertDocument(this.timeFormat.format(dt)+": "+ s+"\r\n",Color.MAGENTA);
				break;
			}
			default: {
				this.THEINTERFACE.insertDocument(this.timeFormat.format(dt)+": "+ s+"\r\n",Color.BLACK);
				break;
			}
			
		
		}
		}
	
	}
	
	public String getLog(){
		return this.log;
	}
	
	public String getTime(){
		return this.timeFormat.format(dt);
	}
	
	public String getTime(SimpleDateFormat sdf){
		return sdf.format(dt);
	}
	/**
	 * Simulator:
	 * To get detailed information from all the objects in table list 
	 * @return
	 */
	public String getSimulatorInfo(){
		String temp = "";
		for(Table t:this.tableList){
			temp +="Table:" + t.getTableOrder().getTableNumber() + "     "+t.getTableOrder().getOrderStatus() +"\r\n";
			for(Item i:t.getTableOrder().getItems()){
				temp += String.format("%-15s", i.getItemID())+String.format("%-15s", i.getCompletedQuantity())+String.format("%-15s", i.getQuantity())+String.format("%-10s", i.getItemStatus())+"\r\n";
			}
		}
		
		return temp;
	}
	
	
	/**
	 * Simulator:
	 * this function return you a string that including all the item information in the item list
	 * @return the full string
	 */
	public String getDeliveryInfo(){
		String temp = "";
		for(Item i: this.itemList){
			temp +=i.getItemID()+"("+ this.itemList.indexOf(i)+")\r\n";
			
		}
		
		return temp;
	}
	
	/**
	 * Simulator:
	 * To get what table is waiting to join the processing list in the table queue list 
	 * @return
	 */
	public String getTableQueueInfo(){
		String temp = "";
		for(Table t:this.tableList_Queue){
			temp +="Table:"+t.getTableOrder().getTableNumber() + " ("+this.tableList_Queue.indexOf(t)+")\r\n";
		}
		return temp;
	}
	
	/**
	 * Simulator:
	 * This function will take an item from the first object in the processing list and pass to the kitchen
	 * it will return the table object when the order of that table is completed
	 * @param index the index of order in the processing list; by default is 0;
	 * @return table object
	 */
	private Table orderProcessor(int index){
		Table t = null;
		if(!this.tableList.isEmpty()){
			t = this.tableList.get(index);

			for(Item i :t.getTableOrder().getItems()){
				if(this.kitchen.addItemToProgress(i)){
					
					this.logWorker("Item(From Order:" +t.getTableOrder().getOrderNumber()+"): "+ i.getItemID() + " is take by Kitchen and will in progrss soon",'b');
				}
			}
			if(t.checkTableStatus()){
				return t;
			}else{
				return null;
			}
			
		}else{
			return null;
		}
	}
	
	/**
	 * This function will work same as "orderProcessor" function, which send the item to the waiter;
	 */
	private void deliveryProcessor(){
		if(!this.itemList.isEmpty()){
			if(this.itemList.size()==1){
				this.kitchen.addItemToDelivery(this.itemList.get(0));
			}else if(this.itemList.size()>=2){
				this.kitchen.addItemToDelivery(this.itemList.get(0));
				this.kitchen.addItemToDelivery(this.itemList.get(1));
			}
		}
		
	}
	
	
	/**
	 * Simulator:
	 * gets kitchen object 
	 */
	public Kitchen getKitchen(){
		return this.kitchen;
	}




	@Override
	public void run() {
		while(true){
		Table t;
		if(this.tableList.size()>=1){
			t = this.orderProcessor(0);			
			if(t != null){
				t.getTableOrder().orderCompleted();
				this.tableList.remove(t);
			}
		}
		queueWorker();
		deliveryProcessor();
		try {
			Thread.sleep(100L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
