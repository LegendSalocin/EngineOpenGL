package de.salocin.gl.util.font;

public interface FontMetrics {
	
	float getAscent();
	
	float getDescent();
	
	float getLineGap();
	
	/**
	 * Returns the font size in pixel.
	 * 
	 * @return The font size
	 */
	int getSize();
	
	float getWidth(char... chars);
	
	float getWidth(CharSequence text);
	
	float getLineHeight();
	
}
