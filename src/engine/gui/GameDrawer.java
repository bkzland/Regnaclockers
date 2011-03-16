package engine.gui;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import engine.GameLoop;
import engine.control.KeyboardControl;

/**
 * the game drawer. everything to draw should be in here.
 * 
 * @author regnaclockers
 */
public class GameDrawer {
	private static final Logger LOGGER = Logger.getLogger(engine.gui.GameDrawer.class.getName());

	private GameLoop loop;
	private List<Drawable> drawables;
	private Thread gameThread;

	/**
	 * creates a new GameDrawer.
	 * 
	 * @param key
	 *            keyboard control
	 */
	public GameDrawer(KeyboardControl key) {
		drawables = new ArrayList<Drawable>();
		loop = new GameLoop(key);
		gameThread = new Thread(loop, "Game Loop");
		gameThread.start();
		LOGGER.info(gameThread.getName() + " started");

	}

	/**
	 * add a drawable object.
	 * @param drawable object which implements Drawable
	 */
	public void add(Drawable drawable) {
		drawables.add(drawable);
	}

	/**
	 * should draw everything, map, hero, interface etc.
	 * 
	 * @param g
	 *            graphics object
	 */
	public void drawGame(Graphics2D g) {
		loop.drawGame(g);
		for (int i = 0, size = drawables.size(); i < size; i++) {
			drawables.get(i).drawIfActivated(g);
		}
	}
}
