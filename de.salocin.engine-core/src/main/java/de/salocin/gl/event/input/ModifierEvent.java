package de.salocin.gl.event.input;

import org.apache.commons.lang3.ArrayUtils;

import de.salocin.gl.util.input.Modifier;

public abstract class ModifierEvent extends InputEvent {
	
	private final Modifier[] modifiers;
	
	public ModifierEvent(Modifier[] modifiers) {
		this.modifiers = modifiers;
	}
	
	public Modifier[] getModifiers() {
		return modifiers;
	}
	
	public boolean isShiftDown() {
		return ArrayUtils.contains(modifiers, Modifier.SHIFT);
	}
	
	public boolean isControlDown() {
		return ArrayUtils.contains(modifiers, Modifier.CONTROL);
	}
	
	public boolean isAltDown() {
		return ArrayUtils.contains(modifiers, Modifier.ALT);
	}
	
	public boolean isSuperDown() {
		return ArrayUtils.contains(modifiers, Modifier.SUPER);
	}
	
}
