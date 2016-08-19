package de.salocin.engine.examples;

import de.salocin.engine.Engine;
import de.salocin.engine.plugin.SimpleCorePlugin;

public class BasicGui extends SimpleCorePlugin {
	
	public static void main(String[] args) {
		Engine.simpleStart(new BasicGui());
	}
	
	@Override
	protected void onEnable() {
	}
	
}
