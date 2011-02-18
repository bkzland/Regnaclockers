package engine;

/**
 * This class is used to represent a tileset.
 * 
 * @author regnaclockers
 */
public class Tileset extends SpriteCollection {
	private int tileSize;

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
