package logApp;

import java.util.ArrayList;

public interface ItemLoader {

	public default ArrayList<Item> loadItems(String fileName, String itemType) {
		return null;

	}

}
