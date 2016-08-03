package de.salocin.gl.event.engine;

public class EngineCLContextCreatedEvent extends EngineEvent {
	
	@Override
	public boolean isCancelable() {
		return false;
	}
	
}
