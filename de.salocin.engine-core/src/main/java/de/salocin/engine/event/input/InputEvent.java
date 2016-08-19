package de.salocin.engine.event.input;

import de.salocin.engine.event.Event;

public abstract class InputEvent extends Event {
	
	@Override
	public boolean isCancelable() {
		return false;
	}
	
}
