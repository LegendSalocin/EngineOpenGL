package de.salocin.engine.event.plugin;

import de.salocin.engine.plugin.Plugin;

public class PluginDisableEvent extends PluginEvent {
	
	public static enum State {
		PRE_DISABLE, POST_DISABLE;
	}
	
	private final State state;
	
	public PluginDisableEvent(State state, Plugin plugin) {
		super(plugin);
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
	
	@Override
	public boolean isCancelable() {
		return state == State.PRE_DISABLE;
	}
	
}
