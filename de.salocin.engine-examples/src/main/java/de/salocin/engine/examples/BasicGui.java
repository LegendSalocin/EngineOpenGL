package de.salocin.engine.examples;

import de.salocin.engine.Engine;
import de.salocin.engine.gui.GuiPlugin;
import de.salocin.engine.gui.GuiRenderState;
import de.salocin.engine.gui.widget.Button;
import de.salocin.engine.gui.widget.Pane;
import de.salocin.engine.plugin.SimpleCorePlugin;

public class BasicGui extends SimpleCorePlugin {
	
	public static void main(String[] args) {
		Engine.simpleStart(new BasicGui());
	}
	
	@Override
	protected void onEnable() {
		setState(new TestGui());
		
		GuiPlugin.setDebugInfoEnabled(true);
	}
	
	public class TestGui extends GuiRenderState {
		
		@Override
		protected void onInit() {
			Pane root = new Pane();
			Button test = new Button("Test");
			
			root.add(test);
			setRoot(root);
			
			System.out.println("root: " + root.getPosX() + " " + root.getPosY() + " " + root.getWidth() + " " + root.getHeight());
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
