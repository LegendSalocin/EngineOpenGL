package de.salocin.gl.display;

import de.salocin.gl.impl.display.ViewportImpl;
import de.salocin.gl.util.engine.Check;
import de.salocin.gl.util.math.Dimension;

public interface Viewport {
	
	public static final float DEFAULT_ORTHO_WIDTH = 1.0f;
	public static final float DEFAULT_ORTHO_HEIGHT = 1.0f;
	public static final int DEFAULT_VIEWPORT_WIDTH = 800;
	public static final int DEFAULT_VIEWPORT_HEIGHT = 600;
	
	public static Viewport getInstance() {
		Check.display();
		return ViewportImpl.instance;
	}
	
	void setOrtho(Dimension ortho);
	
	Dimension getOrtho();
	
	void setViewport(Dimension viewport);
	
	Dimension getViewport();
	
	float scaledWidth(int pixelWidth);
	
	float scaledHeight(int pixelHeight);
	
	int unscaledWidth(float scaledWidth);
	
	int unscaledHeight(float scaledHeight);
	
}
