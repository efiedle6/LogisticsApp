package logApp;

import java.util.HashMap;
import java.util.InputMismatchException;

public class OrderFactory {

	public static Order build(String orderType, FacilityService facilityServ,
			ItemService itemServ, String orderName, Integer orderTime,
			String orderDest, HashMap<String, Integer> itemMap)
			throws InputMismatchException {
		if (orderType instanceof String) { // Check that we are receiving
											// Strings as Inputs
			if (orderType.equals("OrderStandard")) {
				return new OrderStandardImpl(facilityServ, itemServ, orderName,
						orderTime, orderDest, itemMap);
			} else
				return null;
		} else
			throw new InputMismatchException(
					"Expecting type String but receiving type: "
							+ orderType.getClass());

	}
}
