package logApp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FacilityLoaderXML implements FacilityLoader {

	private ArrayList<Facility> facilityList = new ArrayList<Facility>();
	private static Facility delegate;

	public ArrayList<Facility> loadFacilities(String fileName, String facilityType,
			ItemService itemServ) {

		try {
			String fName = fileName;

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			File xml = new File(fName);
			if (!xml.exists()) {
				System.err.println("**** XML File '" + fName
						+ "' cannot be found");
				System.exit(-1);
			}

			Document doc = db.parse(xml);
			doc.getDocumentElement().normalize();

			NodeList facilityEntries = doc.getDocumentElement().getChildNodes();

			for (int i = 0; i < facilityEntries.getLength(); i++) {
				if (facilityEntries.item(i).getNodeType() == Node.TEXT_NODE) {
					continue;
				}

				String entryName = facilityEntries.item(i).getNodeName();
				if (!entryName.equals("Facility")) {
					System.err.println("Unexpected node found: " + entryName);
					return null;
				}

				// Get a named nodes
				Element elem = (Element) facilityEntries.item(i);
				String facilityName = elem.getElementsByTagName("FacilityName")
						.item(0).getTextContent();
				String strFacilityRate = elem
						.getElementsByTagName("FacilityRate").item(0)
						.getTextContent();
				int facilityRate = Integer.parseInt(strFacilityRate);

				// Get all nodes named "FacilityLink" - there can be 0 or more
				// ArrayList<String> linkDescriptions = new ArrayList<>();
				HashMap<String, Double> linksMap = new HashMap<String, Double>();
				NodeList linkList = elem.getElementsByTagName("FacilityLink");
				NodeList stockList = elem.getElementsByTagName("FStock");
				for (int j = 0; j < linkList.getLength(); j++) {
					if (linkList.item(j).getNodeType() == Node.TEXT_NODE) {
						continue;
					}

					entryName = linkList.item(j).getNodeName();
					if (!entryName.equals("FacilityLink")) {
						System.err.println("Unexpected node found: "
								+ entryName);
						return null;
					}

					// Get some named nodes
					elem = (Element) linkList.item(j);
					String linkName = elem.getElementsByTagName("Link").item(0)
							.getTextContent();
					String strLinkDistance = elem.getElementsByTagName("Dist")
							.item(0).getTextContent();
					Double linkDistance = Double.parseDouble(strLinkDistance);
					linksMap.put(linkName, linkDistance);
				}

				// Get all nodes named "FacilityStock" - there can be 0 or more
				// ArrayList<String> stockDescriptions = new ArrayList<>();
				HashMap<Item, Integer> stocksMap = new HashMap<Item, Integer>();
				
				for (int z = 0; z < stockList.getLength(); z++) {
					if (stockList.item(z).getNodeType() == Node.TEXT_NODE) {
						continue;
					}

					entryName = stockList.item(z).getNodeName();
					if (!entryName.equals("FStock")) {
						System.err.println("Unexpected node found: "
								+ entryName);
						return null;
					}

					// Get some named nodes
					elem = (Element) stockList.item(z);
					String strStockItem = elem.getElementsByTagName("Item")
							.item(0).getTextContent();
					// convert this string to its proper Item
					// Item stockItem = new Item(strStockItem,
					// itemServ.getItemList().get(strStockItem));

					Item stockItem = itemServ.itemInList(strStockItem);

					String strStockQuantity = elem
							.getElementsByTagName("Quantity").item(0)
							.getTextContent();
					Integer stockQuantity = Integer.parseInt(strStockQuantity);

					stocksMap.put(stockItem, stockQuantity);
				}
				Stock stockIn = new Stock(stocksMap);

				
				delegate = FacilityFactory.build(facilityType, facilityName, facilityRate, stockIn, linksMap);
				facilityList.add(delegate);
			}

		} catch (ParserConfigurationException | SAXException | IOException
				| DOMException e) {
			e.printStackTrace();
		}

		return facilityList;
	}

}
