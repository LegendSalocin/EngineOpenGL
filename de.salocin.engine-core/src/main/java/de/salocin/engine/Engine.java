package de.salocin.engine;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.lang3.Validate;

import de.salocin.engine.plugin.CorePlugin;
import de.salocin.engine.plugin.PluginManager;
import de.salocin.engine.scheduler.Scheduler;
import de.salocin.engine.util.engine.ExitCode;
import de.salocin.engine.util.exception.EngineException;

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
	
	public static final String REQUIRED_JRE = "1.8";
	private static String JRE_VERSION;
	private static String ENGINE_VERSION;
	
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
		JRE_VERSION = System.getProperty("java.version");
		ENGINE_VERSION = "1.0.0";
		
		if (JRE_VERSION.compareTo(REQUIRED_JRE) == -1) {
			throw new RuntimeException("You need at least Java v" + REQUIRED_JRE + " to run the engine.");
		}
		
		if (isStarted()) {
			throw new RuntimeException("Engine already started.");
		}
		
		if (Validate.notNull(corePlugin).getName() == null) {
			throw new RuntimeException("CorePlugin name can't be null.");
		}
		
		started = true;
		
		try {
			File natives = new File("native");
			
			if (!natives.exists()) {
				throw new FileNotFoundException("'native' folder could not be found.");
			}
			
			System.setProperty("org.lwjgl.librarypath", natives.getAbsolutePath());
			
			Scheduler.init();
			PluginManager.init(corePlugin);
			Scheduler.getInstance().getGameLoop().run();
		} catch (Throwable t) {
			started = false;
			throw new EngineException("Fatal error: Could not start the engine!", t);
		}
	}
	
	public static void stop(ExitCode exitCode) {
		// TODO
		// Scheduler.THREAD_GAME_LOOP.requestClose();
	}
	
	public static boolean isStarted() {
		return started;
	}
	
	public static String getJavaVersion() {
		return JRE_VERSION;
	}
	
	public static String getEngineVersion() {
		return ENGINE_VERSION;
	}
	
}
