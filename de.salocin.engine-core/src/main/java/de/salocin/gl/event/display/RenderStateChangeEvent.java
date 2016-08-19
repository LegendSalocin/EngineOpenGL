package de.salocin.gl.event.display;

import de.salocin.gl.display.RenderState;

public class RenderStateChangeEvent extends DisplayEvent {
	
	private final RenderState oldState;
	private RenderState newState;
	
	public RenderStateChangeEvent(RenderState oldState, RenderState newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	
	public RenderState getOldState() {
		return oldState;
	}
	
	public void setNewState(RenderState newState) {
		this.newState = newState;
	}
	
	public RenderState getNewState() {
		return newState;
	}
	
	@Override
	public boolean isCancelable() {
		return true;
	}
	
}
