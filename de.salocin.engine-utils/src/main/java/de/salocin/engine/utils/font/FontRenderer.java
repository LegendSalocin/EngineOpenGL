package de.salocin.engine.utils.font;

import de.salocin.engine.utils.core.Color;
import de.salocin.engine.utils.texture.Texture;

public interface FontRenderer {
	
	void renderText(String text, float x, float y, Color... colorPerChar);
	
	/**
	 * Determines if a character is supported by that font.
	 * 
	 * @param ch
	 *            The character to check
	 * @return <code>true</code> if it is supported, <code>false</code>
	 *         otherwise
	 */
	boolean canRender(char ch);
	
	/**
	 * Renders the font bitmap.
	 * 
	 * @param x
	 *            The x position
	 * @param y
	 *            The y position
	 */
	void renderBitmap(float x, float y);
	
	/**
	 * Changes the bitmap to draw a character from.<br>
	 * <b>CAUTION:</b><br>
	 * The newBitmap size has to be the exact size as the default one.
	 * 
	 * @param newBitmap
	 */
	void setBitmap(Texture newBitmap);
	
}
