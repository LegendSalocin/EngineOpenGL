package de.salocin.gl.test;

import de.salocin.gl.Engine;
import de.salocin.gl.event.EventHandler;
import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.Listener;
import de.salocin.gl.event.input.KeyPressedEvent;
import de.salocin.gl.plugin.CorePlugin;
import de.salocin.gl.render.gui.GuiState;
import de.salocin.gl.util.EngineException;
import de.salocin.gl.util.ExitCode;

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
	
	public class Menu extends GuiState {
		
		@Override
		protected void onInit() {
			renderDebugInfo(true);
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
