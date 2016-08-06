package de.salocin.gl.util.font;

import de.salocin.gl.util.render.Style;

public enum FontStyle {
	
	PLAIN(true),
	BOLD(true),
	ITALIC(true),
	UNDERLINE,
	OVERLINE,
	STRIKETHROUGH;
	
	protected boolean customFont;
	
	private FontStyle() {
		this(false);
	}
	
	private FontStyle(boolean customFont) {
		this.customFont = customFont;
	}
	
	protected static boolean contains(Style[] styles, Style style) {
		for (Style s : styles) {
			if (s == style) {
				return true;
			}
		}
		
		return false;
	}
	
}
