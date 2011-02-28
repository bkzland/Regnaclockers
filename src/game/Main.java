package game;

import engine.GameLoop;
import engine.gui.GameWindow;

import interpreter.ArchiveReader;

/**
 * A program to load and play rpgs.
 * 
 * @author regnaclockers
 */
public class Main {

	/**
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		// everything just for testing here
		ArchiveReader archiveReader = new ArchiveReader("DummyGame.rcz");
		GameWindow window = new GameWindow();
	}
}