package engine;

import java.awt.BorderLayout;
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

	/**
	 * creates a GameWindow object.
	 */
	public GameWindow() {

		setTitle("Regnaclock");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		requestFocus();

		// test
		Tileset tileset = new Tileset("dummytileset.png", 128);
		int grid[][] = { { 0, 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10, 11 }, { 12, 13, 14, 15, 16, 17 },
				{ 18, 19, 20, 21, 22, 23 }, { 24, 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10, 11 } };

		Map map = new Map("Map", grid, tileset);
		menu = new GameMenu();
		panel = new GamePanel(map);
		add(menu, BorderLayout.NORTH);
		add(panel);
		pack();
		setVisible(true);
		panel.run();
	}
}