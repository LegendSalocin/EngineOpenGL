package de.salocin.gl.gui;

import org.apache.commons.lang3.Validate;

import de.salocin.gl.display.Display;
import de.salocin.gl.util.Color;
import de.salocin.gl.util.font.Font;

public class GuiTitle extends GuiComponent {
	
	protected CharSequence title;
	protected Font titleFont;
	protected Color titleColor;
	protected float titleOffsetX;
	protected float titleOffsetY;
	protected float titleX;
	protected float titleY;
	
	public GuiTitle(CharSequence title, float x, float y, float width, float height) {
		super(x, y, width, height);
		setTitle(title);
		setTitleFont(Display.getDefaultEngineFont());
		setTitleColor(Color.white);
	}
	
	public Color getTitleColor() {
		return titleColor;
	}
	
	public void setTitleColor(Color titleColor) {
		Validate.notNull(titleColor);
		this.titleColor = titleColor;
	}
	
	public CharSequence getTitle() {
		return title;
	}
	
	public void setTitle(CharSequence title) {
		this.title = title;
		updateTitlePos();
	}
	
	public Font getTitleFont() {
		return titleFont;
	}
	
	public void setTitleFont(Font titleFont) {
		Validate.notNull(titleFont);
		this.titleFont = titleFont;
		updateTitlePos();
	}
	
	public void setTitleOffset(float x, float y) {
		this.titleOffsetX = x;
		this.titleOffsetY = y;
		updateTitlePos();
	}
	
	public float getTitleOffsetX() {
		return titleOffsetX;
	}
	
	public float getTitleOffsetY() {
		return titleOffsetY;
	}
	
	@Override
	protected void onBoundsUpdated() {
		updateTitlePos();
	}
	
	protected void updateTitlePos() {
		if (titleFont != null && title != null && bounds.getWidth() >= 0.0f && bounds.getHeight() >= 0.0f) {
			float titleWidth = titleFont.getMetrics().getWidth(title);
			float titleHeight = titleFont.getMetrics().getLineHeight();
			
			titleX = bounds.getX() + titleOffsetX;
			titleY = bounds.getY() + titleOffsetY;
			
			titleX += (bounds.getWidth() - titleWidth) / 2;
			titleY += (bounds.getHeight() - titleHeight) / 2;
			
			// TODO align
			// switch (titleHorAlign) {
			// case CENTER:
			// titleX += (bounds.getWidth() - titleWidth) / 2;
			// break;
			// case LEFT:
			// break;
			// case RIGHT:
			// titleX += bounds.getWidth();
			// break;
			// }
			//
			// switch (titleVertAlign) {
			// case CENTER:
			// titleY += (bounds.getHeight() - titleHeight) / 2;
			// break;
			// case TOP:
			// break;
			// case BOTTOM:
			// titleY += bounds.getHeight() - titleHeight;
			// break;
			// }
		}
	}
	
	@Override
	public void render() {
		super.render();
		if (titleFont != null && title != null) {
			titleFont.renderText(title, titleX, titleY, titleColor);
		}
	}
	
}
