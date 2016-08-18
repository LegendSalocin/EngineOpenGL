package de.salocin.gl.plugin;

import de.salocin.gl.log.PluginLogger;

public interface Plugin {
	
	PluginLogger getLogger();
	
	String getName();
	
	String getVersion();
	
	PluginDescriptionFile getDescription();
	
	boolean isEnabled();
	
	void enable();
	
	void disable();
	
}
