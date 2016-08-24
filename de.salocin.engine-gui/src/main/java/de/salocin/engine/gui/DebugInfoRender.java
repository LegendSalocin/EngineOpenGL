package de.salocin.engine.gui;

import org.apache.commons.lang3.Validate;

import de.salocin.engine.display.Display;
import de.salocin.engine.display.input.Mouse;
import de.salocin.engine.scheduler.FPS;
import de.salocin.engine.scheduler.TimeTracker;
import de.salocin.engine.scheduler.TimeTracker.Mode;
import de.salocin.engine.utils.core.Color;
import de.salocin.engine.utils.font.Font;

public class DebugInfoRender {
	
	private boolean enabled;
	private Color color;
	private Font font;
	private float defaultXOffset;
	private float defaultYOffset;
	private float yOffset;
	
	public DebugInfoRender() {
		setColor(Color.WHITE);
		setFont(GuiPlugin.getDefaultFont());
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
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
		renderLine("V-Sync: " + Display.isVsyncEnabled());
		renderLine("Mouse: [%.2f|%.2f]", Mouse.getMouse().getMouseX(), Mouse.getMouse().getMouseY());
	}
	
	private void renderLine(String lineFormat, Object... args) {
		yOffset += font.getMetrics().getLineHeight();
		font.renderText(String.format(lineFormat, args), defaultXOffset, yOffset);
	}
	
}
