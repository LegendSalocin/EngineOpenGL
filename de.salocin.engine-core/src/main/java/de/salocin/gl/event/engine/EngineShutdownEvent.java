package de.salocin.gl.event.engine;

import de.salocin.gl.util.engine.ExitCode;

public class EngineShutdownEvent extends EngineEvent {
	
	public static enum State {
		REQUEST, PRE_SHUTDOWN;
	}
	
	private final State state;
	private ExitCode exitCode;
	
	public EngineShutdownEvent(State state, ExitCode exitCode) {
		this.state = state;
		this.exitCode = exitCode;
	}
	
	public State getState() {
		return state;
	}
	
	public ExitCode getExitCode() {
		return exitCode;
	}
	
	public void setExitCode(ExitCode exitCode) {
		this.exitCode = exitCode;
	}
	
	@Override
	public boolean isCancelable() {
		return true;
	}
	
}
