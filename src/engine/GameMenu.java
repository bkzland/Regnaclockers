package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

// TODO Help, Info, Load Game, etc.

public class GameMenu extends JMenuBar {
	JMenu file = new JMenu("File");
	JMenuItem quit = new JMenuItem("Quit");

	public GameMenu() {

		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				quit();
			}
		});

		file.add(quit);
		add(file);
	}

	public void quit() {
		System.exit(0);
	}

}
