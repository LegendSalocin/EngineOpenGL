package de.salocin.gl.display;

import static org.lwjgl.glfw.GLFW.*;

import java.util.logging.Logger;

import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.display.DisplayGameStateChangeEvent;
import de.salocin.gl.event.display.DisplayInitializedEvent;
import de.salocin.gl.gui.RenderState;
import de.salocin.gl.log.EngineLogger;
import de.salocin.gl.scheduler.Scheduler;
import de.salocin.gl.util.font.Font;
import de.salocin.gl.util.font.FontBuilder;
import de.salocin.gl.util.input.Keyboard;
import de.salocin.gl.util.input.Mouse;

public class Display {
	
	public static final Logger logger = EngineLogger.getEngineLogger(Display.class);
	private static final FontBuilder engineFontBuilder = new FontBuilder("Arial");
	private static Display instance = new Display();
	private static Font engineFont;
	private Resolution resolution;
	private RenderState renderState;
	private boolean vsync;
	
	private Display() {
	}
	
	public static Display getInstance() {
		return instance;
	}
	
	public static Font getDefaultEngineFont() {
		getInstance().checkInit();
		
		return engineFont;
	}
	
	public static Font getDefaultEngineFontCustomSize(int size) {
		return engineFontBuilder.setFontSize(size).build();
	}
	
	public void init() {
		if (isInitialized()) {
			throw new RuntimeException("Display already initialized.");
		}
		
		resolution = new Resolution();
		
		Mouse.init();
		Keyboard.init();
		
		engineFont = engineFontBuilder.setFontSize(20).build();
		
		logger.info("Display initialized.");
		EventManager.getInstance().callEvent(new DisplayInitializedEvent());
	}
	
	private void checkInit() {
		if (!isInitialized()) {
			throw new RuntimeException("Display not initialized. Wait for the game loop before calling this method or catch a " + DisplayInitializedEvent.class.getSimpleName());
		}
	}
	
	public boolean isInitialized() {
		return resolution != null;
	}
	
	public long getWindow() {
		return Scheduler.getInstance().getGameLoop().getWindow();
	}
	
	public void setRenderState(RenderState state) {
		Scheduler.getInstance().runLater(() -> {
			DisplayGameStateChangeEvent e = new DisplayGameStateChangeEvent(renderState, state);
			if (!EventManager.getInstance().callEvent(e)) {
				if (renderState != null) {
					renderState.exit();
				}
				
				renderState = e.getNewState();
				renderState.init();
			}
		});
	}
	
	public RenderState getRenderState() {
		return renderState;
	}
	
	public void enableVsync(boolean vsync) {
		this.vsync = vsync;
		glfwSwapInterval(vsync == true ? 1 : 0);
	}
	
	public boolean isVsyncEnabled() {
		return vsync;
	}
	
	public Resolution getResolution() {
		checkInit();
		return resolution;
	}
	
}
