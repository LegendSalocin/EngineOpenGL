package de.salocin.gl.event.display;

import de.salocin.gl.plugin.Plugin;

/**
 * @deprecated {@link Plugin#enable()} is now synchronized, this event will
 *             never be called
 */
@Deprecated
public class DisplayInitializedEvent extends DisplayEvent {
	
	@Override
	public boolean isCancelable() {
		return false;
	}
	
}
