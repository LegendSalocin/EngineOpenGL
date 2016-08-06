package de.salocin.gl.util;

import static org.lwjgl.opengl.GL11.*;

public class Viewport {
	
	private static int viewportX;
	private static int viewportY;
	private static int viewportWidth;
	private static int viewportHeight;
	private static float orthoLeft;
	private static float orthoRight;
	private static float orthoBottom;
	private static float orthoTop;
	private static float orthoWidth;
	private static float orthoHeight;
	
	static {
		setOrtho(0.0f, 1.0f, 1.0f, 0.0f);
	}
	
	public static void setOrtho(float left, float right, float bottom, float top) {
		setOrtho(left, right, bottom, top, true);
	}
	
	public static void setOrtho(float left, float right, float bottom, float top, boolean updateGL) {
		Viewport.orthoLeft = left;
		Viewport.orthoRight = right;
		Viewport.orthoBottom = bottom;
		Viewport.orthoTop = top;
		Viewport.orthoWidth = right - left;
		Viewport.orthoHeight = bottom - top;
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(left, right, bottom, top, -1, 1);
		glMatrixMode(GL_MODELVIEW);
	}
	
	public static void setViewport(int viewportX, int viewportY, int viewportWidth, int viewportHeight) {
		setViewport(viewportX, viewportY, viewportWidth, viewportHeight, true);
	}
	
	public static void setViewport(int viewportX, int viewportY, int viewportWidth, int viewportHeight, boolean updateGL) {
		Viewport.viewportX = viewportX;
		Viewport.viewportY = viewportY;
		Viewport.viewportWidth = viewportWidth;
		Viewport.viewportHeight = viewportHeight;
		glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
	}
	
	public static float getViewportWidth() {
		return viewportWidth;
	}
	
	public static float getViewportHeight() {
		return viewportHeight;
	}
	
	public static float getOrthoLeft() {
		return orthoLeft;
	}
	
	public static float getOrthoRight() {
		return orthoRight;
	}
	
	public static float getOrthoBottom() {
		return orthoBottom;
	}
	
	public static float getOrthoTop() {
		return orthoTop;
	}
	
	public static float getOrthoWidth() {
		return orthoWidth;
	}
	
	public static float getOrthoHeight() {
		return orthoHeight;
	}
	
	public static int convertToPixelWidth(float orthoWidth) {
		
	}
	
	public static int convertToPixelHeight(float orthoHeight) {
		
	}
	
}
