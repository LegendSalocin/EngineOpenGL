package de.salocin.gl.util;

public class Check {
	
	public static void init(boolean b) {
		if (b) {
			throw new RuntimeException("Class already initialized.");
		}
	}
	
}
