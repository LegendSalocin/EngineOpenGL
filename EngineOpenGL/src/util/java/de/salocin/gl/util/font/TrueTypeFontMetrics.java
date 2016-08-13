package de.salocin.gl.util.font;

import org.lwjgl.stb.STBTTAlignedQuad;

/**
 * Not part of the official API
 */
public class TrueTypeFontMetrics implements FontMetrics, Cloneable {
	
	protected STBTTAlignedQuad quad = STBTTAlignedQuad.malloc();
	private final TrueTypeFont font;
	private final int size;
	
	protected TrueTypeFontMetrics(TrueTypeFont font, int size) {
		this.font = font;
		this.size = size;
	}
	
	@Override
	public int getSize() {
		return size;
	}
	
	// TODO
	
	@Override
	public float getWidth(char... chars) {
		// TODO
		return 0;
	}
	
	@Override
	public float getWidth(CharSequence charSequence) {
		// TODO
		return 0;
	}
	
	@Override
	public float getLineHeight() {
		return size;
	}
}
