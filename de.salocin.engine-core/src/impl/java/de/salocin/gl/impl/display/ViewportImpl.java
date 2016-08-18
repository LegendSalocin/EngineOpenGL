package de.salocin.gl.impl.display;

import static org.lwjgl.opengl.GL11.*;

import de.salocin.gl.display.Viewport;
import de.salocin.gl.util.math.Dimension;

public class ViewportImpl implements Viewport {
	
	public static final ViewportImpl instance = new ViewportImpl();
	private Dimension ortho;
	private Dimension windowSize;
	
	@Override
	public void setOrtho(Dimension ortho) {
		this.ortho = ortho;
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, ortho.getWidth(), ortho.getHeight(), 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
	}
	
	@Override
	public Dimension getOrtho() {
		return ortho;
	}
	
	@Override
	public void setViewport(Dimension viewport) {
		this.windowSize = viewport;
		
		glViewport(0, 0, (int) viewport.getWidth(), (int) viewport.getHeight());
	}
	
	@Override
	public Dimension getViewport() {
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
