package Restaurant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

//Collection of menu item
public class Menu {
	private IO io;
	private TreeMap menu = new TreeMap();
	
	//constructor calls the function to read the menu.txt file
	public Menu(){
		this.io = new IO();
		this.readMenu();
	}
	
	/**
	 * To generate (formating) frequency report string,
	 * @return Return the full menu in string 
	 */
	public String printMenu(){
		
		String temp= "                                        MENU                                               "+
				"\r\n"+String.format("%-7s", "ID")+String.format("%-80s", "Name")+String.format("%-10s", "Price(£)")+
				"\n=============================================================================================",
				s= "\r\nSTARTER\r\n",m="\r\nMAIN\r\n",d="\r\nDESSERT\r\n",dr="\r\nDRINKS\r\n";
		
		//iterate through the menu and display its details
		Iterator iterator = menu.keySet().iterator();
		while (iterator.hasNext()) 
		{
				Object key = iterator.next();
				Item i = ((Item)(menu.get(key)));
				
			//if the category is STARTER display the details for that
		      if(i.getCategory().equals("STARTER"))
		      {
		    	  s+=String.format("%-7s", i.getItemID())+String.format("%-80s", i.getName())+String.format("%-10s", "£"+i.getItemPrice()+"")+"\r\n";
		      }
		    //if the category is MAIN display the details for that
		      else if(i.getCategory().equals("MAIN"))
		      {
		    	  m+=String.format("%-7s", i.getItemID())+String.format("%-80s", i.getName())+String.format("%-10s", "£"+i.getItemPrice()+"")+"\r\n";
		      }
		    //if the category is DESSERT display the details for that
		      else if(i.getCategory().equals("DESSERT"))
		      {
		    	  d+=String.format("%-7s", i.getItemID())+String.format("%-80s", i.getName())+String.format("%-10s", "£"+i.getItemPrice()+"")+"\r\n";
		      }
		    //if the category is DRINK display the details for that
		      else if(i.getCategory().equals("DRINK"))
		      {
		    	  dr+=String.format("%-7s", i.getItemID())+String.format("%-80s", i.getName())+String.format("%-10s", "£"+i.getItemPrice()+"")+"\r\n";
		      }
		}
		//add the STARTER, MAIN, DESSERT and DRINK to the format and return it
		return temp+"\n\r"+s+"\n\r"+m+"\n\r"+d+"\n\r"+dr;
	}

	/**
	 * The reason why we made this function was because, there are different type of data structures in different use
	 * this method transform tree map to a set, in order to prevent duplicate item in frequency report
	 * @return the hash set object of items
	 */
	public Set transformDataStructure(){
		HashSet<Item> s = new HashSet<Item>();
		Object key;
		Iterator iter = menu.keySet().iterator();
		
		//iterate through the items list and add it to the hash set
		while(iter.hasNext()){
			Item i =(Item) menu.get(iter.next());
			s.add(i);
		}
		return s;
	}
	
	//calls the readMenuTxt function which reads the menu.txt file
	private void readMenu(){
		this.menu = this.io.readMenuTxt();
	}
	
	public Item getRandomItem(){
		String s = String.format("%06d", Simulator.getInstance().getRandom().nextInt((this.menu.size()))+1);
		Item t = (Item)this.menu.get(s);
		//System.out.println("XXRandom Item: "+s);
		return t;
	}
	
	
}
