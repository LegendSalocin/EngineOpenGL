package de.salocin.gl.plugin;

import de.salocin.gl.Engine;
import de.salocin.gl.display.Display;
import de.salocin.gl.gui.RenderState;
import de.salocin.gl.util.engine.ExitCode;
import de.salocin.gl.util.engine.StartArgument;

public abstract class CorePlugin extends SimplePlugin implements Plugin {
	
	public Object applicationObject = new Object();
	
	protected CorePlugin() {
		this(null);
	}
	
	protected CorePlugin(String[] startArguments) {
		if (startArguments != null) {
			StartArgument.reinit(startArguments);
		}
	}
	
	@Override
	public abstract String getName();
	
	@Override
	public abstract String getVersion();
	
	@Override
	public PluginDescriptionFile getDescription() {
		return null;
	}
	
	public String[] getStartArguments() {
		return StartArgument.getArguments();
	}
	
	@Override
	public final boolean isEnabled() {
		return super.isEnabled();
	}
	
	@Override
	public final void enable() {
		if (isEnabled()) {
			throw new RuntimeException("CorePlugin already enabled.");
		}
		
		super.enable();
	}
	
	@Override
	public final void disable() {
		disable(null);
	}
	
	public final void disable(ExitCode exitCode) {
		for (Plugin plugin : PluginManager.getInstance().getPlugins()) {
			plugin.disable();
		}
		
		super.disable();
		
		if (exitCode != null) {
			Engine.stop(exitCode);
		}
	}
	
	public final void setState(RenderState state) {
		Display.getInstance().setRenderState(state);
	}
	
}
