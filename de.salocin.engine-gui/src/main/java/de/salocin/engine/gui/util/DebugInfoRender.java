package de.salocin.engine.gui.util;

import org.apache.commons.lang3.Validate;

import de.salocin.engine.utils.core.Color;
import de.salocin.engine.utils.font.Font;
import de.salocin.gl.display.Display;
import de.salocin.gl.scheduler.FPS;
import de.salocin.gl.scheduler.TimeTracker;
import de.salocin.gl.scheduler.TimeTracker.Mode;
import de.salocin.gl.util.input.Mouse;

// TODO rework this class
public class DebugInfoRender {
	
	private Color color;
	private Font font;
	private float defaultXOffset;
	private float defaultYOffset;
	private float yOffset;
	
	public DebugInfoRender() {
		setColor(Color.white);
		setFont(Display.getInstance().getDefaultEngineFont());
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Font getFont() {
		return font;
	}
	
	public void setFont(Font font) {
		Validate.notNull(font);
		this.font = font;
	}
	
	public void setDefaultOffset(float x, float y) {
		this.defaultXOffset = x;
		this.defaultYOffset = y;
	}
	
	public void render() {
		if (color != null) {
			color.bind();
		}
		
		yOffset = defaultYOffset;
		
		renderLine("FPS: %d", FPS.getFPS());
		renderLine("Threads: %d", Thread.activeCount());
		renderLine("GameLoop Delta: %d", FPS.getDelta());
		renderLine("    %s: %dms; %s: %dms; %s: %dms; %s: %dms", Mode.FPS_COUNTER, TimeTracker.getFpsCounterDelta(), Mode.RENDER_STATE, TimeTracker.getRenderStateDelta(),
				Mode.LOOP_SYNCHRONIZER, TimeTracker.getLoopSyncDelta(), Mode.V_SYNC, TimeTracker.getVSyncDelta());
		renderLine("V-Sync: " + Display.getInstance().isVsyncEnabled());
		renderLine("Mouse: [%.2f|%.2f]", Mouse.getMousePos().getX(), Mouse.getMousePos().getY());
	}
	
	private void renderLine(String lineFormat, Object... args) {
		yOffset += font.getMetrics().getLineHeight();
		font.renderText(String.format(lineFormat, args), defaultXOffset, yOffset);
	}
	
}