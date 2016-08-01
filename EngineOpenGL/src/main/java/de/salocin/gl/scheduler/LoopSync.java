package de.salocin.gl.scheduler;

import java.util.ArrayList;

public class LoopSync {
	
	private static ArrayList<EngineRunnable> runLater = new ArrayList<EngineRunnable>();
	
	private LoopSync() {
	}
	
	protected static void run(long currentTime) {
		ArrayList<EngineRunnable> runLater = copy();
		ArrayList<EngineRunnable> toRemove = new ArrayList<EngineRunnable>();
		
		for (EngineRunnable r : runLater) {
			if (r.getDelay() <= 0 || r.getStartTime() + r.getDelay() <= currentTime) {
				if (r.isRepeated()) {
					if (currentTime - r.lastRepeat >= r.getRepeatPeriod()) {
						r.lastRepeat = currentTime;
						r.getRunnable().run();
					}
				} else {
					r.getRunnable().run();
					toRemove.add(r);
				}
			}
		}
		
		for (EngineRunnable engineRunnable : toRemove) {
			removeEngineRunnable(engineRunnable);
		}
	}
	
	protected static void addEngineRunnable(EngineRunnable runnable) {
		synchronized (runLater) {
			runLater.add(runnable);
		}
	}
	
	protected static void removeEngineRunnable(EngineRunnable runnable) {
		synchronized (runLater) {
			runLater.remove(runnable);
		}
	}
	
	protected static EngineRunnable getEngineRunnable(int runnableId) {
		ArrayList<EngineRunnable> runLaterCopy = copy();
		
		for (EngineRunnable engineRunnable : runLaterCopy) {
			if (engineRunnable.getId() == runnableId) {
				return engineRunnable;
			}
		}
		
		return null;
	}
	
	private static ArrayList<EngineRunnable> copy() {
		ArrayList<EngineRunnable> runLaterCopy;
		synchronized (runLater) {
			runLaterCopy = runLater;
		}
		return runLaterCopy;
	}
	
}
