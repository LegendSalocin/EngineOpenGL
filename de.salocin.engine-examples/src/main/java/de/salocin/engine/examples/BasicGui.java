package de.salocin.engine.examples;

import de.salocin.engine.Engine;
import de.salocin.engine.display.Display;
import de.salocin.engine.geom.Insets;
import de.salocin.engine.gui.GuiPlugin;
import de.salocin.engine.gui.GuiRenderState;
import de.salocin.engine.gui.layout.AbsoluteConstraint;
import de.salocin.engine.gui.layout.AbsoluteLayout;
import de.salocin.engine.gui.widget.Pane;
import de.salocin.engine.gui.widget.Spinner;
import de.salocin.engine.plugin.SimpleCorePlugin;
import de.salocin.engine.utils.core.Color;
import de.salocin.engine.utils.font.Font;

public class BasicGui extends SimpleCorePlugin {
	
	public static void main(String[] args) {
		Engine.simpleStart(new BasicGui());
	}
	
	@Override
	protected void onEnable() {
		setState(new TestGui());
		
		Display.enableVsync(false);
		
		GuiPlugin.setDebugInfoEnabled(true);
	}
	
	public class TestGui extends GuiRenderState {
		
		@Override
		protected void onInit() {
			Pane root = new Pane(AbsoluteLayout.getInstance());
			
			Spinner<Long> test = new Spinner<Long>(-1, 1, Long.MAX_VALUE);
			test.setPadding(new Insets(0.05f));
			test.setBackgroundColor(Color.WHITE);
			test.setTextFont(Font.newBuilder("Arial").setFontSize(100).build());
			test.getSpinnerModel().propertyValue().addValueChangeCallback(e -> {
				test.setCursorBlinkRate(e.getNewValue());
			});
			
			root.add(test, new AbsoluteConstraint(0.5f, 0.5f));
			setRoot(root);
		}
		
		@Override
		protected void onUpdate(long currentTime, long delta) {
		}
		
		@Override
		protected void onRender() {
			// Color.WHITE.bind();
			// Renderer.renderQuad(0, 0, 1, 1);
		}
		
		@Override
		protected void onExit() {
		}
		
	}
	
}
