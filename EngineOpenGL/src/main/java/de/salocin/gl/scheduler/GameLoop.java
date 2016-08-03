package de.salocin.gl.scheduler;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.engine.EngineCLContextCreatedEvent;
import de.salocin.gl.render.Display;
import de.salocin.gl.render.Resolution;
import de.salocin.gl.render.gui.RenderState;
import de.salocin.gl.util.exception.EngineException;
import de.salocin.gl.util.render.TrueTypeFontDefaults;

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
		window = glfwCreateWindow(Resolution.DEFAULT_WIDTH, Resolution.DEFAULT_HEIGHT, "", NULL, NULL);
		
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
				glfwSetWindowShouldClose(window, true);
			}
		});
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - Resolution.DEFAULT_WIDTH) / 2, (vidmode.height() - Resolution.DEFAULT_HEIGHT) / 2);
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		
		GL.createCapabilities();
		
		TrueTypeFontDefaults.init();
		Display.getInstance().init();
		
		EventManager.getInstance().callEvent(new EngineCLContextCreatedEvent());
	}
	
	private void loop() {
		while (!glfwWindowShouldClose(window)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			FPS.run();
			
			glfwPollEvents();
			
			RenderState current = Display.getInstance().getRenderState();
			if (current != null) {
				current.update(FPS.getCurrentTime(), FPS.getDelta());
				current.render();
			}
			
			LoopSync.run(FPS.getCurrentTime());
			
			glfwSwapBuffers(window);
		}
	}
	
}
