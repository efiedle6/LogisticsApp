package logApp;

import java.util.HashMap;

public interface Order {

	public HashMap<Item, Integer> getOrderItems();
	public String getOrderDestination();
	public String getOrderId();
	public int getOrderTime();
	public OrderOutputter getOrderOutList();

}
