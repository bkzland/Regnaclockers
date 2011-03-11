package engine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.logging.Logger;

import engine.map.Map;
import engine.map.MapCoordinates;
import engine.sprite.Charset;
import engine.sprite.Tileset;

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
	private int walkingTime = 1120;
	private long startTime;
	private List<BufferedImage> lookDown;
	private List<BufferedImage> lookLeft;
	private List<BufferedImage> lookRight;
	private List<BufferedImage> lookUp;
	private BufferedImage lookingDirection;
	private int tileSize;

	public Hero(Charset charset, Map map, MapCoordinates position) {
		this.charset = charset;
		this.map = map;
		this.targetPosition = position;
		this.position = position;
		lookDown = charset.getLookDownSprites();
		lookLeft = charset.getLookLeftSprites();
		lookRight = charset.getLookRightSprites();
		lookUp = charset.getLookUpSprites();
		lookingDirection = lookDown.get(0);
		tileSize = map.getTileSize();
	}

	/**
	 * draws the hero.
	 * 
	 * @param g
	 * @param horResolution
	 * @param vertResolution
	 */
	public void drawHero(Graphics g, int horResolution, int vertResolution) {

		int distanceToOldXPosition = 0;
		int distanceToOldYPosition = 0;

		if (position.equals(targetPosition) == false) {
			distanceToOldXPosition = calculateXDistanceToStartPosition(distanceToOldXPosition, walkingTime);
			distanceToOldYPosition = calculateYDistanceToStartPosition(distanceToOldYPosition, walkingTime);
		}

		int charX = calculateNewCharX(distanceToOldXPosition, horResolution);
		int mapX = calculateNewMapX(distanceToOldXPosition, horResolution);
		int charY = calculateNewCharY(distanceToOldYPosition, vertResolution);
		int mapY = calculateNewMapY(distanceToOldYPosition, vertResolution);

		setPositionToTargetPositionIfReached(distanceToOldXPosition, distanceToOldYPosition);

		BufferedImage displayedSprite = setAnimationSprite(distanceToOldXPosition, distanceToOldYPosition);

		LOGGER.finer("Map: (" + mapX + "|" + mapY + ") Hero: (" + charX + "|" + charY + ")");

		map.drawMap(g, mapX, mapY, horResolution, vertResolution);
		g.drawImage(displayedSprite, charX, charY, null);
	}

	private BufferedImage setAnimationSprite(int distanceToOldXPosition, int distanceToOldYPosition) {
		int spriteID = 0;
		BufferedImage displayedSprite = null;

		if (position.getX() > targetPosition.getX()) {
			spriteID = (Math.abs(distanceToOldXPosition) * lookLeft.size()) / tileSize + 1;
			if (spriteID >= lookLeft.size()) {
				spriteID = lookLeft.size() - 1;
			}
			lookingDirection = lookLeft.get(0);
			displayedSprite = lookLeft.get(spriteID);
		} else if (position.getX() < targetPosition.getX()) {
			spriteID = (Math.abs(distanceToOldXPosition) * lookRight.size()) / tileSize + 1;
			if (spriteID >= lookRight.size()) {
				spriteID = lookRight.size() - 1;
			}
			lookingDirection = lookRight.get(0);
			displayedSprite = lookRight.get(spriteID);
		}

		if (position.getY() > targetPosition.getY()) {
			spriteID = (Math.abs(distanceToOldYPosition) * lookUp.size()) / tileSize + 1;
			if (spriteID >= lookUp.size()) {
				spriteID = lookUp.size() - 1;
			}
			lookingDirection = lookUp.get(0);
			displayedSprite = lookUp.get(spriteID);
		} else if (position.getY() < targetPosition.getY()) {
			spriteID = (Math.abs(distanceToOldYPosition) * lookDown.size()) / tileSize + 1;
			if (spriteID >= lookDown.size()) {
				spriteID = lookDown.size() - 1;
			}
			lookingDirection = lookDown.get(0);
			displayedSprite = lookDown.get(spriteID);
		}
		
		if(displayedSprite == null) {
			displayedSprite = lookingDirection;
		}

		return displayedSprite;
	}

	private int reduceDistanceToMaxDistance(int distance) {
		if (distance > tileSize) {
			distance = tileSize;
		}
		return distance;
	}

	private void setPositionToTargetPositionIfReached(int distanceToOldXPosition, int distanceToOldYPosition) {
		if ((distanceToOldXPosition >= tileSize || distanceToOldXPosition <= 0 - tileSize)
				|| (distanceToOldYPosition >= tileSize || distanceToOldYPosition <= (-1) * tileSize)) {
			position = targetPosition;
		}
	}

	private int makeXDistanceNegativeIfTargetXPositionSmaller(int distance) {
		if (position.getX() > targetPosition.getX()) {
			distance = (-1) * distance;
		}
		return distance;
	}

	private int makeYDistanceNegativeIfTargetYPositionSmaller(int distance) {
		if (position.getY() > targetPosition.getY()) {
			distance = (-1) * distance;
		}
		return distance;
	}

	private int calculateHowManyMilliSecondsMustElapseForANewPixelOfDistance(int milliSecondsPerPixel) {
		// pythagoras for diagonal walking
		if (position.getX() != targetPosition.getX() && position.getY() != targetPosition.getY()) {
			int diagonalTime = (int) Math.sqrt(walkingTime * walkingTime * 2);
			milliSecondsPerPixel = diagonalTime / tileSize;
		} else {
			milliSecondsPerPixel = walkingTime / tileSize;
		}

		return milliSecondsPerPixel;
	}

	private int calculateXDistanceToStartPosition(int distance, int walkingTime) {
		int milliSecondsPerPixel = calculateHowManyMilliSecondsMustElapseForANewPixelOfDistance(walkingTime);
		long timeElapsed = System.currentTimeMillis() - startTime;

		if (position.getX() != targetPosition.getX()) {
			distance = (int) timeElapsed / milliSecondsPerPixel;
			distance = reduceDistanceToMaxDistance(distance);
			distance = makeXDistanceNegativeIfTargetXPositionSmaller(distance);

		} else {
			distance = 0;
		}

		return distance;
	}

	private int calculateYDistanceToStartPosition(int distance, int walkingTime) {
		int milliSecondsPerPixel = calculateHowManyMilliSecondsMustElapseForANewPixelOfDistance(walkingTime);
		long timeElapsed = System.currentTimeMillis() - startTime;

		if (position.getY() != targetPosition.getY()) {
			distance = (int) timeElapsed / milliSecondsPerPixel;
			distance = reduceDistanceToMaxDistance(distance);
			distance = makeYDistanceNegativeIfTargetYPositionSmaller(distance);
		} else {
			distance = 0;
		}

		return distance;
	}

	private int calculateNewMapX(int distance, int horResolution) {
		int mapX = position.xToPixel(tileSize);
		int heroSpriteMapXPosition = position.xToPixel(tileSize) + distance + charset.getSpriteWidth() / 2;

		if (map.isHorMapStartReached(heroSpriteMapXPosition, horResolution)) {

		} else if (map.isHorMapEndReached(heroSpriteMapXPosition, horResolution)) {

			// without that the old position would cause problems if it's not in
			// the map end.
			if (map.isXCoordinateInMapEnd(position, horResolution) == false) {
				mapX += tileSize;
			}

		} else {
			mapX += charset.getSpriteWidth() / 2;

			if (position.getX() != targetPosition.getX()) {
				mapX += distance;
			}
		}
		return mapX;
	}

	private int calculateNewCharX(int distance, int horResolution) {
		int charX = position.xToPixel(tileSize);
		int heroSpriteMapXPosition = position.xToPixel(tileSize) + distance + charset.getSpriteWidth() / 2;

		if (map.isHorMapStartReached(heroSpriteMapXPosition, horResolution)) {
			if (position.getX() != targetPosition.getX()) {
				charX += distance;
			}
		} else if (map.isHorMapEndReached(heroSpriteMapXPosition, horResolution)) {
			charX = charX + horResolution - map.getMapWidthInPixel();

			if (position.getX() != targetPosition.getX()) {
				charX += distance;
			}
		} else {
			charX = horResolution / 2 - charset.getSpriteWidth() / 2;
		}
		return charX;
	}

	private int calculateNewMapY(int distance, int vertResolution) {
		int mapY = position.yToPixel(tileSize);
		int heroSpriteMapYPosition = position.yToPixel(tileSize) + distance + charset.getSpriteHeight() / 2
				- charset.getSpriteHeight() + tileSize;

		if (map.isVertMapStartReached(heroSpriteMapYPosition, vertResolution)) {

		} else if (map.isVertMapEndReached(heroSpriteMapYPosition, vertResolution)) {

			// without that the old position would cause problems if it's not in
			// the map end.
			if (map.isYCoordinateInMapEnd(position, vertResolution) == false) {
				mapY += tileSize;
			}
		} else {
			mapY = mapY + charset.getSpriteHeight() / 2 + charset.getSpriteHeight() - 2 * tileSize;

			if (position.getY() != targetPosition.getY()) {
				mapY += distance;
			}
		}

		return mapY;
	}

	private int calculateNewCharY(int distance, int vertResolution) {
		int charY = position.yToPixel(tileSize);
		int heroSpriteMapYPosition = position.yToPixel(tileSize) + distance + charset.getSpriteHeight() / 2
				- charset.getSpriteHeight() + tileSize;

		if (map.isVertMapStartReached(heroSpriteMapYPosition, vertResolution)) {
			charY = charY - charset.getSpriteHeight() + tileSize;
			if (position.getY() != targetPosition.getY()) {
				charY += distance;
			}
		} else if (map.isVertMapEndReached(heroSpriteMapYPosition, vertResolution)) {
			charY = charY + vertResolution - map.getMapWidthInPixel();
			charY = charY - charset.getSpriteHeight() + tileSize;

			if (position.getY() != targetPosition.getY()) {
				charY += distance;
			}
		} else {
			charY = vertResolution / 2 - charset.getSpriteHeight() / 2;

		}
		return charY;
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