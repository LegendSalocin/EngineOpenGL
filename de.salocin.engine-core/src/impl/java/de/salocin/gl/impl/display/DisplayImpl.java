package de.salocin.gl.impl.display;

import static org.lwjgl.glfw.GLFW.*;

import java.util.logging.Logger;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

import de.salocin.gl.display.Display;
import de.salocin.gl.display.Viewport;
import de.salocin.gl.display.font.Font;
import de.salocin.gl.display.font.FontBuilder;
import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.display.RenderStateChangeEvent;
import de.salocin.gl.event.display.DisplayInitializedEvent;
import de.salocin.gl.gui.RenderState;
import de.salocin.gl.log.EngineLogger;
import de.salocin.gl.scheduler.Scheduler;
import de.salocin.gl.util.input.Keyboard;
import de.salocin.gl.util.input.Mouse;
import de.salocin.gl.util.math.Dimension;

public class DisplayImpl implements Display {
	
	public static final DisplayImpl instance = new DisplayImpl();
	public static final Logger logger = EngineLogger.getEngineLogger(Display.class);
	private static final FontBuilder engineFontBuilder = new FontBuilder("Arial");
	private boolean init = false;
	private Font engineFont;
	private RenderState renderState;
	private boolean vsync;
	
	public DisplayImpl() {
	}
	
	@Override
	public Font getDefaultEngineFont() {
		return engineFont;
	}
	
	@Override
	public FontBuilder getDefaultEngineFontBuilder() {
		return engineFontBuilder;
	}
	
	@Override
	public boolean isInitialized() {
		return init;
	}
	
	@Override
	public long getWindowHandle() {
		return Scheduler.getInstance().getGameLoop().getWindow();
	}
	
	@Override
	public void setRenderState(RenderState state) {
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
	
	@Override
	public RenderState getRenderState() {
		return renderState;
	}
	
	@Override
	public void enableVsync(boolean vsync) {
		this.vsync = vsync;
		glfwSwapInterval(vsync == true ? 1 : 0);
	}
	
	@Override
	public boolean isVsyncEnabled() {
		return vsync;
	}
	
	public void init() {
		if (isInitialized()) {
			throw new RuntimeException("Display already initialized.");
		}
		
		init = true;
		
		ViewportImpl.instance.setViewport(new Dimension(Viewport.DEFAULT_VIEWPORT_WIDTH, Viewport.DEFAULT_VIEWPORT_HEIGHT));
		ViewportImpl.instance.setOrtho(new Dimension(Viewport.DEFAULT_ORTHO_WIDTH, Viewport.DEFAULT_ORTHO_HEIGHT));
		
		Mouse.init();
		Keyboard.init();
		
		engineFont = engineFontBuilder.setFontSize(20).build();
		
		GLFW.glfwSetWindowSizeCallback(getWindowHandle(), new GLFWWindowSizeCallbackI() {
			@Override
			public void invoke(long window, int width, int height) {
				Viewport.getInstance().setViewport(new Dimension(width, height));
			}
		});
		
		logger.info("Display initialized and GL context created.");
		EventManager.getInstance().callEvent(new DisplayInitializedEvent());
	}
	
}
