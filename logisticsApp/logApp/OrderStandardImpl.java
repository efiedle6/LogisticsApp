package logApp;

import java.util.HashMap;
import java.util.InputMismatchException;

public class OrderStandardImpl implements Order {

	private int orderTime;
	private OrderOutputter orderOutList = new OrderOutputter();
	private String orderId;
	private String orderDestination;
	private HashMap<Item, Integer> orderItems = new HashMap<Item, Integer>(); //HashMap of Item with quantity


	public OrderStandardImpl(FacilityService facilityServ,ItemService itemServ, String orderIdIn, int orderTimeIn,
			String orderDestinationIn, HashMap<String, Integer> itemMapIn)throws NullPointerException, InputMismatchException {
		
		if (orderTimeIn < 0) {
			throw new InputMismatchException("negative value Passed to orderTime");
		} else {
			this.orderTime = (int) orderTimeIn;
		}

		if (orderIdIn.equals(null)) {
			throw new NullPointerException("Null value Passed to orderId");
		} else {
			this.orderId = orderIdIn;
		}

		if (orderDestinationIn.equals(null)) {
			throw new NullPointerException("Null value Passed to orderDestination");
		} else if (facilityServ.facilityNameInFacilityList(facilityServ.getFacilityList(), orderDestinationIn)) {
			this.orderDestination = orderDestinationIn;
		} else {
			throw new InputMismatchException("Order Destination not in Facility Network");
		}

		if (itemMapIn.equals(null)) {
			throw new NullPointerException("Null value Passed to orderItems");
		} else {
			for (String s : itemMapIn.keySet()) {
				if (s != null) {
					Item iterItem = itemServ.itemInList(s); // make that string into Item
					this.orderItems.put(iterItem, itemMapIn.get(s)); // then add to the orderItem HashMap <Item iterItem, Integer quantity>
				} else
					throw new NullPointerException();

			}
		}
	}// end constructor
	
	public OrderOutputter getOrderOutList(){
		return orderOutList;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Order Id:      ");
		sb.append(getOrderId());
		sb.append("\n");
		sb.append("Order Time:    Day ");
		sb.append(getOrderTime());
		sb.append("\n");
		sb.append("Destination:    ");
		sb.append(getOrderDestination());
		sb.append("\n");
		sb.append("List of Order Items:\n");
		for (Item i : orderItems.keySet()) {
			sb.append("Item ID:  ");
			sb.append(i.getItemId());
			sb.append(",  ");
			sb.append("Quantity:  ");
			sb.append(orderItems.get(i));
			sb.append("\n");
		}

		return sb.toString();
	}

	public int getOrderTime() {
		return orderTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getOrderDestination() {
		return orderDestination;
	}

	public HashMap<Item, Integer> getOrderItems() {
		return orderItems;
	}
}
