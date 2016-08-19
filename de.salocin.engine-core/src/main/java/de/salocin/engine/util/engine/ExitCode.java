package de.salocin.engine.util.engine;

import de.salocin.engine.Engine;

public class ExitCode {
	
	public static final ExitCode NORMAL_EXIT = new ExitCode(0);
	public static final ExitCode ENGINE_START_ERROR = new ExitCode(1);
	
	private final int exitCode;
	
	public ExitCode(int exitCode) {
		this.exitCode = exitCode;
	}
	
	public int getCode() {
		return exitCode;
	}
	
	public void shutdownEngine() {
		Engine.stop(this);
	}
	
}
