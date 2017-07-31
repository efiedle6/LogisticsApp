package logApp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class FacilityService {

	private static final int TRAVEL_TIME = 8;
	private static final int MPH = 50;
	private static ArrayList<Facility> facilityList;
	private static FacilityLoader delegate;
	private static FacilityService instance;

	private FacilityService(String fileName, String fileType, String facilityType,
			ItemService itemServ) throws InputMismatchException {
		if (fileName instanceof String && fileType instanceof String && facilityType instanceof String
				&& itemServ instanceof ItemService) { // Check that we are receiving correct Inputs
			delegate = FacilityLoaderFactory.build(fileType); // create the appropriate FacilityLoader based on the filetype
			loadFacilities(fileName, facilityType, itemServ); // Load the facilities from that file

		} else {
			throw new InputMismatchException("Received incorrect inputs");
		}
	}

	public static void loadFacilities(String fileName, String facilityType, ItemService itemServ) { // method to initiate loading facilities based on delegate

		facilityList = delegate.loadFacilities(fileName, facilityType, itemServ);
	}

	public static FacilityService getInstance(String fileName, String fileType, String facilityType,ItemService itemServ) { // Singleton Facade Pattern
		if (fileName instanceof String && fileType instanceof String) {// Check that we are receiving Strings as Inputs

			if (instance == null)
				instance = new FacilityService(fileName, fileType, facilityType, itemServ);
			return instance;
		} else {
			throw new InputMismatchException(
					"Expecting type String but receiving types: "
							+ fileName.getClass() + " and "
							+ fileType.getClass());
		}
	}

	public ArrayList<Facility> getFacilityList() { // get the facilityList Array
		return facilityList;
	}

	public Facility getFacility(String facilityName) { // return the facility asked for
		for (int i = 0; i < facilityList.size(); i++) {
			if (facilityList.get(i).getName().equals(facilityName)) {
				return facilityList.get(i);
			}
		}
		return null;
	}

	private void facilityStatus(String facilityName) { // Print the facility status of one facility; helper method of printAllFacilityStatus
		Facility currentFacility = getFacility(facilityName); // make sure you get the proper Facility
		System.out.println("\n" + currentFacility.getName()); // print the facility cityname

		StringBuilder linkString = new StringBuilder(); // create the string containing all of the links for that city
		for (String name : (currentFacility.getLinks()).keySet()) {
			String key = name.toString();
			String distance = ( currentFacility.getLinks()).get(name).toString();
			Double distance2 = Double.parseDouble(distance);
			Double byDayDistance = (distance2 / (TRAVEL_TIME * MPH));

			DecimalFormat df = new DecimalFormat("##.##"); // round to 2 decimal places
			Double byDayDistance2 = Double
					.parseDouble(df.format(byDayDistance));

			linkString.append("\n\tCity: " + key + " Distance: "
					+ byDayDistance2 + "d");
		}
		System.out.println("Direct Links: " + linkString); // print link string

		System.out
				.println("Active Inventory: \n" + "\t Item ID" + "\tQuantity");

		StringBuilder depletedInv = new StringBuilder(); // for saving depleted items
		for (Item name : currentFacility.getStock().getItemList().keySet()) {
			String key = name.getItemId();
			String strQuantity = currentFacility.getStock().get(name)
					.toString();
			Integer quantity = Integer.parseInt(strQuantity);

			if (quantity > 0) {
				System.out.printf("%1$15s %2$13s \n", key, quantity); // print create the items currently active
			} else {
				depletedInv.append(key + ", "); //if quantity for an item is 0 place it in this list
			}
		}

		System.out.println("Depleted (Used-up) Inventory: " + depletedInv); // for printing the depleted items

		System.out.println("Schedule: "); // printing the first 20 days of the schedule

		StringBuilder days = new StringBuilder(); // for printing the day number
		days.append("         Day  ");

		StringBuilder available = new StringBuilder(); // for printing the available number of items per day
		available.append("   Available  ");
		
		if (currentFacility.getSchedule().getHighestDay()>20){	//if days of processing is > 20 show all those days //otherwise show up to 20
			for (int h = 1; h <= (currentFacility.getSchedule().getHighestDay()+1); h++) { // for going through all days of the schedule
				days.append("\t" + (h));
				if (currentFacility.getSchedule().getItemsLeft(h) == null){
					available.append("\t" + currentFacility.getRate());
				}
				else
				available.append("\t" + currentFacility.getSchedule().getItemsLeft(h));
			}
		}
		else {
			for (int h = 1; h <= 20; h++) { // for adding the first 20 days of the schedule
				days.append("\t" + (h));
				if (currentFacility.getSchedule().getItemsLeft(h) == null){
					available.append("\t" + currentFacility.getRate());
				}
				else
				available.append("\t" + currentFacility.getSchedule().getItemsLeft(h));
			}			
		} 
		
		System.out.println(days); // printing days
		System.out.println(available); // printing available
		System.out.println("_________________________");

	}

	public void printAllFacilityStatus() { // print the status of each facility
		System.out.println("\nPrinting Facility Status For All Facilities: ");
		for (int i = 0; i < getFacilityList().size(); i++) {
			facilityStatus(getFacilityList().get(i).getName());
		}
	}

	public Boolean facilityNameInFacilityList(ArrayList<Facility> facilityList,
			String facilityName) {
		for (int i = 0; i < facilityList.size(); i++)
			if (facilityList.get(i).getName().equals(facilityName)) {
				return true;
			} else {
			}
		return false;
	}
	
	public int getTRAVEL_TIME(){
		return TRAVEL_TIME;
	}
	public int getMPH(){
		return MPH;
	}

}
