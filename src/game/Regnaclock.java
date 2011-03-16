package game;

import gui.GamePanel;

import interpreter.ArchiveReader;

/**
 * A program to load and play rpgs.
 * 
 * @author regnaclockers
 */
public class Regnaclock {

	/**
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		// everything just for testing here
		new ArchiveReader("DummyGame.rcz");
		new GamePanel();
	}
}