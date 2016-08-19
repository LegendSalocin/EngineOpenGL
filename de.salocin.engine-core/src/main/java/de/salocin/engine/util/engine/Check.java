package de.salocin.engine.util.engine;

import de.salocin.engine.display.Display;

public class Check {
	
	public static void init(boolean b) {
		if (b) {
			throw new RuntimeException("Class already initialized.");
		}
	}
	
	public static void display() {
		if (!Display.isInitialized()) {
			throw new RuntimeException("Display not initialized.");
		}
	}
	
}
