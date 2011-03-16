package engine.gui;

import java.awt.Graphics;

/**
 * for classes which should be able to be print on the gui.
 * @author regnaclockers
 *
 */
public interface Drawable {
	/**
	 * draws if it is activated.
	 * 
	 * @param g graphics object
	 */
	void drawIfActivated(Graphics g);

	/**
	 * activates or deactivates it.
	 */
	void trigger();

}
