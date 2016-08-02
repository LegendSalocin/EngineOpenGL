package de.salocin.gl.util.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.input.MouseButtonEvent;
import de.salocin.gl.event.input.MouseMovedEvent;
import de.salocin.gl.render.Display;
import de.salocin.gl.util.Check;
import de.salocin.gl.util.math.Point;

public class Mouse {
	
	private static boolean initialized = false;
	private static Point mousePos = null;
	
	public static void init() {
		Check.init(initialized);
		initialized = true;
		
		GLFW.glfwSetMouseButtonCallback(Display.getInstance().getWindow(), new GLFWMouseButtonCallbackI() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				EventManager.getInstance().callEvent(new MouseButtonEvent(MouseButton.fromOrdinal(button), Action.fromOrdinal(action), Modifier.getModifiers(mods)));
			}
		});
		
		GLFW.glfwSetCursorPosCallback(Display.getInstance().getWindow(), new GLFWCursorPosCallbackI() {
			
			@Override
			public void invoke(long window, double xpos, double ypos) {
				// TODO convert point to ortho
				// TODO Modifiers not working
				
				Point newPos = new Point((float) ypos, (float) ypos);
				EventManager.getInstance().callEvent(new MouseMovedEvent(mousePos, newPos));
				mousePos = newPos;
			}
		});
	}
	
	public static Point getMousePos() {
		return mousePos == null ? new Point() : mousePos;
	}
	
}
