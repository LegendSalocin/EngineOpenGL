package de.salocin.gl.display;

import de.salocin.gl.impl.display.ViewportImpl;
import de.salocin.gl.util.math.Dimension;

public interface Viewport {
	
	void setOrtho(Dimension dimension);
	
	Dimension getOrtho();
	
	void setWindowSize(Dimension dimension);
	
	Dimension getWindowSize();
	
	float scaledWidth(int pixelWidth);
	
	float scaledHeight(int pixelHeight);
	
	int unscaledWidth(float scaledWidth);
	
	int unscaledHeight(float scaledHeight);
	
	public static Viewport getInstance() {
		return ViewportImpl.getInstance();
	}
	
}
