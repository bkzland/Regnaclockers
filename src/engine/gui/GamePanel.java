package engine.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
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
	private final static Logger LOGGER = Logger.getLogger(engine.gui.GamePanel.class.getName());

	// for measuring the fps
	private int fps = 0;
	private int frames = 0;
	private long firstFrame;
	private long currentFrame;
	private boolean isFpsOn = false;

	private static final int MAX_FPS = 120;

	private KeyboardControl key = new KeyboardControl();
	private GameLoop loop = new GameLoop(key);
	private Thread gameThread = new Thread(loop);
	private Thread graphicsThread = new Thread(this);

	public GamePanel() {
		setPreferredSize(new Dimension(1080, 768));
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
		while (true) {
			try {
				Thread.sleep(1000 / MAX_FPS);
			} catch (InterruptedException e) {
				LOGGER.severe("Couldn't sleep for " + 1000 / MAX_FPS + "ms");
				e.printStackTrace();
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
	 */
	private void showFpsIfOn(Graphics g) {
		if (isFpsOn) {
			if (fps != 0) {
				g.setColor(Color.WHITE);
				g.drawString(fps + "FPS", 0, 10);
			}
		}
	}

	public void triggerFps() {
		if (isFpsOn) {
			isFpsOn = false;
		} else {
			isFpsOn = true;
		}
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}

}