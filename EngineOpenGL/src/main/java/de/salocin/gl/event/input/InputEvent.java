package de.salocin.gl.event.input;

import de.salocin.gl.event.Event;

public abstract class InputEvent extends Event {
	
	@Override
	public boolean isCancelable() {
		return false;
	}
	
}
