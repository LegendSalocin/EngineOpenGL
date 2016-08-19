package de.salocin.engine.event;

public abstract class Event {
	
	private boolean cancel;
	
	public abstract boolean isCancelable();
	
	public boolean isCanceled() {
		return cancel;
	}
	
	public void setCanceled(boolean cancel) {
		if (!isCancelable() && cancel) {
			throw new IllegalArgumentException("This event is not cancelable.");
		}
		this.cancel = cancel;
	}
	
}
