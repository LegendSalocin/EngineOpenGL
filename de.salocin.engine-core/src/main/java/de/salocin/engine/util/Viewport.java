package de.salocin.engine.util;

import static org.lwjgl.opengl.GL11.*;

import de.salocin.engine.event.Callback;
import de.salocin.engine.event.CallbackHandler;
import de.salocin.engine.event.ValueChangeEvent;
import de.salocin.engine.geom.Dimension;

public class Viewport {
	
	private static final CallbackHandler<ValueChangeEvent<Dimension>> orthoSizeCallback = new CallbackHandler<ValueChangeEvent<Dimension>>();
	private static final CallbackHandler<ValueChangeEvent<Dimension>> viewportSizeCallback = new CallbackHandler<ValueChangeEvent<Dimension>>();
	private static Dimension ortho;
	private static Dimension windowSize;
	
	static {
		setOrthoSize(new Dimension(0.0f, 0.0f));
		setViewport(new Dimension(0.0f, 0.0f));
	}
	
	public static void setOrthoSize(Dimension ortho) {
		ValueChangeEvent<Dimension> event = new ValueChangeEvent<Dimension>(Viewport.ortho, ortho);
		orthoSizeCallback.call(event);
		if (event.isCanceled()) {
			return;
		}
		
		Viewport.ortho = event.getNewValue();
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, ortho.getWidth(), ortho.getHeight(), 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
	}
	
	public static Dimension getOrthoSize() {
		return ortho;
	}
	
	public static void setViewport(Dimension viewport) {
		ValueChangeEvent<Dimension> event = new ValueChangeEvent<Dimension>(Viewport.windowSize, viewport);
		viewportSizeCallback.call(event);
		if (event.isCanceled()) {
			return;
		}
		
		Viewport.windowSize = event.getNewValue();
		
		glViewport(0, 0, (int) viewport.getWidth(), (int) viewport.getHeight());
	}
	
	public static Dimension getViewport() {
		return windowSize;
	}
	
	public static void addOrthoCallback(Callback<ValueChangeEvent<Dimension>> callback) {
		orthoSizeCallback.add(callback);
	}
	
	public static void addViewportCallback(Callback<ValueChangeEvent<Dimension>> callback) {
		viewportSizeCallback.add(callback);
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
