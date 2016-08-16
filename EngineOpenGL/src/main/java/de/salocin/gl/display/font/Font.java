package de.salocin.gl.display.font;

import de.salocin.gl.display.Color;

public interface Font {
	
	/**
	 * Returns a {@link FontBuilder} with the current font's settings. In that
	 * case it is very easy to copy the current font, or change some settings
	 * like the font size or the font style.
	 * 
	 * @return The font builder the font was build from.
	 */
	FontBuilder getFontBuilder();
	
	/**
	 * Returns the {@link FontMetrics} of this font which holds basic
	 * information about the font's size for example.
	 * 
	 * @return The font metrics
	 */
	FontMetrics getMetrics();
	
	FontStyle getStyle();
	
	/**
	 * Returns the font size in pixel.
	 * 
	 * @return The font size
	 */
	int getSize();
	
	void setUnderline(boolean underline);
	
	boolean isUnderlined();
	
	void setStrikethrough(boolean strikethrough);
	
	boolean isStrikethrough();
	
	void setOverline(boolean overline);
	
	boolean isOverlined();
	
	/**
	 * Renders the text at the given position.
	 * 
	 * @param text
	 *            The text to render
	 * @param x
	 *            The x position
	 * @param y
	 *            The y position (of the <a href=
	 *            "https://en.wikipedia.org/wiki/Baseline_(typography)">Baseline</a>)
	 */
	void renderText(CharSequence text, float x, float y);
	
	/**
	 * Renders the text at the given position.<br>
	 * This method changes the current color of the current GL Context.
	 * 
	 * @param text
	 *            The text to render
	 * @param x
	 *            The x position
	 * @param y
	 *            The y position (of the <a href=
	 *            "https://en.wikipedia.org/wiki/Baseline_(typography)">Baseline</a>)
	 * @param color
	 *            The color of the text
	 */
	void renderText(CharSequence text, float x, float y, Color color);
	
	/**
	 * @see FontBuilder#FontBuilder(String)
	 */
	public static FontBuilder newBuilder(String fontFamily) {
		return new FontBuilder(fontFamily);
	}
	
	/**
	 * @see FontBuilder#FontBuilder(String, FontStyle)
	 */
	public static FontBuilder newBuilder(String fontFamily, FontStyle fontStyle) {
		return new FontBuilder(fontFamily, fontStyle);
	}
	
}
