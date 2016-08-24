package de.salocin.engine.event;

public class ValueChangeEvent<T> extends Event {
	
	private final boolean cancelable;
	private final T oldValue;
	private final T newValue;
	
	public ValueChangeEvent(T oldValue, T newValue) {
		this(oldValue, newValue, true);
	}
	
	public ValueChangeEvent(T oldValue, T newValue, boolean cancelable) {
		this.cancelable = cancelable;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	public T getOldValue() {
		return oldValue;
	}
	
	public T getNewValue() {
		return newValue;
	}
	
	@Override
	public boolean isCancelable() {
		return cancelable;
	}
	
}
