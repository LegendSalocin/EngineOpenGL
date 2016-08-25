package de.salocin.engine.examples;

import org.lwjgl.glfw.GLFW;

import de.salocin.engine.Engine;
import de.salocin.engine.display.Display;
import de.salocin.engine.display.RenderState;
import de.salocin.engine.display.input.Action;
import de.salocin.engine.display.input.Key;
import de.salocin.engine.display.input.Keyboard;
import de.salocin.engine.event.Callback;
import de.salocin.engine.event.KeyEvent;
import de.salocin.engine.gui.GuiPlugin;
import de.salocin.engine.model.Sphere2D;
import de.salocin.engine.plugin.SimpleCorePlugin;
import de.salocin.engine.util.math.Vector3f;
import de.salocin.engine.utils.core.Color;

public class Basic3D extends SimpleCorePlugin {
	
	public static void main(String[] args) {
		Engine.simpleStart(new Basic3D());
	}
	
	@Override
	protected void onEnable() {
		Display.setRenderState(new TestState());
	}
	
	private class TestState implements RenderState {
		
		private Sphere2D s;
		
		@Override
		public void init() {
			GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 4);
			
			s = new Sphere2D(new Vector3f(0.5f, 0.5f, 0), 0.1f);
			Keyboard.getKeyboard().addKeyCallback(new Callback<KeyEvent>() {
				@Override
				public void call(KeyEvent event) {
					if (event.getAction() != Action.RELEASED) {
						if (event.getKey() == Key.KEY_NUMPAD_SUBTRACT) {
							if (s.getPrecision() - 0.05f > 0.0f) {
								s.setPrecision(s.getPrecision() - 0.05f);
							}
						} else if (event.getKey() == Key.KEY_NUMPAD_ADD) {
							s.setPrecision(s.getPrecision() + 0.05f);
						}
					}
				}
			});
			
			s.setEndAngle(270.0f);
		}
		
		@Override
		public void update(long currentTime, long delta) {
		}
		
		@Override
		public void render() {
			Color.WHITE.bind();
			GuiPlugin.getDefaultFont().renderText(String.format("Precision: %.2f", s.getPrecision()), 0.1f, 0.1f);
			s.render();
		}
		
		@Override
		public void exit() {
		}
		
	}
	
}
