package de.salocin.engine.display.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import de.salocin.engine.display.Display;
import de.salocin.engine.event.Callback;
import de.salocin.engine.event.CallbackImpl;
import de.salocin.engine.event.KeyEvent;

public class Keyboard {
	
	/**
	 * Returns the Keyboard for the default window
	 * 
	 * @return
	 */
	public static Keyboard getKeyboard() {
		return Display.getKeyboard();
	}
	
	private final CallbackImpl<KeyEvent> keyCallback = new CallbackImpl<KeyEvent>();
	private char lastPressedChar;
	
	public Keyboard(long window) {
		GLFW.glfwSetCharCallback(window, new GLFWCharCallbackI() {
			@Override
			public void invoke(long window, int codepoint) {
				lastPressedChar = (char) codepoint;
			}
		});
		
		GLFW.glfwSetKeyCallback(window, new GLFWKeyCallbackI() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				keyCallback.call(new KeyEvent(Key.fromId(key), Action.fromOrdinal(action), Modifier.getModifiers(mods), lastPressedChar));
				
				lastPressedChar = (char) 0;
			}
		});
	}
	
	public void addKeyCallback(Callback<KeyEvent> callback) {
		keyCallback.add(callback);
	}
	
}
