package de.salocin.gl.plugin;

import java.io.IOException;
import java.util.logging.Logger;

import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.PluginException;
import de.salocin.gl.event.plugin.PluginDisableEvent;
import de.salocin.gl.event.plugin.PluginEnableEvent;
import de.salocin.gl.scheduler.Scheduler;

public abstract class SimplePlugin implements Plugin {
	
	private Logger logger;
	private PluginDescriptionFile description;
	private boolean enabled;
	
	@Override
	public Logger getLogger() {
		return logger;
	}
	
	@Override
	public final String getId() {
		return getClass().getName();
	}
	
	@Override
	public String getName() {
		return description.pluginName;
	}
	
	@Override
	public String getVersion() {
		return description.pluginVersion;
	}
	
	@Override
	public PluginDescriptionFile getDescription() {
		return description;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public void enable() {
		if (enabled == true) {
			return;
		}
		
		Scheduler.getInstance().runLater(() -> {
			if (EventManager.getInstance().callEvent(new PluginEnableEvent(PluginEnableEvent.State.PRE_ENABLE, this))) {
				return;
			}
			
			logger = Logger.getLogger(getName());
			
			enabled = true;
			onEnable();
			
			EventManager.getInstance().callEvent(new PluginEnableEvent(PluginEnableEvent.State.POST_ENABLE, this));
		});
	}
	
	protected abstract void onEnable();
	
	@Override
	public void disable() {
		if (enabled == false) {
			return;
		}
		
		Scheduler.getInstance().runLater(() -> {
			if (EventManager.getInstance().callEvent(new PluginDisableEvent(PluginDisableEvent.State.PRE_DISABLE, this))) {
				return;
			}
			
			enabled = false;
			onDisable();
			
			EventManager.getInstance().callEvent(new PluginDisableEvent(PluginDisableEvent.State.POST_DISABLE, this));
		});
	}
	
	protected abstract void onDisable();
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SimplePlugin) {
			SimplePlugin plugin = ((SimplePlugin) obj);
			
			if (description != null && plugin.description != null) {
				return plugin.getId().equals(getId());
			}
		}
		
		return false;
	}
	
	@Override
	public void reloadDescriptionFile() {
		try {
			description = PluginDescriptionFile.load(getClass().getResourceAsStream("/plugin.ini"));
		} catch (IOException e) {
			new PluginException(e);
		}
	}
	
}
