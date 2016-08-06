package de.salocin.gl.test;

import java.util.Random;

import de.salocin.gl.Engine;
import de.salocin.gl.event.EventHandler;
import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.Listener;
import de.salocin.gl.event.input.KeyPressedEvent;
import de.salocin.gl.plugin.CorePlugin;
import de.salocin.gl.render.gui.GuiButton;
import de.salocin.gl.render.gui.RenderState;
import de.salocin.gl.util.engine.ExitCode;
import de.salocin.gl.util.exception.EngineException;
import de.salocin.gl.util.font.Color;
import de.salocin.gl.util.render.AlginH;
import de.salocin.gl.util.render.AlginV;

public class TestGui extends CorePlugin implements Listener {
	
	public static void main(String[] args) {
		try {
			Engine.start(new TestGui());
		} catch (EngineException e) {
			e.log();
			ExitCode.ENGINE_START_ERROR.shutdownEngine();
		}
	}
	
	@Override
	public String getName() {
		return TestGui.class.getSimpleName();
	}
	
	@Override
	public String getVersion() {
		return "1.0";
	}
	
	@Override
	protected void onEnable() {
		EventManager.getInstance().registerListener(this);
		setState(new Menu());
	}
	
	@Override
	protected void onDisable() {
	}
	
	@EventHandler
	public void onKey(KeyPressedEvent e) {
		System.out.println(e.getChar());
	}
	
	public class Menu extends RenderState {
		
		private Random rand = new Random();
		private GuiButton btn;
		
		@Override
		protected void onInit() {
			renderDebugInfo(true);
			btn = new GuiButton("Test", 0.45f, 0.45f, 0.1f, 0.1f);
			btn.setBackgroundColor(Color.red);
			btn.setTitleAlginH(AlginH.CENTER);
			btn.setTitleAlginV(AlginV.CENTER);
			btn.setOnAction(() -> {
				btn.setBackgroundColor(Color.fromFloat(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
			});
			add(btn);
		}
		
		@Override
		protected void onUpdate(long currentTime, long delta) {
		}
		
		@Override
		protected void onRender() {
		}
		
		@Override
		protected void onExit() {
			
		}
		
	}
	
}
