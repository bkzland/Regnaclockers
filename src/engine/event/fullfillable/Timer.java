package engine.event.fullfillable;

import java.util.logging.Logger;

public class Timer implements Fullfillable, Runnable {
	private final static Logger LOGGER = Logger.getLogger(engine.event.fullfillable.Timer.class.getName());

	private boolean timeIsUp;
	private int seconds;
	private long startTime;
	private Thread thread = new Thread(this);
	private static final int TICKS_PER_SECOND = 5;

	public Timer(int minutes, int seconds) {
		this(60 * minutes + seconds);
	}

	public Timer(int seconds) {
		this.seconds = seconds;
	}

	/**
	 * returns true if the time's up.
	 * 
	 * @return timeIsUp
	 */
	@Override
	public boolean isFullfilled() {
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

	public void addTime(int additionalSeconds) {
		if (additionalSeconds > 0) {
			seconds += additionalSeconds;
		} else {
			LOGGER.severe("addTime(int) accepts only positive numbers.");
		}

	}

	public void reduceTime(int lessSeconds) {
		if (lessSeconds > 0) {
			seconds -= lessSeconds;
		} else {
			LOGGER.severe("reduceTime(int) accepts only positive numbers.");
		}
	}

	@Override
	public String toString() {
		return "Timer (" + seconds + "s left)";
	}
}