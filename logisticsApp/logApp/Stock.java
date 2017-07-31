package logApp;

import java.util.HashMap;

public class Stock {

	private HashMap<Item, Integer> itemList; //HashMap of the Item and the Quantity of it
	
	public Stock(HashMap<Item, Integer> itemListIn){
		this.itemList = itemListIn;
	}
	
	public HashMap<Item, Integer> getItemList(){
		return itemList;
	}
	
	public Integer get(Item itemIn){
		return itemList.get(itemIn);
	}
	
	public void put(Item itemIn, Integer intIn){
		itemList.put(itemIn, intIn);
	}
	
}
