package de.salocin.gl.util.font;

import java.io.InputStream;

import de.salocin.gl.util.Color;

/**
 * Not part of the official API
 */
public class TrueTypeFont implements Font {
	
	protected final TrueTypeFontMetrics metrics;
	protected final TrueTypeFontRenderer renderer;
	protected final InputStream ttf;
	protected char[] customChars;
	private FontStyle[] styles;
	
	protected TrueTypeFont(InputStream ttf, int size, char[] customChars) {
		this.ttf = ttf;
		this.customChars = customChars;
		this.metrics = new TrueTypeFontMetrics(this, size);
		this.renderer = new TrueTypeFontRenderer(this, size, buildChars(customChars));
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
	public FontMetrics getMetrics() {
		return metrics;
	}
	
	@Override
	public void setStyle(FontStyle... styles) {
		// TODO
	}
	
	@Override
	public FontStyle[] getStyle() {
		return styles;
	}
	
	@Override
	public Font setSize(int sizeInPixel) {
		return copy(ttf, sizeInPixel, renderer.usedChars);
	}
	
	@Override
	public int getSize() {
		return metrics.getSize();
	}
	
	@Override
	public void renderText(CharSequence text, float x, float y) {
		renderText(text, x, y, Color.white);
	}
	
	@Override
	public void renderText(CharSequence text, float x, float y, Color color) {
		renderer.renderText(text.toString(), x, y, color);
	}
	
	@Override
	public Font copy() {
		return copy(ttf, metrics.getSize(), renderer.usedChars);
	}
	
	private Font copy(InputStream newTtf, int newSize, char[] newChars) {
		TrueTypeFont font = new TrueTypeFont(newTtf, newSize, newChars);
		font.styles = styles;
		return font;
	}
	
}
