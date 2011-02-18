package engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * This abstract class gives subclasses essential methods to handle sets of
 * sprites.
 * 
 * @author regnaclockers
 */
public abstract class SpriteCollection {

	protected ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();

	private BufferedImage spriteset;
	private int spriteWidth;
	private int spriteHeight;
	private File imageFile;

	/**
	 * creates a SpriteCollection Object.
	 * 
	 * @param imagePath
	 *            Path to the spriteset.
	 * @param spriteWidth
	 *            Width of the subimages.
	 * @param spriteHeight
	 *            Height of the subimages.
	 */
	public SpriteCollection(String imagePath, int spriteWidth, int spriteHeight) {
		imageFile = new File(imagePath);
		try {
			spriteset = ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		loadTiles();

	}

	/**
	 * creates a SpriteCollection Object. It calls the SpriteCollection(String,
	 * int, int) constructor with the same int value.
	 * 
	 * @param imagePath
	 *            Path to the spriteset.
	 * @param spriteSize
	 *            Height and Width of the subimages.
	 */
	public SpriteCollection(String imagePath, int spriteSize) {
		this(imagePath, spriteSize, spriteSize);

	}

	/**
	 * extracts the sprites from the spriteset.
	 */
	private void loadTiles() {
		if (spriteset.getWidth() % spriteWidth != 0 || spriteset.getHeight() % spriteHeight != 0) {
			System.err.println("ERROR: Sprite Size is wrong.");
			System.exit(1);
		}

		int widthInTiles = spriteset.getWidth() / spriteWidth;
		int heightInTiles = spriteset.getHeight() / spriteHeight;

		for (int y = 0; y < heightInTiles; y++) {
			for (int x = 0; x < widthInTiles; x++) {
				sprites.add(spriteset.getSubimage(x * spriteWidth, y * spriteHeight, spriteWidth, spriteHeight));
			}
		}
	}

	/**
	 * returns the amount of sprites.
	 * 
	 * @return spriteAmount
	 */
	public int length() {
		int spriteAmount = sprites.size();
		return spriteAmount;
	}

	/**
	 * returns the width of the sprites.
	 * 
	 * @return spriteWidth
	 */
	public int getSpriteWidth() {
		return spriteWidth;
	}

	/**
	 * returns the height of the sprites.
	 * 
	 * @return spriteHeight
	 */
	public int getSpriteHeight() {
		return spriteHeight;
	}

	/**
	 * returns a sprite by ID.
	 * 
	 * @param spriteID
	 *            the number of the tile.
	 * @return sprite
	 */
	public BufferedImage getSprite(int spriteID) {
		BufferedImage sprite;
		try {
			sprite = sprites.get(spriteID);
			return sprite;
		} catch (IndexOutOfBoundsException e) {
			System.err.println("ERROR: Sprite ID " + spriteID + " does not exist. Exiting.");
			System.exit(1);
			return null;
		}

	}

	/**
	 * returns the name of the image file without path.
	 * 
	 * @return fileName
	 */
	public String getFileName() {
		String fileName = imageFile.getName();
		return fileName;
	}

}
