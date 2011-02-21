package engine;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * This class is used to represent a map.
 * 
 * @author regnaclockers
 */
public class Map {
	private HashMap<Point, Integer> mapGrid = new HashMap<Point, Integer>();
	private BufferedImage map;
	private int mapWidth;
	private int mapHeight;
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
		this.mapWidth = mapGrid.length;
		this.mapHeight = mapGrid[0].length;
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
	 * @param mapWidth
	 *            width of the map.
	 * @param mapHeight
	 *            height of the map.
	 * @param tileset
	 *            the Tileset object it should use.
	 */
	public Map(String mapName, HashMap<Point, Integer> mapGrid, int mapWidth, int mapHeight, Tileset tileset) {
		this.mapName = mapName;
		this.mapGrid = mapGrid;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.tileset = tileset;
		tileSize = tileset.getTileSize();
		loadMap();
	}

	/**
	 * changes tile on map position (x|y)
	 * 
	 * @param x
	 *            the x-value of the position.
	 * @param y
	 *            the y-value of the position.
	 * @param tileID
	 *            the tile which is set on position (x|y).
	 */
	public void changeTile(int x, int y, int tileID) {
		mapGrid.put(new Point(x, y), tileID);
	}

	/**
	 * returns the width of the map.
	 * 
	 * @return mapWidth
	 */
	public int getWidth() {
		return mapWidth;
	}

	/**
	 * returns the height of the map.
	 * 
	 * @return mapHeight
	 */
	public int getHeight() {
		return mapHeight;
	}

	/**
	 * returns name of the map.
	 * 
	 * @return mapName
	 */
	public String getName() {
		return mapName;
	}

	/**
	 * returns the size of the tiles.
	 * 
	 * @return tileSize
	 */
	public int getTileSize() {
		int tileSize = tileset.getTileSize();
		return tileSize;
	}

	/**
	 * returns a part of the map. (x|y) is the coordinate, width and height the
	 * size of the map part. It starts on the top left.
	 * 
	 * @param x
	 *            x position in pixels.
	 * @param y
	 *            y position in pixels.
	 * @param width
	 *            width in pixels.
	 * @param height
	 *            height in pixels.
	 * @return mapPart part of the map
	 */
	public BufferedImage getMapPart(int x, int y, int width, int height) {
		//TODO prevent error by requesting a subimage outside of the maps size
		BufferedImage mapPart = map.getSubimage(x, y, width, height);
		return mapPart;
	}

	/**
	 * creates an image of the whole map.
	 */
	private void loadMap() {
		map = new BufferedImage(mapWidth * tileSize, mapHeight * tileSize, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = map.createGraphics();

		for (int x = 0; x < mapWidth; x++) {
			for (int y = 0; y < mapHeight; y++) {
				g2d.drawImage(tileset.getSprite(mapGrid.get(new Point(x, y))), y * tileSize, x * tileSize, null);
			}
		}
	}
}