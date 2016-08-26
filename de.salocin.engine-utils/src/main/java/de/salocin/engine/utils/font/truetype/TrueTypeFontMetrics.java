package de.salocin.engine.utils.font.truetype;

import static org.lwjgl.stb.STBTruetype.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import de.salocin.engine.util.Viewport;
import de.salocin.engine.utils.font.FontMetrics;

/**
 * Not part of the official API
 */
public class TrueTypeFontMetrics implements FontMetrics, Cloneable {
	
	private final TrueTypeFont font;
	private final int size;
	private int ascent;
	private int descent;
	private int lineGap;
	
	protected TrueTypeFontMetrics(TrueTypeFont font, int size) {
		this.font = font;
		this.size = size;
	}
	
	protected void init(TrueTypeFontRenderer renderer, float scale) {
		IntBuffer ascent = BufferUtils.createIntBuffer(1);
		IntBuffer descent = BufferUtils.createIntBuffer(1);
		IntBuffer lineGap = BufferUtils.createIntBuffer(1);
		
		stbtt_GetFontVMetrics(renderer.fontInfo, ascent, descent, lineGap);
		
		this.ascent = (int) (ascent.get(0) * scale);
		this.descent = (int) (descent.get(0) * scale);
		this.lineGap = (int) (lineGap.get(0) * scale);
	}
	
	@Override
	public float getAscent() {
		return Viewport.scaledHeight(ascent);
	}
	
	@Override
	public float getDescent() {
		return Viewport.scaledHeight(descent);
	}
	
	@Override
	public float getLineGap() {
		return Viewport.scaledHeight(lineGap);
	}
	
	@Override
	public int getSize() {
		return size;
	}
	
	@Override
	public float getLineHeight() {
		return Viewport.scaledHeight(size);
	}
	
	@Override
	public float getWidth(char... chars) {
		return getWidth(new String(chars));
	}
	
	@Override
	public float getWidth(CharSequence text) {
		return font.renderer.getTextWidth(text.toString(), 0, 0, null, false);
	}
	
}
