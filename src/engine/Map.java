package engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import engine.sprite.Tileset;

/**
 * This class is used to represent a map.
 * 
 * @author regnaclockers
 */
public class Map {
	private HashMap<Point, Integer> mapGrid = new HashMap<Point, Integer>();
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
	public Map(String mapName, int[][] mapGrid, Tileset tileset) {
		this.mapName = mapName;
		for (int x = 0, width = mapGrid.length; x < width; x++) {
			for (int y = 0, height = mapGrid[0].length; y < height; y++) {
				this.mapGrid.put(new Point(x, y), mapGrid[x][y]);
			}
		}
		this.mapWidthInTiles = mapGrid.length;
		this.mapHeightInTiles = mapGrid[0].length;
		this.tileset = tileset;
		tileSize = tileset.getTileSize();
		loadMap();
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
			Tileset tileset) {
		this.mapName = mapName;
		this.mapGrid = mapGrid;
		this.mapWidthInTiles = mapWidthInTiles;
		this.mapHeightInTiles = mapHeightInTiles;
		this.tileset = tileset;
		tileSize = tileset.getTileSize();
		loadMap();
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
		int startX = x - horResolution / 2;
		int startY = y - vertResolution / 2;

		// prevents that the map moves if there's no more map to show
		// only effects the upper and left side
		if (startX < 0) {
			startX = 0;
		}
		if (startY < 0) {
			startY = 0;
		}
		// same for lower and right side
		if (startX + horResolution > mapWidthInPixel) {
			startX = mapWidthInPixel - horResolution;
		}
		if (startY + vertResolution > mapHeightInPixel) {
			startY = mapHeightInPixel - vertResolution;
		}
		g.drawImage(map.getSubimage(startX, startY, horResolution, vertResolution), 0, 0, null);
	}

	/**
	 * creates an image of the whole map.
	 */
	private void loadMap() {
		map = new BufferedImage(mapWidthInTiles * tileSize, mapHeightInTiles * tileSize, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = map.createGraphics();

		for (int x = 0; x < mapWidthInTiles; x++) {
			for (int y = 0; y < mapHeightInTiles; y++) {
				g2d.drawImage(tileset.getSprite(mapGrid.get(new Point(x, y))), y * tileSize, x * tileSize, null);
			}
		}
		mapWidthInPixel = map.getWidth();
		mapHeightInPixel = map.getHeight();
	}
}