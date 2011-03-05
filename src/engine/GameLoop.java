package engine;

import java.awt.Graphics;

import engine.map.Map;
import engine.map.MapCoordinates;
import java.util.ArrayList;

import engine.event.fullfillable.*;
import engine.control.KeyboardControl;
import engine.event.Event;
import engine.event.Page;
import engine.sprite.Charset;
import engine.sprite.Tileset;

public class GameLoop implements Runnable {
	private static final int TICKS_PER_SECOND = 25;

	// creating objects for testing
	private Tileset tileset = new Tileset("dummytileset.png", 128);
	private int grid[][] = { { 11, 12, 18, 18, 18, 22, 18 }, { 11, 12, 18, 18, 18, 22, 18 },
			{ 11, 12, 18, 18, 18, 22, 18 }, { 11, 12, 12, 12, 18, 22, 18 }, { 11, 11, 11, 12, 18, 22, 18 },
			{ 11, 11, 11, 12, 22, 22, 18 }, { 11, 11, 11, 12, 18, 22, 18 } };

	private ArrayList<Fullfillable> conditions = new ArrayList<Fullfillable>();
	private ArrayList<Fullfillable> conditions2 = new ArrayList<Fullfillable>();

	private Page page;
	private Page page2;
	private ArrayList<Page> pages = new ArrayList<Page>();

	private Event event;

	private Map map;
	private Charset charset = new Charset("dummycharset.png", 128, 192);
	private Character character = new Character(charset);

	private int x;
	private int y;

	KeyboardControl key;

	public GameLoop(KeyboardControl key) {
		this.key = key;

		// test
		Timer timer = new Timer(5);
		conditions.add(timer);
		timer.start();
		conditions2.add(new Switch(false));
		page = new Page(tileset, 0, conditions2, null);
		page2 = new Page(tileset, 1, conditions, null);
		pages.add(page);
		pages.add(page2);
		event = new Event("TestEvent", pages, new MapCoordinates(2, 1));

		ArrayList<Event> events = new ArrayList<Event>();
		events.add(event);

		map = new Map("Map", grid, tileset, events);
	}

	@Override
	public void run() {

		while (true) {
			event.changePageIfConditionTrue();

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