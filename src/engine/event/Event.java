package engine.event;

import java.awt.Graphics;
import engine.map.MapCoordinates;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Event {
	private final static Logger LOGGER = Logger.getLogger(engine.event.Event.class.getName());

	private ArrayList<Page> pages;
	private Page currentPage;
	private MapCoordinates position;
	private String eventName;
	private int tileSize = 128;

	public Event(String eventName, ArrayList<Page> pages, MapCoordinates position) {

		this.eventName = eventName;
		this.pages = pages;
		this.position = position;
		try {
			currentPage = this.pages.get(0); // first page is default
		} catch (IndexOutOfBoundsException e) {
			LOGGER.severe("Event \"" + this.toString() + "\"  was created with 0 Pages");
		}

		LOGGER.info("Event \"" + this.toString() + "\" created on (" + position.getX() + '|' + position.getY()
				+ ") with " + pages.size() + ((pages.size() != 1) ? " Pages" : " Page"));
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
	public void drawEvent(Graphics g, int mapXInPixel, int mapYInPixel, int horResolution, int vertResolution) {

		if (mapXInPixel < position.xToPixel(tileSize) + tileSize
				&& mapXInPixel + horResolution > position.xToPixel(tileSize)
				&& mapYInPixel < position.yToPixel(tileSize) + tileSize
				&& mapYInPixel + vertResolution > position.yToPixel(tileSize)) {
			currentPage.drawSprite(g, position.xToPixel(tileSize) - mapXInPixel, position.yToPixel(tileSize)
					- mapYInPixel);
		}

	}

	/**
	 * It will make the highest page which has a fullfilled condition to
	 * currentPage.
	 */
	public void changePageIfConditionTrue() {
		Page tempPage = currentPage;
		for (int i = 0, length = pages.size(); i < length; i++) {

			if (pages.get(i).isAConditionFullfilled()) {
				tempPage = pages.get(i);
			}
			if (currentPage != tempPage) {
				currentPage = tempPage;
				LOGGER.info("\"" + this.toString() + "\"" + " changed Page");
			}
		}
	}

	@Override
	public String toString() {
		return eventName;
	}
}