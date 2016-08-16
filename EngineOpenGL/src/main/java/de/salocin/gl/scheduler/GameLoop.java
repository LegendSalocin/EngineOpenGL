package de.salocin.gl.scheduler;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import de.salocin.gl.display.Display;
import de.salocin.gl.display.Viewport;
import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.engine.EngineGLContextCreatedEvent;
import de.salocin.gl.gui.RenderState;
import de.salocin.gl.impl.display.DisplayImpl;
import de.salocin.gl.scheduler.TimeTracker.Mode;
import de.salocin.gl.util.exception.EngineException;

public class GameLoop implements Runnable {
	
	private long window;
	
	protected GameLoop() {
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
			glfwFreeCallbacks(window);
			glfwDestroyWindow(window);
			glfwTerminate();
			glfwSetErrorCallback(null).free();
		}
	}
	
	private void init() {
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		glfwDefaultWindowHints();
		window = glfwCreateWindow(Viewport.DEFAULT_VIEWPORT_WIDTH, Viewport.DEFAULT_VIEWPORT_HEIGHT, "", NULL, NULL);
		
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
				glfwSetWindowShouldClose(window, true);
			}
		});
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - Viewport.DEFAULT_VIEWPORT_WIDTH) / 2, (vidmode.height() - Viewport.DEFAULT_VIEWPORT_HEIGHT) / 2);
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		
		GL.createCapabilities();
		
		DisplayImpl.instance.init();
		
		EventManager.getInstance().callEvent(new EngineGLContextCreatedEvent());
	}
	
	private void loop() {
		while (!glfwWindowShouldClose(window)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			TimeTracker.start(Mode.FPS_COUNTER);
			FPS.run();
			TimeTracker.end();
			
			glfwPollEvents();
			
			TimeTracker.start(Mode.RENDER_STATE);
			RenderState current = Display.getInstance().getRenderState();
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
