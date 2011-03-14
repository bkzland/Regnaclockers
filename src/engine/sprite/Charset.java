package engine.sprite;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * contains the charset and gives access to the images sorted by direction the
 * character looks.
 * 
 * @author regnaclockers
 */
public class Charset extends AbstractImageSet {
	private static final Logger LOGGER = Logger.getLogger(engine.sprite.Charset.class.getName());

	private int animationSpriteAmount;
	private List<BufferedImage> lookDown = new ArrayList<BufferedImage>();
	private List<BufferedImage> lookLeft = new ArrayList<BufferedImage>();
	private List<BufferedImage> lookRight = new ArrayList<BufferedImage>();
	private List<BufferedImage> lookUp = new ArrayList<BufferedImage>();
	private BufferedImage standSprite;

	/**
	 * creates a Charset Object.
	 * 
	 * @param charsetPath
	 *            Path to the file.
	 * @param charWidth
	 *            Width of the sprite.
	 * @param charHeight
	 *            Height of the sprite.
	 */
	public Charset(String charsetPath, int charWidth, int charHeight) {
		super(charsetPath, charWidth, charHeight);
		animationSpriteAmount = spriteset.getWidth() / charWidth;
		readSprites();
		standSprite = lookDown.get(0); // character looks down by default
		LOGGER.fine("\"" + toString() + "\" loaded");
	}

	/**
	 * returns all sprites with the character looking down. It assumes the
	 * character looks in the first images down, then left, then right, then up.
	 * 
	 * @return lookDown
	 */
	public List<BufferedImage> getLookDownSprites() {
		return lookDown;
	}

	/**
	 * returns all sprites with the character looking left. It assumes the
	 * character looks in the first images down, then left, then right, then up.
	 * 
	 * @return lookLeft
	 */
	public List<BufferedImage> getLookLeftSprites() {
		return lookLeft;
	}

	/**
	 * returns all sprites with the character looking right. It assumes the
	 * character looks in the first images down, then left, then right, then up.
	 * 
	 * @return lookRight
	 */
	public List<BufferedImage> getLookRightSprites() {
		return lookRight;
	}

	/**
	 * returns all sprites with the character looking up. It assumes the
	 * character looks in the first images down, then left, then right, then up.
	 * 
	 * @return lookUp
	 */
	public List<BufferedImage> getLookUpSprites() {
		return lookUp;
	}

	/**
	 * organizes the sprites in lists.
	 */
	private void readSprites() {
		lookDown = getSprites(0, animationSpriteAmount - 1);
		lookLeft = getSprites(animationSpriteAmount, 2 * animationSpriteAmount - 1);
		lookRight = getSprites(2 * animationSpriteAmount, 3 * animationSpriteAmount - 1);
		lookUp = getSprites(3 * animationSpriteAmount, 4 * animationSpriteAmount - 1);
	}

	/**
	 * returns an array of sprites from the tiles ArrayList.
	 * 
	 * @param start
	 *            TileID to start.
	 * @param end
	 *            TileID to stop.
	 * @return spriteArray
	 */
	private List<BufferedImage> getSprites(int start, int end) {
		List<BufferedImage> spriteArray = new ArrayList<BufferedImage>();
		for (int i = 0; start + i <= end; i++) {
			spriteArray.add(getImage(start + i));
		}
		return spriteArray;
	}

	/**
	 * draws the requested sprite on (charX|charY) on the panel.
	 * 
	 * @param g
	 *            graphics object
	 * @param spriteID
	 *            id of the sprite
	 * @param charX
	 *            char x position on JPanel in pixel
	 * @param charY
	 *            char y position on JPanel in pixel
	 */
	public void drawDownSprite(Graphics g, int spriteID, int charX, int charY) {
		standSprite = lookDown.get(0);
		try {
			g.drawImage(lookDown.get(spriteID), charX, charY, null);
		} catch (IndexOutOfBoundsException e) {
			LOGGER.severe("No sprite with ID " + spriteID);
		}
	}

	/**
	 * draws the requested sprite on (charX|charY) on the panel.
	 * 
	 * @param g
	 *            graphics object
	 * @param spriteID
	 *            id of the sprite
	 * @param charX
	 *            char x position on JPanel in pixel
	 * @param charY
	 *            char y position on JPanel in pixel
	 */
	public void drawLeftSprite(Graphics g, int spriteID, int charX, int charY) {
		standSprite = lookLeft.get(0);
		try {
			g.drawImage(lookLeft.get(spriteID), charX, charY, null);
		} catch (IndexOutOfBoundsException e) {
			LOGGER.severe("No sprite with ID " + spriteID);

		}
	}

	/**
	 * draws the requested sprite on (charX|charY) on the panel.
	 * 
	 * @param g
	 *            graphics object
	 * @param spriteID
	 *            id of the sprite
	 * @param charX
	 *            char x position on JPanel in pixel
	 * @param charY
	 *            char y position on JPanel in pixel
	 */
	public void drawRightSprite(Graphics g, int spriteID, int charX, int charY) {
		standSprite = lookRight.get(0);
		try {
			g.drawImage(lookRight.get(spriteID), charX, charY, null);
		} catch (IndexOutOfBoundsException e) {
			LOGGER.severe("No sprite with ID " + spriteID);

		}
	}

	/**
	 * draws the requested sprite on (charX|charY) on the panel.
	 * 
	 * @param g
	 *            graphics object
	 * @param spriteID
	 *            id of the sprite
	 * @param charX
	 *            char x position on JPanel in pixel
	 * @param charY
	 *            char y position on JPanel in pixel
	 */
	public void drawUpSprite(Graphics g, int spriteID, int charX, int charY) {
		standSprite = lookUp.get(0);
		try {
			g.drawImage(lookUp.get(spriteID), charX, charY, null);
		} catch (IndexOutOfBoundsException e) {
			LOGGER.severe("No sprite with ID " + spriteID);

		}
	}

	/**
	 * draws the 'stand' sprite depending on the last walking direction.
	 * 
	 * @param g
	 *            graphics object
	 * @param charX
	 *            char x position on JPanel in pixel
	 * @param charY
	 *            char y position on JPanel in pixel
	 */
	public void drawLast(Graphics g, int charX, int charY) {
		g.drawImage(standSprite, charX, charY, null);
	}
}