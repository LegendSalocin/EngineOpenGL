package de.salocin.engine.scheduler;

public final class FPS {
	
	private static long currentTime;
	private static long delta;
	private static int fps;
	private static long lastTime;
	private static long lastFpsDetect;
	private static int fpsCounter;
	private static int targetFps;
	private static int targetFpsTimeout;
	
	private FPS() {
	}
	
	protected static void run() {
		timer();
		countFPS();
		
		if (targetFps > 0) {
			try {
				Thread.sleep(targetFpsTimeout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void timer() {
		currentTime = System.currentTimeMillis();
		delta = currentTime - lastTime;
		lastTime = currentTime;
	}
	
	private static void countFPS() {
		if (currentTime - lastFpsDetect > 1000) {
			fps = fpsCounter;
			fpsCounter = 0;
			lastFpsDetect = currentTime;
		} else {
			fpsCounter++;
		}
	}
	
	public static long getCurrentTime() {
		return currentTime;
	}
	
	public static long getDelta() {
		return delta;
	}
	
	public static int getFPS() {
		return fps;
	}
	
	public static int getTargetFPS() {
		return targetFps;
	}
	
	public static void setTargetFPS(int targetFps) {
		FPS.targetFps = targetFps;
		FPS.targetFpsTimeout = 1000 / targetFps;
	}
	
}
