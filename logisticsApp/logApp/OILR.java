package logApp;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class OILR { //Order Item Logistics Record
					//Takes list of solutions and calculates/stores important values

	
	private ArrayList<Record> solList = new ArrayList<Record>(); //input coming in with the solution list to process
																//constructed to be in order of earliest solution first
	private Item itemID; //Item in question
	private int itemRequested; //number of items requested 
	private int itemReceived; //number of items received - calculated
	private int totalCost; //total cost of the order - calculated
	private int firstDelDay; //earliest day of arrival
	private int lastDelDay; //latest day of arrival

	private ArrayList<Integer> costList = new ArrayList<Integer>(); 
	
	
	public OILR (Item itemIn, int itemRequestedIn) throws NullPointerException, InputMismatchException { //this will load in the solution list 
		this.solList = new ArrayList<Record>();
		if (itemIn != null){
			itemID = itemIn;
		}
		else
			throw new NullPointerException();
		
		if(itemRequestedIn<0){
			throw new InputMismatchException("itemRequested cannot be negative");
		}
		else
			itemRequested = itemRequestedIn;
		
		itemReceived = 0;
		totalCost = 0;
		firstDelDay = 0;
		lastDelDay = 0;
	}
	
	public void addSolution(Record solutionIn){ //each factory will add its solution via this method
		solList.add(solutionIn);
	}
	
	public Item getItemID(){
		return itemID;
	}
	
	public int getItemReceived(){
		return itemReceived;
	}
	
	public ArrayList<Integer> getCostList(){
		return costList;
	}
	
	public int getTotalCost(){
		return totalCost;
	}
	
	public int getFirstDelDay(){
		return firstDelDay;
	}
	
	public int getLastDelDay(){
		return lastDelDay;
	}
	
	public void calcAll(){ //runs the method to calculate all important values
							//by running through the solution List
		
		for (Record r : solList){
			//itemReceived
			itemReceived = itemReceived + r.getItemsGiven();
			
			//individual record cost
			int itemCost;
				itemCost = (itemID.getItemPrice() * r.getItemsGiven());//cost of item * value given
			int procCost;
				procCost = (int) (Math.round((r.getPercentAtStart() * 300)) + (r.getPEndDay() - r.getPStartDay() -1) + Math.round((r.getPercentAtEnd() * 300))); //for first and last day need to calc percent of cost based on time used that day, for the rest of the days use full cost
			int transCost;
				transCost = ((r.getArrivalDay() - (r.getPEndDay()+1) + 1) * 500);//(arrivalday - tavel start + 1)* 500
			
			int indivCost = (itemCost + procCost + transCost);
			
			
			
			//Add indiv to totalCost
			totalCost = totalCost + indivCost;
			
			//firstDelDay
			if (firstDelDay == 0){//check if a val has been assigned, if not assign
				firstDelDay = r.getArrivalDay();
			}
			else{ //if so compare and smaller is firstDayDay
				if(r.getArrivalDay()<firstDelDay){
					firstDelDay = r.getArrivalDay();
				}
			}
			
			//lastDelDay
			if (lastDelDay == 0){//check if a val has been assigned, if not assign
				lastDelDay = r.getArrivalDay();
			}
			else{ //if so compare and larger is lastDelDay
				if(r.getArrivalDay()>lastDelDay){
					lastDelDay = r.getArrivalDay();
				}
			}
			
			//costList
			costList.add(solList.indexOf(r),indivCost);
		}
	}
	
	public String toString(){ //build and return the solution plan
		StringBuilder sb = new StringBuilder();
		
		sb.append("Item ID: " + itemID.getItemId() + ", Quantity: " + itemRequested + ", Cost: $" + totalCost + "\n");
		sb.append("\n");
		sb.append("Item Arrivals: \n");
		Double totAdd = 0.0;
		for (Record r : solList){
			totAdd = totAdd + (((double)r.getItemsGiven())/((double)itemRequested)*100);
			sb.append("- Day " + r.getArrivalDay() + ": " + r.getItemsGiven() + " (" + (((double)r.getItemsGiven())/((double)itemRequested)*100) + "%, " 
									+ totAdd + "% of total)\n");
		}
		sb.append("\n");
		sb.append("Logistics Details: \n");
		for (Record r: solList){
			sb.append((solList.indexOf(r)+1) + ") Name: " + r.getSite().getName() + " (" + r.getItemsGiven() + " of " + itemRequested + ")\n");
			sb.append("  Cost: $" + costList.get(solList.indexOf(r)) + "\n");
			sb.append("    Processing Start:  Day " + r.getPStartDay() + "\n");
			sb.append("    Processing End:    Day " + r.getPEndDay() + "\n");
			sb.append("    Travel Start:      Day " + (r.getPEndDay()+1) + "\n");
			sb.append("    Travel End:        Day " + r.getArrivalDay() + "\n");
			sb.append("    ---------------\n");
			sb.append("    Arrival:  Day " + r.getArrivalDay() + "\n\n");
		}
		
		return sb.toString();
	}
	
	
	
	
}
