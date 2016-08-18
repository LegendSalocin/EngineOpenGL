package de.salocin.gl.impl.display;

import static org.lwjgl.opengl.GL11.*;

import java.io.InputStream;

import de.salocin.gl.display.Color;
import de.salocin.gl.display.font.Font;
import de.salocin.gl.display.font.FontBuilder;
import de.salocin.gl.display.font.FontMetrics;
import de.salocin.gl.display.font.FontStyle;

/**
 * Not part of the official API
 */
public class TrueTypeFont implements Font {
	
	protected final FontBuilder builtFrom;
	protected final TrueTypeFontMetrics metrics;
	protected final TrueTypeFontRenderer renderer;
	protected final InputStream ttf;
	protected final FontStyle fontStyle;
	protected final char[] customChars;
	private boolean underline;
	private boolean strikethrough;
	private boolean overline;
	
	public TrueTypeFont(FontBuilder builtFrom, InputStream ttf, FontStyle fontStyle, int size, char[] customChars) {
		if (size > 300) {
			DisplayImpl.logger.warning("Font Size is greater than 300: Very big font bitmap! Be carful with these values.");
		}
		
		this.builtFrom = builtFrom;
		this.ttf = ttf;
		this.customChars = customChars;
		this.fontStyle = fontStyle;
		this.metrics = new TrueTypeFontMetrics(this, size);
		this.renderer = new TrueTypeFontRenderer(this, buildChars(customChars));
	}
	
	private char[] buildChars(char[] customChars) {
		char[] all = new char[256 + customChars.length];
		
		for (int i = 0; i < all.length; i++) {
			if (i < 256) {
				all[i] = (char) i;
			} else {
				all[i] = customChars[i - 256];
			}
		}
		
		return all;
	}
	
	@Override
	public FontBuilder getFontBuilder() {
		return builtFrom;
	}
	
	@Override
	public FontMetrics getMetrics() {
		return metrics;
	}
	
	@Override
	public FontStyle getStyle() {
		return fontStyle;
	}
	
	@Override
	public int getSize() {
		return metrics.getSize();
	}
	
	@Override
	public void setUnderline(boolean underline) {
		this.underline = underline;
	}
	
	@Override
	public boolean isUnderlined() {
		return underline;
	}
	
	@Override
	public void setStrikethrough(boolean strikethrough) {
		this.strikethrough = strikethrough;
	}
	
	@Override
	public boolean isStrikethrough() {
		return strikethrough;
	}
	
	@Override
	public void setOverline(boolean overline) {
		this.overline = overline;
	}
	
	@Override
	public boolean isOverlined() {
		return overline;
	}
	
	@Override
	public void renderText(CharSequence text, float x, float y) {
		renderText(text, x, y, Color.white);
	}
	
	@Override
	public void renderText(CharSequence text, float x, float y, Color color) {
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		float width = renderer.renderText(text.toString(), x, y, color);
		
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		
		if (underline && width > 0.0f) {
			renderLine(x, y - metrics.getDescent(), width + width * 0.005f);
		}
		
		if (strikethrough && width > 0.0f) {
			renderLine(x, y - metrics.getAscent() * 0.3f, width + width * 0.005f);
		}
		
		if (overline && width > 0.0f) {
			renderLine(x, y - metrics.getAscent(), width + width * 0.005f);
		}
	}
	
	private void renderLine(float x, float y, float width) {
		glLineWidth(2f);
		
		glBegin(GL_LINES);
		{
			glVertex2f(x, y);
			glVertex2f(x + width, y);
		}
		glEnd();
	}
	
}
