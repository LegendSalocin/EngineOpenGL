package de.salocin.gl;

import java.util.logging.Logger;

import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.engine.EngineShutdownEvent;
import de.salocin.gl.event.engine.EngineShutdownEvent.State;
import de.salocin.gl.log.Log;
import de.salocin.gl.plugin.CorePlugin;
import de.salocin.gl.plugin.PluginManager;
import de.salocin.gl.scheduler.Scheduler;
import de.salocin.gl.util.EngineException;
import de.salocin.gl.util.ExitCode;

public class Engine {
	
	public static final Logger ENGINE_LOGGER = Log.ENGINE_LOGGER;
	private static boolean started = false;
	
	/**
	 * Starts the engine with the given {@link CorePlugin}.
	 */
	public static void start(CorePlugin corePlugin) throws EngineException {
		if (isStarted()) {
			throw new RuntimeException("Engine already started.");
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
		EngineShutdownEvent requestEvent = new EngineShutdownEvent(State.REQUEST, exitCode);
		if (EventManager.getInstance().callEvent(requestEvent)) {
			return;
		}
		
		// TODO
		// Scheduler.THREAD_GAME_LOOP.requestClose();
		
		EngineShutdownEvent preShutdownEvent = new EngineShutdownEvent(State.PRE_SHUTDOWN, requestEvent.getExitCode());
		if (!EventManager.getInstance().callEvent(preShutdownEvent)) {
			exitCode = preShutdownEvent.getExitCode();
			System.exit(exitCode.getCode());
		}
	}
	
	public static boolean isStarted() {
		return started;
	}
	
}
