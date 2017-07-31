package logApp;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class OrderService {

	private static ArrayList<Order> orderList;
	private static OrderLoader delegate;
	private static OrderService instance;

	private OrderService(String fileName, String fileType, String orderType,
			FacilityService facilityServ, ItemService itemServ)
			throws InputMismatchException {
		if (fileName instanceof String && fileType instanceof String
				&& orderType instanceof String
				&& itemServ instanceof ItemService
				&& facilityServ instanceof FacilityService) { // Check that we are receiving correct Inputs
			delegate = OrderLoaderFactory.build(fileType);
			loadOrders(fileName, orderType, facilityServ, itemServ);// load the orders
		} else {
			throw new InputMismatchException("Received incorrect inputs");
		}
	}

	// method to initiate loading orders based on delegate
	public static void loadOrders(String fileName, String orderType,
			FacilityService facilityServ, ItemService itemServ) {
		orderList = delegate.loadOrders(fileName, orderType, facilityServ,
				itemServ);
	}

	// method for singleton instantiation
	public static OrderService getInstance(String fileName, String fileType,
			String orderType, FacilityService facilityServ, ItemService itemServ) { // Singleton Facade Pattern
		if (fileName instanceof String && fileType instanceof String) {// Check that we are receiving Strings as Inputs
			if (instance == null)
				instance = new OrderService(fileName, fileType, orderType,
						facilityServ, itemServ);
			return instance;
		} else {
			throw new InputMismatchException("Receiving incorrect inputs");
		}
	}

	public ArrayList<Order> getOrderList(){
		return orderList;
	}
	
	// method to print orderList
	public void printOrderList() { // Print the itemList HashMap
		System.out.println("Printing Order List: ");
		for (Order o : orderList) {
			System.out.println(o.toString());

		}
	}

}
