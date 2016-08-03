package de.salocin.gl.test;

import de.salocin.gl.Engine;
import de.salocin.gl.plugin.CorePlugin;
import de.salocin.gl.render.Display;
import de.salocin.gl.render.gui.RenderState;
import de.salocin.gl.scheduler.FPS;
import de.salocin.gl.util.engine.ExitCode;
import de.salocin.gl.util.exception.EngineException;
import de.salocin.gl.util.render.AlginH;
import de.salocin.gl.util.render.Color;
import de.salocin.gl.util.render.TrueTypeFont;

public class TestFontRender extends CorePlugin {
	
	public static void main(String[] args) {
		try {
			Engine.start(new TestFontRender());
		} catch (EngineException e) {
			e.log();
			ExitCode.ENGINE_START_ERROR.shutdownEngine();
		}
	}
	
	@Override
	public String getName() {
		return TestFontRender.class.getSimpleName();
	}
	
	@Override
	public String getVersion() {
		return "1.0";
	}
	
	@Override
	protected void onEnable() {
		Display.getInstance().setTargetFPS(60);
		setState(new State());
	}
	
	@Override
	protected void onDisable() {
	}
	
	private class State extends RenderState {
		
		private TrueTypeFont timesNewRoman;
		private TrueTypeFont segoeUI;
		private TrueTypeFont segoePrint;
		private TrueTypeFont arial;;
		private TrueTypeFont calibri;
		
		@Override
		public void onInit() {
			timesNewRoman = new TrueTypeFont("Times New Roman", 25);
			segoeUI = new TrueTypeFont("Segoe UI", 50);
			segoePrint = new TrueTypeFont("Segoe Print", 75);
			arial = new TrueTypeFont("Arial", 100);
			calibri = new TrueTypeFont("Calibri", 16);
		}
		
		@Override
		public void onUpdate(long currentTime, long delta) {
		}
		
		@Override
		public void onRender() {
			timesNewRoman.renderText("Times New Roman", 0.01f, 0, Color.blue);
			segoeUI.renderText("Segoe UI", 0.01f, 0.1f, Color.red);
			segoePrint.renderText("Segoe Print", 0.01f, 0.3f, Color.green);
			arial.renderText("Arial", 0.01f, 0.5f, Color.yellow);
			calibri.renderText("FPS: " + FPS.getFPS(), 0.99f, 0.0f, Color.white, AlginH.RIGHT);
		}
		
		@Override
		public void onExit() {
		}
	}
	
}
