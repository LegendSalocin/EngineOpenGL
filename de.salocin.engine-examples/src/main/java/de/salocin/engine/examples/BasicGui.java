package de.salocin.engine.examples;

import de.salocin.engine.Engine;
import de.salocin.engine.display.Display;
import de.salocin.engine.display.input.Action;
import de.salocin.engine.display.input.Key;
import de.salocin.engine.display.input.Keyboard;
import de.salocin.engine.gui.GuiRenderState;
import de.salocin.engine.gui.widget.BorderPane;
import de.salocin.engine.gui.widget.BorderPane.Location;
import de.salocin.engine.gui.widget.TextField;
import de.salocin.engine.plugin.SimpleCorePlugin;
import de.salocin.engine.scheduler.FPS;

public class BasicGui extends SimpleCorePlugin {
	
	public static void main(String[] args) {
		Engine.simpleStart(new BasicGui());
	}
	
	@Override
	protected void onEnable() {
		setState(new TestGui());
		
		Keyboard.getKeyboard().addKeyCallback(e -> {
			if (e.getAction() != Action.RELEASED) {
				if (e.getKey() == Key.KEY_NUMPAD_ADD) {
					FPS.setTargetFPS(FPS.getTargetFPS() + 1);
				} else if (e.getKey() == Key.KEY_NUMPAD_SUBTRACT) {
					if (FPS.getTargetFPS() != 1) {
						FPS.setTargetFPS(FPS.getTargetFPS() - 1);
					}
				}
			}
		});
	}
	
	public class TestGui extends GuiRenderState {
		
		@Override
		protected void onInit() {
			BorderPane pane = new BorderPane();
			
			TextField field = new TextField("TEST");
			pane.add(field, Location.TOP);
			
			setRoot(pane);
			
			Display.enableVsync(false);
		}
		
		@Override
		protected void onUpdate(long currentTime, long delta) {
		}
		
		@Override
		protected void onRender() {
		}
		
		@Override
		protected void onExit() {
		}
		
	}
	
}
