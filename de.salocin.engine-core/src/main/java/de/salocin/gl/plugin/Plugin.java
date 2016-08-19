package de.salocin.gl.plugin;

import java.util.logging.Logger;

public interface Plugin {
	
	Logger getLogger();
	
	String getId();
	
	String getName();
	
	String getVersion();
	
	PluginDescriptionFile getDescription();
	
	boolean isEnabled();
	
	void enable();
	
	void disable();
	
	void reloadDescriptionFile();
	
}
