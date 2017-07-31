package logApp;

import java.util.ArrayList;
import java.util.InputMismatchException;

//make singleton
public class ItemService {

	private static ArrayList<Item> itemList = new ArrayList<Item>();
	private static ItemLoader delegate;
	private static ItemService instance;

	private ItemService(String fileName, String fileType, String itemType)
			throws InputMismatchException {
		if (fileName instanceof String && fileType instanceof String && itemType instanceof String) { // Check that we are receiving Strings as Inputs
			delegate = ItemLoaderFactory.build(fileType); // create the appropriate ItemLoader based on the filetype
			loadItems(fileName, itemType); // Load the items from that file
		} else {
			throw new InputMismatchException(
					"Expecting type String but receiving types: "
							+ fileName.getClass() + " and "
							+ fileType.getClass());
		}
	}

	public static void loadItems(String fileName, String itemType) { // method to initiate loading items based on delegate
		itemList = delegate.loadItems(fileName, itemType);
	}

	public static ItemService getInstance(String fileName, String fileType, String itemType) { // Singleton Facade Pattern
		if (fileName instanceof String && fileType instanceof String) {// Check that we are receiving Strings as Inputs
			if (instance == null)
				instance = new ItemService(fileName, fileType, itemType);
			return instance;
		} else {
			throw new InputMismatchException(
					"Expecting type String but receiving types: "
							+ fileName.getClass() + " and "
							+ fileType.getClass());
		}
	}

	public ArrayList<Item> getItemList() { // get the itemList HashMap
		return itemList;
	}

	public Item itemInList(String s) { // method when given a string containing an Item Id returns the correct Item object
		for (Item c : getItemList()) {
			if (c.getItemId().equals(s)) {
				return c;
			}
		}
		return null;
	}

	public void printItemList() { // Print the itemList HashMap
		System.out.println("Printing Item List: ");
		for (Item i : itemList) {
			System.out.println("Item: " + i.getItemId() + " Price: $"
					+ i.getItemPrice());
		}
	}

}
