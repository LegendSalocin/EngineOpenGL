package de.salocin.gl.util.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import de.salocin.gl.display.Display;
import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.input.KeyPressedEvent;
import de.salocin.gl.util.engine.Check;

public class Keyboard {
	
	private static boolean initialized = false;
	private static char lastPressedChar;
	
	public static void init() {
		Check.init(initialized);
		initialized = true;
		
		GLFW.glfwSetCharCallback(Display.getInstance().getWindowHandle(), new GLFWCharCallbackI() {
			@Override
			public void invoke(long window, int codepoint) {
				lastPressedChar = (char) codepoint;
			}
		});
		
		GLFW.glfwSetKeyCallback(Display.getInstance().getWindowHandle(), new GLFWKeyCallbackI() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				EventManager.getInstance().callEvent(new KeyPressedEvent(Key.fromId(key), Action.fromOrdinal(action), Modifier.getModifiers(mods), lastPressedChar));
				lastPressedChar = (char) 0;
			}
		});
	}
}
