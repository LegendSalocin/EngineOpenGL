package de.salocin.gl.util.font;

import java.awt.Font;
import java.awt.FontMetrics;
import java.nio.IntBuffer;

import de.salocin.gl.util.texture.Texture;

public class TrueTypeFontData implements Cloneable {
	
	protected FontStyle[] style;
	protected Font font;
	protected FontMetrics fontMetrics;
	protected IntBuffer charPos;
	protected Texture texture;
	
	protected TrueTypeFontData(FontStyle... styles) {
		this.style = styles;
	}
	
	protected void setFont(Font font) {
		this.font = font;
		this.fontMetrics = TrueTypeFontRenderer.GLOBAL_GRAPHICS.getFontMetrics(font);
	}
	
	protected boolean is(FontStyle... styles) {
		OUTER: for (FontStyle style : styles) {
			if (style.customFont) {
				for (FontStyle s : this.style) {
					if (style == s) {
						continue OUTER;
					}
				}
				
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	protected TrueTypeFontData clone() {
		TrueTypeFontData d = new TrueTypeFontData(style);
		d.font = font;
		d.fontMetrics = fontMetrics;
		d.charPos = charPos;
		d.texture = texture;
		return d;
	}
	
	@Override
	public String toString() {
		return font.getFontName();
	}
	
}
