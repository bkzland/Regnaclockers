package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardControl implements KeyListener {
	private boolean downPressed = false;
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean upPressed = false;

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				downPressed = true;
				System.out.println("DOWN pressed.");
				break;
			case KeyEvent.VK_LEFT:
				leftPressed = true;
				System.out.println("LEFT pressed.");
				break;
			case KeyEvent.VK_RIGHT:
				rightPressed = true;
				System.out.println("RIGHT pressed.");
				break;
			case KeyEvent.VK_UP:
				upPressed = true;
				System.out.println("UP pressed.");
				break;
			case KeyEvent.VK_ESCAPE:
				System.out.println("Exiting");
				System.exit(0);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				downPressed = false;
				System.out.println("DOWN released.");
				break;
			case KeyEvent.VK_LEFT:
				leftPressed = false;
				System.out.println("LEFT released.");
				break;
			case KeyEvent.VK_RIGHT:
				rightPressed = false;
				System.out.println("RIGHT released.");
				break;
			case KeyEvent.VK_UP:
				upPressed = false;
				System.out.println("UP released.");
				break;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

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
	


}
