package de.salocin.engine.gui.widget;

import de.salocin.engine.display.Renderer;
import de.salocin.engine.utils.core.Color;
import de.salocin.engine.utils.texture.Texture;

public abstract class WidgetBackground extends Widget {
	
	private Texture background;
	private Color backgroundColor;
	
	public Texture getBackground() {
		return background;
	}
	
	public void setBackground(Texture background) {
		this.background = background;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	@Override
	public void render() {
		renderBackground();
	}
	
	protected void renderBackground() {
		if (backgroundColor != null) {
			backgroundColor.bind();
		}
		
		if (background != null) {
			background.render(getPosX(), getPosY(), getWidth(), getHeight());
		} else if (backgroundColor != null) {
			Renderer.renderQuad(getPosX(), getPosY(), getWidth(), getHeight());
		}
	}
	
}
