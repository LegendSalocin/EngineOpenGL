package de.salocin.engine.utils.font;

import de.salocin.engine.utils.core.Color;
import de.salocin.engine.utils.core.ColoredString;
import de.salocin.engine.utils.texture.Texture;

public interface FontRenderer {
	
	/**
	 * Renders a text with a custom color per char. You can also use
	 * {@link ColoredString#render(Font, float, float)} to render text with
	 * different colored chars.
	 * 
	 * @param text
	 *            The text to render
	 * @param x
	 *            The x position
	 * @param y
	 *            The y position (of the baseline)
	 * @param colorPerChar
	 *            The color for each char
	 * @throws IllegalArgumentException
	 *             if the text is null or the given color length does not math
	 *             with the text length
	 * @see ColoredString
	 */
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
