package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

// TODO Help, Info, Load Game, etc.

@SuppressWarnings("serial")
public class GameMenu extends JMenuBar {
	private JMenu file = new JMenu("File");
	private JMenuItem quit = new JMenuItem("Quit");

	private JMenu options = new JMenu("Options");
	private JCheckBoxMenuItem showFps = new JCheckBoxMenuItem("Show FPS");

	public GameMenu() {
		ActionListener quitListener = new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				quit();		
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

	public void quit() {
		System.exit(0);
	}

	public void showFps() {

	}
}