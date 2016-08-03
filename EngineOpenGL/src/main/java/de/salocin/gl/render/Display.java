package de.salocin.gl.render;

import java.util.logging.Logger;

import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.display.DisplayGameStateChangeEvent;
import de.salocin.gl.event.display.DisplayInitializedEvent;
import de.salocin.gl.log.EngineLogger;
import de.salocin.gl.render.gui.RenderState;
import de.salocin.gl.scheduler.Scheduler;
import de.salocin.gl.util.input.Keyboard;
import de.salocin.gl.util.input.Mouse;

public class Display {
	
	public static final Logger logger = EngineLogger.getEngineLogger(Display.class);
	private static Display instance = new Display();
	private Resolution resolution;
	private RenderState renderState;
	private int targetFps;
	
	private Display() {
	}
	
	public static Display getInstance() {
		return instance;
	}
	
	public void init() {
		if (isInitialized()) {
			throw new RuntimeException("Display already initialized.");
		}
		
		resolution = new Resolution();
		
		Mouse.init();
		Keyboard.init();
		
		logger.info("Display initialized.");
		EventManager.getInstance().callEvent(new DisplayInitializedEvent());
	}
	
	public boolean isInitialized() {
		return resolution != null;
	}
	
	private void checkInit() {
		if (!isInitialized()) {
			throw new RuntimeException("Display not initialized. Wait for the game loop before calling this method or catch a " + DisplayInitializedEvent.class.getSimpleName());
		}
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
	
	public void setTargetFPS(int fps) {
		this.targetFps = fps;
	}
	
	public int getTargetFps() {
		return targetFps;
	}
	
	public Resolution getResolution() {
		checkInit();
		return resolution;
	}
	
}
