package de.salocin.gl.event.engine;

import de.salocin.gl.event.display.DisplayInitializedEvent;

/**
 * @deprecated use {@link DisplayInitializedEvent} instead
 */
@Deprecated
public class EngineGLContextCreatedEvent extends EngineEvent {
	
	@Override
	public boolean isCancelable() {
		return false;
	}
	
}
