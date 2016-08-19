package de.salocin.engine.event.plugin;

import de.salocin.engine.plugin.Plugin;

public class PluginEnableEvent extends PluginEvent {
	
	public static enum State {
		PRE_ENABLE, POST_ENABLE;
	}
	
	private final State state;
	
	public PluginEnableEvent(State state, Plugin plugin) {
		super(plugin);
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
	
	@Override
	public boolean isCancelable() {
		return state == State.PRE_ENABLE;
	}
	
}
