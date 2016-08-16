package de.salocin.gl.util.font;

public enum FontStyle {
	
	PLAIN,
	BOLD,
	ITALIC,
	BOLD_ITALIC;
	
	public String getFontSuffix() {
		switch (this) {
		case PLAIN:
			return "";
		case BOLD_ITALIC:
			return " " + name().replace("_", " ");
		default:
			return " " + name();
		}
	}
	
}
