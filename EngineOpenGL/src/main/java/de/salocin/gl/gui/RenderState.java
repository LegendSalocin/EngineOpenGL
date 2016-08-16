package de.salocin.gl.gui;

import java.util.ArrayList;
import java.util.Arrays;

import de.salocin.gl.event.EventHandler;
import de.salocin.gl.event.EventManager;
import de.salocin.gl.event.Listener;
import de.salocin.gl.event.input.KeyPressedEvent;
import de.salocin.gl.event.input.MouseButtonEvent;
import de.salocin.gl.event.input.MouseMovedEvent;
import de.salocin.gl.util.Renderable;
import de.salocin.gl.util.render.DebugInfoRender;

public abstract class RenderState implements Listener, Renderable {
	
	private final ArrayList<GuiComponent> components = new ArrayList<GuiComponent>();
	private DebugInfoRender debugInfoRender;
	private boolean renderDebugInfo;
	
	public RenderState() {
		EventManager.getInstance().registerListener(this);
	}
	
	public void enableRenderDebugInfo(boolean renderDebugInfo) {
		this.renderDebugInfo = renderDebugInfo;
	}
	
	public DebugInfoRender getDebugInfoRender() {
		return debugInfoRender;
	}
	
	public void add(GuiComponent... components) {
		this.components.addAll(Arrays.asList(components));
	}
	
	public void remove(GuiComponent... components) {
		this.components.removeAll(Arrays.asList(components));
	}
	
	public GuiComponent getComponent(int id) {
		return components.get(id);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GuiComponent> T getComponent(Class<T> componentClass, int id) {
		return (T) getComponent(id);
	}
	
	public int getComponentId(GuiComponent component) {
		for (int i = 0; i < components.size(); i++) {
			if (component == components.get(i)) {
				return i;
			}
		}
		
		return -1;
	}
	
	public void init() {
		debugInfoRender = new DebugInfoRender();
		
		onInit();
	}
	
	protected abstract void onInit();
	
	public void update(long currentTime, long delta) {
		onUpdate(currentTime, delta);
	}
	
	protected abstract void onUpdate(long currentTime, long delta);
	
	@Override
	public void render() {
		onRender();
		
		for (GuiComponent c : components) {
			c.render();
		}
		
		if (renderDebugInfo) {
			debugInfoRender.render();
		}
	}
	
	protected abstract void onRender();
	
	public void exit() {
		onExit();
	}
	
	protected abstract void onExit();
	
	@EventHandler
	public void onKeyPress(KeyPressedEvent e) {
		for (GuiComponent c : components) {
			c.onKeyPress(e);
		}
	}
	
	@EventHandler
	public void onMousePress(MouseButtonEvent e) {
		for (GuiComponent c : components) {
			c.onMousePress(e);
		}
	}
	
	@EventHandler
	public void onMouseMove(MouseMovedEvent e) {
		for (GuiComponent c : components) {
			c.onMouseMove(e);
		}
	}
	
}
