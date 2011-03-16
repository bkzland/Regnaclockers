package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.logging.Logger;

import javax.swing.JPanel;

import engine.control.KeyboardControl;
import engine.gui.Fps;
import engine.gui.GameDrawer;

/**
 * This class contains all graphical content.
 * 
 * @author regnaclockers
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(gui.GamePanel.class.getName());

	private static final int MAX_FPS = 120;
	private boolean isGraphicsLoopOn = true;
	private KeyboardControl key = new KeyboardControl();
	private GameDrawer drawer = new GameDrawer(key);
	private Fps fps = new Fps(0, 10);
	private Thread graphicsThread = new Thread(this, "Graphics Thread");
	private GameWindow window;
	private GameMenu menu = new GameMenu(this);

	/**
	 * creates the game panel and the graphics thread.
	 */
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(1024, 768));
		setDoubleBuffered(true);
		setFocusable(true);
		addKeyListener(key);
		drawer.add(fps);
		fps.trigger();
		window = new GameWindow();
		window.add(this);
		window.add(menu, BorderLayout.NORTH);
		window.pack();
		graphicsThread.setPriority(Thread.MIN_PRIORITY);
		graphicsThread.start();
		LOGGER.info(graphicsThread.getName() + " started");
	}

	/**
	 * draws everything.
	 * 
	 * @param g
	 *            graphics object
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawer.drawGame(g2);
	}

	@Override
	public void run() {
		while (isGraphicsLoopOn) {
			tryToSleep();
			repaint();
		}
	}

	/**
	 * makes the graphics thread sleep to let it only run MAX_FPS times in a
	 * second.
	 */
	private void tryToSleep() {
		try {
			Thread.sleep(1000 / MAX_FPS);
		} catch (InterruptedException e) {
			LOGGER.severe("Cannot sleep for " + 1000 / MAX_FPS + "ms");
		}
	}

	/**
	 *stops the graphics loop.
	 */
	public void stopGraphicsLoopProperly() {
		isGraphicsLoopOn = false;
		LOGGER.info("Stopped Graphics Loop");
	}

}