package engine.map.event.fulfillable;

import java.util.logging.Logger;

/**
 * A timer which will count down until 0.
 * 
 * @author regnaclockers
 */
public class Timer implements Fulfillable, Runnable {
	private static final Logger LOGGER = Logger.getLogger(engine.map.event.fulfillable.Timer.class.getName());

	private boolean timeIsUp;
	private int seconds;
	private long startTime;
	private Thread thread = new Thread(this);
	private static final int TICKS_PER_SECOND = 5;

	/**
	 * create a new timer.
	 * 
	 * @param minutes
	 *            minutes to start
	 * @param seconds
	 *            seconds to start
	 */
	public Timer(int minutes, int seconds) {
		this(60 * minutes + seconds);
	}

	/**
	 * create a new timer.
	 * 
	 * @param seconds
	 *            seconds to start
	 */
	public Timer(int seconds) {
		this.seconds = seconds;
	}

	/**
	 * returns true if the time's up.
	 * 
	 * @return timeIsUp
	 */
	@Override
	public boolean isFulfilled() {
		return timeIsUp;
	}

	/**
	 * starts the Timer.
	 */
	public void start() {
		startTime = System.currentTimeMillis();
		LOGGER.info("Timer Thread started (" + seconds + "s)");
		thread.start();

	}

	@Override
	public void run() {
		while (!timeIsUp) {
			try {
				Thread.sleep(1000 / TICKS_PER_SECOND);
			} catch (InterruptedException e) {
				LOGGER.severe("Cannot sleep for " + 1000 / TICKS_PER_SECOND + "ms");
			}
			if (startTime <= System.currentTimeMillis() - (seconds * 1000)) {
				timeIsUp = true;
				LOGGER.info("Time's up" + " (" + seconds + "s)");

			}
		}
	}

	/**
	 * adds additional seconds to the timer.
	 * 
	 * @param additionalSeconds
	 *            seconds to add
	 */
	public void addTime(int additionalSeconds) {
		if (additionalSeconds > 0) {
			seconds += additionalSeconds;
		} else {
			LOGGER.severe("Only positive numbers are accepted");
		}

	}

	/**
	 * decreases the amount of seconds until timer reaches 0.
	 * 
	 * @param lessSeconds
	 *            seconds to remove
	 */
	public void reduceTime(int lessSeconds) {
		if (lessSeconds > 0) {
			seconds -= lessSeconds;
		} else {
			LOGGER.severe("Only positive numbers are accepted");
		}
	}

	@Override
	public String toString() {
		return "Timer (" + seconds + "s left)";
	}
}