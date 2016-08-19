package de.salocin.engine.utils.font;

/**
 * Represents the font styles REGULAR (PLAIN), BOLD, ITALIC and BOLD_ITALIC.
 * 
 * Use: {@link FontBuilder#FontBuilder(String, FontStyle)}
 */
public enum FontStyle {
	
	PLAIN,
	BOLD,
	ITALIC,
	BOLD_ITALIC;
	
	public String getFontFamilySuffix() {
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
