package engine.map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import engine.event.Event;
import engine.sprite.Tileset;

/**
 * This class is used to represent a map.
 * 
 * @author regnaclockers
 */
public class Map {
	private final static Logger LOGGER = Logger.getLogger(engine.map.Map.class.getName());

	private HashMap<MapCoordinates, Integer> mapGrid = new HashMap<MapCoordinates, Integer>();
	private ArrayList<Event> events;
	private BufferedImage map;
	private int mapWidthInTiles;
	private int mapHeightInTiles;
	private int mapWidthInPixel;
	private int mapHeightInPixel;
	private String mapName;
	private Tileset tileset;
	private int tileSize;

	/**
	 * creates a Map object. It gets the height and width of the map from the
	 * dimensions of the mapGrid array.
	 * 
	 * @param mapName
	 *            the name which the map should have.
	 * @param mapGrid
	 *            contains the IDs of the tiles for every single position.
	 * @param tileset
	 *            the Tileset object it should use.
	 */
	public Map(String mapName, int[][] mapGrid, Tileset tileset, ArrayList<Event> events) {
		this.mapName = mapName;
		this.mapWidthInTiles = mapGrid.length;
		this.mapHeightInTiles = mapGrid[0].length;
		tileSize = tileset.getTileSize();
		mapWidthInPixel = mapWidthInTiles * tileSize;
		mapHeightInPixel = mapHeightInTiles * tileSize;
		for (int x = 0, width = mapWidthInTiles; x < width; x++) {
			for (int y = 0, height = mapHeightInTiles; y < height; y++) {
				this.mapGrid.put(new MapCoordinates(x, y), mapGrid[x][y]);
			}
		}
		this.tileset = tileset;

		this.events = events;
		loadMap();
		LOGGER.info("Map \"" + mapName + "\" created with " + tileset.toString());
	}

	/**
	 * creates a Map object.
	 * 
	 * @param mapName
	 *            the name which the map should have.
	 * @param mapGrid
	 *            contains the IDs of the tiles for every single position.
	 * @param mapWidthInTiles
	 *            width of the map.
	 * @param mapHeightInTiles
	 *            height of the map.
	 * @param tileset
	 *            the Tileset object it should use.
	 */
	public Map(String mapName, HashMap<MapCoordinates, Integer> mapGrid, int mapWidthInTiles, int mapHeightInTiles,
			Tileset tileset, ArrayList<Event> events) {
		this.mapName = mapName;
		this.mapGrid = mapGrid;
		this.mapWidthInTiles = mapWidthInTiles;
		this.mapHeightInTiles = mapHeightInTiles;
		this.tileset = tileset;
		tileSize = tileset.getTileSize();
		mapWidthInPixel = mapWidthInTiles * tileSize;
		mapHeightInPixel = mapHeightInTiles * tileSize;
		this.events = events;
		loadMap();
		LOGGER.info("Map \"" + mapName + "\" created");
	}

	/**
	 * draws a part of the map. It creates a rectangle around (x|y).
	 * 
	 * @param g
	 * @param charMapPositionXInPixel
	 *            players position on x-axis
	 * @param charMapPositionYInPixel
	 *            players position on y-axis.
	 * @param horResolution
	 * @param vertResolution
	 */
	public void drawMap(Graphics g, int charMapPositionXInPixel, int charMapPositionYInPixel, int horResolution,
			int vertResolution) {

		int mapXInPixel = charMapPositionXInPixel - horResolution / 2;
		int mapYInPixel = charMapPositionYInPixel - vertResolution / 2;

		// prevents that the map moves if there's no more map to show
		// only effects the upper and left side
		if (mapXInPixel < 0) {
			mapXInPixel = 0;
		}
		if (mapYInPixel < 0) {
			mapYInPixel = 0;
		}
		// same for lower and right side
		if (mapXInPixel + horResolution > mapWidthInPixel) {
			mapXInPixel = mapWidthInPixel - horResolution;
		}
		if (mapYInPixel + vertResolution > mapHeightInPixel) {
			mapYInPixel = mapHeightInPixel - vertResolution;
		}
		g.drawImage(map.getSubimage(mapXInPixel, mapYInPixel, horResolution, vertResolution), 0, 0, null);

		// paint all events
		for (int i = 0; i < events.size(); i++) {
			events.get(i).drawEvent(g, mapXInPixel, mapYInPixel, horResolution, vertResolution);
		}
	}

	/**
	 * creates an image of the whole map.
	 */
	private void loadMap() {

		map = new BufferedImage(mapWidthInPixel, mapHeightInPixel, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = map.createGraphics();
		for (int x = 0; x < mapWidthInTiles; x++) {
			for (int y = 0; y < mapHeightInTiles; y++) {
				g2d
						.drawImage(tileset.getImage(mapGrid.get(new MapCoordinates(x, y))), x * tileSize, y * tileSize,
								null);
			}
		}
	}

	public int getTileSize() {
		return tileset.getTileSize();
	}

	/**
	 * returns true if the horizontal start (left side) of the map is reached.
	 * 
	 * @param xPosition
	 * @param horResolution
	 * @return
	 */
	public boolean isHorMapStartReached(int xPosition, int horResolution) {
		if (xPosition <= horResolution / 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * returns true if the vertical start (upper side) of the map is reached.
	 * 
	 * @param yPosition
	 * @param vertResolution
	 * @return
	 */
	public boolean isVertMapStartReached(int yPosition, int vertResolution) {
		if (yPosition <= vertResolution / 2) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isXCoordinateInMapEnd(MapCoordinates position, int horResolution) {
		if (position.xToPixel(tileSize) >= mapWidthInPixel - horResolution / 2) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isYCoordinateInMapEnd(MapCoordinates position, int vertResolution) {
		if (position.yToPixel(tileSize) >= mapHeightInPixel - vertResolution / 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * returns true if the horizontal end (right side) of the map is reached.
	 * 
	 * @param xPosition
	 * @param horResolution
	 * @return
	 */
	public boolean isHorMapEndReached(int xPosition, int horResolution) {
		if (xPosition >= mapWidthInPixel - horResolution / 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * returns true if the vertical end (lower side) of the map is reached.
	 * 
	 * @param xPosition
	 * @param vertResolution
	 * @return
	 */
	public boolean isVertMapEndReached(int xPosition, int vertResolution) {
		if (xPosition >= mapHeightInPixel - vertResolution / 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * gives back a valid position. If (x|y) is too high or too low, it will
	 * return the highest/lowest possible coordinate.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public MapCoordinates getLegitCoordinates(int x, int y) {
		int newX;
		int newY;
		if (x >= 0 && x <= mapWidthInTiles - 1) {
			newX = x;
		} else if (x < 0) {
			newX = 0;
		} else {
			newX = mapWidthInTiles - 1;
		}

		if (y >= 0 && y <= mapHeightInTiles - 1) {
			newY = y;
		} else if (y < 0) {
			newY = 0;
		} else {
			newY = mapHeightInTiles - 1;
		}

		return new MapCoordinates(newX, newY);
	}

	public int getMapWidthInPixel() {
		return mapWidthInPixel;
	}

	public int getMapHeightInPixel() {
		return mapHeightInPixel;
	}

	@Override
	public String toString() {
		return mapName;
	}
}