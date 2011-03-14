package engine.map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import engine.map.event.Event;
import engine.sprite.Tileset;

/**
 * This class is used to represent a map.
 * 
 * @author regnaclockers
 */
public class TileMap {
	private static final Logger LOGGER = Logger.getLogger(engine.map.TileMap.class.getName());
	private static int mapIdCounter = 1;

	private Map<MapCoordinates, Integer> mapGrid;
	private List<Event> events;
	private BufferedImage imageOfWholeMap;
	private int mapWidthInTiles;
	private int mapHeightInTiles;
	private int mapWidthInPixel;
	private int mapHeightInPixel;
	private String mapName;
	private Tileset tileset;
	private int tileSize;
	private final int mapId;

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
	 * @param events
	 *            all events of the map.
	 */
	public TileMap(String mapName, int[][] mapGrid, Tileset tileset, List<Event> events) {
		this.mapName = mapName;
		this.mapWidthInTiles = mapGrid.length;
		this.mapHeightInTiles = mapGrid[0].length;
		tileSize = tileset.getTileSize();
		mapWidthInPixel = mapWidthInTiles * tileSize;
		mapHeightInPixel = mapHeightInTiles * tileSize;
		this.mapGrid = new HashMap<MapCoordinates, Integer>();
		for (int x = 0, width = mapWidthInTiles; x < width; x++) {
			for (int y = 0, height = mapHeightInTiles; y < height; y++) {
				this.mapGrid.put(new MapCoordinates(x, y), mapGrid[x][y]);
			}
		}
		this.tileset = tileset;

		this.events = events;
		loadMap();
		mapId = mapIdCounter++;
		LOGGER.info("Map #" + mapId + " \"" + mapName + "\" created with " + tileset.toString());
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
	 * @param events
	 *            all events of the map.
	 */
	public TileMap(String mapName, Map<MapCoordinates, Integer> mapGrid, int mapWidthInTiles, int mapHeightInTiles,
			Tileset tileset, List<Event> events) {
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
		mapId = mapIdCounter++;

		LOGGER.info("Map \"" + mapName + "\" created");
	}

	/**
	 * draws a part of the map. It creates a rectangle around (x|y).
	 * 
	 * @param g
	 *            graphics object
	 * @param heroXInPixel
	 *            players position on x-axis
	 * @param heroYInPixel
	 *            players position on y-axis.
	 * @param horResolution
	 *            width of JPanel
	 * @param vertResolution
	 *            height of JPanel
	 */
	public void drawMap(Graphics g, int heroXInPixel, int heroYInPixel, int horResolution, int vertResolution) {

		int mapXInPixel = heroXInPixel - horResolution / 2;
		int mapYInPixel = heroYInPixel - vertResolution / 2;

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
		g.drawImage(imageOfWholeMap.getSubimage(mapXInPixel, mapYInPixel, horResolution, vertResolution), 0, 0, null);

		// paint all events
		for (int i = 0; i < events.size(); i++) {
			events.get(i).drawEvent(g, mapXInPixel, mapYInPixel, horResolution, vertResolution);
		}
	}

	/**
	 * creates an image of the whole map.
	 */
	private void loadMap() {
		imageOfWholeMap = new BufferedImage(mapWidthInPixel, mapHeightInPixel, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = imageOfWholeMap.createGraphics();
		for (int x = 0; x < mapWidthInTiles; x++) {
			for (int y = 0; y < mapHeightInTiles; y++) {
				g2d
						.drawImage(tileset.getImage(mapGrid.get(new MapCoordinates(x, y))), x * tileSize, y * tileSize,
								null);
			}
		}
	}

	/**
	 * returns tile size.
	 * 
	 * @return tile size
	 */
	public int getTileSize() {
		return tileset.getTileSize();
	}

	/**
	 * returns true if the horizontal start (left side) of the map is reached.
	 * 
	 * @param xPositionInPixel
	 *            x position on the map in pixel
	 * @param horResolution
	 *            width of the JPanel
	 * @return true, if left border visible
	 */
	public boolean isLeftBorderVisible(int xPositionInPixel, int horResolution) {
		return xPositionInPixel < horResolution / 2;
	}

	/**
	 * returns true if the vertical start (upper side) of the map is reached.
	 * 
	 * @param yPosition
	 *            y position on the map in pixel
	 * @param vertResolution
	 *            height of the JPanel
	 * @return true, if upper border visible
	 */
	public boolean isUpperBorderVisible(int yPosition, int vertResolution) {
		return yPosition < vertResolution / 2;
	}

	/**
	 * checks if position is in the right border.
	 * 
	 * @param position
	 *            position in tiles
	 * @param horResolution
	 *            width of the JPanel
	 * @return true, if x is in the right border.
	 */
	public boolean isXInRightBorder(MapCoordinates position, int horResolution) {
		return position.xToPixel(tileSize) >= mapWidthInPixel - horResolution / 2;
	}

	/**
	 * checks if position is in the lower border.
	 * 
	 * @param position
	 *            position in tiles
	 * @param vertResolution
	 *            height of the JPanel
	 * @return true, if y is in the lower border.
	 */
	public boolean isYInLowerBorder(MapCoordinates position, int vertResolution) {
		return position.yToPixel(tileSize) >= mapHeightInPixel - vertResolution / 2;
	}

	/**
	 * returns true if the horizontal end (right side) of the map is reached.
	 * 
	 * @param xPosition
	 *            x position on the map in pixel
	 * @param horResolution
	 *            width of the JPanel
	 * @return true, if right order is visible
	 */
	public boolean isRightBorderVisible(int xPosition, int horResolution) {
		return xPosition > mapWidthInPixel - horResolution / 2;
	}

	/**
	 * returns true if the vertical end (lower side) of the map is reached.
	 * 
	 * @param yPosition
	 *            y position on the map in pixel
	 * @param vertResolution
	 *            height of the JPanel
	 * @return true, if lower border is visible
	 */
	public boolean isLowerBorderVisible(int yPosition, int vertResolution) {
		return yPosition > mapHeightInPixel - vertResolution / 2;
	}

	/**
	 * gives back a valid position. If (x|y) is too high or too low, it will
	 * return the highest/lowest possible coordinate.
	 * 
	 * @param x
	 *            x that should be on map in tiles
	 * @param y
	 *            y that should be on map in tiles
	 * @return legit map coordinates
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

	/**
	 * returns map width in pixel.
	 * 
	 * @return map width in pixel
	 */
	public int getMapWidthInPixel() {
		return mapWidthInPixel;
	}

	/**
	 * returns map height in pixel.
	 * 
	 * @return map height in pixel
	 */
	public int getMapHeightInPixel() {
		return mapHeightInPixel;
	}

	@Override
	public String toString() {
		return "Map #" + mapId + " \"" + mapName + '"';
	}
}
