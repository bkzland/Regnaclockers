package engine.map;

import java.util.logging.Logger;

/**
 * Map coordinates for a map in tiles.
 * 
 * @author regnaclockers
 */

public class MapCoordinates {
	private final static Logger LOGGER = Logger.getLogger(engine.map.MapCoordinates.class.getName());

	private final int x;
	private final int y;

	/**
	 * creates MapCoordinates with x and y. Only positive integers or zero are
	 * accepted.
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
			this.x = 0;
			this.y = 0;
		}
		LOGGER.finer("MapCoordinates (" + x + '|' + y + ") created");
	}

	/**
	 * returns the x coordinate.
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * returns the y coordinate.
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * multiplies the x coordinate with the tileWidth.
	 * 
	 * @param tileWidth
	 * @return
	 */
	public int xToPixel(int tileWidth) {
		return tileWidth * x;
	}

	/**
	 * multiplies the y coordinate with the tileWidth.
	 * 
	 * @param tileWidth
	 * @return
	 */
	public int yToPixel(int tileHeight) {
		return tileHeight * y;
	}

	public boolean xGreaterThan(MapCoordinates coords) {
		return x > coords.getX();
	}

	public boolean yGreaterThan(MapCoordinates coords) {
		return y > coords.getY();
	}

	public boolean xLessThan(MapCoordinates coords) {
		return x < coords.getX();
	}

	public boolean yLessThan(MapCoordinates coords) {
		return y < coords.getY();
	}

	public boolean xEquals(MapCoordinates coords) {
		return x == coords.getX();
	}

	public boolean yEquals(MapCoordinates coords) {
		return y == coords.getY();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MapCoordinates) {
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
		String string = "" + '(' + x + '|' + y + ')';
		return string;
	}
}