package de.salocin.engine.examples;

import de.salocin.engine.Engine;
import de.salocin.engine.display.Display;
import de.salocin.engine.display.input.Action;
import de.salocin.engine.display.input.Key;
import de.salocin.engine.display.input.Keyboard;
import de.salocin.engine.gui.GuiPlugin;
import de.salocin.engine.gui.GuiRenderState;
import de.salocin.engine.plugin.SimpleCorePlugin;
import de.salocin.engine.scheduler.FPS;
import de.salocin.engine.utils.font.Font;

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
		
		Font f;
		
		@Override
		protected void onInit() {
			// AbsolutePane pane = new AbsolutePane();
			//
			// TextField text = new TextField("TEXT");
			// text.setTextFont(Font.newBuilder("Consolas").setFontSize(50).build());
			//
			// pane.add(text);
			// setRoot(pane);
			
			f = Font.newBuilder("Arial").setFontSize(50).build();
			
			Display.enableVsync(false);
			FPS.setTargetFPS(1);
			GuiPlugin.setDebugInfoEnabled(true);
		}
		
		@Override
		protected void onUpdate(long currentTime, long delta) {
			f.renderText("Target: " + FPS.getTargetFPS(), 0.5f, 0.5f);
		}
		
		@Override
		protected void onRender() {
		}
		
		@Override
		protected void onExit() {
		}
		
	}
	
}
