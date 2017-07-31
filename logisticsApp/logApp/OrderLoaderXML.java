package logApp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class OrderLoaderXML implements OrderLoader {

	private ArrayList<Order> orderList = new ArrayList<Order>();
	private static Order delegate;

	public ArrayList<Order> loadOrders(String fileName, String orderType,
			FacilityService facilityServ, ItemService itemServ)
			throws InputMismatchException {
		if (fileName instanceof String && orderType instanceof String
				&& itemServ instanceof ItemService
				&& facilityServ instanceof FacilityService) { // Check that we
																// are receiving
																// Strings as
																// Inputs

			try {
				String fName = fileName;

				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();

				File xml = new File(fName);
				if (!xml.exists()) {
					System.err.println("**** XML File '" + fName
							+ "' cannot be found");
					System.exit(-1);
				}

				Document doc = db.parse(xml);
				doc.getDocumentElement().normalize();

				NodeList orderEntries = doc.getDocumentElement()
						.getChildNodes();

				for (int i = 0; i < orderEntries.getLength(); i++) {
					if (orderEntries.item(i).getNodeType() == Node.TEXT_NODE) {
						continue;
					}

					String entryName = orderEntries.item(i).getNodeName();
					if (!entryName.equals("Order")) {
						System.err.println("Unexpected node found: "
								+ entryName);
						return null;
					}

					// Get a named nodes
					Element elem = (Element) orderEntries.item(i);
					String orderName = elem.getElementsByTagName("OrderId")
							.item(0).getTextContent();
					String strOrderTime = elem
							.getElementsByTagName("OrderTime").item(0)
							.getTextContent();
					int orderTime = Integer.parseInt(strOrderTime);
					String orderDest = elem
							.getElementsByTagName("OrderDestination").item(0)
							.getTextContent();

					// Get all nodes named "OrderItems" - there can be 0 or more

					HashMap<String, Integer> itemMap = new HashMap<String, Integer>();
					NodeList itemList = elem.getElementsByTagName("OrderItems");
					for (int j = 0; j < itemList.getLength(); j++) {
						if (itemList.item(j).getNodeType() == Node.TEXT_NODE) {
							continue;
						}

						entryName = itemList.item(j).getNodeName();
						if (!entryName.equals("OrderItems")) {
							System.err.println("Unexpected node found: "
									+ entryName);
							return null;
						}

						// Get some named nodes
						elem = (Element) itemList.item(j);
						String itemName = elem.getElementsByTagName("Item")
								.item(0).getTextContent();
						String strItemQuantity = elem
								.getElementsByTagName("Quantity").item(0)
								.getTextContent();
						Integer itemQuantity = Integer
								.parseInt(strItemQuantity);

						

						itemMap.put(itemName, itemQuantity);
					}

					delegate = OrderFactory.build(orderType, facilityServ,
							itemServ, orderName, orderTime, orderDest, itemMap);

					// For Testing
					// System.out.println(delegate.toString());
					// End Testing

					orderList.add(delegate);
				}

			} catch (ParserConfigurationException | SAXException | IOException
					| DOMException e) {
				e.printStackTrace();
			}

		} else {
			throw new InputMismatchException(
					"Expecting type String but receiving types: "
							+ fileName.getClass() + " and "
							+ orderType.getClass());
		}

		return orderList;
	}

}
