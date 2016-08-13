package de.salocin.gl.util.font;

public interface FontMetrics {
	
	/**
	 * Returns the font size in pixel.
	 * 
	 * @return The font size
	 */
	int getSize();
	
	float getWidth(char... chars);
	
	float getWidth(CharSequence charSequence);
	
	float getLineHeight();
	
}
