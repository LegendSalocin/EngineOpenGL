package de.salocin.engine.plugin;

/**
 * An easier version for {@link CorePlugin}. Only the {@link #onEnable()} method
 * has to be overridden.<br>
 * {@link #getName()}, {@link #getVersion()} and {@link #onEnable()} are
 * optional.
 */
public abstract class SimpleCorePlugin extends CorePlugin {
	
	@Override
	public String getName() {
		return getClass().getSimpleName();
	}
	
	@Override
	public String getVersion() {
		return "1.0";
	}
	
	@Override
	protected abstract void onEnable();
	
	@Override
	protected void onDisable() {
	}
	
}
