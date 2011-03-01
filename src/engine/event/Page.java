package engine.event;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import engine.sprite.Charset;
import engine.sprite.Tileset;
import engine.event.fullfillable.Fullfillable;

public class Page {
	private ArrayList<Fullfillable> conditions;
	String script;
	Tileset tileset;

	public Page(Charset charset, ArrayList<Fullfillable> conditions, String script) {
		this(conditions, script);
	}

	public Page(Tileset tileset, ArrayList<Fullfillable> conditions, String script) {
		this(conditions, script);
		this.tileset = tileset;
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
				System.out.println("Condition " + i + " true.");
				return true;
			}
		}
		return false;
	}

	public void drawSprite(Graphics g, Point point) {
		// test
		g.drawImage(tileset.getSprite(5), (int) point.getX() * tileset.getTileSize(), (int) point.getY()
				* tileset.getTileSize(), null);
	}
}