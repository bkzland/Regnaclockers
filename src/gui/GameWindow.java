package gui;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * This class is used to represent the main game window.
 * 
 * @author regnaclockers
 */
@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	private static final Logger LOGGER = Logger.getLogger(gui.GameWindow.class.getName());

	// private GameMenu menu = new GameMenu(drawer);

	/**
	 * creates a GameWindow object.
	 */
	public GameWindow() {
		super();
		setTitle("Regnaclock");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		requestFocus();
		setVisible(true);

		try {
			Image img = ImageIO.read(new File("regnaclock.png"));
			setIconImage(img);
		} catch (IOException e) {
			LOGGER.severe("Cannot load program icon");
		}

	}
}