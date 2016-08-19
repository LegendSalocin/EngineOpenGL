package de.salocin.engine.event.input;

import de.salocin.engine.util.input.Action;
import de.salocin.engine.util.input.Key;
import de.salocin.engine.util.input.Modifier;

public class KeyPressedEvent extends ModifierEvent {
	
	private final Key key;
	private final Action action;
	private final char keyChar;
	
	public KeyPressedEvent(Key key, Action action, Modifier[] modifiers) {
		this(key, action, modifiers, (char) 0);
	}
	
	public KeyPressedEvent(Key key, Action action, Modifier[] modifiers, char keyChar) {
		super(modifiers);
		this.key = key;
		this.action = action;
		this.keyChar = keyChar;
	}
	
	public Key getKey() {
		return key;
	}
	
	public Action getAction() {
		return action;
	}
	
	public String getChar() {
		if (action == Action.RELEASED || !key.isPrintable()) {
			return null;
		}
		
		return String.valueOf(keyChar);
	}
	
}
