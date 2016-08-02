package de.salocin.gl.render.gui.component;

import de.salocin.gl.render.Texture;
import de.salocin.gl.util.math.Rectangle;

public class GuiComponent extends Rectangle {
	
	private Texture texture;
	private boolean mouseOver;
	
	public GuiComponent() {
		super();
	}
	
	public GuiComponent(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public void update() {
		
	}
	
	public void render() {
	}
	
}
