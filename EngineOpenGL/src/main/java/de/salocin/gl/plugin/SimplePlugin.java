package de.salocin.gl.plugin;

import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.plugin.PluginDisableEvent;
import de.salocin.gl.event.plugin.PluginEnableEvent;
import de.salocin.gl.log.PluginLogger;

public abstract class SimplePlugin implements Plugin {
	
	private PluginLogger logger;
	private PluginDescriptionFile description;
	private boolean enabled;
	
	@Override
	public PluginLogger getLogger() {
		return logger;
	}
	
	@Override
	public String getName() {
		return description.getPluginName();
	}
	
	@Override
	public String getVersion() {
		return description.getPluginVersion();
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
		
		if (EventManager.getInstance().callEvent(new PluginEnableEvent(PluginEnableEvent.State.PRE_ENABLE, this))) {
			return;
		}
		
		logger = PluginLogger.getPluginLogger(this);
		
		enabled = true;
		onEnable();
		
		EventManager.getInstance().callEvent(new PluginEnableEvent(PluginEnableEvent.State.POST_ENABLE, this));
	}
	
	protected abstract void onEnable();
	
	@Override
	public void disable() {
		if (enabled == false) {
			return;
		}
		
		if (EventManager.getInstance().callEvent(new PluginDisableEvent(PluginDisableEvent.State.PRE_DISABLE, this))) {
			return;
		}
		
		enabled = false;
		onDisable();
		
		EventManager.getInstance().callEvent(new PluginDisableEvent(PluginDisableEvent.State.POST_DISABLE, this));
	}
	
	protected abstract void onDisable();
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Plugin) {
			return ((Plugin) obj).getName().equals(getName());
		}
		
		return false;
	}
	
}
