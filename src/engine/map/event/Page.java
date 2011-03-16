package engine.map.event;

import java.awt.Graphics;

import java.util.List;
import java.util.logging.Logger;

import engine.map.event.conditions.Condition;
import engine.sprite.Charset;
import engine.sprite.AbstractImageSet;
import engine.sprite.Tileset;

/**
 * A page contains sprites and scripts. It is used by event objects.
 * 
 * @author regnaclockers
 */
public class Page {
	private static final Logger LOGGER = Logger.getLogger(engine.map.event.Page.class.getName());

	private List<Condition> conditions;
	private String script;
	private AbstractImageSet sprites;
	private int tileID;

	/**
	 * creates a new page.
	 * 
	 * @param charset
	 *            the charset.
	 * @param conditions
	 *            conditions for its activation
	 * @param script
	 *            the script
	 */
	public Page(Charset charset, List<Condition> conditions, String script) {
		this(conditions, script);
		this.sprites = charset;
	}

	/**
	 * creates a new page.
	 * 
	 * @param tileset
	 *            the tileset
	 * @param tileID
	 *            the tile ID
	 * @param conditions
	 *            conditions for its activation
	 * @param script
	 *            the scrpt
	 */
	public Page(Tileset tileset, int tileID, List<Condition> conditions, String script) {
		this(conditions, script);
		this.sprites = tileset;
		this.tileID = tileID;
	}

	/**
	 * creates a new page.
	 * 
	 * @param conditions
	 *            conditions for its activation
	 * @param script
	 *            the script
	 */
	public Page(List<Condition> conditions, String script) {
		this.conditions = conditions;
		this.script = script;
	}

	/**
	 * checks all conditions if one returns that it is fulfilled. Returns true
	 * in that case.
	 * 
	 * @return true, if a condition is fulfilled
	 */
	public boolean isSomeConditionFulfilled() {
		for (int i = 0, length = conditions.size(); i < length; i++) {
			if (conditions.get(i).isFulfilled()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * draws the image on the panel at (x|y).
	 * 
	 * @param g
	 *            graphics object
	 * @param panelX
	 *            JPanel position in pixel
	 * @param panelY
	 *            JPanel position in pixel
	 */
	public void drawSprite(Graphics g, int panelX, int panelY) {
		LOGGER.entering(this.getClass().getName(), "drawSprite", new Object[] { panelX, panelY });
		g.drawImage(sprites.getImage(tileID), panelX, panelY, null);
	}
}