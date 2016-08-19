package de.salocin.engine.util;

import static org.lwjgl.opengl.GL11.*;

import de.salocin.engine.util.math.Dimension;

public class Viewport {
	
	private static Dimension ortho;
	private static Dimension windowSize;
	
	static {
		setOrthoSize(new Dimension(0.0f, 0.0f));
		setViewport(new Dimension(0.0f, 0.0f));
	}
	
	public static void setOrthoSize(Dimension ortho) {
		Viewport.ortho = ortho;
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, ortho.getWidth(), ortho.getHeight(), 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
	}
	
	public static Dimension getOrthoSize() {
		return ortho;
	}
	
	public static void setViewport(Dimension viewport) {
		Viewport.windowSize = viewport;
		
		glViewport(0, 0, (int) viewport.getWidth(), (int) viewport.getHeight());
	}
	
	public static Dimension getViewport() {
		return windowSize;
	}
	
	public static float scaledWidth(int pixelWidth) {
		return ((float) pixelWidth / windowSize.getWidth()) * ortho.getWidth();
	}
	
	public static float scaledHeight(int pixelHeight) {
		return ((float) pixelHeight / windowSize.getHeight()) * ortho.getHeight();
	}
	
	public static int unscaledWidth(float scaledWidth) {
		return (int) ((scaledWidth / ortho.getWidth()) * windowSize.getWidth());
	}
	
	public static int unscaledHeight(float scaledHeight) {
		return (int) ((scaledHeight / ortho.getHeight()) * windowSize.getHeight());
	}
	
}
