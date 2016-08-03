package de.salocin.gl.test;

import de.salocin.gl.Engine;
import de.salocin.gl.plugin.CorePlugin;
import de.salocin.gl.util.engine.ExitCode;
import de.salocin.gl.util.exception.DetailedException;
import de.salocin.gl.util.exception.EngineException;

public class TestLogger extends CorePlugin {
	
	public static void main(String[] args) {
		try {
			Engine.start(new TestLogger());
		} catch (EngineException e) {
			e.printStackTrace();
			ExitCode.ENGINE_START_ERROR.shutdownEngine();
		}
	}
	
	@Override
	public String getName() {
		return getClass().getSimpleName();
	}
	
	@Override
	public String getVersion() {
		return "1.0";
	}
	
	@Override
	protected void onEnable() {
		/* Test Logger */
		getLogger().info("This should be logged.");
		
		/* Test normal output */
		System.out.println("This should be logged too.");
		
		/* Test Exception logged */
		new DetailedException(new NullPointerException()).addDetail("Name", "Salocin").addDetail("Session", 586).addDetail("Logged in", true).log();
	}
	
	@Override
	protected void onDisable() {
	
	}
	
}
