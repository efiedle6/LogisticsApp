package logApp;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.util.ArrayList;

//utilize code from XMLReader given by professor adapted to match item class

public class ItemLoaderXML implements ItemLoader {

	private ArrayList<Item> itemList = new ArrayList<Item>();
	private static Item delegate;

	public ArrayList<Item> loadItems(String fileName, String itemType) {

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

			NodeList itemEntries = doc.getDocumentElement().getChildNodes();

			for (int i = 0; i < itemEntries.getLength(); i++) {
				if (itemEntries.item(i).getNodeType() == Node.TEXT_NODE) {
					continue;
				}

				String entryName = itemEntries.item(i).getNodeName();
				if (!entryName.equals("Item")) {
					System.err.println("Unexpected node found: " + entryName);
					return null;
				}

				// Get a named nodes
				Element elem = (Element) itemEntries.item(i);
				String itemItemID = elem.getElementsByTagName("ItemID").item(0)
						.getTextContent();
				String itemItemPrice = elem.getElementsByTagName("ItemPrice")
						.item(0).getTextContent();

				// Here I would create a Item object using the data I just
				// loaded from the XML
				Integer tempItemPrice = Integer.parseInt(itemItemPrice);
				if ((i % 2) == 0) {
				} else {
					
					delegate = ItemFactory.build(itemType, itemItemID, tempItemPrice);
					itemList.add(delegate);
				}
			}

		} catch (ParserConfigurationException | SAXException | IOException
				| DOMException e) {
			e.printStackTrace();
		}

		return itemList;
	}

}
