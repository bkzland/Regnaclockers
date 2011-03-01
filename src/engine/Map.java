package engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
	private final static Logger LOGGER = Logger.getLogger(engine.Map.class.getName());

	private HashMap<Point, Integer> mapGrid = new HashMap<Point, Integer>();
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
		for (int x = 0, width = mapWidthInTiles; x < width; x++) {
			for (int y = 0, height = mapHeightInTiles; y < height; y++) {
				this.mapGrid.put(new Point(x, y), mapGrid[x][y]);
			}
		}
		this.tileset = tileset;
		tileSize = tileset.getTileSize();
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
	public Map(String mapName, HashMap<Point, Integer> mapGrid, int mapWidthInTiles, int mapHeightInTiles,
			Tileset tileset, ArrayList<Event> events) {
		this.mapName = mapName;
		this.mapGrid = mapGrid;
		this.mapWidthInTiles = mapWidthInTiles;
		this.mapHeightInTiles = mapHeightInTiles;
		this.tileset = tileset;
		tileSize = tileset.getTileSize();
		this.events = events;
		loadMap();
		LOGGER.info("Map \"" + mapName + "\" created");
	}

	/**
	 * draws a part of the map. It creates a rectangle around (x|y).
	 * 
	 * @param g
	 * @param x
	 *            players position on x-axis
	 * @param y
	 *            players position on y-axis.
	 * @param horResolution
	 * @param vertResolution
	 */
	public void drawMapPart(Graphics g, int x, int y, int horResolution, int vertResolution) {

		int upperLeftX = x - horResolution / 2;
		int upperLeftY = y - vertResolution / 2;

		// prevents that the map moves if there's no more map to show
		// only effects the upper and left side
		if (upperLeftX < 0) {
			upperLeftX = 0;
		}
		if (upperLeftY < 0) {
			upperLeftY = 0;
		}
		// same for lower and right side
		if (upperLeftX + horResolution > mapWidthInPixel) {
			upperLeftX = mapWidthInPixel - horResolution;
		}
		if (upperLeftY + vertResolution > mapHeightInPixel) {
			upperLeftY = mapHeightInPixel - vertResolution;
		}
		g.drawImage(map.getSubimage(upperLeftX, upperLeftY, horResolution, vertResolution), 0, 0, null);
	}

	public void drawEvents(Graphics g, int x, int y, int horResolution, int vertResolution) {

	}

	/**
	 * creates an image of the whole map.
	 */
	private void loadMap() {
		mapWidthInPixel = mapWidthInTiles * tileSize;
		mapHeightInPixel = mapHeightInTiles * tileSize;

		map = new BufferedImage(mapWidthInPixel, mapHeightInPixel, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = map.createGraphics();
		for (int x = 0; x < mapWidthInTiles; x++) {
			for (int y = 0; y < mapHeightInTiles; y++) {
				g2d.drawImage(tileset.getSprite(mapGrid.get(new Point(x, y))), x * tileSize, y * tileSize, null);
			}
		}
	}
}