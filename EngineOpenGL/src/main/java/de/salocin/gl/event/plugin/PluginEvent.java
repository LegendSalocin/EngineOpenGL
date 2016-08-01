package de.salocin.gl.event.plugin;

import de.salocin.gl.event.Event;
import de.salocin.gl.plugin.Plugin;

public abstract class PluginEvent extends Event {
	
	private final Plugin plugin;
	
	public PluginEvent(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public Plugin getPlugin() {
		return plugin;
	}
	
}
