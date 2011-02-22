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
	GamePanel panel;
	GameMenu menu;

	/**
	 * creates a GameWindow object.
	 */
	public GameWindow() {
		setTitle("Regnaclock");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		requestFocus();
		menu = new GameMenu();
		panel = new GamePanel();
		add(menu, BorderLayout.NORTH);
		add(panel);
		pack();
		setVisible(true);
		panel.run();
	}
}