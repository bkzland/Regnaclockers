package engine;

import java.awt.Graphics;

public class GameLoop implements Runnable {
	private static final int TICKS_PER_SECOND = 25;

	private Tileset tileset = new Tileset("dummytileset.png", 128);
	private int grid[][] = { { 11, 11, 11, 11, 11, 11 }, { 11, 18, 22, 18, 18, 11 }, { 11, 18, 22, 18, 18, 11 },
			{ 11, 18, 22, 18, 18, 11 }, { 11, 18, 22, 18, 18, 11 }, { 11, 11, 11, 11, 11, 11 } };

	private Map map = new Map("Map", grid, tileset);
	private Charset charset = new Charset("dummycharset.png", 128, 192);
	private Character character = new Character(charset);

	private int x;
	private int y;

	KeyBoardControl key;

	public GameLoop(KeyBoardControl key) {
		this.key = key;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000 / TICKS_PER_SECOND);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//test
			if (key.isDownPressed()) {
				y += 5;
			} else if (key.isLeftPressed()) {
				x -= 5;
			} else if (key.isRightPressed()) {
				x += 5;
			} else if (key.isUpPressed()) {
				y -= 5;
			}
		}
	}

	public void drawGame(Graphics g) {
		map.drawMapPart(g, x, y, 640, 480);
		character.drawCharacter(g, x, y);
	}
}