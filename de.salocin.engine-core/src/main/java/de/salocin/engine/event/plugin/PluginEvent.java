package de.salocin.engine.event.plugin;

import de.salocin.engine.event.Event;
import de.salocin.engine.plugin.Plugin;

public abstract class PluginEvent extends Event {
	
	private final Plugin plugin;
	
	public PluginEvent(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public Plugin getPlugin() {
		return plugin;
	}
	
}
