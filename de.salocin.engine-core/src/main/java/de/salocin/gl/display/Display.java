package de.salocin.gl.display;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.display.DisplayInitializedEvent;
import de.salocin.gl.event.display.DisplaySizeChangeEvent;
import de.salocin.gl.event.display.RenderStateChangeEvent;
import de.salocin.gl.scheduler.Scheduler;
import de.salocin.gl.util.Viewport;
import de.salocin.gl.util.input.Keyboard;
import de.salocin.gl.util.input.Mouse;
import de.salocin.gl.util.math.Dimension;

public class Display {
	
	public static final float DEFAULT_ORTHO_WIDTH = 1.0f;
	public static final float DEFAULT_ORTHO_HEIGHT = 1.0f;
	public static final int DEFAULT_VIEWPORT_WIDTH = 800;
	public static final int DEFAULT_VIEWPORT_HEIGHT = 600;
	
	private static boolean init = false;
	private static RenderState renderState;
	private static boolean vsync;
	
	/**
	 * A method that returns <code>true</code> when the GL context was
	 * created.<br>
	 * You can also listen to the {@link DisplayInitializedEvent} or call
	 * {@link Scheduler#runLater(Runnable)} to make some GL settings after the
	 * context creation.
	 * 
	 * @return <code>true</code> if the GL context is created, otherwise
	 *         <code>false</code>
	 */
	public static boolean isInitialized() {
		return init;
	}
	
	public static long getWindowHandle() {
		return Scheduler.getInstance().getGameLoop().getWindow();
	}
	
	public static void setRenderState(RenderState state) {
		Scheduler.getInstance().runLater(new Runnable() {
			@Override
			public void run() {
				RenderStateChangeEvent e = new RenderStateChangeEvent(renderState, state);
				if (!EventManager.getInstance().callEvent(e)) {
					if (renderState != null) {
						renderState.exit();
					}
					
					renderState = e.getNewState();
					renderState.init();
				}
			}
		});
	}
	
	public static RenderState getRenderState() {
		return renderState;
	}
	
	public static void enableVsync(boolean vsync) {
		Display.vsync = vsync;
		GLFW.glfwSwapInterval(vsync == true ? 1 : 0);
	}
	
	public static boolean isVsyncEnabled() {
		return vsync;
	}
	
	public static void init() {
		if (isInitialized()) {
			throw new RuntimeException("Display already initialized.");
		}
		
		init = true;
		
		Viewport.setViewport(new Dimension(DEFAULT_VIEWPORT_WIDTH, DEFAULT_VIEWPORT_HEIGHT));
		Viewport.setOrthoSize(new Dimension(DEFAULT_ORTHO_WIDTH, DEFAULT_ORTHO_HEIGHT));
		
		Mouse.init();
		Keyboard.init();
		
		GLFW.glfwSetWindowSizeCallback(getWindowHandle(), new GLFWWindowSizeCallbackI() {
			private int oldWidth;
			private int oldHeight;
			
			@Override
			public void invoke(long window, int width, int height) {
				Viewport.setViewport(new Dimension(width, height));
				EventManager.getInstance().callEvent(new DisplaySizeChangeEvent(oldWidth, oldHeight, width, height));
				this.oldWidth = width;
				this.oldHeight = height;
			}
		});
		
		// TODO remove
		EventManager.getInstance().callEvent(new DisplayInitializedEvent());
	}
	
}
