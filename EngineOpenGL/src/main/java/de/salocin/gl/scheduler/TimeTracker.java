package de.salocin.gl.scheduler;

import java.util.HashMap;

public class TimeTracker {
	
	private static final HashMap<Mode, Long> detectedTimes = new HashMap<Mode, Long>();
	private static Mode lastMode;
	private static long lastStart;
	
	public static void start(Mode mode) {
		lastMode = mode;
		lastStart = System.currentTimeMillis();
	}
	
	public static void end() {
		detectedTimes.put(lastMode, System.currentTimeMillis() - lastStart);
	}
	
	public static long getDeltaTime(Mode mode) {
		if (detectedTimes.containsKey(mode)) {
			return detectedTimes.get(mode);
		} else {
			return -1;
		}
	}
	
	public static long getFpsCounterDelta() {
		return getDeltaTime(Mode.FPS_COUNTER);
	}
	
	public static long getRenderStateDelta() {
		return getDeltaTime(Mode.RENDER_STATE);
	}
	
	public static long getLoopSyncDelta() {
		return getDeltaTime(Mode.LOOP_SYNCHRONIZER);
	}
	
	public static long getVSyncDelta() {
		return getDeltaTime(Mode.V_SYNC);
	}
	
	public static enum Mode {
		FPS_COUNTER("FPS_COUNT"),
		RENDER_STATE(),
		LOOP_SYNCHRONIZER("LOOP_SYNC"),
		V_SYNC();
		
		private final String shortName;
		
		private Mode() {
			this(null);
		}
		
		private Mode(String shortName) {
			this.shortName = shortName;
		}
		
		public String getShortName() {
			return shortName == null ? name() : shortName;
		}
		
		@Override
		public String toString() {
			return getShortName();
		}
	}
	
}
