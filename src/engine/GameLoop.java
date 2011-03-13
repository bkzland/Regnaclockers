package engine;

import java.awt.Graphics;

import engine.map.TileMap;
import engine.map.MapCoordinates;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import engine.control.KeyboardControl;
import engine.event.Event;
import engine.event.Page;
import engine.event.fullfillable.Fullfillable;
import engine.event.fullfillable.Switch;
import engine.event.fullfillable.Timer;
import engine.sprite.Charset;
import engine.sprite.Tileset;

public class GameLoop implements Runnable {
	private final static Logger LOGGER = Logger.getLogger(engine.GameLoop.class.getName());

	private static final int TICKS_PER_SECOND = 25;
	private boolean isGameLoopOn = true;

	// creating objects for testing
	private Tileset tileset = new Tileset("dummytileset.png", 128);
	private int grid[][] = { { 11, 12, 18, 18, 18, 22, 18, 18, 18, 22, 18 },
			{ 11, 12, 18, 18, 18, 22, 18, 18, 18, 22, 18 }, { 11, 12, 18, 18, 18, 22, 18, 18, 18, 22, 18 },
			{ 11, 12, 12, 12, 18, 22, 18, 18, 18, 22, 18 }, { 11, 11, 11, 12, 18, 22, 18, 18, 18, 22, 18 },
			{ 11, 11, 11, 12, 22, 22, 18, 18, 18, 22, 18 }, { 11, 11, 11, 12, 18, 22, 18, 18, 18, 22, 18 },
			{ 11, 11, 11, 12, 18, 22, 18, 11, 11, 22, 18 }, { 11, 11, 11, 12, 18, 22, 18, 11, 11, 22, 18 },
			{ 11, 11, 11, 12, 18, 22, 22, 22, 22, 22, 22 }, { 11, 11, 11, 12, 18, 22, 18, 18, 18, 18, 18 } };

	private List<Fullfillable> conditions = new ArrayList<Fullfillable>();
	private List<Fullfillable> conditions2 = new ArrayList<Fullfillable>();

	private Page page;
	private Page page2;
	private List<Page> pages = new ArrayList<Page>();

	private Event event;

	private TileMap map;
	private Charset charset;
	private Hero hero;

	private KeyboardControl key;

	public GameLoop(KeyboardControl key) {
		this.key = key;

		// test
		Timer timer = new Timer(5);
		conditions.add(timer);
		timer.start();
		conditions2.add(new Switch(false));
		page = new Page(tileset, 12, conditions2, null);
		page2 = new Page(tileset, 11, conditions, null);
		pages.add(page);
		pages.add(page2);
		event = new Event("TestEvent", pages, new MapCoordinates(2, 1));

		ArrayList<Event> events = new ArrayList<Event>();
		events.add(event);
		charset = new Charset("Regnaclock_Char2.png", 128, 256);
		map = new TileMap("Map", grid, tileset, events);
		hero = new Hero(charset, map, new MapCoordinates(2, 2));
	}

	@Override
	public void run() {

		while (isGameLoopOn) {
			event.changePageIfConditionTrue();

			try {
				Thread.sleep(1000 / TICKS_PER_SECOND);
			} catch (InterruptedException e) {
				LOGGER.severe("Cannot sleep for " + 1000 / TICKS_PER_SECOND + "ms");
			}
			// test
			if (key.isWalkKeyPressed()) {
				int x = 0;
				int y = 0;
				if (key.isDownPressed()) {
					y++;
				}
				if (key.isLeftPressed()) {
					x--;
				}
				if (key.isRightPressed()) {
					x++;
				}
				if (key.isUpPressed()) {
					y--;

				}
				hero.walk(x, y);
			}
		}
	}

	public void drawGame(Graphics g) {
		hero.drawHero(g, 1024, 768);
	}

	public void stopGameLoopProperly() {
		isGameLoopOn = false;
		LOGGER.info("Stopped Game Loop");

	}
}
