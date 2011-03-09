package engine.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

public class KeyboardControl implements KeyListener {
	private final static Logger LOGGER = Logger.getLogger(engine.control.KeyboardControl.class.getName());

	private boolean downPressed = false;
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean upPressed = false;

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
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public boolean isDownPressed() {
		return downPressed;
	}

	public boolean isLeftPressed() {
		return leftPressed;
	}

	public boolean isRightPressed() {
		return rightPressed;
	}

	public boolean isUpPressed() {
		return upPressed;
	}
	
	public boolean isWalkKeyPressed() {
		return downPressed || leftPressed || rightPressed || upPressed;
	}
}