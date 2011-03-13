package engine.event;

import java.awt.Graphics;
import engine.map.MapCoordinates;
import java.util.List;
import java.util.logging.Logger;

public class Event {
	private static final Logger LOGGER = Logger.getLogger(engine.event.Event.class.getName());

	private List<Page> pages;
	private Page activatedPage;
	private MapCoordinates position;
	private String eventName;
	private int tileSize = 128;

	public Event(final String eventName, final List<Page> pages, final MapCoordinates position) {

		this.eventName = eventName;
		this.pages = pages;
		this.position = position;
		try {
			activatedPage = this.pages.get(0); // first page is default
		} catch (IndexOutOfBoundsException e) {
			LOGGER.severe("Event \"" + this.toString() + "\"  was created with 0 Pages");
		}

		LOGGER.info("Event \"" + this.toString() + "\" created on (" + position.getX() + '|' + position.getY()
				+ ") with " + pages.size() + ((pages.size() == 1) ? " Page" : " Pages"));
	}

	/**
	 * if the event or a part of it would be visible, it asks the active page to
	 * draw its image.
	 * 
	 * @param g
	 * @param mapXInPixel
	 * @param mapYInPixel
	 * @param horResolution
	 * @param vertResolution
	 */
	public final void drawEvent(final Graphics g, final int mapXInPixel, final int mapYInPixel, final int horResolution, final int vertResolution) {

		if (mapXInPixel < position.xToPixel(tileSize) + tileSize
				&& mapXInPixel + horResolution > position.xToPixel(tileSize)
				&& mapYInPixel < position.yToPixel(tileSize) + tileSize
				&& mapYInPixel + vertResolution > position.yToPixel(tileSize)) {
			activatedPage.drawSprite(g, position.xToPixel(tileSize) - mapXInPixel, position.yToPixel(tileSize)
					- mapYInPixel);
		}

	}

	/**
	 * It will make the highest page which has a fullfilled condition to
	 * currentPage.
	 */
	public final void changePageIfConditionTrue() {
		Page tempPage = activatedPage;
		for (int i = 0, length = pages.size(); i < length; i++) {

			if (pages.get(i).isSomeConditionFullfilled()) {
				tempPage = pages.get(i);
			}
			if (activatedPage != tempPage) {
				activatedPage = tempPage;
				LOGGER.info("\"" + this.toString() + "\"" + " changed Page");
			}
		}
	}

	@Override
    public final String toString() {
		return eventName;
	}
}