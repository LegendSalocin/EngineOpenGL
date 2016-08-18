package de.salocin.engine.gui;

import de.salocin.engine.gui.component.GuiComponent;
import de.salocin.gl.event.EventHandler;
import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.Listener;
import de.salocin.gl.event.display.RenderStateChangeEvent;
import de.salocin.gl.event.input.MouseButtonEvent;
import de.salocin.gl.gui.RenderState;

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
		for (GuiComponent comp : GuiRenderState.current.components) {
		}
	}
	
}
