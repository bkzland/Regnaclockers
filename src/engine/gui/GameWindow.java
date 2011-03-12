package engine.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * This class is used to represent the main game window.
 * 
 * @author regnaclockers
 */
@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	private GamePanel panel = new GamePanel();
	private GameMenu menu = new GameMenu(panel);

	/**
	 * creates a GameWindow object.
	 */
	public GameWindow() {
		setTitle("Regnaclock");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		requestFocus();
		add(menu, BorderLayout.NORTH);
		add(panel);
		pack();
		setVisible(true);
	}
}