package de.salocin.gl.log;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import de.salocin.gl.Engine;

public class EngineLogger extends Logger {
	
	private static final LogManager manager = LogManager.getLogManager();
	
	protected EngineLogger(String name) {
		super(name, null);
		manager.addLogger(this);
		setParent(Engine.ENGINE_LOGGER);
	}
	
	public void log(Throwable thrown) {
		logThrown(this, thrown);
	}
	
	public static EngineLogger getEngineLogger(Class<?> theClass) {
		return getEngineLogger(theClass.getSimpleName());
	}
	
	public static EngineLogger getEngineLogger(String name) {
		return getEngineLogger(name, new LoggerCreator.EngineLoggerCreator());
	}
	
	public static <T extends EngineLogger> T getEngineLogger(Class<?> theClass, LoggerCreator<T> creator) {
		return getEngineLogger(theClass.getSimpleName(), creator);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends EngineLogger> T getEngineLogger(String name, LoggerCreator<T> creator) {
		if (manager.getLogger(name) != null) {
			try {
				return (T) manager.getLogger(name);
			} catch (Exception e) {
				throw new IllegalArgumentException("The requested logger is not an instance of PluginLogger", e);
			}
		} else {
			return creator.createLogger(name);
		}
	}
	
	public static void logThrown(Logger logger, Throwable thrown) {
		logger.log(Level.SEVERE, thrown.getMessage() != null ? thrown.getMessage() : null, thrown);
	}
	
	public interface LoggerCreator<T extends EngineLogger> {
		
		T createLogger(String name);
		
		public static class EngineLoggerCreator implements LoggerCreator<EngineLogger> {
			
			@Override
			public EngineLogger createLogger(String name) {
				return new EngineLogger(name);
			}
			
		}
		
	}
	
}
