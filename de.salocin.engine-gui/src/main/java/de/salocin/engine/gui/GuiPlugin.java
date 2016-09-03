package de.salocin.engine.gui;

import org.apache.commons.lang3.Validate;

import de.salocin.engine.display.Display;
import de.salocin.engine.display.input.Keyboard;
import de.salocin.engine.display.input.Mouse;
import de.salocin.engine.gui.widget.Pane;
import de.salocin.engine.plugin.Instance;
import de.salocin.engine.plugin.SimplePlugin;
import de.salocin.engine.util.Viewport;
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
			final GuiRenderState state = GuiRenderState.getCurrentState();
			if (state != null) {
				final Pane root = state.getRootPane();
				if (root != null) {
					root.simulateMouseMoveEvent(e);
				}
			}
		});
		
		Mouse.getMouse().addMouseButtonCallback(e -> {
			final GuiRenderState state = GuiRenderState.getCurrentState();
			if (state != null) {
				final Pane root = state.getRootPane();
				if (root != null) {
					root.simulateMouseButtonEvent(e);
				}
			}
		});
		
		Keyboard.getKeyboard().addKeyCallback(e -> {
			final GuiRenderState state = GuiRenderState.getCurrentState();
			if (state != null) {
				final Pane root = state.getRootPane();
				if (root != null) {
					root.simulateKeyEvent(e);
				}
			}
		});
		
		Display.addRenderStateCallback(e -> {
			if (e.getNewValue() != null && GuiRenderState.class.isAssignableFrom(e.getNewValue().getClass())) {
				GuiRenderState.current = (GuiRenderState) e.getNewValue();
			}
		});
		
		Viewport.addOrthoCallback(e -> {
			if (GuiRenderState.getCurrentState() != null && GuiRenderState.getCurrentState().getRootPane() != null) {
				Pane root = GuiRenderState.getCurrentState().getRootPane();
				
				root.setMinSize(e.getNewValue().getWidth(), e.getNewValue().getHeight());
				root.layout();
			}
		});
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
	
	public static void setDebugInfoRender(DebugInfoRender render) {
		GuiPlugin.render = Validate.notNull(render);
	}
	
	public static void setDebugInfoEnabled(boolean enabled) {
		render.setEnabled(enabled);
	}
	
}
