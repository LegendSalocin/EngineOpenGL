package de.salocin.engine.gui;

import de.salocin.engine.display.RenderState;
import de.salocin.engine.gui.component.pane.Pane;
import de.salocin.engine.gui.util.DebugInfoRender;

public abstract class GuiRenderState implements RenderState {
	
	protected static GuiRenderState current;
	private Pane<?> rootPane;
	private DebugInfoRender debugInfoRender;
	private boolean renderDebugInfo;
	
	public GuiRenderState() {
		this(true);
	}
	
	public GuiRenderState(boolean renderDebugInfo) {
		this.renderDebugInfo = renderDebugInfo;
	}
	
	public static GuiRenderState getCurrentState() {
		if (current == null) {
			throw new RuntimeException("Current RenderState is not instance of GuiRenderState");
		}
		
		return current;
	}
	
	public DebugInfoRender getDebugInfoRenderer() {
		return debugInfoRender;
	}
	
	public void setRoot(Pane<?> rootPane) {
		this.rootPane = rootPane;
	}
	
	public void init() {
		debugInfoRender = new DebugInfoRender();
		
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
		
		if (renderDebugInfo) {
			debugInfoRender.render();
		}
	}
	
	protected abstract void onRender();
	
	public void exit() {
		onExit();
	}
	
	protected abstract void onExit();
	
}
