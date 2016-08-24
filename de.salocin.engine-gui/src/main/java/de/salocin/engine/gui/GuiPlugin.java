package de.salocin.engine.gui;

import org.apache.commons.lang3.Validate;

import de.salocin.engine.display.Display;
import de.salocin.engine.display.input.Keyboard;
import de.salocin.engine.display.input.Mouse;
import de.salocin.engine.event.Event;
import de.salocin.engine.gui.widget.Widget;
import de.salocin.engine.plugin.Instance;
import de.salocin.engine.plugin.SimplePlugin;
import de.salocin.engine.util.ReflectionUtils;
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
		
		Mouse.getMouse().addMouseMoveCallback(e -> {
			callWidgetEvent("onMouseMove", e);
		});
		
		Mouse.getMouse().addMouseButtonCallback(e -> {
			callWidgetEvent("onMouseButton", e);
		});
		
		Keyboard.getKeyboard().addKeyCallback(e -> {
			callWidgetEvent("onKey", e);
		});
		
		Display.addRenderStateCallback(e -> {
			if (e.getNewValue() != null && GuiRenderState.class.isAssignableFrom(e.getNewValue().getClass())) {
				GuiRenderState.current = (GuiRenderState) e.getNewValue();
			}
		});
	}
	
	@Override
	protected void onDisable() {
	}
	
	private <T extends Event> void callWidgetEvent(String methodName, T e) {
		GuiRenderState state = GuiRenderState.getCurrentState();
		
		if (state != null) {
			for (Widget widget : state.getRootPane().getChildren()) {
				ReflectionUtils.invokeMethod(Widget.class, widget, methodName, e);
			}
		}
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
