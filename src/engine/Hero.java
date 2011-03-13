package engine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.logging.Logger;

import engine.map.TileMap;
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
	private TileMap map;
	private MapCoordinates target;
	private MapCoordinates position;
	private int walkingTime = 600;
	private long startTime;
	private List<BufferedImage> lookDown;
	private List<BufferedImage> lookLeft;
	private List<BufferedImage> lookRight;
	private List<BufferedImage> lookUp;
	private BufferedImage lookingDirection;
	private int tileSize;

	public Hero(Charset charset, TileMap map, MapCoordinates position) {
		this.charset = charset;
		this.map = map;
		this.target = position;
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

		int distanceOldX = calculateDistanceToStart(position.getX(), target.getX(), walkingTime);
		int distanceOldY = calculateDistanceToStart(position.getY(), target.getY(), walkingTime);

		int charX = calculateNewCharX(distanceOldX, horResolution);
		int mapX = calculateNewMapX(distanceOldX, horResolution);
		int charY = calculateNewCharY(distanceOldY, vertResolution);
		int mapY = calculateNewMapY(distanceOldY, vertResolution);

		setToTargetIfReached(distanceOldX, distanceOldY);

		BufferedImage displayedSprite = getAnimationSprite(distanceOldX, distanceOldY);

		LOGGER.finer("Map: (" + mapX + '|' + mapY + ") Hero: (" + charX + '|' + charY + ')');

		map.drawMap(g, mapX, mapY, horResolution, vertResolution);
		g.drawImage(displayedSprite, charX, charY, null);
	}

	private BufferedImage getAnimationSprite(int distanceOldXPosition, int distanceOldYPosition) {
		int spriteID;
		BufferedImage displayedSprite = null;

		if (position.xGreaterThan(target)) {
			spriteID = (Math.abs(distanceOldXPosition) * lookLeft.size() + ((tileSize / lookLeft.size() * 2)))
					/ tileSize;
			if (spriteID >= lookLeft.size()) {
				spriteID = 0;
			}
			lookingDirection = lookLeft.get(0);
			displayedSprite = lookLeft.get(spriteID);
		} else if (position.xLessThan(target)) {
			spriteID = (Math.abs(distanceOldXPosition) * lookRight.size() + ((tileSize / lookRight.size() * 2)))
					/ tileSize;
			if (spriteID >= lookRight.size()) {
				spriteID = 0;
			}
			lookingDirection = lookRight.get(0);
			displayedSprite = lookRight.get(spriteID);
		}

		if (position.yGreaterThan(target)) {
			spriteID = (Math.abs(distanceOldYPosition) * lookUp.size() + ((tileSize / lookUp.size() * 2))) / tileSize;
			if (spriteID >= lookUp.size()) {
				spriteID = 0;
			}
			lookingDirection = lookUp.get(0);
			displayedSprite = lookUp.get(spriteID);
		} else if (position.yLessThan(target)) {
			spriteID = (Math.abs(distanceOldYPosition) * lookDown.size() + ((tileSize / lookDown.size() * 2)))
					/ tileSize;
			if (spriteID >= lookDown.size()) {
				spriteID = 0;
			}
			lookingDirection = lookDown.get(0);
			displayedSprite = lookDown.get(spriteID);
		}

		if (displayedSprite == null) {
			displayedSprite = lookingDirection;
		}

		return displayedSprite;
	}

	private void setToTargetIfReached(int distanceOldX, int distanceOldY) {
		if ((distanceOldX >= tileSize || distanceOldX <= (-1) * tileSize)
				|| (distanceOldY >= tileSize || distanceOldY <= (-1) * tileSize)) {
			position = target;
		}
	}

	private int calculateMilliSecondsForNewPixel() {
		int milliSecondsPerPixel;
		if (position.xEquals(target) || position.yEquals(target)) {
			milliSecondsPerPixel = walkingTime / tileSize;
		} else {
			// pythagoras for diagonal walking
			int diagonalTime = (int) Math.sqrt(2 * walkingTime * walkingTime);
			milliSecondsPerPixel = diagonalTime / tileSize;
		}
		return milliSecondsPerPixel;
	}

	private int calculateDistanceToStart(int oldValue, int newValue, int walkingTime) {
		int milliSecondsPerPixel = calculateMilliSecondsForNewPixel();
		long timeElapsed = System.currentTimeMillis() - startTime;
		int distance;

		if (oldValue < newValue) {
			distance = (int) timeElapsed / milliSecondsPerPixel;
			if (distance > tileSize) {
				distance = tileSize;
			}
		} else if (oldValue > newValue) {
			distance = (-1) * (int) timeElapsed / milliSecondsPerPixel;
			if (distance < (-1) * tileSize) {
				distance = (-1) * tileSize;
			}
		} else {
			distance = 0;
		}
		return distance;
	}

	private int calculateNewMapX(int distance, int horResolution) {
		int mapX = position.xToPixel(tileSize);
		int heroSpriteMapXPosition = position.xToPixel(tileSize) + distance + charset.getSpriteWidth() / 2;

		if (map.isRightBorderVisible(heroSpriteMapXPosition, horResolution)) {

			// without that the old position would cause problems if it's not in
			// the map end.
			if (!map.isXCoordinateInMapEnd(position, horResolution)) {
				mapX += tileSize;
			}
		} else if (!map.isLeftBorderVisible(heroSpriteMapXPosition, horResolution)) {
			mapX += charset.getSpriteWidth() / 2;

			if (!position.xEquals(target)) {
				mapX += distance;
			}
		}
		return mapX;
	}

	private int calculateNewCharX(int distance, int horResolution) {
		int charX = position.xToPixel(tileSize);
		int heroSpriteMapXPosition = position.xToPixel(tileSize) + distance + charset.getSpriteWidth() / 2;

		if (map.isLeftBorderVisible(heroSpriteMapXPosition, horResolution)) {
			if (!position.xEquals(target)) {
				charX += distance;
			}
		} else if (map.isRightBorderVisible(heroSpriteMapXPosition, horResolution)) {
			charX = charX + horResolution - map.getMapWidthInPixel();

			if (!position.xEquals(target)) {
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

		if (map.isLowerBorderVisible(heroSpriteMapYPosition, vertResolution)) {

			// without that the old position would cause problems if it's not in
			// the map end.
			if (!map.isYCoordinateInMapEnd(position, vertResolution)) {
				mapY += tileSize;
			}
		} else if (!map.isUpperBorderVisible(heroSpriteMapYPosition, vertResolution)) {
			mapY = mapY + charset.getSpriteHeight() / 2 + (tileSize - charset.getSpriteHeight());

			if (!position.yEquals(target)) {
				mapY += distance;
			}
		}

		return mapY;
	}

	private int calculateNewCharY(int distance, int vertResolution) {
		int charY = position.yToPixel(tileSize);
		int heroSpriteMapYPosition = position.yToPixel(tileSize) + distance + charset.getSpriteHeight() / 2
				- charset.getSpriteHeight() + tileSize;

		if (map.isUpperBorderVisible(heroSpriteMapYPosition, vertResolution)) {
			charY = charY - charset.getSpriteHeight() + tileSize;
			if (!position.yEquals(target)) {
				charY += distance;
			}
		} else if (map.isLowerBorderVisible(heroSpriteMapYPosition, vertResolution)) {
			charY = charY + vertResolution - map.getMapWidthInPixel();
			charY = charY - charset.getSpriteHeight() + tileSize;

			if (!position.yEquals(target)) {
				charY += distance;
			}
		} else {
			charY = vertResolution / 2 - charset.getSpriteHeight() / 2;

		}
		return charY;
	}

	public void walk(int x, int y) {
		if (position.equals(target)) {
			int newX = position.getX() + x;
			int newY = position.getY() + y;
			target = map.getLegitCoordinates(newX, newY);
			startTime = System.currentTimeMillis();
		}
	}
}
