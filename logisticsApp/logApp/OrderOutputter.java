package logApp;

import java.util.ArrayList;

public class OrderOutputter { //This is for an order to hold all of its Item Records
	
	private ArrayList<OILR> orderOut = new ArrayList<OILR>();
	
	public OrderOutputter(){
		orderOut = new ArrayList<OILR>();
	}
	
	public void addItem(OILR solutionIn){
		orderOut.add(solutionIn);
	}
	
	public ArrayList<OILR> getOrderOut(){
		return orderOut;
	}
	
	public String toString(Order o){
		StringBuilder sb = new StringBuilder();
		
		sb.append("Processing Solution: \n");
		sb.append("Order ID: " + o.getOrderId() + "\n");
		sb.append("- Destination: " + o.getOrderDestination() + "\n");
		
		int totalCost = 0;
		int firstDelDay = 0;
		int lastDelDay = 0;
		
		for (OILR oi : orderOut){
			totalCost = totalCost + oi.getTotalCost();
			
			if (firstDelDay==0){
				firstDelDay = oi.getFirstDelDay();
			}
			else{
				if (oi.getFirstDelDay()<firstDelDay){
					firstDelDay = oi.getFirstDelDay();
				}
			}
			
			if (lastDelDay==0){
				lastDelDay = oi.getLastDelDay();
			}
			else{
				if (oi.getLastDelDay()>lastDelDay){
					lastDelDay = oi.getLastDelDay();
				}
			}
			
		}
		sb.append("- Total Cost: $" + totalCost + "\n");
		sb.append("- 1st Delivery Day: " + firstDelDay + "\n");
		sb.append("- Last Delivery Day: " + lastDelDay + "\n");
		sb.append("- Order Items:\n");
		sb.append("\tItem ID\tQuantity\tCost\tNum. Sources\tFirst Day\tLast Day\n");
		for (OILR oi : orderOut){
			sb.append("\t" + oi.getItemID().getItemId() + "   " + oi.getItemReceived() + "\t\t" + oi.getTotalCost() + "\t" + oi.getCostList().size() + 
					"\t\t" + oi.getFirstDelDay() + "\t\t" + oi.getLastDelDay() + "\n"); 
		
		}
		sb.append("  ------------------\n");
		
		return sb.toString();
	}
	
}
