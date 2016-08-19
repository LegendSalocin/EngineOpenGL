package de.salocin.engine.utils.font;

public interface FontMetrics {
	
	/**
	 * Returns the ascent line, an offset value from the baseline.<br>
	 * <b>Example:</b>
	 * <p>
	 * <code>
	 * int ascentY = baselineY - getAscent();
	 * </code>
	 * </p>
	 * 
	 * @return The ascent line offset
	 */
	float getAscent();
	
	/**
	 * Returns the descent line, an offset value from the baseline.<br>
	 * <b>Example:</b>
	 * <p>
	 * <code>
	 * int descentY = baselineY - getDescent();
	 * </code>
	 * </p>
	 * 
	 * @return The descent line offset
	 */
	float getDescent();
	
	/**
	 * Returns the value of the gap between to lines
	 * 
	 * @return The line gap
	 */
	float getLineGap();
	
	/**
	 * Returns the font size in pixel.
	 * 
	 * @return The font size
	 */
	int getSize();
	
	/**
	 * Returns the font size based on the current ortho's settings.<br>
	 * It is the same value as {@link #getAscent()} - {@link #getDescent()}
	 * 
	 * @return The line height.
	 */
	float getLineHeight();
	
	/**
	 * Computes the char array's width
	 * 
	 * @param chars
	 *            The chars
	 * @return The computed width
	 */
	float getWidth(char... chars);
	
	/**
	 * Computes the text's width
	 * 
	 * @param text
	 *            The text
	 * @return The computed width
	 */
	float getWidth(CharSequence text);
	
}
