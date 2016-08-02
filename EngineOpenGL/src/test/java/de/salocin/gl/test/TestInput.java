package de.salocin.gl.test;

import de.salocin.gl.Engine;
import de.salocin.gl.event.EventHandler;
import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.Listener;
import de.salocin.gl.event.input.KeyPressedEvent;
import de.salocin.gl.event.input.MouseButtonEvent;
import de.salocin.gl.plugin.CorePlugin;
import de.salocin.gl.util.EngineException;
import de.salocin.gl.util.input.Action;
import de.salocin.gl.util.input.Modifier;

public class TestInput extends CorePlugin implements Listener {
	
	public static void main(String[] args) {
		try {
			Engine.start(new TestInput());
		} catch (EngineException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getName() {
		return TestInput.class.getSimpleName();
	}
	
	@Override
	public String getVersion() {
		return "1.0";
	}
	
	@Override
	protected void onEnable() {
		EventManager.getInstance().registerListener(this);
	}
	
	@Override
	protected void onDisable() {
	}
	
	@EventHandler
	public void onKeyPress(KeyPressedEvent e) {
		if (e.getAction() == Action.RELEASED) {
			if (e.getKey().isPrintable()) {
				System.out.println(e.getKey().getLocalizedName());
			}
			
			System.out.print(e.getKey());
			printModifiers(e.getModifiers());
			System.out.println();
		}
	}
	
	@EventHandler
	public void onMousePress(MouseButtonEvent e) {
		if (e.getAction() == Action.RELEASED) {
			System.out.print(e.getButton());
			printModifiers(e.getModifiers());
			System.out.println();
		}
	}
	
	private void printModifiers(Modifier[] modifiers) {
		for (Modifier modifier : modifiers) {
			System.out.print(" " + modifier);
		}
	}
	
}
