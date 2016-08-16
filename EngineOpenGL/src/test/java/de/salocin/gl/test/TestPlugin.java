package de.salocin.gl.test;

import java.io.File;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import de.salocin.gl.Engine;
import de.salocin.gl.config.FileConfiguration;
import de.salocin.gl.config.StringProperty;
import de.salocin.gl.display.Display;
import de.salocin.gl.event.EventHandler;
import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.Listener;
import de.salocin.gl.event.input.KeyPressedEvent;
import de.salocin.gl.gui.RenderState;
import de.salocin.gl.plugin.SimpleCorePlugin;
import de.salocin.gl.util.Color;
import de.salocin.gl.util.font.Font;
import de.salocin.gl.util.font.FontMetrics;
import de.salocin.gl.util.font.FontStyle;
import de.salocin.gl.util.input.Action;
import de.salocin.gl.util.input.Key;

public class TestPlugin extends SimpleCorePlugin {
	
	private TestFontState state;
	private FileConfiguration config;
	
	public static void main(String[] args) {
		Engine.simpleStart(new TestPlugin());
	}
	
	@Override
	protected void onEnable() {
		try {
			config = FileConfiguration.loadFrom(new File("test-config.ini"));
			config.registerProperty(StringProperty.newInstance("test-property", "This is an example. FontStyle Tests (äöü).?-+="));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		EventManager.getInstance().registerListener(new Events());
		state = new TestFontState();
		setState(state);
	}
	
	@Override
	protected void onDisable() {
		try {
			config.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public class Events implements Listener {
		
		private int currentStyle = 0;
		
		@EventHandler
		public void onKeyPress(KeyPressedEvent e) {
			if (e.getAction() == Action.RELEASED) {
				if (e.getKey() == Key.KEY_V) {
					Display.getInstance().enableVsync(!Display.getInstance().isVsyncEnabled());
				} else if (e.getKey() == Key.KEY_S) {
					state.times = state.times.getFontBuilder().setStrikethrough(!state.times.isStrikethrough()).build();
				} else if (e.getKey() == Key.KEY_U) {
					state.times = state.times.getFontBuilder().setUnderline(!state.times.isUnderlined()).build();
				} else if (e.getKey() == Key.KEY_O) {
					state.times = state.times.getFontBuilder().setOverline(!state.times.isOverlined()).build();
				} else if (e.getKey() == Key.KEY_F) {
					state.times = state.times.getFontBuilder().setFontStyle(FontStyle.values()[++currentStyle % FontStyle.values().length]).build();
				} else if (e.getKey() == Key.KEY_NUMPAD_ADD) {
					state.times = state.times.getFontBuilder().setFontSize(state.times.getSize() + 1).build();
				} else if (e.getKey() == Key.KEY_NUMPAD_SUBTRACT) {
					state.times = state.times.getFontBuilder().setFontSize(state.times.getSize() - 1).build();
				}
			}
		}
	}
	
	public class TestFontState extends RenderState {
		
		private Font arial;
		private FontMetrics arialMetrics;
		private Font times;
		
		@Override
		protected void onInit() {
			arial = Font.newBuilder("Arial").setFontSize(25).build();
			arialMetrics = arial.getMetrics();
			
			times = Font.newBuilder("Times New Roman").setFontSize(30).build();
			
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, 800, 600, 0, -1, 1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			
			enableRenderDebugInfo(true);
			Display.getInstance().enableVsync(true);
		}
		
		@Override
		protected void onUpdate(long currentTime, long delta) {
		}
		
		@Override
		protected void onRender() {
			arial.renderText("Controls:", 10, 175 + arialMetrics.getLineHeight() * 0, Color.yellow);
			arial.renderText("Press 'V' to toggle V-Sync", 50, 175 + arialMetrics.getLineHeight() * 1, Color.red);
			arial.renderText("Press 'S' to toggle Strikethrough", 50, 175 + arialMetrics.getLineHeight() * 2, Color.red);
			arial.renderText("Press 'U' to toggle Underline", 50, 175 + arialMetrics.getLineHeight() * 3, Color.red);
			arial.renderText("Press 'O' to toggle Overline", 50, 175 + arialMetrics.getLineHeight() * 4, Color.red);
			arial.renderText("Press 'F' to toggle Font Style", 50, 175 + arialMetrics.getLineHeight() * 5, Color.red);
			arial.renderText("Press '+' to increase Font size", 50, 175 + arialMetrics.getLineHeight() * 6, Color.red);
			arial.renderText("Press '-' to decrease Font size", 50, 175 + arialMetrics.getLineHeight() * 7, Color.red);
			
			String exampleText = times.getSize() + " " + times.getStyle().name() + ": " + config.getStringProperty("test-property").getString();
			
			float width = times.getMetrics().getWidth(exampleText);
			
			times.renderText(exampleText, (800 - width) / 2, 500);
		}
		
		@Override
		protected void onExit() {
		}
		
	}
	
}
