package de.salocin.gl.util.exception;

public class EngineException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public EngineException() {
		super();
	}
	
	public EngineException(Throwable cause) {
		super(cause);
	}
	
	public EngineException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public void log() {
		printStackTrace();
	}
	
}
