package de.salocin.engine.gui.component;

import org.apache.commons.lang3.Validate;

import de.salocin.gl.display.Color;
import de.salocin.gl.display.Texture;
import de.salocin.gl.event.input.KeyPressedEvent;
import de.salocin.gl.event.input.MouseButtonEvent;
import de.salocin.gl.event.input.MouseMovedEvent;
import de.salocin.gl.gui.Gui;
import de.salocin.gl.util.Renderable;
import de.salocin.gl.util.math.Rectangle;

public class GuiComponent implements Renderable {
	
	protected Texture texture;
	protected Color color;
	protected Rectangle bounds = new Rectangle() {
		public void onUpdate() {
			onBoundsUpdated();
		}
	};
	protected boolean mouseOver;
	
	public GuiComponent(float x, float y, float width, float height) {
		bounds.setBounds(x, y, width, height);
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void setBounds(Rectangle bounds) {
		Validate.notNull(bounds);
		this.bounds = bounds;
	}
	
	public Texture getBackgroundTexture() {
		return texture;
	}
	
	public void setBackgroundTexture(Texture texture) {
		this.texture = texture;
	}
	
	public Color getBackgroundColor() {
		return color;
	}
	
	public void setBackgroundColor(Color color) {
		this.color = color;
	}
	
	protected void onBoundsUpdated() {
	}
	
	protected void onKeyPress(KeyPressedEvent e) {
	}
	
	protected void onMousePress(MouseButtonEvent e) {
	}
	
	protected void onMouseMove(MouseMovedEvent e) {
		mouseOver = bounds.contains(e.getNewPos());
	}
	
	@Override
	public void render() {
		if (texture != null) {
			if (color != null) {
				color.bind();
			}
			
			texture.render(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
		} else if (color != null) {
			color.bind();
			Gui.renderQuad(bounds);
		}
	}
	
	public boolean isMouseOver() {
		return mouseOver;
	}
	
}
