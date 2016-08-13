package de.salocin.gl.util.font;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import de.salocin.gl.util.Color;
import de.salocin.gl.util.Copyable;

public interface Font extends Copyable<Font> {
	
	/**
	 * Returns the {@link FontMetrics} of this font which holds basic
	 * information about the font's size for example.
	 * 
	 * @return The font metrics
	 */
	FontMetrics getMetrics();
	
	/**
	 * Sets the real font size (in pixel). This method will return a copy of the
	 * current font.
	 * 
	 * @param fontSizeInPixel
	 *            The font size
	 * @return A copy of the current font
	 */
	Font setSize(int sizeInPixel);
	
	/**
	 * Returns the font size in pixel.
	 * 
	 * @return The font size
	 */
	int getSize();
	
	/**
	 * Sets the font style.
	 * 
	 * @param styles
	 */
	void setStyle(FontStyle... styles);
	
	/**
	 * Returns the font style
	 * 
	 * @return
	 */
	FontStyle[] getStyle();
	
	/**
	 * Renders the text at the given position.
	 * 
	 * @param text
	 *            The text to render
	 * @param x
	 *            The x position of the position
	 * @param y
	 *            The y position of the position
	 */
	void renderText(CharSequence text, float x, float y);
	
	/**
	 * Renders the text at the given position.<br>
	 * This method changes the current color of the current GL Context.
	 * 
	 * @param text
	 *            The text to render
	 * @param x
	 *            The x position of the position
	 * @param y
	 *            The y position of the position
	 * @param color
	 *            The color of the text
	 */
	void renderText(CharSequence text, float x, float y, Color color);
	
	/**
	 * Clones the current Font without reloading it.
	 * 
	 * @return The cloned Font.
	 */
	Font copy();
	
	/**
	 * @see FontBuilder#FontBuilder(String)
	 */
	public static FontBuilder newBuilder(String fontName) {
		return new FontBuilder(fontName);
	}
	
	/**
	 * @see FontBuilder#FontBuilder(InputStream)
	 */
	public static FontBuilder newBuilder(InputStream fontInputStream) throws FontFormatException, IOException {
		return new FontBuilder(fontInputStream);
	}
	
	/**
	 * @see FontBuilder#FontBuilder(File)
	 */
	public static FontBuilder newBuilder(File fontFile) throws FontFormatException, IOException {
		return new FontBuilder(fontFile);
	}
	
}
