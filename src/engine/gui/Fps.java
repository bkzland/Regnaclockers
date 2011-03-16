package engine.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Logger;

/**
 * measures and display frames per second.
 * 
 * @author regnaclockers
 */
public class Fps implements Drawable {
	private static final Logger LOGGER = Logger.getLogger(engine.gui.GameDrawer.class.getName());

	private int fps;
	private int frames;
	private long firstFrame;
	private long currentFrame;
	private boolean isFpsOn;
	private int x;
	private int y;

	/**
	 * creates a fps object which displays frames per second on (x|y) if
	 * activated.
	 * 
	 * @param x
	 *            x on JPanel
	 * @param y
	 *            y on JPanel
	 */
	public Fps(int x, int y) {
		this.x = x;
		this.y = y;
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
	 * turns fps display on or off.
	 */
	@Override
	public void trigger() {
		if (isFpsOn) {
			isFpsOn = false;
			LOGGER.info("FPS display deactivated");

		} else {
			isFpsOn = true;
			LOGGER.info("FPS display activated");
		}
	}

	/**
	 * shows the frames per second in the upper left corner.
	 * 
	 * @param g
	 *            graphics object
	 */
	@Override
	public void drawIfActivated(Graphics g) {
		if (isFpsOn) {
			measureFpsIfOn();
			g.setColor(Color.WHITE);
			g.drawString(fps + "FPS", x, y);
		}
	}

}
