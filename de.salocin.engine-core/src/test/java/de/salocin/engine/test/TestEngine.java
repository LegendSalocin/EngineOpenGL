package de.salocin.engine.test;

import de.salocin.gl.Engine;
import de.salocin.gl.plugin.SimpleCorePlugin;

public class TestEngine extends SimpleCorePlugin {
	
	public static void main(String[] args) {
		Engine.simpleStart(new TestEngine());
	}
	
	@Override
	protected void onEnable() {
		System.out.println("Hello Java v" + Engine.getJavaVersion() + " and Engine v" + Engine.getEngineVersion());
	}
	
}
