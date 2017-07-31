package logApp;

public class ItemStandardImpl implements Item{

	private String itemId;
	private Integer itemPrice;

	public ItemStandardImpl(String itemIdIn, Integer itemPriceIn)
			throws IllegalArgumentException, NullPointerException // constructor
	{
		if (itemIdIn.equals(null) || itemPriceIn.equals(null)) {
			throw new NullPointerException("No null arguments");
		}
		if (itemPriceIn < 0) {
			throw new IllegalArgumentException(
					"Price must be greater than or equal to zero");
		}
		itemId = itemIdIn;
		itemPrice = itemPriceIn;
	}

	// getter for itemId
	public String getItemId() {
		return itemId;
	}

	public String toString() {
		String theString = new String(getItemId() + ", " + getItemPrice()
				+ "\n");
		return theString;
	}

	// getter for itemPrice
	public int getItemPrice() {
		return itemPrice;
	}

}
