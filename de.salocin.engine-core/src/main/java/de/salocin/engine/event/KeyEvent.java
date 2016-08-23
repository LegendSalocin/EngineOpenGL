package de.salocin.engine.event;

import de.salocin.engine.display.input.Action;
import de.salocin.engine.display.input.Key;
import de.salocin.engine.display.input.Modifier;

public class KeyEvent extends Event {
	
	private final Key key;
	private final Action action;
	private final Modifier[] modifiers;
	private final char keyChar;
	
	public KeyEvent(Key key, Action action, Modifier[] modifiers) {
		this(key, action, modifiers, (char) 0);
	}
	
	public KeyEvent(Key key, Action action, Modifier[] modifiers, char keyChar) {
		this.key = key;
		this.action = action;
		this.modifiers = modifiers;
		this.keyChar = keyChar;
	}
	
	public Key getKey() {
		return key;
	}
	
	public Action getAction() {
		return action;
	}
	
	public Modifier[] getModifiers() {
		return modifiers;
	}
	
	public String getChar() {
		if (action == Action.RELEASED || !key.isPrintable()) {
			return null;
		}
		
		return String.valueOf(keyChar);
	}
	
	@Override
	public boolean isCancelable() {
		return false;
	}
}
