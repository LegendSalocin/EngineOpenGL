package de.salocin.gl.scheduler;

public class EngineRunnable {
	
	private static int idCounter;
	private final int id;
	private final Runnable runnable;
	private long startTime;
	private long delay;
	protected long lastRepeat;
	private long repeatPeriod;
	
	protected EngineRunnable(Runnable runnable) {
		this(runnable, -1);
	}
	
	protected EngineRunnable(Runnable runnable, long delay) {
		this(runnable, delay, -1);
	}
	
	protected EngineRunnable(Runnable runnable, long delay, long repeatPeriod) {
		this.id = idCounter++;
		this.runnable = runnable;
		this.delay = delay == 0 ? -1 : delay;
		this.repeatPeriod = repeatPeriod;
		this.startTime = System.currentTimeMillis();
	}
	
	public int getId() {
		return id;
	}
	
	public Runnable getRunnable() {
		return runnable;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public long getDelay() {
		return delay;
	}
	
	public void setDelay(long delay) {
		this.delay = delay;
	}
	
	public long getRepeatPeriod() {
		return repeatPeriod;
	}
	
	public void setRepeatPeriod(long repeatPeriod) {
		this.repeatPeriod = repeatPeriod;
	}
	
	public boolean isRepeated() {
		return repeatPeriod >= 0;
	}
	
	public void cancel() {
		Scheduler.getInstance().cancelRepeatedRunnable(this);
	}
	
}
