package de.salocin.engine.event;

public abstract class Event {
	
	public static final Event NONE = new Event() {
		@Override
		public boolean isCancelable() {
			return false;
		}
	};
	
	private boolean cancel;
	
	public abstract boolean isCancelable();
	
	public boolean isCanceled() {
		return isCancelable() ? false : cancel;
	}
	
	public void setCanceled(boolean cancel) {
		if (!isCancelable()) {
			throw new IllegalArgumentException("This event is not cancelable.");
		}
		this.cancel = cancel;
	}
	
}
