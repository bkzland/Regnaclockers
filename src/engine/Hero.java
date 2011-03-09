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
public class Hero {
	private Charset charset;
	private Map map;
	private MapCoordinates targetPosition;
	private MapCoordinates position;
	private int walkingTime = 700;
	private long startTime;

	public Hero(Charset charset, Map map, MapCoordinates position) {
		this.charset = charset;
		this.map = map;
		this.targetPosition = position;
		this.position = position;
	}

	public void drawHero(Graphics g, int horResolution, int vertResolution) {

		int mapX = position.xToPixel(map.getTileSize());
		int mapY = position.yToPixel(map.getTileSize());
		int charX = position.xToPixel(map.getTileSize());
		int charY = position.yToPixel(map.getTileSize());
		int distanceToOldXPosition = 0;
		int distanceToOldYPosition = 0;

		System.out.println("Old: " + position.toString());
		System.out.println("New: " + targetPosition.toString());
		if (position.equals(targetPosition) == false) {
			int newPixelsPerSecond;

			// for diagonal walking
			if (position.getX() != targetPosition.getX() && position.getY() != targetPosition.getY()) {
				int diagonalTime = (int) Math.sqrt(walkingTime * walkingTime * 2); // pythagoras
				newPixelsPerSecond = diagonalTime / map.getTileSize();
			} else {
				newPixelsPerSecond = walkingTime / map.getTileSize();
			}

			// calculates how many pixel away from the start position the hero
			// is
			long timeElapsed = System.currentTimeMillis() - startTime;
			if (position.getX() != targetPosition.getX()) {
				distanceToOldXPosition = (int) timeElapsed / newPixelsPerSecond;
			}
			if (position.getY() != targetPosition.getY()) {
				distanceToOldYPosition = (int) timeElapsed / newPixelsPerSecond;
			}
			System.out.println(timeElapsed);

			// prevents bugs
			if (distanceToOldXPosition > map.getTileSize()) {
				distanceToOldXPosition = map.getTileSize();
			}
			if (distanceToOldYPosition > map.getTileSize()) {
				distanceToOldYPosition = map.getTileSize();
			}

			// if you go to a smaller x- or y-value the variable should be
			// negative.
			// otherwise the isMapReached methods would return wrong booleans.
			if (position.getX() > targetPosition.getX()) {
				distanceToOldXPosition = 0 - distanceToOldXPosition;
			}
			if (position.getY() > targetPosition.getY()) {
				distanceToOldYPosition = 0 - distanceToOldYPosition;
			}
		}

		// distinctions if hero is near a map end

		// x-value
		if (map.isHorMapStartReached(position, distanceToOldXPosition, horResolution, charset.getSpriteWidth())) {
			if (position.getX() != targetPosition.getX()) {
				charX += distanceToOldXPosition;
			}
		} else if (map.isHorMapEndReached(position, distanceToOldXPosition, horResolution, charset.getSpriteWidth())) {
			charX = charX + horResolution - map.getMapWidthInPixel();

			// without that the old position would cause problems if it's not in
			// the map end.
			if (map.isXCoordinateInMapEnd(position, horResolution) == false) {
				mapX += map.getTileSize();
			}

			if (position.getX() != targetPosition.getX()) {
				charX += distanceToOldXPosition;
			}
		} else {
			charX = horResolution / 2 - charset.getSpriteWidth() / 2;
			mapX += charset.getSpriteWidth() / 2;

			if (position.getX() != targetPosition.getX()) {
				mapX += distanceToOldXPosition;
			}
		}

		// y-value
		if (map.isVertMapStartReached(position, distanceToOldYPosition, vertResolution, charset.getSpriteHeight())) {
			charY = charY - charset.getSpriteHeight() + map.getTileSize();
			if (position.getY() != targetPosition.getY()) {
				charY += distanceToOldYPosition;
			}
		} else if (map.isVertMapEndReached(position, distanceToOldYPosition, vertResolution, charset.getSpriteHeight())) {
			charY = charY + vertResolution - map.getMapWidthInPixel();
			charY = charY - charset.getSpriteHeight() + map.getTileSize();

			// without that the old position would cause problems if it's not in
			// the map end.
			if (map.isYCoordinateInMapEnd(position, vertResolution) == false) {
				mapY += map.getTileSize();
			}

			if (position.getY() != targetPosition.getY()) {
				charY += distanceToOldYPosition;
			}
		} else {
			charY = vertResolution / 2 - charset.getSpriteHeight() / 2;
			mapY = mapY +  charset.getSpriteHeight() / 2 + charset.getSpriteHeight() -  2* map.getTileSize();


			if (position.getY() != targetPosition.getY()) {
				mapY += distanceToOldYPosition;
			}
		}

		// walking stops if hero reaches new tile
		if ((distanceToOldXPosition >= map.getTileSize() || distanceToOldXPosition <= 0 - map.getTileSize())
				|| (distanceToOldYPosition >= map.getTileSize() || distanceToOldYPosition <= 0 - map.getTileSize())) {
			position = targetPosition;
		}

		// without, the hero sprite would be too low on the tile
//		charY = charY - charset.getSpriteHeight() + map.getTileSize();


		// first the map must be drawn
		map.drawMap(g, mapX, mapY, horResolution, vertResolution);
		// then the hero on top
		g.drawImage(charset.getLookDownSprites().get(0), charX, charY, null);
	}

	public void walk(int x, int y) {

		if (position.equals(targetPosition)) {
			position = targetPosition;

			int newX = targetPosition.getX() + x;
			int newY = targetPosition.getY() + y;
			targetPosition = map.getLegitCoordinates(newX, newY);
			startTime = System.currentTimeMillis();
		}
	}
}