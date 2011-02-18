package engine;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.zip.ZipException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

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
	 * 
	 */
	public GameWindow() {

		setTitle("Regnaclock");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		requestFocus();

		// test
		Tileset tileset = new Tileset("dummytileset.png", 128);
		int grid[][] = { { 0, 1, 2 }, { 1, 0, 1 }, { 0, 1, 0 } };

		Map map = new Map("Map", grid, tileset);
		menu = new GameMenu();
		panel = new GamePanel(map);
		add(menu, BorderLayout.NORTH);
		add(panel);
		pack();
		setVisible(true);

	}
}
