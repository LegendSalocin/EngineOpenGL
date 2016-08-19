package de.salocin.engine.gui;

import de.salocin.engine.display.RenderState;
import de.salocin.engine.event.EventHandler;
import de.salocin.engine.event.EventManager;
import de.salocin.engine.event.Listener;
import de.salocin.engine.event.display.RenderStateChangeEvent;
import de.salocin.engine.event.input.MouseButtonEvent;

public class GuiEventListener implements Listener {
	
	protected GuiEventListener() {
		EventManager.getInstance().registerListener(this);
	}
	
	@EventHandler
	public void onStateChanged(RenderStateChangeEvent e) {
		RenderState next = e.getNewState();
		
		if (next instanceof GuiRenderState) {
			GuiRenderState.current = (GuiRenderState) next;
		} else {
			GuiRenderState.current = null;
		}
	}
	
	@EventHandler
	public void onMouseButton(MouseButtonEvent e) {
	}
	
}
