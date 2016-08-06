package de.salocin.gl.util.font;

import java.awt.Font;

import de.salocin.gl.util.Color;

public class TrueTypeFont implements de.salocin.gl.util.font.Font {
	
	protected TrueTypeFontMetrics metrics;
	protected TrueTypeFontRenderer renderer;
	private boolean antiAlias;
	private FontStyle[] styles;
	
	/**
	 * Only used for {@link #clone()}
	 */
	private TrueTypeFont() {
	}
	
	protected TrueTypeFont(Font f, int baseFontSize, boolean antiAlias, char[] chars) {
		metrics = new TrueTypeFontMetrics(this, baseFontSize);
		renderer = new TrueTypeFontRenderer(this, f, chars);
		
		/* Init settings */
		setSize(baseFontSize);
		setStyle();
	}
	
	@Override
	public de.salocin.gl.util.font.FontMetrics getMetrics() {
		return metrics;
	}
	
	@Override
	public boolean isAntiAliasEnabled() {
		return antiAlias;
	}
	
	@Override
	public void setStyle(FontStyle... styles) {
		renderer.updateStyle(styles);
		this.styles = styles.length == 0 ? new FontStyle[] { FontStyle.PLAIN } : styles;
	}
	
	@Override
	public FontStyle[] getStyle() {
		return styles;
	}
	
	@Override
	public void setSize(int sizeInPixel) {
		metrics.setSize(sizeInPixel);
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
		renderer.renderText(text, x, y, color);
	}
	
	@Override
	public TrueTypeFont clone() {
		TrueTypeFont font = new TrueTypeFont();
		font.metrics = metrics.clone();
		font.renderer = renderer.clone();
		font.antiAlias = antiAlias;
		font.styles = styles;
		return font;
	}
	
}
