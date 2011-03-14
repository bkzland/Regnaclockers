package engine.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

// TODO Help, Info, Load Game, etc.

/**
 * the menu with options for the user.
 */
public class GameMenu extends JMenuBar {
	private GamePanel panel;
	private JMenu file = new JMenu("File");
	private JMenuItem quit = new JMenuItem("Quit");

	private JMenu options = new JMenu("Options");
	private JCheckBoxMenuItem showFps = new JCheckBoxMenuItem("Show FPS");

	/**
	 * creates a new game menu.
	 * 
	 * @param panel
	 *            the panel to use its methods
	 */
	public GameMenu(GamePanel panel) {
		super();
		this.panel = panel;

		ActionListener quitListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				quitGame();
			}
		};

		ActionListener showFpsListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showFps();
			}
		};

		quit.addActionListener(quitListener);
		showFps.addActionListener(showFpsListener);
		file.add(quit);
		options.add(showFps);
		add(file);
		add(options);
	}

	/**
	 * quits the game.
	 */
	public void quitGame() {
		System.exit(0);
	}

	/**
	 * triggers the displaying of frames per second.
	 */
	public void showFps() {
		panel.triggerFps();
	}
}