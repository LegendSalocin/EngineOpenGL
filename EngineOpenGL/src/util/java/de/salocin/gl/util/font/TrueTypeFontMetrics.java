package de.salocin.gl.util.font;

public class TrueTypeFontMetrics implements FontMetrics, Cloneable {
	
	private final TrueTypeFont font;
	private final int baseSize;
	private int size;
	
	protected TrueTypeFontMetrics(TrueTypeFont font, int baseSize) {
		this.font = font;
		this.baseSize = baseSize;
	}
	
	@Override
	public int getBaseSize() {
		return baseSize;
	}
	
	@Override
	public void setSize(int sizeInPixel) {
		this.size = sizeInPixel;
	}
	
	@Override
	public int getSize() {
		return size;
	}
	
	@Override
	public float getBaseline() {
		// TODO
		return 0;
	}
	
	@Override
	public float getAscentOffset() {
		// TODO
		return 0;
	}
	
	@Override
	public float getDescentOffset() {
		// TODO
		return 0;
	}
	
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
	public float getHeight() {
		return 0;
	}
	
	@Override
	protected TrueTypeFontMetrics clone() {
		TrueTypeFontMetrics m = new TrueTypeFontMetrics(font, baseSize);
		m.size = size;
		return m;
	}
}
