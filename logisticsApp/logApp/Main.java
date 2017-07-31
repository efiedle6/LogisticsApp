package logApp;

public class Main {

	public static void main(String[] args) {
		
		//list input files, file types, and Object types
		String itemFileName = "ItemsFile.xml";
		String itemFileType = "XML";
		String itemType = "ItemStandard";

		String facilityFileName = "FacilityFile.xml";
		String facilityFileType = "XML";
		String facilityType = "FacilityStandard";

		String orderFileName = "OrderFile.xml";
		String orderFileType = "XML";
		String orderType = "OrderStandard";

		ItemService theItemService = ItemService.getInstance(itemFileName, itemFileType, itemType);
		
		//theItemService.printItemList(); //To See Item List

		FacilityService theFacilityService = FacilityService.getInstance(facilityFileName, facilityFileType, facilityType, theItemService);
		
		//To See Initial Facilities Status
		System.out.println("Initial Facility Status");
		theFacilityService.printAllFacilityStatus();
		
		System.out.println("\n///////////////////////////////////////////////////////\n"); //to seperate outputs

		NetworkService theNetworkService = NetworkService.getInstance(theFacilityService);
		
		/*To test ShortestPath
		System.out.println("\n");
		String start = "Santa Fe, NM";
		String dest = "Chicago, IL";
		theNetworkService.shortestPath(start, dest);
		*/

		OrderService theOrderService = OrderService.getInstance(orderFileName, orderFileType, orderType,
					theFacilityService, theItemService);
		
		System.out.println("Order Outputs");
		OrderProcessor theProcessor = OrderProcessor.getInstance(theItemService, theFacilityService, theNetworkService, theOrderService);
		
		
		System.out.println("\n////////////////////////////////////////////////////////\n"); //to seperate outputs
		
		
		System.out.println("Final Facility Status");
		//To See Initial Facilities Status
		theFacilityService.printAllFacilityStatus();
		
	}

}
