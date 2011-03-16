package engine.map.event;

import java.awt.Graphics;
import java.util.List;
import java.util.logging.Logger;

import engine.map.MapCoordinates;

/**
 * Events are objects on maps which can have sprites and perform scripts.
 * Usually that would be NPCs, changing tiles, teleports etc. Events itself
 * don't do much, the functionality comes with pages, which contain sprites,
 * scripts etc. The event can switch through those pages.
 * 
 * @author regnaclockers
 */
public class Event {
	private static final Logger LOGGER = Logger.getLogger(engine.map.event.Event.class.getName());
	private static int eventIdCounter = 1;

	private List<Page> pages;
	private Page activatedPage;
	private MapCoordinates position;
	private String eventName;
	private int tileSize;
	private final int eventId;

	/**
	 * Instantiates a new event. An event contains one or more pages. First page
	 * will be activated by default, other pages must fulfill its conditions to
	 * get activated. There can always be just one activated page.
	 * 
	 * @param eventName
	 *            the event name
	 * @param pages
	 *            the pages
	 * @param position
	 *            the position on the map
	 */
	public Event(String eventName, List<Page> pages, MapCoordinates position) {
		this.eventName = eventName;
		this.pages = pages;
		this.position = position;

		try {
			activatedPage = this.pages.get(0); // first page is default
		} catch (IndexOutOfBoundsException e) {
			LOGGER.severe(this.toString() + " was created with 0 Pages");
		}

		eventId = eventIdCounter++;

		LOGGER
				.info(this.toString() + " with " + pages.size()
						+ ((pages.size() == 1) ? " Page" : " Pages" + " created"));

	}

	/**
	 * if the event or a part of it would be visible, it asks the active page to
	 * draw its image.
	 * 
	 * @param g
	 *            graphics object
	 * @param mapXInPixel
	 *            the x map position in pixel on the top left corner of the
	 *            JPanel
	 * @param mapYInPixel
	 *            the y map position in pixel on the top left corner of the
	 *            JPanel
	 * @param horResolution
	 *            width of JPanel
	 * @param vertResolution
	 *            height of JPanel
	 */
	public void drawEvent(Graphics g, int mapXInPixel, int mapYInPixel, int horResolution, int vertResolution) {

		if (mapXInPixel < position.xToPixel(tileSize) + tileSize
				&& mapXInPixel + horResolution > position.xToPixel(tileSize)
				&& mapYInPixel < position.yToPixel(tileSize) + tileSize
				&& mapYInPixel + vertResolution > position.yToPixel(tileSize)) {
			activatedPage.drawSprite(g, position.xToPixel(tileSize) - mapXInPixel, position.yToPixel(tileSize)
					- mapYInPixel);
		}

	}

	/**
	 * This will activate the highest page with a fulfilled condition.
	 */
	public void changePageIfConditionTrue() {
		Page tempPage = activatedPage;
		for (int i = 0, length = pages.size(); i < length; i++) {

			if (pages.get(i).isSomeConditionFulfilled()) {
				tempPage = pages.get(i);
			}
			if (activatedPage != tempPage) {
				activatedPage = tempPage;
				LOGGER.info(this.toString() + " changed Page");
			}
		}
	}

	@Override
	public String toString() {
		return "Event #" + eventId + " \"" + eventName + "\" (" + position.getX() + '|' + position.getY() + ')';
	}
}