package engine.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

/**
 * listens to keyboard.
 * 
 * @author regnaclockers
 */
public class KeyboardControl implements KeyListener {
	private static final Logger LOGGER = Logger.getLogger(engine.control.KeyboardControl.class.getName());

	private boolean downPressed;
	private boolean leftPressed;
	private boolean rightPressed;
	private boolean upPressed;

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_DOWN:

				downPressed = true;
				LOGGER.fine("Down pressed");
				break;
			case KeyEvent.VK_LEFT:
				leftPressed = true;
				LOGGER.fine("Left pressed");
				break;
			case KeyEvent.VK_RIGHT:
				rightPressed = true;
				LOGGER.fine("Right pressed");

				break;
			case KeyEvent.VK_UP:
				upPressed = true;
				LOGGER.fine("Up pressed");
				break;
			case KeyEvent.VK_ESCAPE:
				LOGGER.fine("Escape pressed");
				System.exit(0);
				break;
			default:
				LOGGER.finer("Key without function pressed");

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				downPressed = false;
				break;
			case KeyEvent.VK_LEFT:
				leftPressed = false;
				break;
			case KeyEvent.VK_RIGHT:
				rightPressed = false;
				break;
			case KeyEvent.VK_UP:
				upPressed = false;
				break;
			default:
				LOGGER.finer("Key without function released");
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * returns if down is pressed.
	 * 
	 * @return true, if down is pressed
	 */
	public boolean isDownPressed() {
		return downPressed;
	}

	/**
	 * returns if left is pressed.
	 * 
	 * @return true, if left is pressed
	 */
	public boolean isLeftPressed() {
		return leftPressed;
	}

	/**
	 * returns if right is pressed.
	 * 
	 * @return true, if right is pressed
	 */
	public boolean isRightPressed() {
		return rightPressed;
	}

	/**
	 * returns if up is pressed.
	 * 
	 * @return true, if up is pressed
	 */
	public boolean isUpPressed() {
		return upPressed;
	}

	/**
	 * returns if a walking key is pressed.
	 * 
	 * @return true if a walking key is pressed
	 */
	public boolean isWalkKeyPressed() {
		return downPressed || leftPressed || rightPressed || upPressed;
	}
}