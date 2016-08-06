package de.salocin.gl.util.render;

public class TrueTypeFontDefaults {
	
	private static final TrueTypeFont ARIAL = TrueTypeFont.newBuilder("Arial").build();
	
	public static final void init() {
	}
	
	public static TrueTypeFont getDefaultEngineFont() {
		return ARIAL.clone();
	}
	
}
