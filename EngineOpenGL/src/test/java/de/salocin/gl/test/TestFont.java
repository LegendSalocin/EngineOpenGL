package de.salocin.gl.test;

import org.lwjgl.opengl.GL11;

import de.salocin.gl.Engine;
import de.salocin.gl.plugin.SimpleCorePlugin;
import de.salocin.gl.render.Display;
import de.salocin.gl.render.gui.RenderState;
import de.salocin.gl.util.Color;
import de.salocin.gl.util.font.Font;

public class TestFont extends SimpleCorePlugin {
	
	public static void main(String[] args) {
		Engine.simpleStart(new TestFont());
	}
	
	@Override
	protected void onEnable() {
		setState(new TestFontState());
	}
	
	public class TestFontState extends RenderState {
		
		private Font arial;
		
		@Override
		protected void onInit() {
			arial = Font.newBuilder("Arial").setFontSize(70).build();
			
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
			arial.renderText("TEST", 400, 300 + arial.getMetrics().getLineHeight() * 0, Color.white);
			arial.renderText("TEST 2", 400, 300 + arial.getMetrics().getLineHeight() * 1, Color.red);
			arial.renderText("TEST 3", 400, 300 + arial.getMetrics().getLineHeight() * 2, Color.green);
		}
		
		@Override
		protected void onExit() {
		}
		
	}
	
}
