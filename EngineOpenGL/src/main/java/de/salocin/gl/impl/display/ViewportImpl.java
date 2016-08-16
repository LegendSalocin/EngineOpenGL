package de.salocin.gl.impl.display;

import de.salocin.gl.display.Viewport;
import de.salocin.gl.util.math.Dimension;

public class ViewportImpl implements Viewport {
	
	private static final Viewport instance = new ViewportImpl();
	private Dimension ortho;
	private Dimension windowSize;
	
	private ViewportImpl() {
		throw new UnsupportedOperationException();
	}
	
	public static Viewport getInstance() {
		return instance;
	}
	
	@Override
	public void setOrtho(Dimension dimension) {
		this.ortho = dimension;
	}
	
	@Override
	public Dimension getOrtho() {
		return ortho;
	}
	
	@Override
	public void setWindowSize(Dimension dimension) {
		this.windowSize = dimension;
	}
	
	@Override
	public Dimension getWindowSize() {
		return windowSize;
	}
	
	@Override
	public float scaledWidth(int pixelWidth) {
		return ((float) pixelWidth / windowSize.getWidth()) * ortho.getWidth();
	}
	
	@Override
	public float scaledHeight(int pixelHeight) {
		return ((float) pixelHeight / windowSize.getHeight()) * ortho.getHeight();
	}
	
	@Override
	public int unscaledWidth(float scaledWidth) {
		return (int) ((scaledWidth / ortho.getWidth()) * windowSize.getWidth());
	}
	
	@Override
	public int unscaledHeight(float scaledHeight) {
		return (int) ((scaledHeight / ortho.getHeight()) * windowSize.getHeight());
	}
	
}
