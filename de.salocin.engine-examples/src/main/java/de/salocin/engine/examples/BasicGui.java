package de.salocin.engine.examples;

import de.salocin.engine.Engine;
import de.salocin.engine.gui.GuiRenderState;
import de.salocin.engine.gui.widget.AbsolutePane;
import de.salocin.engine.gui.widget.TextField;
import de.salocin.engine.plugin.SimpleCorePlugin;
import de.salocin.engine.utils.font.Font;

public class BasicGui extends SimpleCorePlugin {
	
	public static void main(String[] args) {
		Engine.simpleStart(new BasicGui());
	}
	
	@Override
	protected void onEnable() {
		setState(new TestGui());
	}
	
	public class TestGui extends GuiRenderState {
		
		@Override
		protected void onInit() {
			AbsolutePane pane = new AbsolutePane();
			
			TextField text = new TextField("TEXT");
			text.setTextFont(Font.newBuilder("Consolas").setFontSize(50).build());
			
			pane.add(text);
			setRoot(pane);
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
