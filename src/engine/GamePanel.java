package engine;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

/**
 * This class contains all graphical content.
 * 
 * @author regnaclockers
 */
public class GamePanel extends JPanel implements Runnable {
	KeyBoardControl key = new KeyBoardControl();

	// test
	Tileset tileset = new Tileset("dummytileset.png", 128);
	int grid[][] = { { 11, 11, 11, 11, 11, 11 }, { 11, 18, 22, 18, 18, 11 }, { 11, 18, 22, 18, 18, 11 },
			{ 11, 18, 22, 18, 18, 11 }, { 11, 18, 22, 18, 18, 11 }, { 11, 11, 11, 11, 11, 11 } };

	Map map = new Map("Map", grid, tileset);
	Charset charset = new Charset("dummycharset.png", 128, 192, 4);
	Character character = new Character(charset);

	int x = 128;
	int y = 128;
	
	/**
	 * creates the game panel.
	 */
	public GamePanel() {
		setPreferredSize(new Dimension(640, 480));
		setDoubleBuffered(true);
		setFocusable(true);
		addKeyListener(key);
	}

	/**
	 * draws everything.
	 */
	@Override
	public void paintComponent(Graphics g) {
		map.drawMapPart(g, 3330, 1250, 640, 480);
		character.drawCharacter(g, x, y);
	}

	/**
	 * the game loop.
	 */
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (key.isDownPressed()) {
				y++;
			}
			else if (key.isLeftPressed()) {
				x--;
			}
			else if (key.isRightPressed()) {
				x++;
			}
			else if (key.isUpPressed()) {
				y--;
			}
			repaint();
		}
	}
}