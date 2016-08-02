package de.salocin.gl.event.input;

import de.salocin.gl.util.input.Action;
import de.salocin.gl.util.input.Key;
import de.salocin.gl.util.input.Modifier;

public class KeyPressedEvent extends ModifierEvent {
	
	private final Key key;
	private final Action action;
	
	public KeyPressedEvent(Key key, Action action, Modifier[] modifiers) {
		super(modifiers);
		this.key = key;
		this.action = action;
	}
	
	public Key getKey() {
		return key;
	}
	
	public Action getAction() {
		return action;
	}
	
}
