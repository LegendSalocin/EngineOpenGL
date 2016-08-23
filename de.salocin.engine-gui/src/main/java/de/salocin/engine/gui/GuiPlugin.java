package de.salocin.engine.gui;

import org.apache.commons.lang3.Validate;

import de.salocin.engine.plugin.Instance;
import de.salocin.engine.plugin.SimplePlugin;
import de.salocin.engine.utils.font.Font;
import de.salocin.engine.utils.font.FontBuilder;

public class GuiPlugin extends SimplePlugin {
	
	@Instance("de.salocin.engine.gui.GuiPlugin")
	public static GuiPlugin instance;
	
	private static final FontBuilder defaultFontBuilder = new FontBuilder("Arial");
	private static Font defaultFont;
	private static DebugInfoRender render;
	
	@Override
	protected void onEnable() {
		defaultFont = defaultFontBuilder.setFontSize(20).build();
		render = new DebugInfoRender();
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
	
	public static DebugInfoRender getDebugInfoRender() {
		return render;
	}
	
	public static void getDebugInfoRender(DebugInfoRender render) {
		GuiPlugin.render = Validate.notNull(render);
	}
	
	public static void setDebugInfoEnabled(boolean enabled) {
		render.setEnabled(enabled);
	}
	
}
