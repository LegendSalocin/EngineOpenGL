package de.salocin.engine.examples;

import de.salocin.engine.Engine;
import de.salocin.engine.display.Display;
import de.salocin.engine.display.RenderState;
import de.salocin.engine.plugin.SimpleCorePlugin;
import de.salocin.engine.utils.core.Color;
import de.salocin.engine.utils.core.ResourceLocation;
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
		private Font custom;
		private Font customMono;
		
		@Override
		public void init() {
			arial = Font.newBuilder("Arial").setFontSize(50).build();
			times = Font.newBuilder("Times New Roman").setFontSize(50).build();
			comicSans = Font.newBuilder("Comic Sans MS").setFontSize(50).build();
			calibri = Font.newBuilder("Calibri").setFontSize(50).build();
			consolas = Font.newBuilder("Consolas").setFontSize(50).build();
			custom = Font.newBuilder(ResourceLocation.newInstance(BasicUtils.this, "/digital-r.ttf")).setFontSize(30).build();
			customMono = Font.newBuilder(ResourceLocation.newInstance(BasicUtils.this, "/digital-mono.ttf")).setFontSize(30).build();
		}
		
		@Override
		public void update(long currentTime, long delta) {
		}
		
		@Override
		public void render() {
			float x = 0.1f;
			float y = 0.1f;
			
			arial.renderText("Arial", x, y);
			
			y += times.getMetrics().getLineHeight();
			times.renderText("Times New Roman", x, y);
			
			y += comicSans.getMetrics().getLineHeight();
			comicSans.renderText("Comic Sans", x, y);
			
			y += calibri.getMetrics().getLineHeight();
			calibri.renderText("Calibri", x, y);
			
			y += consolas.getMetrics().getLineHeight();
			consolas.renderText("Consolas", x, y);
			
			y += custom.getMetrics().getLineHeight() * 1.75f;
			custom.renderText("Digital", x, y);
			
			y += customMono.getMetrics().getLineHeight() * 1.75f;
			customMono.renderText("8888888888", x, y, Color.fromRGB(0x222222));
			customMono.renderText("0123456789", x, y, Color.WHITE);
		}
		
		@Override
		public void exit() {
		}
		
	}
	
}
