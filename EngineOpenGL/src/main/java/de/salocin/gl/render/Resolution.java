package de.salocin.gl.render;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

public class Resolution {
	
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 600;
	public static final float DEFAULT_ORTHO_WIDTH = 1.0f;
	public static final float DEFAULT_ORTHO_HEIGHT = 1.0f;
	
	private float orthoWidth;
	private float orthoHeight;
	private int width;
	private int height;
	
	protected Resolution() {
		this.width = DEFAULT_WIDTH;
		this.height = DEFAULT_HEIGHT;
		setOrthoSize(DEFAULT_ORTHO_WIDTH, DEFAULT_ORTHO_HEIGHT);
		
		GLFW.glfwSetWindowSizeCallback(Display.getInstance().getWindow(), new GLFWWindowSizeCallbackI() {
			
			@Override
			public void invoke(long window, int width, int height) {
				Resolution.this.width = width;
				Resolution.this.height = height;
				glViewport(0, 0, width, height);
			}
		});
		
	}
	
	public static Resolution getInstance() {
		return Display.getInstance().getResolution();
	}
	
	public void setOrthoSize(float width, float height) {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		
		this.orthoWidth = width;
		this.orthoHeight = height;
	}
	
	public float getOrthoWidth() {
		return orthoWidth;
	}
	
	public float getOrthoHeight() {
		return orthoHeight;
	}
	
	public int getWindowWidth() {
		return width;
	}
	
	public int getWindowHeight() {
		return height;
	}
	
	public int getOrthoWidthInPixel(float width) {
		return (int) ((width / this.orthoWidth) * this.width);
	}
	
	public int getOrthoHeightInPixel(float height) {
		return (int) ((height / this.orthoHeight) * this.height);
	}
	
	public float getPixelInOrthoWidth(int pixel) {
		return ((float) pixel / (float) width) * orthoWidth;
	}
	
	public float getPixelInOrthoHeight(int pixel) {
		return ((float) pixel / (float) height) * orthoHeight;
	}
	
	public static int convertOrthoWidth(float width) {
		return getInstance().getOrthoWidthInPixel(width);
	}
	
	public static int convertOrthoHeight(float height) {
		return getInstance().getOrthoHeightInPixel(height);
	}
	
	public static float convertPixelWidth(int pixel) {
		return getInstance().getPixelInOrthoWidth(pixel);
	}
	
	public static float convertPixelHeight(int pixel) {
		return getInstance().getPixelInOrthoHeight(pixel);
	}
	
}
