package engine.map.event.conditions;

import java.util.logging.Logger;

/**
 * A simple switch.
 * 
 * @author regnaclockers
 */
public class Switch implements Condition {
	private static final Logger LOGGER = Logger.getLogger(engine.map.event.conditions.Switch.class.getName());

	private boolean isOn;

	/**
	 * creates a switch which is on (true) or off (false).
	 * 
	 * @param isOn
	 *            initial state of the switch
	 */
	public Switch(boolean isOn) {
		this.isOn = isOn;
		LOGGER.info("Switch created");
	}

	/**
	 * Returns true if the switch is on.
	 * 
	 * @return isOn
	 */
	@Override
	public boolean isFulfilled() {
		return isOn;
	}

	/**
	 * turns the switch on.
	 */
	public void turnOn() {
		if (isOn) {
			LOGGER.warning("Switch already on");
		} else {
			isOn = true;
			LOGGER.info("Switch turned on");
		}
	}

	/**
	 * turns the switch off.
	 */
	public void turnOff() {
		if (isOn) {
			isOn = false;
			LOGGER.info("Switch turned off");
		} else {
			LOGGER.warning("Switch already off");
		}
	}
}