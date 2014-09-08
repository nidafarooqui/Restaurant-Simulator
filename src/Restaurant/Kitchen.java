package Restaurant;


import java.util.ArrayList;

/**
 * Imagine that their is a real kitchen in our restaurant and it takes work from system.
 * We assume our chefs work together, they take one item to work on each time, they are well communicate so don't get repeated work
 * 
 * @author Sonne
 */
public class Kitchen {
	
	// We assume that 3 chefs work in our kitchen
	final int chef = 3;
	final int waiter = 2;
	
	// this is where they live!  Yes! In an array List!
	ArrayList<Chef> chefList = new ArrayList<Chef>();
	
	ArrayList<Waiter> waiterList = new ArrayList<Waiter>();
	/**
	 * Constructor for Kitchen class 
	 */
	public Kitchen(){
		for(int i= 0;i<this.chef;i++){
			this.chefList.add(new Chef("Chef "+i));
		}
		for(int i= 0;i<this.waiter;i++){
			this.waiterList.add(new Waiter("Waiter "+i));
		}
	}
	
	public void startMad(){
		for(Chef c:this.chefList){
			c.getMad();
		}
	}
	public void endMad(){
		for(Chef c:this.chefList){
			c.clameDown();
		}
	}
	
	
	/**
	 * 
	 * @param i
	 * @return 
	 */
	public boolean addItemToProgress(Item i){
		
		//if the Item is already done it cannot be added to the queue
		if(i.checkItemStatus()){
			return false;
		}else{
			//Search the list to find if someone (chef) is not working right now

			// we first look our chefs see if someone does't have work to do
			for(Chef c:this.chefList){
				//if someone not working, we are going to give him an item to work on
				if(!c.isWorking()){
					//but before we assign a job we should know if someone already processing it 
					
					//if the item quantity more than 1
					if(i.getQuantity()>1){
						//we should know is a certain quantity finished
						//and if someone is working on it, it will be finish soon
						int g=i.getCompletedQuantity();
						for(Chef d:this.chefList){
							if(d.workingItem == i){
								g+=1;
							}
						}
						//if the completed number + item which is in processing less than the total quantity
						if(g<i.getQuantity()){
							//add item;
							c.addItem(i);
							return true;
						}else{
							return false;
						}
						
					}
					//if the item quantity is 1 we just need to know what item other chef is working on
					else{
						//we assume that none is processing it 
						for(Chef d:this.chefList){
							//if we find anyone is processing it 
							//then we can not let anyone to process this item
							if(d.workingItem == i){
								return false;
							}
						}
						//otherwise take it 
						c.addItem(i);
						return true;
					}
				}
			}
			return false;
		}
	}
	
	
	
	public boolean addItemToDelivery(Item i){
		//if the Item is already done it cannot be added to the queue
		if(!i.checkItemStatus()){
			return false;
		}else if(i.checkDelivery()){
			return false;
		}else{
			for(Waiter c:this.waiterList){
				//if someone is not working
				if(!c.isWorking()){
					for(Waiter e:this.waiterList){
						if(e.isWorking()){
							if(e.itemCheck(i)){
								continue;
							}
						}
					}
					
					c.startDelivering(i);
					return true;
				}
			}
			return false;
		}
	}
	
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public String getChefInfo( int index){
		String temp = "";
		if (this.chefList.get(index)!= null && (index<this.chefList.size()&&index>=0)){
			temp = this.chefList.get(index).getProgrssInfo()+"\r\n";
		}
		
		return temp;
	}
	
	
	public String getWaiterInfo( int index){
		String temp = "";
		if (this.waiterList.get(index)!= null && (index<this.waiterList.size()&&index>=0)){
			temp = this.waiterList.get(index).getProgrssInfo()+"\r\n";
		}
		
		return temp;
	}
	
	
	/**
	 * The chef will work in the kitchen
	 * so we make this class inside kitchen class
	 * @author Sonne
	 *
	 */
	public class Chef implements Runnable{
		Item 			workingItem;				// What item is working on right now
		boolean 		isLive;						// The thread status
		String 			name;						// The name of the chef
		String 			info;						// Current progress information
		int 			progress;					// represent the current completeness
		final int 		maxprogess 	= 10;			// the progress like a progress bar
		final int 		normalSpeed = 5;			// the normal speed is 5 second
		final int 		madSpeed 	= 2;			// the faster speed is 2 second
		int 			speed 		= normalSpeed;	// default speed is normal speed
		
		/**
		 * Constructor
		 * @param name The name of Chef
		 */
		public Chef(String name){
			this.name = name;
		}
		
