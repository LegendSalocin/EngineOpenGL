package de.salocin.engine.scheduler;

import org.lwjgl.opengl.GL;

import de.salocin.engine.Engine;

public class Scheduler {
	
	private static Scheduler instance;
	private final GameLoop gameLoop = new GameLoop();
	
	private Scheduler() {
	}
	
	public static void init() {
		if (isInitialized()) {
			throw new RuntimeException("Scheduler already initialized.");
		}
		
		if (!Engine.isStarted()) {
			throw new RuntimeException("You have to call Engine.start() to start the engine first.");
		}
		
		instance = new Scheduler();
	}
	
	public static boolean isInitialized() {
		return instance != null;
	}
	
	public static Scheduler getInstance() {
		return instance;
	}
	
	public GameLoop getGameLoop() {
		return gameLoop;
	}
	
	public EngineRunnable runLater(Runnable runnable) {
		return runLater(runnable, 0);
	}
	
	public EngineRunnable runLater(Runnable runnable, long delay) {
		return runRepeated(runnable, delay, -1);
	}
	
	public EngineRunnable runRepeated(Runnable runnable, long repeatPeriod) {
		return runRepeated(runnable, 0, repeatPeriod);
	}
	
	public EngineRunnable runRepeated(Runnable runnable, long delay, long repeatPeriod) {
		EngineRunnable engineRunnable = new EngineRunnable(runnable, delay, repeatPeriod);
		LoopSynchronizer.addEngineRunnable(engineRunnable);
		return engineRunnable;
	}
	
	public EngineRunnable getRunnable(int runnableId) {
		return LoopSynchronizer.getEngineRunnable(runnableId);
	}
	
	public void cancelRepeatedRunnable(EngineRunnable runnable) {
		if (!runnable.isRepeated()) {
			throw new IllegalArgumentException("The EngineRunnable is not repeated.");
		} else {
			LoopSynchronizer.removeEngineRunnable(runnable);
		}
	}
	
	public static boolean hasGLContext() {
		try {
			return GL.getCapabilities() != null;
		} catch (IllegalStateException e) {
			return false;
		}
	}
	
}
