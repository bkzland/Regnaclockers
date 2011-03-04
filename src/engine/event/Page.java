package engine.event;

import java.awt.Graphics;
import engine.map.MapCoordinates;
import java.util.ArrayList;
import java.util.logging.Logger;

import engine.sprite.Charset;
import engine.sprite.ImageSet;
import engine.sprite.Tileset;
import engine.event.fullfillable.Fullfillable;

public class Page {
	private final static Logger LOGGER = Logger.getLogger(engine.event.Page.class.getName());

	private ArrayList<Fullfillable> conditions;
	private String script;
	private ImageSet sprites;
	private int spriteWidth;
	private int spriteHeight;

	public Page(Charset charset, ArrayList<Fullfillable> conditions, String script) {
		this(conditions, script);
		this.sprites = charset;
		spriteWidth = this.sprites.getSpriteWidth();
		spriteHeight = this.sprites.getSpriteHeight();
	}

	public Page(Tileset tileset, ArrayList<Fullfillable> conditions, String script) {
		this(conditions, script);
		this.sprites = tileset;
		spriteWidth = this.sprites.getSpriteWidth();
		spriteHeight = this.sprites.getSpriteHeight();
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
	public boolean checkCondition() {
		for (int i = 0, length = conditions.size(); i < length; i++) {
			if (conditions.get(i).isFullfilled() == true) {
				LOGGER.info("Condition " + i + " true.");
				return true;
			}
		}
		return false;
	}

	public void drawSprite(Graphics g, MapCoordinates point) {
		// test
		g.drawImage(sprites.getImage(5), point.xToPixel(spriteWidth), point.yToPixel(spriteHeight), null);
	}
}