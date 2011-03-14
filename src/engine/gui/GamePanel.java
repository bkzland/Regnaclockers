package engine.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.logging.Logger;

import javax.swing.JPanel;

import engine.GameLoop;
import engine.control.KeyboardControl;

/**
 * This class contains all graphical content.
 * 
 * @author regnaclockers
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(engine.gui.GamePanel.class.getName());

	// for measuring the fps
	private int fps;
	private int frames;
	private long firstFrame;
	private long currentFrame;
	private boolean isFpsOn;

	private static final int MAX_FPS = 120;
	private boolean isGraphicsLoopOn = true;

	private KeyboardControl key = new KeyboardControl();
	private GameLoop loop = new GameLoop(key);
	private Thread gameThread = new Thread(loop);
	private Thread graphicsThread = new Thread(this);

	/**
	 * creates the game panel and the graphics thread.
	 */
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(1024, 768));
		setDoubleBuffered(true);
		setFocusable(true);
		addKeyListener(key);
		gameThread.setPriority(Thread.MAX_PRIORITY);
		gameThread.start();
		LOGGER.info("Game Thread started");
		graphicsThread.setPriority(Thread.MIN_PRIORITY);
		graphicsThread.start();
		LOGGER.info("Graphics Thread started");
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
		loop.drawGame(g2);
		showFpsIfOn(g2);
	}

	@Override
	public void run() {
		while (isGraphicsLoopOn) {
			try {
				Thread.sleep(1000 / MAX_FPS);
			} catch (InterruptedException e) {
				LOGGER.severe("Cannot sleep for " + 1000 / MAX_FPS + "ms");
			}
			repaint();
			measureFpsIfOn();
		}
	}

	/**
	 * measures the frames per second.
	 */
	private void measureFpsIfOn() {
		if (isFpsOn) {
			frames++;
			currentFrame = System.currentTimeMillis();
			if (currentFrame >= firstFrame + 1000) {
				firstFrame = currentFrame;
				fps = frames;
				frames = 0;
			}
		}
	}

	/**
	 * shows the frames per second in the upper left corner.
	 * 
	 * @param g
	 *            graphics object
	 */
	private void showFpsIfOn(Graphics g) {
		if (isFpsOn) {
			g.setColor(Color.WHITE);
			g.drawString(fps + "FPS", 0, 10);
		}
	}

	/**
	 * turns fps display on and off.
	 */
	public void triggerFps() {
		if (isFpsOn) {
			isFpsOn = false;
			LOGGER.info("FPS display deactivated");

		} else {
			isFpsOn = true;
			LOGGER.info("FPS display activated");
		}
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	/**
	 *stops the graphics loop.
	 */
	public void stopGraphicsLoopProperly() {
		isGraphicsLoopOn = false;
		LOGGER.info("Stopped Graphics Loop");
	}

}