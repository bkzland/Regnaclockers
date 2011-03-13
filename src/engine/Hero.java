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
	private int walkingTime = 1120;
	private long startTime;
	private List<BufferedImage> lookDown;
	private List<BufferedImage> lookLeft;
	private List<BufferedImage> lookRight;
	private List<BufferedImage> lookUp;
	private BufferedImage lookingDirection;
	private int tileSize;
	private int distanceOldX;
	private int distanceOldY;
	private int xRequest = 0;
	private int yRequest = 0;

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

		distanceOldX = calculateDistanceToStart(position.getX(), target.getX());
		distanceOldY = calculateDistanceToStart(position.getY(), target.getY());

		int heroX = calculateNewHeroX(distanceOldX, horResolution);
		int mapX = calculateNewMapX(distanceOldX, horResolution);
		int heroY = calculateNewHeroY(distanceOldY, vertResolution);
		int mapY = calculateNewMapY(distanceOldY, vertResolution);

		map.drawMap(g, mapX, mapY, horResolution, vertResolution);

		// BufferedImage displayedSprite = getAnimationSprite(distanceOldX,
		// distanceOldY);

		LOGGER.finer("Map: (" + mapX + '|' + mapY + ") Hero: (" + heroX + '|' + heroY + ')');
		drawSprite(g, heroX, heroY, distanceOldX, distanceOldY);
		// g.drawImage(displayedSprite, heroX, heroY, null);
		setToTargetIfReached(distanceOldX, distanceOldY);

	}

	private void drawSprite(Graphics g, int heroX, int heroY, int distanceOldX, int distanceOldY) {
		int spriteID;
		if (position.equals(target)) {
			charset.drawLast(g, heroX, heroY);
		} else if (position.xGreaterThan(target)) {
			spriteID = (Math.abs(distanceOldX) * (lookLeft.size() - 1)) / tileSize + 1;
			if (spriteID >= lookLeft.size()) {
				spriteID = lookLeft.size() - 1;
			}
			charset.drawLeftSprite(g, spriteID, heroX, heroY);
		} else if (position.xLessThan(target)) {
			spriteID = (Math.abs(distanceOldX) * (lookRight.size() - 1)) / tileSize + 1;
			if (spriteID >= lookRight.size()) {
				spriteID = lookRight.size() - 1;
			}
			charset.drawRightSprite(g, spriteID, heroX, heroY);
		} else if (position.yGreaterThan(target)) {
			spriteID = (Math.abs(distanceOldY) * (lookUp.size() - 1)) / tileSize + 1;
			if (spriteID >= lookUp.size()) {
				spriteID = lookUp.size() - 1;
			}
			charset.drawUpSprite(g, spriteID, heroX, heroY);
		} else if (position.yLessThan(target)) {
			spriteID = (Math.abs(distanceOldY) * (lookDown.size() - 1)) / tileSize + 1;
			if (spriteID >= lookDown.size()) {
				spriteID = lookDown.size() - 1;
			}
			charset.drawDownSprite(g, spriteID, heroX, heroY);
		} else {
			LOGGER.severe("This should be impossible to reach");
		}
	}

	private void setToTargetIfReached(int distanceOldX, int distanceOldY) {
		if ((distanceOldX >= tileSize || distanceOldX <= (-1) * tileSize)
				|| (distanceOldY >= tileSize || distanceOldY <= (-1) * tileSize)) {
			position = target;

			// Sets new position if position change was requested while walking
			if (xRequest != 0 || yRequest != 0) {
				int newX = position.getX() + xRequest;
				int newY = position.getY() + yRequest;
				target = map.getLegitCoordinates(newX, newY);
				startTime = System.currentTimeMillis();
				xRequest = 0;
				yRequest = 0;
			}
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

	private int calculateDistanceToStart(int oldValue, int newValue) {
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

	private int calculateNewHeroX(int distance, int horResolution) {
		int heroX = position.xToPixel(tileSize);
		int heroSpriteMapXPosition = position.xToPixel(tileSize) + distance + charset.getSpriteWidth() / 2;

		if (map.isLeftBorderVisible(heroSpriteMapXPosition, horResolution)) {
			if (!position.xEquals(target)) {
				heroX += distance;
			}
		} else if (map.isRightBorderVisible(heroSpriteMapXPosition, horResolution)) {
			heroX = heroX + horResolution - map.getMapWidthInPixel();

			if (!position.xEquals(target)) {
				heroX += distance;
			}
		} else {
			heroX = horResolution / 2 - charset.getSpriteWidth() / 2;
		}
		return heroX;
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

	private int calculateNewHeroY(int distance, int vertResolution) {
		int heroY = position.yToPixel(tileSize);
		int heroSpriteMapYPosition = position.yToPixel(tileSize) + distance + charset.getSpriteHeight() / 2
				- charset.getSpriteHeight() + tileSize;

		if (map.isUpperBorderVisible(heroSpriteMapYPosition, vertResolution)) {
			heroY = heroY - charset.getSpriteHeight() + tileSize;
			if (!position.yEquals(target)) {
				heroY += distance;
			}
		} else if (map.isLowerBorderVisible(heroSpriteMapYPosition, vertResolution)) {
			heroY = heroY + vertResolution - map.getMapWidthInPixel();
			heroY = heroY - charset.getSpriteHeight() + tileSize;

			if (!position.yEquals(target)) {
				heroY += distance;
			}
		} else {
			heroY = vertResolution / 2 - charset.getSpriteHeight() / 2;
		}
		return heroY;
	}

	public void walk(int x, int y) {
		if (position.equals(target)) {
			int newX = position.getX() + x;
			int newY = position.getY() + y;
			target = map.getLegitCoordinates(newX, newY);
			startTime = System.currentTimeMillis();
		} else if (Math.abs(distanceOldX) + Math.abs(distanceOldX) / 5 > tileSize
				|| Math.abs(distanceOldY) + Math.abs(distanceOldY) / 5 > tileSize) {
			xRequest = x;
			yRequest = y;
		}
	}
}
