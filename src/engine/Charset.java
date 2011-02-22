package engine;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * contains the charset and gives access to the images sorted by direction the
 * character looks.
 * 
 * @author regnaclockers
 */
public class Charset extends SpriteCollection {
	int animationSpriteAmount;
	ArrayList<BufferedImage> lookDown = new ArrayList<BufferedImage>();
	ArrayList<BufferedImage> lookLeft = new ArrayList<BufferedImage>();
	ArrayList<BufferedImage> lookRight = new ArrayList<BufferedImage>();
	ArrayList<BufferedImage> lookUp = new ArrayList<BufferedImage>();

	/**
	 * creates a Charset Object.
	 * 
	 * @param charsetPath
	 *            Path to the file.
	 * @param charWidth
	 *            Width of the sprite.
	 * @param charHeight
	 *            Height of the sprite.
	 * @param animationSprites
	 *            Amount of sprites for animation.
	 */
	public Charset(String charsetPath, int charWidth, int charHeight) {
		super(charsetPath, charWidth, charHeight);
		animationSpriteAmount = spriteset.getWidth() / charWidth;
		readSprites();
	}

	/**
	 * returns all sprites with the character looking down. It assumes the
	 * character looks in the first images down, then left, then right, then up.
	 * 
	 * @return lookDown
	 */
	public ArrayList<BufferedImage> getLookDownSprites() {
		return lookDown;
	}

	/**
	 * returns all sprites with the character looking left. It assumes the
	 * character looks in the first images down, then left, then right, then up.
	 * 
	 * @return lookLeft
	 */
	public ArrayList<BufferedImage> getLookLeftSprites() {
		return lookLeft;
	}

	/**
	 * returns all sprites with the character looking right. It assumes the
	 * character looks in the first images down, then left, then right, then up.
	 * 
	 * @return lookRight
	 */
	public ArrayList<BufferedImage> getLookRightSprites() {
		return lookRight;
	}

	/**
	 * returns all sprites with the character looking up. It assumes the
	 * character looks in the first images down, then left, then right, then up.
	 * 
	 * @return lookUp
	 */
	public ArrayList<BufferedImage> getLookUpSprites() {
		return lookUp;
	}

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
	private ArrayList<BufferedImage> getSprites(int start, int end) {
		ArrayList<BufferedImage> spriteArray = new ArrayList<BufferedImage>();
		for (int i = 0; start + i <= end; i++) {
			spriteArray.add(getSprite(start + i));
		}
		return spriteArray;
	}
}