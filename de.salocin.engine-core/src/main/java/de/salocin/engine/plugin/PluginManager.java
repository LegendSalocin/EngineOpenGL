package de.salocin.engine.plugin;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Properties;
import java.util.jar.JarFile;

import de.salocin.engine.Engine;
import de.salocin.engine.event.PluginException;
import de.salocin.engine.util.ReflectionUtils;

public class PluginManager {
	
	public static final String UTILS_PLUGIN_ID = "de.salocin.engine.utils.UtilsPlugin";
	public static final String GUI_PLUGIN_ID = "de.salocin.engine.gui.GuiPlugin";
	
	private static PluginManager instance;
	private CorePlugin corePlugin;
	private HashSet<Plugin> plugins = new HashSet<Plugin>();
	
	private PluginManager() {
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
		
		instance.loadEnginePlugins();
		instance.loadExternalPlugins();
	}
	
	private void loadEnginePlugins() {
		try {
			load(Class.forName(UTILS_PLUGIN_ID).asSubclass(SimplePlugin.class));
		} catch (ClassNotFoundException e) {
		}
		
		try {
			load(Class.forName(GUI_PLUGIN_ID).asSubclass(SimplePlugin.class));
		} catch (ClassNotFoundException e) {
		}
	}
	
	private void loadExternalPlugins() {
		File plugins = new File("plugins");
		
		if (!plugins.exists()) {
			plugins.mkdir();
		}
		
		for (File pluginFile : plugins.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar");
			}
		})) {
			try (JarFile jar = new JarFile(pluginFile)) {
				Properties p = new Properties();
				p.load(jar.getInputStream(jar.getEntry("plugin.ini")));
				
				String main = p.getProperty("main");
				
				if (main == null) {
					throw new NullPointerException();
				}
				
				ClassLoader cl = URLClassLoader.newInstance(new URL[] { pluginFile.toURI().toURL() }, getClass().getClassLoader());
				Class<?> pluginClass = Class.forName(main, true, cl);
				
				load(pluginClass.asSubclass(Plugin.class));
			} catch (IOException e) {
				new PluginException("Could not found plugin.ini (" + pluginFile.getName() + ")", e).printStackTrace();
			} catch (ClassNotFoundException e) {
				new PluginException("The main class specified in the plugin.ini does not exist (" + pluginFile.getName() + ")", e).printStackTrace();
			} catch (NullPointerException e) {
				new PluginException("No main class specified in the plugin.ini (" + pluginFile.getName() + ")", e).printStackTrace();
			}
		}
	}
	
	private <T extends SimplePlugin> T load(Class<T> pluginClass) {
		try {
			return load(pluginClass.getConstructor().newInstance());
		} catch (Exception e) {
			throw new PluginException("The plugin '" + pluginClass.getName() + "' has no empty constructor or it is not accessible", e);
		}
	}
	
	private <T extends SimplePlugin> T load(T plugin) {
		Class<?> pluginClass = plugin.getClass();
		
		if (CorePlugin.class.isAssignableFrom(pluginClass)) {
			throw new PluginException("You have to extend the SimplePlugin class");
		} else {
			try {
				ReflectionUtils.setFieldValue(SimplePlugin.class, plugin, "description", PluginDescriptionFile.load(pluginClass.getResourceAsStream("/plugin.ini")));
			} catch (IOException e) {
				throw new PluginException(e);
			}
			
			if (isPluginPresent(plugin.getId())) {
				throw new PluginException("Plugin " + plugin.getName() + " already loaded");
			}
			
			plugins.add(plugin);
			plugin.enable();
			return plugin;
		}
	}
	
	public CorePlugin getCorePlugin() {
		return corePlugin;
	}
	
	public HashSet<Plugin> getPlugins() {
		return plugins;
	}
	
	public boolean isPluginPresent(String id) {
		return getPlugin(id) != null;
	}
	
	public Plugin getPlugin(String id) {
		if (id.equalsIgnoreCase(corePlugin.getId())) {
			return corePlugin;
		}
		
		for (Plugin plugin : plugins) {
			if (plugin.getId().equalsIgnoreCase(id)) {
				return plugin;
			}
		}
		
		return null;
	}
	
}
