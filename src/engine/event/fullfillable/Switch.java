package engine.event.fullfillable;

import java.util.logging.Logger;

/**
 * A simple switch.
 * 
 * @author regnaclockers
 */
public class Switch implements Fullfillable {
	private final static Logger LOGGER = Logger.getLogger(engine.event.fullfillable.Switch.class.getName());

	private boolean isOn;

	public Switch(boolean isOn) {
		this.isOn = isOn;
		LOGGER.info("Switch created");
	}

	/**
	 * Returns true if the switch is on.
	 * 
	 * @return isOn;
	 */
	@Override
	public boolean isFullfilled() {
		return isOn;
	}

	public void turnOn() {
		if (!isOn) {
			isOn = true;
			LOGGER.info("Switch turned on");
		} else {
			LOGGER.warning("Switch already on");
		}
	}

	public void turnOff() {
		if (isOn) {
			isOn = false;
			LOGGER.info("Switch turned off");
		} else {
			LOGGER.warning("Switch already off");
		}
	}
}