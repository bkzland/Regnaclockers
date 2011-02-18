package game;

/**
 * This class represents weapon items.
 * 
 * @author regnaclockers
 * 
 */
public class Weapon extends Item {
	private int attack;

	/**
	 * creates a scriptless weapon.
	 * 
	 * @param name
	 *            name of the weapon.
	 * @param price
	 *            price of the weapon.
	 * @param attack
	 *            attack value of the weapon.
	 * @param script
	 *            script of the weapon.
	 */
	public Weapon(String name, int price, int attack) {
		super(name, price);
		this.attack = attack;
	}

	/**
	 * creates a weapon with a script.
	 * 
	 * @param name
	 *            name of the weapon.
	 * @param price
	 *            price of the weapon.
	 * @param attack
	 *            attack value of the weapon.
	 * @param script
	 *            script of the weapon.
	 */
	public Weapon(String name, int price, int attack, String script) {
		super(name, price, script);
		this.attack = attack;
	}

}
