package de.salocin.gl.util.font;

import static org.lwjgl.stb.STBTruetype.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

/**
 * Not part of the official API
 */
public class TrueTypeFontMetrics implements FontMetrics, Cloneable {
	
	private final TrueTypeFont font;
	private final int size;
	private float ascent;
	private float descent;
	private float lineGap;
	
	protected TrueTypeFontMetrics(TrueTypeFont font, int size) {
		this.font = font;
		this.size = size;
	}
	
	protected void init(TrueTypeFontRenderer renderer, float scale) {
		IntBuffer ascent = BufferUtils.createIntBuffer(1);
		IntBuffer descent = BufferUtils.createIntBuffer(1);
		IntBuffer lineGap = BufferUtils.createIntBuffer(1);
		
		stbtt_GetFontVMetrics(renderer.fontInfo, ascent, descent, lineGap);
		
		this.ascent = ascent.get(0) * scale;
		this.descent = descent.get(0) * scale;
		this.lineGap = lineGap.get(0) * scale;
	}
	
	@Override
	public float getAscent() {
		return ascent;
	}
	
	@Override
	public float getDescent() {
		return descent;
	}
	
	@Override
	public float getLineGap() {
		return lineGap;
	}
	
	@Override
	public int getSize() {
		return size;
	}
	
	@Override
	public float getLineHeight() {
		return size;
	}
	
	@Override
	public float getWidth(char... chars) {
		return getWidth(new String(chars));
	}
	
	@Override
	public float getWidth(CharSequence text) {
		return font.renderer.getTextWidth(text.toString(), 0, 0, false);
	}
	
}
