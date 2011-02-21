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
	Map map;
	Character character;
	KeyBoardControl key;



	/**
	 * creates the game panel.
	 */
	public GamePanel(Map map, Character character, KeyBoardControl key) {
		setPreferredSize(new Dimension(640, 480));
		setDoubleBuffered(true);
		this.map = map;
		this.character = character;
		this.key = key;

	}


	//test
	int x = 128;
	int y = 128;
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
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//test
			if(key.isDownPressed()) {
				y++;
			}
			if(key.isLeftPressed()) {
				x--;
			}
			if(key.isRightPressed()) {
				x++;
			}
			if(key.isUpPressed()) {
				y--;
			}
			repaint();
			
		}
	}
}