package de.salocin.gl.util.engine;

import de.salocin.gl.impl.display.DisplayImpl;

public class Check {
	
	public static void init(boolean b) {
		if (b) {
			throw new RuntimeException("Class already initialized.");
		}
	}
	
	public static void display() {
		if (!DisplayImpl.instance.isInitialized()) {
			throw new RuntimeException("Display not initialized.");
		}
	}
	
}
