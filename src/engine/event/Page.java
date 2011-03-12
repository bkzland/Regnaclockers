package engine.event;

import java.awt.Graphics;

import java.util.ArrayList;
import java.util.logging.Logger;

import engine.sprite.Charset;
import engine.sprite.ImageSet;
import engine.sprite.Tileset;
import engine.event.fullfillable.Fullfillable;

/**
 * @author regnaclockers
 */
public class Page {
	private final static Logger LOGGER = Logger.getLogger(engine.event.Page.class.getName());

	private ArrayList<Fullfillable> conditions;
	private String script;
	private ImageSet sprites;
	private int tileID;

	public Page(Charset charset, ArrayList<Fullfillable> conditions, String script) {
		this(conditions, script);
		this.sprites = charset;
	}

	public Page(Tileset tileset, int tileID, ArrayList<Fullfillable> conditions, String script) {
		this(conditions, script);
		this.sprites = tileset;
		this.tileID = tileID;
	}

	public Page(ArrayList<Fullfillable> conditions, String script) {
		this.conditions = conditions;
		this.script = script;
	}

	/**
	 * checks all conditions if one returns that it is fullfilled. Returns true
	 * in that case.
	 * 
	 * @return
	 */
	public boolean isAConditionFullfilled() {
		for (int i = 0, length = conditions.size(); i < length; i++) {
			if (conditions.get(i).isFullfilled()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * draws the image on the panel at (x|y).
	 * 
	 * @param g
	 * @param panelX
	 *            position in pixel
	 * @param panelY
	 *            position in pixel
	 */
	public void drawSprite(Graphics g, int panelX, int panelY) {
		LOGGER.entering(this.getClass().getName(), "drawSprite", new Object[] { panelX, panelY });
		g.drawImage(sprites.getImage(tileID), panelX, panelY, null);
	}
}