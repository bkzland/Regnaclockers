package engine;

import java.awt.Graphics;

import engine.sprite.Charset;

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
		g.drawImage(charset.getLookDownSprites().get(0), x, y, null);
	}
}