package de.salocin.engine.gui.component;

import de.salocin.engine.event.input.KeyPressedEvent;
import de.salocin.engine.event.input.MouseButtonEvent;
import de.salocin.engine.event.input.MouseMovedEvent;
import de.salocin.engine.util.math.Dimension;
import de.salocin.engine.util.math.Rectangle;
import de.salocin.engine.utils.core.Color;
import de.salocin.engine.utils.texture.Texture;

public abstract class Component {
	
	protected boolean mouseOver;
	private Component parent;
	private Texture texture;
	private Color color;
	private Rectangle bounds;
	private Dimension pref;
	
	public Component getParent() {
		return parent;
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
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void setPrefWidth(float width) {
		pref.setWidth(width);
	}
	
	public void setPrefHeight(float height) {
		pref.setHeight(height);
	}
	
	public void setPrefSize(float width, float height) {
		setPrefWidth(width);
		setPrefHeight(height);
	}
	
	public void setPrefSize(Dimension pref) {
		this.pref = pref;
	}
	
	public Dimension getPrefSize() {
		return pref;
	}
	
	/**
	 * Calculates the best width and height of this component and overrides the
	 * preferred component's size ({@link #setPrefSize(Dimension)}).
	 */
	public abstract void pack();
	
	protected void onKeyPress(KeyPressedEvent e) {
	}
	
	protected void onMousePress(MouseButtonEvent e) {
	}
	
	protected void onMouseMove(MouseMovedEvent e) {
		// TODO
		// mouseOver = bounds.contains(e.getNewPos());
	}
	
	public void render() {
	}
	
	public boolean isMouseOver() {
		return mouseOver;
	}
	
}
