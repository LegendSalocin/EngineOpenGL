package de.salocin.engine.event;

public interface SimpleCallback extends Callback<Event> {
	
	void call();
	
	@Override
	default void call(Event event) {
		call();
	}
	
}
