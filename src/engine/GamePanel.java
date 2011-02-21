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
		map.drawMapPart(g, 3330, 1250, 640, 480);

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