package engine.sprite;

import java.util.logging.Logger;

/**
 * This class is used to represent a tileset.
 * 
 * @author regnaclockers
 */
public class Tileset extends AbstractImageSet {
	private static final Logger LOGGER = Logger.getLogger(engine.sprite.Tileset.class.getName());

	private final int tileSize;

	/**
	 * creates a Tileset object.
	 * 
	 * @param tilesetPath
	 *            file name of a tileset.
	 * @param tileSize
	 *            the size of the tiles the tileset contains.
	 * @throws IOException
	 */
	public Tileset(String tilesetPath, int tileSize) {
		super(tilesetPath, tileSize);
		this.tileSize = tileSize;
		LOGGER.fine("\"" + toString() + "\" loaded");
	}

	/**
	 * returns the size of the tiles.
	 * 
	 * @return tileSize
	 */
	public int getTileSize() {
		return tileSize;
	}
}