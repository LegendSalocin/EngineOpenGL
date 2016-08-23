package de.salocin.engine.scheduler;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import de.salocin.engine.Engine;
import de.salocin.engine.display.Display;
import de.salocin.engine.display.RenderState;
import de.salocin.engine.scheduler.TimeTracker.Mode;
import de.salocin.engine.util.engine.ExitCode;
import de.salocin.engine.util.exception.EngineException;

public class GameLoop implements Runnable {
	
	private long window;
	private boolean closed = false;
	
	protected GameLoop() {
	}
	
	public void requestClose() {
		if (!closed) {
			glfwSetWindowShouldClose(window, true);
		}
	}
	
	public long getWindow() {
		return window;
	}
	
	@Override
	public void run() {
		try {
			init();
			loop();
		} catch (Exception e) {
			new EngineException(e).log();
		} finally {
			closed = true;
			
			if (Engine.isRunning()) {
				Engine.stop(ExitCode.NORMAL_EXIT);
			}
			
			if (window != 0) {
				glfwFreeCallbacks(window);
				glfwDestroyWindow(window);
				glfwTerminate();
				glfwSetErrorCallback(null).free();
			}
		}
	}
	
	private void init() {
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		glfwDefaultWindowHints();
		window = glfwCreateWindow(Display.DEFAULT_VIEWPORT_WIDTH, Display.DEFAULT_VIEWPORT_HEIGHT, "", NULL, NULL);
		
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
				glfwSetWindowShouldClose(window, true);
			}
		});
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - Display.DEFAULT_VIEWPORT_WIDTH) / 2, (vidmode.height() - Display.DEFAULT_VIEWPORT_HEIGHT) / 2);
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		
		GL.createCapabilities();
		
		Display.init();
	}
	
	private void loop() {
		while (!glfwWindowShouldClose(window)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			TimeTracker.start(Mode.FPS_COUNTER);
			FPS.run();
			TimeTracker.end();
			
			glfwPollEvents();
			
			TimeTracker.start(Mode.RENDER_STATE);
			RenderState current = Display.getRenderState();
			if (current != null) {
				current.update(FPS.getCurrentTime(), FPS.getDelta());
				current.render();
			}
			TimeTracker.end();
			
			TimeTracker.start(Mode.LOOP_SYNCHRONIZER);
			LoopSynchronizer.run(FPS.getCurrentTime());
			TimeTracker.end();
			
			TimeTracker.start(Mode.V_SYNC);
			glfwSwapBuffers(window);
			TimeTracker.end();
		}
	}
	
}