		/**
		 * to assign a job to the chef
		 * @param i the item that you want chef to work on
		 * @return
		 */
		public boolean addItem(Item i ){
			// if chef is working on something right now return false to reject this requirement
			if(this.workingItem!= null){
				return false;
			}else if(this.workingItem == null){
			// if chef is not working on anything assign him this item
				
				// start to counting
				this.progress = 0;
				
				// accept this item
				this.workingItem= i;
				
				// log his working progress
				this.info = this.name + " Working on "+"\r\n"+this.workingItem.itemDetail() +"\r\n";
				
				// he is start working now
				this.isLive =  true;
				
				// to send a log record his activity
				Simulator.getInstance().logWorker(this.name +" is now working on "+ i.itemID,'g');
				
				// job taken
				return true;
			}else return false;
		}
		
		
		/**
		 * When progress reach max progress value 
		 * progress value will be set to true and working item will be deleted
		 */
		public void itemDone(){
			this.workingItem.increaseCompletedQuantity();
			this.info += this.name + " Has Finish " + this.workingItem.itemDetail();
			Simulator.getInstance().logWorker(this.name + " Has Finish " + this.workingItem.itemDetail(),'r');
			Simulator.getInstance().addItem(workingItem);
			this.progress = 0;
			this.workingItem = null;
			this.isLive = false;
			
			
		}
		
		
		public void getMad(){
			this.speed = this.madSpeed;
		}
		
		public void clameDown(){
			this.speed = this.normalSpeed;
		}
		
		public boolean IsLive(){
			return this.isLive;
		}
		
		public boolean isWorking(){
			return (this.workingItem!= null&& this.isLive);
		}
		
		public String getProgrssInfo(){
			return this.info;
		}
		
		public void clearInfo(){
			this.info = "";
		}
		
		public void run() {
			while(true){
				 if(this.workingItem != null&&this.isLive){ 
					 //if current process value reach the max value call the item done function
			        if(this.progress == this.maxprogess){
			        	this.itemDone();
			        }else{
			        	this.progress++;
			        	this.info += "Progress: ("+(this.maxprogess - this.progress)+") " +"\r\n";
					}
				 }
				 try {
			        	// the thread will sleep a random time.
						Thread.sleep((long)(1+(Math.random()*(this.speed-1)))*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

			}
		}		
	}
	
	
	
	public class Waiter implements Runnable{

		Item 			workingItem;				// What item is working on right now
		boolean 		isLive;						// The thread status
		String 			name;						// The name of the chef
		String 			info;						// Current progress information
		int 			progress;					// represent the current completeness
		final int 		maxprogess 	= 5;			// the progress like a progress bar
		int 			speed 		= 2;	// default speed is normal speed
		
		
		public Waiter(String name){
			this.name = name;
		}
		
		
		public boolean startDelivering(Item i){
			if(this.workingItem!= null){
				return false;
			}else if (this.workingItem == null){
				if(i.checkItemStatus()){
					this.workingItem = i;
					// start to counting
					this.progress = 0;
					
					// accept this item
					this.workingItem= i;
					
					// log his working progress
					this.info = this.name + " Delivering "+"\r\n"+this.workingItem.itemDetail() +"\r\n";
					
					// he is start working now
					this.isLive =  true;
					
					// to send a log record his activity
					Simulator.getInstance().logWorker(this.name +" is now working on "+ i.itemID,'g');
					
					// job taken
					return true;
				}else {
					return false;
				}

			}
			return false;
		}
		
		
		public boolean IsLive(){
			return this.isLive;
		}
		
		public boolean isWorking(){
			return (this.workingItem!= null&& this.isLive);
		}
		
		/**
		 * this function check if the current working item is the required item
		 * @param i Item that required to send
		 * @return true: when items are same; False when items are different
		 */
		public boolean itemCheck(Item i){
			if(i == this.workingItem){
				return true;
			}
			return false;
		}
		
		public String getProgrssInfo(){
			return this.info;
		}
		
		public void clearInfo(){
			this.info = "";
		}	
		
		private void itemDelivered(){
			this.workingItem.itemDelivered();
			this.info += this.name + " Delivered " + this.workingItem.itemDetail();
			Simulator.getInstance().logWorker(this.name + " Delivered " + this.workingItem.itemDetail(),'r');
			this.progress = 0;
			Simulator.getInstance().removeItem(workingItem);
			this.workingItem = null;
			this.isLive = false;

			
		}
		
		
		@Override
		public void run() {
			while(true){
				 if(this.workingItem != null&&this.isLive){ 
					 //if current process value reach the max value call the item done function
			        if(this.progress == this.maxprogess){
			        	this.itemDelivered();
			        }else{
			        	this.progress++;
			        	this.info += "Progress: ("+(this.maxprogess - this.progress)+") " +"\r\n";
					}
				 }
				 try {
			        	// the thread will sleep a random time.
						Thread.sleep((long)(1+(Math.random()*(this.speed-1)))*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

			}
			
		}
		
		
		
	}
}
