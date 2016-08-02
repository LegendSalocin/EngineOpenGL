package de.salocin.gl.event.input;

import de.salocin.gl.util.input.Action;
import de.salocin.gl.util.input.Modifier;
import de.salocin.gl.util.input.MouseButton;

public class MouseButtonEvent extends ModifierEvent {
	
	private final MouseButton button;
	private final Action action;
	
	public MouseButtonEvent(MouseButton button, Action action, Modifier[] modifiers) {
		super(modifiers);
		this.button = button;
		this.action = action;
	}
	
	public MouseButton getButton() {
		return button;
	}
	
	public Action getAction() {
		return action;
	}
	
}
