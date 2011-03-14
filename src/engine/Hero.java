package engine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.logging.Logger;

import engine.map.TileMap;
import engine.map.MapCoordinates;
import engine.sprite.Charset;

/**
 * The hero which is controlled by the player.
 * 
 * @author regnaclockers
 */
public class Hero {
	private static final Logger LOGGER = Logger.getLogger(engine.Hero.class.getName());

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
	private int tileSize;
	private int distanceOldX;
	private int distanceOldY;
	private int xRequest = 0;
	private int yRequest = 0;

	/**
	 * creates a new hero. The hero is controlled by the player.
	 * 
	 * @param charset
	 *            the graphical representation of the hero
	 * @param map
	 *            the map the hero is on
	 * @param position
	 *            the heros position on the map.
	 */
	public Hero(Charset charset, TileMap map, MapCoordinates position) {
		this.charset = charset;
		this.map = map;
		this.target = position;
		this.position = position;
		lookDown = charset.getLookDownSprites();
		lookLeft = charset.getLookLeftSprites();
		lookRight = charset.getLookRightSprites();
		lookUp = charset.getLookUpSprites();
		tileSize = map.getTileSize();
	}

	/**
	 * draws the hero.
	 * 
	 * @param g
	 *            graphics object
	 * @param horResolution
	 *            width of JPanel
	 * @param vertResolution
	 *            height of JPanel
	 */
	public void drawHero(Graphics g, int horResolution, int vertResolution) {

		distanceOldX = calculateDistanceToStart(position.getX(), target.getX());
		distanceOldY = calculateDistanceToStart(position.getY(), target.getY());

		int heroX = calculateNewHeroX(distanceOldX, horResolution);
		int mapX = calculateNewMapX(distanceOldX, horResolution);
		int heroY = calculateNewHeroY(distanceOldY, vertResolution);
		int mapY = calculateNewMapY(distanceOldY, vertResolution);

		map.drawMap(g, mapX, mapY, horResolution, vertResolution);
		drawSprite(g, heroX, heroY, distanceOldX, distanceOldY);

		LOGGER.finer("Map: (" + mapX + '|' + mapY + ") Hero: (" + heroX + '|' + heroY + ')');

		setToTargetIfReached(distanceOldX, distanceOldY);

	}

	/**
	 * draws the sprite which has to be shown according to the position of the
	 * hero. This will result in a walking animation.
	 * 
	 * @param g
	 *            graphics object
	 * @param heroX
	 *            x position of the hero on the JPanel
	 * @param heroY
	 *            y position of the hero on the JPanel
	 * @param distanceOldX
	 *            distance to x of the old position
	 * @param distanceOldY
	 *            distance to y of the old position
	 */
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

	/**
	 * sets the hero position to the target position, except if a new position
	 * was requested. It will set the new target position then, too.
	 * 
	 * @param distanceOldX
	 *            distance to old x position
	 * @param distanceOldY
	 *            distance to old y position
	 */
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

	/**
	 * calculates how many milliseconds have to elapse for a new pixel of
	 * distance. If the new position is diagonal from the old position, it will
	 * return more milliseconds because it's a greater distance (pythagoras).
	 * 
	 * @return milliSecondsPerPixel
	 */
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

	/**
	 * calculates how far away from his old position the hero is.
	 * 
	 * @param oldValue
	 *            old x or y position
	 * @param newValue
	 *            new x or y position
	 * @return distance to old position in pixel
	 */
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

	/**
	 * calculates the x position of the map on the JPanel.
	 * 
	 * @param distance
	 *            distance to the old position
	 * @param horResolution
	 *            width of the JPanel
	 * @return mapX
	 */

	private int calculateNewMapX(int distance, int horResolution) {
		int mapX = position.xToPixel(tileSize);
		int heroSpriteMapXPosition = position.xToPixel(tileSize) + distance + charset.getSpriteWidth() / 2;

		if (map.isRightBorderVisible(heroSpriteMapXPosition, horResolution)) {

			// without that the old position would cause problems if it's not in
			// the map end.
			if (!map.isXInRightBorder(position, horResolution)) {
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

	/**
	 * calculates the y position of the map on the JPanel.
	 * 
	 * @param distance
	 *            distance to the old position
	 * @param vertResolution
	 *            height of the JPanel
	 * @return mapY
	 */
	private int calculateNewMapY(int distance, int vertResolution) {
		int mapY = position.yToPixel(tileSize);
		int heroSpriteMapYPosition = position.yToPixel(tileSize) + distance + charset.getSpriteHeight() / 2
				- charset.getSpriteHeight() + tileSize;

		if (map.isLowerBorderVisible(heroSpriteMapYPosition, vertResolution)) {

			// without that the old position would cause problems if it's not in
			// the map end.
			if (!map.isYInLowerBorder(position, vertResolution)) {
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

	/**
	 * calculates the x position of the hero sprite on the JPanel.
	 * 
	 * @param distance
	 *            distance to the old position
	 * @param horResolution
	 *            width of the JPanel
	 * @return heroX
	 */
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

	/**
	 * calculates the y position of the hero sprite on the JPanel.
	 * 
	 * @param distance
	 *            distance to the old position
	 * @param vertResolution
	 *            height of the JPanel
	 * @return heroY
	 */
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

	/**
	 * makes the hero walk to a new position. It will only react if the hero is
	 * not moving or the hero is close to its target. Negative values are
	 * allowed.
	 * 
	 * @param x
	 *            added to the current position
	 * @param y
	 *            added to the current position
	 */
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
