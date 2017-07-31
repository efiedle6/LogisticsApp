package logApp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;

public class OrderProcessor {

	private static OrderProcessor instance;
	
	private OrderProcessor(ItemService itemServ, FacilityService facilityServ, NetworkService networkServ, OrderService orderServ) throws InputMismatchException {
		if (itemServ instanceof ItemService && facilityServ instanceof FacilityService && networkServ instanceof NetworkService && orderServ instanceof OrderService) { // Check that we are receiving correct Inputs
			processOrders(itemServ, facilityServ, networkServ, orderServ); // Load the facilities from that file

		} else {
			throw new InputMismatchException("Received incorrect inputs");
		}
	}

	public static OrderProcessor getInstance(ItemService itemServ, FacilityService facilityServ, NetworkService networkServ, OrderService orderServ) { // Singleton Facade Pattern
		// Check that we are receiving Strings as Inputs
		if (itemServ instanceof ItemService && facilityServ instanceof FacilityService && networkServ instanceof NetworkService && orderServ instanceof OrderService) {
			
			if (instance == null)
				instance = new OrderProcessor(itemServ, facilityServ, networkServ, orderServ);
			return instance;
		} else {
			throw new InputMismatchException("Receiving incorrect inputs");
		}
	}
	
	//Processes all the Orders in the orderList of the OrderService that was passed to it
	public void processOrders(ItemService itemServ, FacilityService facilityServ, NetworkService networkServ, OrderService orderServ){ 
		if (itemServ instanceof ItemService && facilityServ instanceof FacilityService && networkServ instanceof NetworkService && orderServ instanceof OrderService) {
		int count = 0;
		for (Order o : orderServ.getOrderList()){ //for each Order
			count = count + 1;
			for(Item i : o.getOrderItems().keySet()) {//for each item in the Order
				//System.out.println("Item: " + i.getItemId());
				OILR oilrObj = new OILR(i, o.getOrderItems().get(i));//create the OILR item to store solutions
				
				int facCount = -1; //create an int to count the number of facilities holding that item
				ArrayList<Facility> facHoldingList = new ArrayList<Facility>((facilityServ.getFacilityList().size()+1)); //create list to hold facilities containing item
				ArrayList<Double> facDistList = new ArrayList<Double>((facilityServ.getFacilityList().size()+1));//Create another array list to hold the distances 
																										//to each facility in the same index # as facHoldingList
				for(Facility f : facilityServ.getFacilityList()){ //iterate through the facility list and see if it holds that item
					if (f.getStock().getItemList().containsKey(i) && (f.getStock().get(i)!= 0)){ //make sure the item has quantity if it is there
						facCount = facCount+1; //add to the number of facilities holding said item
						facHoldingList.add(facCount, f); //add the facility to the list in the index it got from facCount
						facDistList.add(facCount, networkServ.shortestPath(o.getOrderDestination(), facHoldingList.get(facCount).getName())); 
					}//else do nothing
				}
				
				if (facCount > -1){
					while(facDistList.contains(0.0)){ //get rid of a facility finding itself as the shortest dist
						facDistList.remove(facDistList.indexOf(0.0));
					}
					//System.out.println(facDistList.toString());
					int milesPerDay = facilityServ.getTRAVEL_TIME() * facilityServ.getMPH();
					
					ArrayList<Double> facTimeList = new ArrayList<Double>(facDistList.size());
					List<Record> recordList = new ArrayList<Record>(facDistList.size());
					
					//for each facility in the generate a Record/Dist(Time) and add it to Lists
					for (Double d : facDistList){ 
						facTimeList.add(facDistList.indexOf(d), d/milesPerDay);
						int orderQuantity = o.getOrderItems().get(i); //number of items to be processed
						Facility site = facHoldingList.get(facDistList.indexOf(d));
						
						int procEndDay = site.getSchedule().returnProcEndDay(o.getOrderTime(), orderQuantity); //get day that ProcessingEnds
				
						Record aRecord = new Record(site, site.getStock().get(i), site.getSchedule().getProcStartDay(o.getOrderTime(), orderQuantity), 
														procEndDay, facTimeList.get(facDistList.indexOf(d)));
						recordList.add(aRecord);
					}
					
					//Sort recordList according to arrival Day
					Collections.sort(recordList, new Comparator<Record>(){
					    public int compare(Record left, Record right) {
					        return (int) (left.getArrivalDay() - right.getArrivalDay());
					    }
					});
					
					int orderQuantity = o.getOrderItems().get(i); 
					
					while (!recordList.isEmpty()) { //when recordList is empty we are out of facilities to give items so end
						if (orderQuantity > 0) { //so when orderQuantity is greater than 0
							if(orderQuantity >= recordList.get(0).getItemCount()){ //if the order needs more/equal # items than the Record can give
								orderQuantity = orderQuantity - recordList.get(0).getItemCount();
								recordList.get(0).setItemsGiven(recordList.get(0).getItemCount());//it gives all its items so set it to its max
								
								recordList.get(0).getSite().getStock().put(i, 0);//change the inventory (Stock) of the Facility in the record to 0 because it gave all its items
								
								recordList.get(0).getSite().getSchedule().assignItems(o.getOrderTime(), recordList.get(0).getItemCount()); //update the schedule for number of items given
								recordList.get(0).setPercentAtEnd();//find and set the % of time used at the end of 
								
								oilrObj.addSolution(recordList.remove(0)); //this Record has given all it can to remove it from the list and add it to Solutions
								
							}
							else{ //Record has more items to give than order Needs
								recordList.get(0).setItemsGiven(orderQuantity); //the Record gives up to the number the order needs because it can
								
								Integer oldStockQuant = recordList.get(0).getSite().getStock().get(i); //retrieve the quantity of the facilities stock of that item
								Integer newStockQuant = oldStockQuant - orderQuantity; //remove from the facilities Stock the amount given
								recordList.get(0).getSite().getStock().put(i, newStockQuant);//change the inventory (Stock) of the Facility in the record to new value
								
								recordList.get(0).getSite().getSchedule().assignItems(o.getOrderTime(), orderQuantity); //update the schedule for number of items given
								recordList.get(0).setPercentAtEnd();//find and set the % of time used at the end of 
								
								orderQuantity = 0; //all orderItems taken care of
								
								oilrObj.addSolution(recordList.remove(0)); //this Record has completed the order so remove it from the list and add it to Solutions
							}
						}
						else{
							recordList.remove(0);//if orderQuantity is 0 remove any unneccesary Records without adding to solution in order to get out of loop
						}
					}//end while loop 
					
					//at this point if there has been a solution found orderQuantity will be 0, if not it will be > 0
					if (orderQuantity > 0){ //so remind to Back-Order these items
						System.out.println("No facilities found for " + orderQuantity + " of Item " + i.getItemId() + "\nSo need to Back-Order.");
					}
					
					//need to generate the logistics record output for the solution
					
					
				}
				else { //do nothing if no facility contains said item and print what happened
					System.out.println("No facility was holding item " + i.getItemId() + " for Order " + o + "\n\tPlacing item on Back-Order");
					
				}
				
				//here we've been through all facilities that an Order will get to receive an Item
				oilrObj.calcAll(); //run the method to calculate values for this Item     
				
				o.getOrderOutList().addItem(oilrObj); //add the OILR oilrObj to the orders OrderOutputter
				
				//to test
				//System.out.println(oilrObj.toString());
				
			} //end for Item - before we hit here need to add all OILRs to the orders OrderOutputter
			
			System.out.println("");
			System.out.println("Order Number: " + count);
			System.out.println(o.toString());
			System.out.println(o.getOrderOutList().toString(o));
		}//here we have added all OILRs to the OrderOutputter so we can output the order in the correct format
		
		
		orderServ.getOrderList().clear();//remove all orders from the list
		}
		else {
			throw new InputMismatchException("Receiving incorrect inputs");
		}
	}
	
	
}
