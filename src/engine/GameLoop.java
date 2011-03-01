package engine;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import engine.control.KeyboardControl;
import engine.event.Event;
import engine.event.Page;
import engine.sprite.Charset;
import engine.sprite.Tileset;

public class GameLoop implements Runnable {
	private static final int TICKS_PER_SECOND = 25;

	private Tileset tileset = new Tileset("dummytileset.png", 128);
	private int grid[][] = { 
			{ 11, 12, 18, 18, 18, 22, 18 }, 
			{ 11, 12, 18, 18, 18, 22, 18 },
			{ 11, 12, 18, 18, 18, 22, 18 }, 
			{ 11, 12, 12, 12, 18, 22, 18 },
			{ 11, 11, 11, 12, 18, 22, 18 },
			{ 11, 11, 11, 12, 22, 22, 18 },
			{ 11, 11, 11, 12, 18, 22, 18 } };

	private Page page = new Page(tileset, null, null);
	private ArrayList<Page> pages = new ArrayList<Page>();

	private Event event;

	private Map map = new Map("Map", grid, tileset, null);
	private Charset charset = new Charset("dummycharset.png", 128, 192);
	private Character character = new Character(charset);

	private int x;
	private int y;

	KeyboardControl key;

	public GameLoop(KeyboardControl key) {
		this.key = key;

		pages.add(page);
		event = new Event("TestEvent", pages, new Point(2, 2));
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000 / TICKS_PER_SECOND);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// test
			if (key.isDownPressed()) {
				y += 5;
			} else if (key.isLeftPressed()) {
				x -= 5;
			} else if (key.isRightPressed()) {
				x += 5;
			} else if (key.isUpPressed()) {
				y -= 5;
			}
		}
	}

	public void drawGame(Graphics g) {
		map.drawMapPart(g, x, y, 640, 480);
		character.drawCharacter(g, x, y);
	}
}