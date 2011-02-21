package engine;

import java.awt.BorderLayout;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

/**
 * This class is used to represent the main game window.
 * 
 * @author regnaclockers
 */
public class GameWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2690299399524473746L;
	GamePanel panel;
	GameMenu menu;
	KeyBoardControl key = new KeyBoardControl();

	/**
	 * creates a GameWindow object.
	 */
	public GameWindow() {

		setTitle("Regnaclock");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		requestFocus();

		addKeyListener(key);

		// test
		Tileset tileset = new Tileset("dummytileset.png", 128);
		int grid[][] = { { 11, 11, 11, 11, 11, 11 }, { 11, 18, 22, 18, 18, 11 }, { 11, 18, 22, 18, 18, 11 },
				{ 11, 18, 22, 18, 18, 11 }, { 11, 18, 22, 18, 18, 11 }, { 11, 11, 11, 11, 11, 11 } };

		Map map = new Map("Map", grid, tileset);
		Charset charset = new Charset("dummycharset.png", 128, 192, 4);
		Character character = new Character(charset);

		menu = new GameMenu();
		panel = new GamePanel(map, character, key);
		add(menu, BorderLayout.NORTH);
		add(panel);
		pack();
		setVisible(true);
		panel.run();
	}
}