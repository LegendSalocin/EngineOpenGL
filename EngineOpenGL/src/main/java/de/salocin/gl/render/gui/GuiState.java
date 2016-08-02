package de.salocin.gl.render.gui;

import de.salocin.gl.render.RenderState;
import de.salocin.gl.util.render.DebugInfoRender;

public abstract class GuiState implements RenderState {
	
	private DebugInfoRender debugInfoRender;
	private boolean renderDebugInfo;
	
	public void renderDebugInfo(boolean renderDebugInfo) {
		this.renderDebugInfo = renderDebugInfo;
	}
	
	public DebugInfoRender getDebugInfoRender() {
		return debugInfoRender;
	}
	
	@Override
	public void init() {
		debugInfoRender = new DebugInfoRender();
		
		onInit();
	}
	
	protected abstract void onInit();
	
	@Override
	public void update(long currentTime, long delta) {
		onUpdate(currentTime, delta);
	}
	
	protected abstract void onUpdate(long currentTime, long delta);
	
	@Override
	public void render() {
		if (renderDebugInfo) {
			debugInfoRender.render();
		}
		
		onRender();
	}
	
	protected abstract void onRender();
	
	@Override
	public void exit() {
		onExit();
	}
	
	protected abstract void onExit();
	
}
