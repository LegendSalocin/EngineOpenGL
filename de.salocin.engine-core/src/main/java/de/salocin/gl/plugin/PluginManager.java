package de.salocin.gl.plugin;

import java.util.HashSet;

import de.salocin.gl.Engine;

public class PluginManager {
	
	private static PluginManager instance;
	private CorePlugin corePlugin;
	private HashSet<Plugin> plugins = new HashSet<Plugin>();
	
	private PluginManager() {
	}
	
	public static void init(CorePlugin corePlugin) {
		if (isInitialized()) {
			throw new RuntimeException("PluginManager already initialized.");
		}
		
		if (!Engine.isStarted()) {
			throw new RuntimeException("You have to call Engine.start() to start the engine first.");
		}
		
		instance = new PluginManager();
		instance.corePlugin = corePlugin;
		instance.corePlugin.enable();
		
		/* TODO load external plugins */
	}
	
	public static PluginManager getInstance() {
		if (!isInitialized()) {
			throw new RuntimeException("PluginManager is not initialized.");
		}
		
		return instance;
	}
	
	public static boolean isInitialized() {
		return instance != null;
	}
	
	public CorePlugin getCorePlugin() {
		return corePlugin;
	}
	
	public static <T extends Plugin> T newInstance(Class<T> pluginClass) {
		try {
			return pluginClass.newInstance();
		} catch (Exception e) {
		}
		return null;
	}
	
	public <T extends Plugin> T load(Class<T> pluginClass) {
		return load(newInstance(pluginClass));
	}
	
	public <T extends Plugin> T load(T plugin) {
		Class<?> pluginClass = plugin.getClass();
		
		if (CorePlugin.class.isAssignableFrom(pluginClass)) {
			throw new IllegalArgumentException("You have to extend the SimplePlugin class.");
		} else {
			if (plugins.contains(plugin) || isPluginPresent(plugin.getName())) {
				throw new IllegalArgumentException("Plugin " + plugin.getName() + " already loaded.");
			}
			
			plugins.add(plugin);
			plugin.enable();
			return plugin;
		}
	}
	
	public HashSet<Plugin> getPlugins() {
		return plugins;
	}
	
	public boolean isPluginPresent(String name) {
		return getPlugin(name) != null;
	}
	
	public Plugin getPlugin(String name) {
		if (name.equalsIgnoreCase(corePlugin.getName())) {
			return corePlugin;
		}
		
		for (Plugin plugin : plugins) {
			if (plugin.getName().equals(name)) {
				return plugin;
			}
		}
		
		return null;
	}
	
}
