package de.salocin.gl.util.font;

public interface FontMetrics {
	
	/**
	 * Returns the base font size. This size will be scaled when changing the
	 * {@link Font} size ({@link #setSize(int)}).<br>
	 * As a result, the higher the number the more detailed the final Font will
	 * be.
	 * 
	 * @return The base size of the font
	 */
	int getBaseSize();
	
	/**
	 * Sets the real font size (in pixel). The {@link #getBaseSize()} will be
	 * scaled to match this value.<br>
	 * 
	 * @param fontSizeInPixel
	 *            The font size
	 */
	void setSize(int sizeInPixel);
	
	/**
	 * Returns the font size in pixel.
	 * 
	 * @return The font size
	 */
	int getSize();
	
	float getBaseline();
	
	float getAscentOffset();
	
	float getDescentOffset();
	
	float getWidth(char... chars);
	
	float getWidth(CharSequence charSequence);
	
	float getHeight();
	
}
