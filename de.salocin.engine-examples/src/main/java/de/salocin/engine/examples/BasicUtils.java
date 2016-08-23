package de.salocin.engine.examples;

import de.salocin.engine.Engine;
import de.salocin.engine.display.Display;
import de.salocin.engine.display.RenderState;
import de.salocin.engine.plugin.SimpleCorePlugin;
import de.salocin.engine.utils.font.Font;

public class BasicUtils extends SimpleCorePlugin {
	
	public static void main(String[] args) {
		Engine.simpleStart(new BasicUtils());
	}
	
	@Override
	protected void onEnable() {
		Display.setRenderState(new FontRenderState());
	}
	
	public class FontRenderState implements RenderState {
		
		private Font arial;
		private Font times;
		private Font comicSans;
		private Font calibri;
		private Font consolas;
		
		@Override
		public void init() {
			arial = Font.newBuilder("Arial").setFontSize(50).build();
			times = Font.newBuilder("Times New Roman").setFontSize(50).build();
			comicSans = Font.newBuilder("Comic Sans MS").setFontSize(50).build();
			calibri = Font.newBuilder("Calibri").setFontSize(50).build();
			consolas = Font.newBuilder("Consolas").setFontSize(50).build();
		}
		
		@Override
		public void update(long currentTime, long delta) {
		}
		
		@Override
		public void render() {
			float x = 0.1f;
			float y = 0.1f;
			
			arial.renderText("Arial", x, y);
			
			y += arial.getMetrics().getLineHeight();
			times.renderText("Times New Roman", x, y);
			
			y += times.getMetrics().getLineHeight();
			comicSans.renderText("Comic Sans", x, y);
			
			y += comicSans.getMetrics().getLineHeight();
			calibri.renderText("Calibri", x, y);
			
			y += calibri.getMetrics().getLineHeight();
			consolas.renderText("Consolas", x, y);
		}
		
		@Override
		public void exit() {
		}
		
	}
	
}
