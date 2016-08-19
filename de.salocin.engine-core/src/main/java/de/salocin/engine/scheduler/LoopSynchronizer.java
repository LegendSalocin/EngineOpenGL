package de.salocin.engine.scheduler;

import java.util.ArrayList;

import org.apache.commons.lang3.Validate;

public class LoopSynchronizer {
	
	private static ArrayList<EngineRunnable> runLater = new ArrayList<EngineRunnable>();
	
	private LoopSynchronizer() {
	}
	
	protected static void run(long currentTime) {
		ArrayList<EngineRunnable> runLater = copy();
		
		for (int i = runLater.size() - 1; i >= 0; i--) {
			EngineRunnable r = runLater.get(i);
			if (r.getDelay() <= 0 || r.getStartTime() + r.getDelay() <= currentTime) {
				if (r.isRepeated()) {
					if (currentTime - r.lastRepeat >= r.getRepeatPeriod()) {
						r.lastRepeat = currentTime;
						r.getRunnable().run();
					}
				} else {
					r.getRunnable().run();
					removeEngineRunnable(r);
					r = null;
				}
			}
		}
	}
	
	protected static void addEngineRunnable(EngineRunnable runnable) {
		Validate.notNull(runnable);
		
		synchronized (runLater) {
			runLater.add(runnable);
		}
	}
	
	protected static void removeEngineRunnable(EngineRunnable runnable) {
		Validate.notNull(runnable);
		
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
		ArrayList<EngineRunnable> runLaterCopy = new ArrayList<EngineRunnable>();
		synchronized (runLater) {
			runLaterCopy.addAll(runLater);
		}
		return runLaterCopy;
	}
	
}
