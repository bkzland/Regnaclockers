package engine.gui;

import java.awt.Dimension;
import java.awt.Graphics;
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

	private static final int MAX_FPS = 120;

	private KeyboardControl key = new KeyboardControl();
	private GameLoop loop = new GameLoop(key);
	private Thread gameThread = new Thread(loop);
	private Thread graphicsThread = new Thread(this);

	public GamePanel() {
		setPreferredSize(new Dimension(1024, 768));
		setDoubleBuffered(true);
		setFocusable(true);
		addKeyListener(key);
		gameThread.start();
		LOGGER.info("Game Thread started");
		graphicsThread.start();
		LOGGER.info("Graphics Thread started");

	}

	/**
	 * draws everything.
	 */
	@Override
	public void paintComponent(Graphics g) {
		loop.drawGame(g);
		showFps(g);
	}

	@Override
	public void run() {
		while (true) {
			if (fps >= MAX_FPS || fps == 0) {
				try {
					Thread.sleep(1000 / MAX_FPS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			repaint();
			measureFps();
		}
	}

	/**
	 * measures the frames per second.
	 */
	private void measureFps() {
		frames++;
		currentFrame = System.currentTimeMillis();
		if (currentFrame > firstFrame + 1000) {
			firstFrame = currentFrame;
			fps = frames;
			frames = 0;
		}
	}

	/**
	 * shows the frames per second in the upper left corner.
	 * 
	 * @param g
	 */
	private void showFps(Graphics g) {
		if (fps != 0) {
			g.drawString(fps + "FPS", 0, 10);
		}
	}
}