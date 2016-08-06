package de.salocin.gl.util.render;

public enum Style {
	
	PLAIN(true),
	BOLD(true),
	ITALIC(true),
	UNDERLINE,
	OVERLINE,
	STRIKETHROUGH;
	
	private boolean customFont;
	
	private Style() {
		this(false);
	}
	
	private Style(boolean customFont) {
		this.customFont = customFont;
	}
	
	public boolean hasCustomFont() {
		return customFont;
	}
	
	public static boolean contains(Style[] styles, Style style) {
		for (Style s : styles) {
			if (s == style) {
				return true;
			}
		}
		
		return false;
	}
}
