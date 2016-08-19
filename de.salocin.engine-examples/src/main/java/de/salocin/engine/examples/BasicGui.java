package de.salocin.engine.examples;

import de.salocin.gl.Engine;
import de.salocin.gl.plugin.SimpleCorePlugin;

public class BasicGui extends SimpleCorePlugin {
	
	public static void main(String[] args) {
		Engine.simpleStart(new BasicGui());
	}
	
	@Override
	protected void onEnable() {
	}
	
}
