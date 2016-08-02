package de.salocin.gl.util.render;

import org.apache.commons.lang3.Validate;

import de.salocin.gl.scheduler.FPS;
import de.salocin.gl.util.Color;
import de.salocin.gl.util.TrueTypeFont;
import de.salocin.gl.util.input.Mouse;

public class DebugInfoRender {
	
	private Color color;
	private TrueTypeFont font;
	private float defaultXOffset;
	private float defaultYOffset;
	private float yOffset;
	
	public DebugInfoRender() {
		setColor(Color.white);
		setFont(TrueTypeFontDefaults.ENGINE_FONT_SMALL);
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public TrueTypeFont getFont() {
		return font;
	}
	
	public void setFont(TrueTypeFont font) {
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
		renderLine("Mouse: [%.2f|%.2f]", Mouse.getMousePos().getX(), Mouse.getMousePos().getY());
	}
	
	private void renderLine(String lineFormat, Object... args) {
		font.renderText(String.format(lineFormat, args), defaultXOffset, yOffset);
		yOffset += font.getLineHeight();
	}
	
}
