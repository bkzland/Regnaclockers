package engine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.logging.Logger;

import engine.map.Map;
import engine.map.MapCoordinates;
import engine.sprite.Charset;

/**
 * The player.
 * 
 * @author regnaclockers
 */
public class Hero {
	private final static Logger LOGGER = Logger.getLogger(engine.Hero.class.getName());

	
	private Charset charset;
	private Map map;
	private MapCoordinates targetPosition;
	private MapCoordinates position;
	private int walkingTime = 700;
	private long startTime;
	private List<BufferedImage> lookDown;
	private List<BufferedImage> lookLeft;
	private List<BufferedImage> lookRight;
	private List<BufferedImage> lookUp;
	BufferedImage displayedSprite;

	public Hero(Charset charset, Map map, MapCoordinates position) {
		this.charset = charset;
		this.map = map;
		this.targetPosition = position;
		this.position = position;
		lookDown = charset.getLookDownSprites();
		lookLeft = charset.getLookLeftSprites();
		lookRight = charset.getLookRightSprites();
		lookUp = charset.getLookUpSprites();
		displayedSprite = lookDown.get(0);

	}

	/**
	 * draws the hero.
	 * 
	 * @param g
	 * @param horResolution
	 * @param vertResolution
	 */
	public void drawHero(Graphics g, int horResolution, int vertResolution) {

		int mapX = position.xToPixel(map.getTileSize());
		int mapY = position.yToPixel(map.getTileSize());
		int charX = position.xToPixel(map.getTileSize());
		int charY = position.yToPixel(map.getTileSize());
		int distanceToOldXPosition = 0;
		int distanceToOldYPosition = 0;

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
			mapY = mapY + charset.getSpriteHeight() / 2 + charset.getSpriteHeight() - 2 * map.getTileSize();

			if (position.getY() != targetPosition.getY()) {
				mapY += distanceToOldYPosition;
			}
		}

		// walking stops if hero reaches new tile
		if ((distanceToOldXPosition >= map.getTileSize() || distanceToOldXPosition <= 0 - map.getTileSize())
				|| (distanceToOldYPosition >= map.getTileSize() || distanceToOldYPosition <= (-1) * map.getTileSize())) {
			position = targetPosition;
		}

		// char animation

		getAnimationSprite(distanceToOldXPosition, distanceToOldYPosition);
		
		LOGGER.finer("Map: (" + mapX + "|" + mapY + ") Hero: (" + charX + "|" + charY + ")");
		// first the map must be drawn
		map.drawMap(g, mapX, mapY, horResolution, vertResolution);
		// then the hero on top
		g.drawImage(displayedSprite, charX, charY, null);
	}

	private void getAnimationSprite(int distanceToOldXPosition, int distanceToOldYPosition) {
		if (position.getX() > targetPosition.getX()) {
			int spriteID = (Math.abs(distanceToOldXPosition) * lookLeft.size() - 1) / map.getTileSize() + 1;
			if (spriteID >= lookLeft.size())
				spriteID = 0;

			displayedSprite = lookLeft.get(spriteID);
		} else if (position.getX() < targetPosition.getX()) {
			int spriteID = (Math.abs(distanceToOldXPosition) * lookRight.size() - 1) / map.getTileSize() + 1;
			if (spriteID >= lookRight.size())
				spriteID = 0;

			displayedSprite = lookRight.get(spriteID);
		}

		if (position.getY() > targetPosition.getY()) {
			int spriteID = (Math.abs(distanceToOldYPosition) * lookUp.size() - 1) / map.getTileSize() + 1;
			if (spriteID >= lookUp.size())
				spriteID = 0;

			displayedSprite = lookUp.get(spriteID);
		} else if (position.getY() < targetPosition.getY()) {
			int spriteID = (Math.abs(distanceToOldYPosition) * lookDown.size() - 1) / map.getTileSize() + 1;
			if (spriteID >= lookDown.size())
				spriteID = 0;

			displayedSprite = lookDown.get(spriteID);
		}
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