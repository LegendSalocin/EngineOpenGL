package de.salocin.gl.util.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.input.KeyPressedEvent;
import de.salocin.gl.render.Display;
import de.salocin.gl.util.Check;

public class Keyboard {
	
	private static boolean initialized = false;
	
	public static void init() {
		Check.init(initialized);
		initialized = true;
		
		GLFW.glfwSetKeyCallback(Display.getInstance().getWindow(), new GLFWKeyCallbackI() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				EventManager.getInstance().callEvent(new KeyPressedEvent(Key.fromId(key), Action.fromOrdinal(action), Modifier.getModifiers(mods)));
			}
		});
	}
}
