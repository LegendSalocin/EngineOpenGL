package de.salocin.engine.display.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import de.salocin.engine.display.Display;
import de.salocin.engine.event.Callback;
import de.salocin.engine.event.CallbackHandler;
import de.salocin.engine.event.MouseButtonEvent;
import de.salocin.engine.event.MouseMoveEvent;
import de.salocin.engine.util.Viewport;
import de.salocin.engine.util.math.Point;

public class Mouse {
	
	/**
	 * Returns the Mouse for the default window
	 * 
	 * @return
	 */
	public static Mouse getMouse() {
		return Display.getMouse();
	}
	
	private CallbackHandler<MouseButtonEvent> mouseButtonCallback = new CallbackHandler<MouseButtonEvent>();
	private CallbackHandler<MouseMoveEvent> mouseMoveCallback = new CallbackHandler<MouseMoveEvent>();
	private Point mousePos;
	
	public Mouse(long window) {
		GLFW.glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallbackI() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				mouseButtonCallback.call(new MouseButtonEvent(MouseButton.fromOrdinal(button), Action.fromOrdinal(action), Modifier.getModifiers(mods)));
			}
		});
		
		GLFW.glfwSetCursorPosCallback(window, new GLFWCursorPosCallbackI() {
			@Override
			public void invoke(long window, double xpos, double ypos) {
				final Point newPos = new Point(Viewport.scaledWidth((int) xpos), Viewport.scaledHeight((int) ypos));
				final MouseMoveEvent e = new MouseMoveEvent(mousePos, newPos);
				mouseMoveCallback.call(e);
				mousePos = e.getNewPos();
			}
		});
	}
	
	public void addMouseButtonCallback(Callback<MouseButtonEvent> callback) {
		mouseButtonCallback.add(callback);
	}
	
	public void addMouseMoveCallback(Callback<MouseMoveEvent> callback) {
		mouseMoveCallback.add(callback);
	}
	
}
