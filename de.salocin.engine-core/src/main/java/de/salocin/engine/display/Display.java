package de.salocin.engine.display;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

import de.salocin.engine.display.input.Keyboard;
import de.salocin.engine.display.input.Mouse;
import de.salocin.engine.event.Callback;
import de.salocin.engine.event.CallbackHandler;
import de.salocin.engine.event.ValueChangeEvent;
import de.salocin.engine.geom.Dimension;
import de.salocin.engine.scheduler.Scheduler;
import de.salocin.engine.util.Viewport;

public class Display {
	
	public static final float DEFAULT_ORTHO_WIDTH = 1.0f;
	public static final float DEFAULT_ORTHO_HEIGHT = 1.0f;
	public static final int DEFAULT_VIEWPORT_WIDTH = 800;
	public static final int DEFAULT_VIEWPORT_HEIGHT = 600;
	
	private static boolean init = false;
	private static CallbackHandler<ValueChangeEvent<RenderState>> renderStateCallback = new CallbackHandler<ValueChangeEvent<RenderState>>();
	private static RenderState renderState;
	private static boolean vsync;
	private static Mouse mouse;
	private static Keyboard keyboard;
	
	/**
	 * A method that returns <code>true</code> when the GL context was
	 * created.<br>
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
	
	public static Mouse getMouse() {
		return mouse;
	}
	
	public static Keyboard getKeyboard() {
		return keyboard;
	}
	
	public static void setRenderState(RenderState state) {
		Scheduler.getInstance().runLater(new Runnable() {
			@Override
			public void run() {
				ValueChangeEvent<RenderState> e = new ValueChangeEvent<RenderState>(renderState, state);
				renderStateCallback.call(e);
				
				if (!e.isCanceled()) {
					if (renderState != null) {
						renderState.exit();
					}
					
					renderState = e.getNewValue();
					
					if (renderState != null) {
						renderState.init();
					}
				}
			}
		});
	}
	
	public static RenderState getRenderState() {
		return renderState;
	}
	
	public static void addRenderStateCallback(Callback<ValueChangeEvent<RenderState>> callback) {
		renderStateCallback.add(callback);
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
		
		final long window = getWindowHandle();
		
		init = true;
		
		Viewport.setViewport(new Dimension(DEFAULT_VIEWPORT_WIDTH, DEFAULT_VIEWPORT_HEIGHT));
		Viewport.setOrthoSize(new Dimension(DEFAULT_ORTHO_WIDTH, DEFAULT_ORTHO_HEIGHT));
		
		mouse = new Mouse(window);
		keyboard = new Keyboard(window);
		
		enableVsync(true);
		
		GLFW.glfwSetWindowSizeCallback(getWindowHandle(), new GLFWWindowSizeCallbackI() {
			@Override
			public void invoke(long window, int width, int height) {
				Viewport.setViewport(new Dimension(width, height));
			}
		});
	}
	
}
