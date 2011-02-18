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
	public Charset(String charsetPath, int charWidth, int charHeight, int animationSpriteAmount) {
		super(charsetPath, charWidth, charHeight);
		this.animationSpriteAmount = animationSpriteAmount;
	}

	/**
	 * returns all sprites with the character looking down. It assumes the
	 * character looks in the first images down, then left, then right, then up.
	 * 
	 * @return downArray
	 */
	public ArrayList<BufferedImage> getLookDownArray() {
		ArrayList<BufferedImage> downArray = getSpriteArray(0, animationSpriteAmount - 1);
		return downArray;
	}

	/**
	 * returns all sprites with the character looking left. It assumes the
	 * character looks in the first images down, then left, then right, then up.
	 * 
	 * @return leftArray
	 */
	public ArrayList<BufferedImage> getLookLeftArray() {
		ArrayList<BufferedImage> leftArray = getSpriteArray(animationSpriteAmount, 2 * animationSpriteAmount - 1);
		return leftArray;
	}

	/**
	 * returns all sprites with the character looking right. It assumes the
	 * character looks in the first images down, then left, then right, then up.
	 * 
	 * @return rightArray
	 */
	public ArrayList<BufferedImage> getLookRightArray() {
		ArrayList<BufferedImage> rightArray = getSpriteArray(2 * animationSpriteAmount, 3 * animationSpriteAmount - 1);
		return rightArray;
	}

	/**
	 * returns all sprites with the character looking up. It assumes the
	 * character looks in the first images down, then left, then right, then up.
	 * 
	 * @return upArray
	 */
	public ArrayList<BufferedImage> getLookUpArray() {
		ArrayList<BufferedImage> upArray = getSpriteArray(3 * animationSpriteAmount, 4 * animationSpriteAmount - 1);
		return upArray;
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
	private ArrayList<BufferedImage> getSpriteArray(int start, int end) {
		ArrayList<BufferedImage> spriteArray = new ArrayList<BufferedImage>();
		for (int i = 0; start + i < end; i++) {
			spriteArray.add(getSprite(start + i));
		}
		return spriteArray;
	}

}
