package de.salocin.engine.event;

public interface Callback<T extends Event> {
	
	void call(T event);
	
}
