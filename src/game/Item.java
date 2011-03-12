package game;

import java.util.logging.Logger;

/**
 * This class represents items.
 * 
 * @author regnaclockers
 */
abstract class Item {
	private final static Logger LOGGER = Logger.getLogger(game.Item.class.getName());

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
			LOGGER.severe("ERROR: 'price'  of " + name + "was set to less than 0 (" + price + ").");
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

	@Override
	public String toString() {
		return name + " (Value: " + price + ')';
	}
}