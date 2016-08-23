package de.salocin.engine.examples;

import de.salocin.engine.Engine;
import de.salocin.engine.display.Display;
import de.salocin.engine.display.input.Action;
import de.salocin.engine.display.input.Key;
import de.salocin.engine.plugin.SimpleCorePlugin;
import de.salocin.engine.util.engine.ExitCode;

public class BasicEngine extends SimpleCorePlugin {
	
	public static void main(String[] args) {
		Engine.simpleStart(new BasicEngine());
	}
	
	@Override
	protected void onEnable() {
		System.out.println("enable");
		
		Display.getKeyboard().addKeyCallback((e) -> {
			if (e.getAction() == Action.RELEASED) {
				if (e.getKey() == Key.KEY_ESCAPE) {
					Engine.stop(ExitCode.NORMAL_EXIT);
				}
			}
		});
		
		Display.getMouse().addMouseMoveCallback((e) -> {
			System.out.println(e.getNewPos().getX() + " " + e.getNewPos().getY());
		});
	}
	
	@Override
	protected void onDisable() {
		System.out.println("disable");
	}
	
}
