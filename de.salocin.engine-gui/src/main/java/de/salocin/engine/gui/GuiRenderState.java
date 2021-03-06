package de.salocin.engine.gui;

import de.salocin.engine.display.RenderState;
import de.salocin.engine.gui.widget.Pane;
import de.salocin.engine.util.Viewport;

public abstract class GuiRenderState implements RenderState {
	
	protected static GuiRenderState current;
	private Pane rootPane;
	
	public static GuiRenderState getCurrentState() {
		return current;
	}
	
	public void setRoot(Pane rootPane) {
		this.rootPane = rootPane;
		rootPane.setMinSize(Viewport.getOrthoSize().getWidth(), Viewport.getOrthoSize().getHeight());
		rootPane.layout();
	}
	
	public Pane getRootPane() {
		return rootPane;
	}
	
	public void init() {
		onInit();
	}
	
	protected abstract void onInit();
	
	public void update(long currentTime, long delta) {
		onUpdate(currentTime, delta);
	}
	
	protected abstract void onUpdate(long currentTime, long delta);
	
	@Override
	public void render() {
		onRender();
		
		if (rootPane != null) {
			rootPane.render();
		}
		
		if (GuiPlugin.getDebugInfoRender().isEnabled()) {
			GuiPlugin.getDebugInfoRender().render();
		}
	}
	
	protected abstract void onRender();
	
	public void exit() {
		onExit();
	}
	
	protected abstract void onExit();
	
}
