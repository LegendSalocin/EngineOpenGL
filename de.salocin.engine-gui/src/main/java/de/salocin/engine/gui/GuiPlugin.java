package de.salocin.engine.gui;

import de.salocin.engine.plugin.SimplePlugin;
import de.salocin.engine.utils.font.Font;
import de.salocin.engine.utils.font.FontBuilder;

public class GuiPlugin extends SimplePlugin {
	
	private static final FontBuilder defaultFontBuilder = new FontBuilder("Arial");
	private static Font defaultFont;
	
	@Override
	protected void onEnable() {
	}
	
	@Override
	protected void onDisable() {
	}
	
	public static GuiRenderState getRenderState() {
		return GuiRenderState.getCurrentState();
	}
	
	public static FontBuilder getDefaultFontBuilder() {
		return defaultFontBuilder;
	}
	
	public static Font getDefaultFont() {
		return defaultFont;
	}
	
}
