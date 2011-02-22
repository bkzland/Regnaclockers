package game;

/**
 * This class represents items.
 * 
 * @author regnaclockers
 * 
 */
abstract class Item {
	private String name;
	private int price;
	private String script;

	/**
	 * creates a scriptless item.
	 * 
	 * @param name
	 *            name of the item.
	 * @param price
	 *            price of the item.
	 */
	public Item(String name, int price) {
		this.name = name;
		if (price >= 0) {
			this.price = price;
		} else {
			System.err.println("ERROR: 'price'  of " + name
					+ "was set to less than 0 (" + price
					+ "). Setting to '0'...");
			price = 0;
		}
	}

	/**
	 * create an item with a script.
	 * 
	 * @param name
	 *            name of the item.
	 * @param price
	 *            price of the item.
	 * @param script
	 *            script of the item.
	 */
	public Item(String name, int price, String script) {
		this(name, price);
		this.script = script;
	}
}