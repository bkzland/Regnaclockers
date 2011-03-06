package engine;

import java.awt.Graphics;

import engine.map.Map;
import engine.map.MapCoordinates;
import engine.sprite.Charset;

/**
 * The player.
 * 
 * @author regnaclockers
 */
public class Character {
	private Charset charset;
	private Map map;
	private MapCoordinates targetPosition;
	private MapCoordinates position;
	private int walkingSpeed = 700;
	private long startTime;

	public Character(Charset charset, Map map, MapCoordinates position) {
		this.charset = charset;
		this.map = map;
		this.targetPosition = position;
		this.position = position;
	}

	public void drawCharacter(Graphics g, int horResolution, int vertResolution) {

		int mapX = position.xToPixel(map.getTileSize());
		int mapY = position.yToPixel(map.getTileSize());
		int charX = mapX;
		int charY = mapY;
		int distanceToTile = 0;

		// changes the position pixel by pixel until tile is reached
		if (position != targetPosition) {

			long timeElapsed = System.currentTimeMillis() - startTime;
			int newPixelsPerSecond = walkingSpeed / map.getTileSize();
			distanceToTile = (int) timeElapsed / newPixelsPerSecond;
			if (position.getX() < targetPosition.getX()) {
				charX += distanceToTile;
			} else if (position.getX() > targetPosition.getX()) {
				charX -= distanceToTile;
			} else if (position.getY() < targetPosition.getY()) {
				charY += distanceToTile;
			} else if (position.getY() > targetPosition.getY()) {
				charY -= distanceToTile;
			}

		}

		// if center of panel is reached, map moves and char is fixed on the
		// center
		if (charX > horResolution / 2 && map.isHorMapEndReached(targetPosition, horResolution) == false) {
			if (position != targetPosition) {
				mapX += charX - position.xToPixel(map.getTileSize());
			}
			charX = horResolution / 2;
		}
		if (charY > vertResolution / 2 && map.isVertMapEndReached(targetPosition, vertResolution) == false) {
			if (position != targetPosition) {
				mapY += charY - position.yToPixel(map.getTileSize());
			}
			charY = vertResolution / 2;
		}
		if (distanceToTile >= map.getTileSize() || distanceToTile <= 0 - map.getTileSize()) {
			position = targetPosition;
		}
		distanceToTile = 0;

		if (map.isHorMapEndReached(targetPosition, horResolution)) {
			charX += horResolution - map.getMapWidthInPixel();
		}

		if (map.isVertMapEndReached(targetPosition, vertResolution)) {
			charY += vertResolution - map.getMapHeightInPixel();
		}

		charY = charY - charset.getSpriteHeight() + map.getTileSize();

		map.drawMap(g, mapX, mapY, horResolution, vertResolution);
		g.drawImage(charset.getLookDownSprites().get(0), charX, charY, null);
	}

	public void walkDown() {
		walk(0, 1);
	}

	public void walkLeft() {
		walk(-1, 0);
	}

	public void walkRight() {
		walk(1, 0);
	}

	public void walkUp() {
		walk(0, -1);
	}

	private void walk(int x, int y) {

		if (position == targetPosition) {
			position = targetPosition;

			int newX = targetPosition.getX() + x;
			int newY = targetPosition.getY() + y;
			targetPosition = map.getLegitCoordinates(newX, newY);

			startTime = System.currentTimeMillis();

		}
	}
}