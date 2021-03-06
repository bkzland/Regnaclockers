package engine.sprite;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * This abstract class gives subclasses essential methods to handle sets of
 * sprites.
 * 
 * @author regnaclockers
 */
public abstract class AbstractImageSet {
	private static final Logger LOGGER = Logger.getLogger(engine.sprite.AbstractImageSet.class.getName());
	
	private List<BufferedImage> sprites = new ArrayList<BufferedImage>();
	protected BufferedImage spriteset;
	private int spriteWidth;
	private int spriteHeight;
	private String imagePath;

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
	public AbstractImageSet(String imagePath, int spriteWidth, int spriteHeight) {
		try {
			spriteset = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			LOGGER.severe("Cannot read \"" + imagePath + '"');
		}
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.imagePath = imagePath;
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
	public AbstractImageSet(String imagePath, int spriteSize) {
		this(imagePath, spriteSize, spriteSize);
	}

	/**
	 * extracts the sprites from the spriteset.
	 */
	private void loadTiles() {
		if (spriteset.getWidth() % spriteWidth != 0 || spriteset.getHeight() % spriteHeight != 0) {
			LOGGER.severe("Sprite size is wrong: " + spriteWidth + 'x' + spriteHeight);
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
		return sprites.size();
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
	public BufferedImage getImage(int spriteID) {
		LOGGER.entering(this.getClass().getName(), "getImage", spriteID);

		BufferedImage sprite;
		try {
			sprite = sprites.get(spriteID);

		} catch (IndexOutOfBoundsException e) {
			LOGGER.severe("Sprite ID " + spriteID + " does not exist");
			sprite = sprites.get(0);
		}

		LOGGER.exiting(this.getClass().getName(), "getImage", sprite);
		return sprite;
	}

	@Override
	public String toString() {
		return imagePath;
	}
}