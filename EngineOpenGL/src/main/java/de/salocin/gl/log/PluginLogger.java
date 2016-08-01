package de.salocin.gl.log;

import de.salocin.gl.plugin.Plugin;
import de.salocin.gl.plugin.PluginManager;

public class PluginLogger extends EngineLogger {
	
	private final Plugin plugin;
	
	protected PluginLogger(Plugin plugin) {
		super(plugin.getName());
		this.plugin = plugin;
	}
	
	public Plugin getPlugin() {
		return plugin;
	}
	
	public static PluginLogger getPluginLogger(Plugin plugin) {
		return getEngineLogger(plugin.getName(), new LoggerCreator<PluginLogger>() {
			@Override
			public PluginLogger createLogger(String name) {
				Plugin plugin = PluginManager.getInstance().getPlugin(name);
				if (plugin == null) {
					throw new RuntimeException("Plugin " + name + " is not present.");
				}
				
				return new PluginLogger(plugin);
			}
		});
	}
	
}
