package engine;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * This class contains all graphical content.
 * 
 * @author regnaclockers
 */
public class GamePanel extends JPanel implements Runnable {
	Map map;

	/**
	 * creates the game panel.
	 */
	public GamePanel(Map map) {
		setPreferredSize(new Dimension(640, 480));
		setDoubleBuffered(true);
		this.map = map;
	}

	/**
	 * draws everything.
	 */
	@Override
	public void paintComponent(Graphics g) {
		drawMap(g);
		drawPlayer(g);

	}

	// test only
	int loops = 0;
	int a = 0;

	/**
	 * draws the map.
	 * 
	 * @param g
	 */
	private void drawMap(Graphics g) {
		if (a > 100)
			a = 0;
		if (loops % 1 == 0) {
			a += 2;
		}
		g.drawImage(map.getMapPart(a, 0, 640, 480), 0, 0, this);
		loops++;

	}

	/**
	 * draws the player.
	 * 
	 * @param g
	 */
	private void drawPlayer(Graphics g) {

	}

	/**
	 * the game loop.
	 */
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}
}