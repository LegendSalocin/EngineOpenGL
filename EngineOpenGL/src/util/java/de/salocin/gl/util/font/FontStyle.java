package de.salocin.gl.util.font;

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
	
	protected static boolean contains(FontStyle[] styles, FontStyle style) {
		for (FontStyle s : styles) {
			if (s == style) {
				return true;
			}
		}
		
		return false;
	}
	
}
