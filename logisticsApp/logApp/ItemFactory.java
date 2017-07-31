package logApp;

import java.util.InputMismatchException;

public class ItemFactory {
	public static Item build(String itemType,String itemIdIn, Integer itemPriceIn)throws InputMismatchException {
		if (itemType instanceof String) { // Check that we are receiving
											// Strings as Inputs
			if (itemType.equals("ItemStandard")) {
				return new ItemStandardImpl(itemIdIn, itemPriceIn);
			} else
				return null;
		} else
			throw new InputMismatchException(
					"Expecting type String but receiving type: "
							+ itemType.getClass());
	}
}
