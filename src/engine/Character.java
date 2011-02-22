package engine;

import java.awt.Graphics;

/**
 * The graphical representation of any character.
 * 
 * @author regnaclockers
 */
public class Character {
	Charset charset;

	public Character(Charset charset) {
		this.charset = charset;
	}

	public void drawCharacter(Graphics g, int x, int y) {
		g.drawImage(charset.lookDown.get(0), x, y, null);
	}
}