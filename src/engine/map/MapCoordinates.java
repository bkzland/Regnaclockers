package engine.map;

import java.util.logging.Logger;

/**
 * Map coordinates for a map in tiles.
 * 
 * @author regnaclockers
 */

public class MapCoordinates{
	private final static Logger LOGGER = Logger.getLogger(engine.map.MapCoordinates.class.getName());

	private int x;
	private int y;

	/**
	 * creates MapCoordinates with x and y. Only positive integers are accepted.
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 */
	public MapCoordinates(int x, int y) {
		if (x >= 0 && y >= 0) {
			this.x = x;
			this.y = y;
		} else {
			LOGGER.severe("x and y must be >= 0");
		}
		LOGGER.finer("MapCoordinates " + toString() + " created");
	}

	/**
	 * returns the x coordinate.
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/** returns the y coordinate.
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * multiplies the x coordinate with the tileWidth.
	 * @param tileWidth
	 * @return
	 */
	public int xToPixel(int tileWidth) {
		return tileWidth * x;
	}

	/**
	 * multiplies the y coordinate with the tileWidth.
	 * @param tileWidth
	 * @return
	 */
	public int yToPixel(int tileHeight) {
		return tileHeight * y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MapCoordinates) {
			MapCoordinates coords = (MapCoordinates) obj;
			return (x == coords.getX()) && (y == coords.getY());
		}
		return false;
 	}
	
	@Override
	public int hashCode() {
		return (x * 11) ^ (y * 17);
	}
	
	@Override
	public String toString() {
		return new String("(" + x + "|" + y + ")");
	}
}