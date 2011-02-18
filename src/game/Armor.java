package game;

/**
 * This class represents Armor items.
 * 
 * @author regnaclockers
 * 
 */
public class Armor extends Item {
	private int defense;

	/**
	 * creates a scriptless armor.
	 * 
	 * @param name
	 *            name of the armor.
	 * @param price
	 *            price of the armor.
	 * @param defense
	 *            defense value of the armor.
	 */
	public Armor(String name, int price, int defense) {
		super(name, price);
		this.defense = defense;
	}

	/**
	 * creates an armor with a script.
	 * 
	 * @param name
	 *            name of the armor.
	 * @param price
	 *            price of the armor.
	 * @param defense
	 *            defense value of the armor.
	 * @param script
	 *            script of the armor.
	 */
	public Armor(String name, int price, int defense, String script) {
		super(name, price, script);
		this.defense = defense;
	}

}
