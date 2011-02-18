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

	/**
	 * draws the map.
	 * 
	 * @param g
	 */
	private void drawMap(Graphics g) {
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				g.drawImage(map.getTile(x, y), y * map.getTileSize(), x * map.getTileSize(), this);
			}
		}
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
