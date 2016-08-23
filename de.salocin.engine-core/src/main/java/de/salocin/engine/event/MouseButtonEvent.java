package de.salocin.engine.event;

import de.salocin.engine.display.input.Action;
import de.salocin.engine.display.input.Modifier;
import de.salocin.engine.display.input.MouseButton;

public class MouseButtonEvent extends Event {
	
	private final MouseButton button;
	private final Action action;
	private final Modifier[] modifiers;
	
	public MouseButtonEvent(MouseButton button, Action action, Modifier[] modifiers) {
		this.button = button;
		this.action = action;
		this.modifiers = modifiers;
	}
	
	public MouseButton getButton() {
		return button;
	}
	
	public Action getAction() {
		return action;
	}
	
	public Modifier[] getModifiers() {
		return modifiers;
	}
	
	@Override
	public boolean isCancelable() {
		return false;
	}
	
}
