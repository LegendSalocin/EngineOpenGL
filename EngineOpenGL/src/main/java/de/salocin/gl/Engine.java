package de.salocin.gl;

import java.util.logging.Logger;

import org.apache.commons.lang3.Validate;

import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.engine.EngineShutdownEvent;
import de.salocin.gl.log.Log;
import de.salocin.gl.plugin.CorePlugin;
import de.salocin.gl.plugin.PluginManager;
import de.salocin.gl.scheduler.Scheduler;
import de.salocin.gl.util.engine.ExitCode;
import de.salocin.gl.util.exception.EngineException;

/**
 * The Engine class is the basic class of the Engine. You have to start the
 * engine with the class and you can stop it here.<br>
 * The class only contains static methods, you don't have to create an instance.
 * 
 * @see #simpleStart(CorePlugin)
 * @see #start(CorePlugin)
 * @see #isStarted()
 * @see #stop(ExitCode)
 */
public class Engine {
	
	public static final Logger ENGINE_LOGGER = Log.ENGINE_LOGGER;
	private static boolean started = false;
	
	/**
	 * An alias for {@link #start(CorePlugin)}. This version automatically stops
	 * the Engine if a fatal error on startup occurs and logs the exception.<br>
	 * You don't have to catch the thrown exception by your own with this
	 * method.
	 */
	public static void simpleStart(CorePlugin corePlugin) {
		try {
			start(corePlugin);
		} catch (EngineException e) {
			e.log();
			ExitCode.ENGINE_START_ERROR.shutdownEngine();
		}
	}
	
	/**
	 * Starts the engine with the given {@link CorePlugin}.
	 * 
	 * @throws EngineException
	 *             if an fatal error on startup occurs
	 */
	public static void start(CorePlugin corePlugin) throws EngineException {
		if (isStarted()) {
			throw new RuntimeException("Engine already started.");
		}
		
		Validate.notNull(corePlugin);
		
		if (corePlugin.getName() == null) {
			throw new RuntimeException("CorePlugin name can't be null.");
		}
		
		started = true;
		
		try {
			Log.init();
			EventManager.init();
			Scheduler.init();
			PluginManager.init(corePlugin);
			Scheduler.getInstance().getGameLoop().run();
		} catch (Throwable t) {
			started = false;
			throw new EngineException("Fatal error: Could not start the engine!", t);
		}
	}
	
	public static void stop(ExitCode exitCode) {
		EngineShutdownEvent requestEvent = new EngineShutdownEvent(EngineShutdownEvent.State.REQUEST, exitCode);
		if (EventManager.getInstance().callEvent(requestEvent)) {
			return;
		}
		
		// TODO
		// Scheduler.THREAD_GAME_LOOP.requestClose();
		
		EngineShutdownEvent preShutdownEvent = new EngineShutdownEvent(EngineShutdownEvent.State.PRE_SHUTDOWN, requestEvent.getExitCode());
		if (!EventManager.getInstance().callEvent(preShutdownEvent)) {
			exitCode = preShutdownEvent.getExitCode();
			System.exit(exitCode.getCode());
		}
	}
	
	public static boolean isStarted() {
		return started;
	}
	
}
