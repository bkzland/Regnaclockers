package engine.event;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Event {
	private final static Logger LOGGER = Logger.getLogger(engine.event.Event.class.getName());

	private ArrayList<Page> pages;
	private Page currentPage;
	private Point position;
	private String eventName;

	public Event(String eventName, ArrayList<Page> pages, Point position) {

		this.eventName = eventName;
		this.pages = pages;
		this.position = position;
		try {
			currentPage = this.pages.get(0); // first page is default
		} catch (IndexOutOfBoundsException e) {
			LOGGER.severe("Event \"" + this.toString() + "\"  was created with 0 Pages");
			e.printStackTrace();
		}

		LOGGER.info("Event \"" + this.toString() + "\" created on (" + (int) position.getX() + "|"
				+ (int) position.getY() + ") with " + pages.size() + ((pages.size() != 1) ? " Pages" : " Page"));
	}

	public void drawEvent(Graphics g, Point position, int horResolution, int vertResolution) {
		currentPage.drawSprite(g, position);
	}

	/**
	 * It will make the highest page which has a fullfilled condition to
	 * currentPage.
	 */
	private void changePageIfConditionTrue() {
		Page tempPage = currentPage;
		for (int i = 0, length = pages.size(); i < length; i++) {

			if (pages.get(i).checkCondition() == true) {
				tempPage = pages.get(i);
			}
			if (currentPage != tempPage) {
				currentPage = tempPage;
				LOGGER.info(this.toString() + "changed Page");
			}
		}
	}

	@Override
	public String toString() {
		return eventName;
	}
}