package de.salocin.gl.test;

import de.salocin.gl.Engine;
import de.salocin.gl.event.EventHandler;
import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.Listener;
import de.salocin.gl.event.display.DisplayInitializedEvent;
import de.salocin.gl.plugin.CorePlugin;
import de.salocin.gl.util.engine.ExitCode;
import de.salocin.gl.util.exception.EngineException;

public class TestEvents extends CorePlugin implements Listener {
	
	public static void main(String[] args) {
		try {
			Engine.start(new TestEvents());
		} catch (EngineException e) {
			e.log();
			ExitCode.ENGINE_START_ERROR.shutdownEngine();
		}
	}
	
	@Override
	public String getName() {
		return TestEvents.class.getSimpleName();
	}
	
	@Override
	public String getVersion() {
		return "1.0";
	}
	
	@Override
	protected void onEnable() {
		EventManager.getInstance().registerListener(this);
	}
	
	@Override
	protected void onDisable() {
	}
	
	@EventHandler
	public void onDisplayInit(DisplayInitializedEvent e) {
		getLogger().info("Display init!");
	}
	
}
