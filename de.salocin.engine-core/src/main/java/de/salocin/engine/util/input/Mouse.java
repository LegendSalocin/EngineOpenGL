package de.salocin.engine.util.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import de.salocin.engine.display.Display;
import de.salocin.engine.event.EventManager;
import de.salocin.engine.event.input.MouseButtonEvent;
import de.salocin.engine.event.input.MouseMovedEvent;
import de.salocin.engine.util.Viewport;
import de.salocin.engine.util.engine.Check;
import de.salocin.engine.util.math.Point;

public class Mouse {
	
	private static boolean initialized = false;
	private static Point mousePos = null;
	
	public static void init() {
		Check.init(initialized);
		initialized = true;
		
		GLFW.glfwSetMouseButtonCallback(Display.getWindowHandle(), new GLFWMouseButtonCallbackI() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				EventManager.getInstance().callEvent(new MouseButtonEvent(MouseButton.fromOrdinal(button), Action.fromOrdinal(action), Modifier.getModifiers(mods)));
			}
		});
		
		GLFW.glfwSetCursorPosCallback(Display.getWindowHandle(), new GLFWCursorPosCallbackI() {
			@Override
			public void invoke(long window, double xpos, double ypos) {
				Point newPos = new Point(Viewport.scaledWidth((int) xpos), Viewport.scaledHeight((int) ypos));
				EventManager.getInstance().callEvent(new MouseMovedEvent(mousePos, newPos));
				mousePos = newPos;
			}
		});
	}
	
	public static Point getMousePos() {
		return mousePos == null ? new Point() : mousePos;
	}
	
}
