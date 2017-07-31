package logApp;

import java.util.ArrayList;

public interface OrderLoader {

	public default ArrayList<Order> loadOrders(String fileName,
			String orderType, FacilityService facilityserv, ItemService itemServ) {
		return null;

	}
}
