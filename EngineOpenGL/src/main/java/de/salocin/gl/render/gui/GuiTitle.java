package de.salocin.gl.render.gui;

import org.apache.commons.lang3.Validate;

import de.salocin.gl.util.font.Color;
import de.salocin.gl.util.render.AlginH;
import de.salocin.gl.util.render.AlginV;
import de.salocin.gl.util.render.TrueTypeFont;
import de.salocin.gl.util.render.TrueTypeFontDefaults;

public class GuiTitle extends GuiComponent {
	
	protected CharSequence title;
	protected TrueTypeFont titleFont;
	protected Color titleColor;
	protected AlginV titleVertAlgin = AlginV.CENTER;
	protected AlginH titleHorAlgin = AlginH.CENTER;
	protected float titleOffsetX;
	protected float titleOffsetY;
	protected float titleX;
	protected float titleY;
	
	public GuiTitle(CharSequence title, float x, float y, float width, float height) {
		super(x, y, width, height);
		setTitle(title);
		setTitleFont(TrueTypeFontDefaults.getDefaultEngineFont());
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
	
	public TrueTypeFont getTitleFont() {
		return titleFont;
	}
	
	public void setTitleFont(TrueTypeFont titleFont) {
		Validate.notNull(titleFont);
		this.titleFont = titleFont;
		updateTitlePos();
	}
	
	public AlginV getVAlgin() {
		return titleVertAlgin;
	}
	
	public void setTitleAlginV(AlginV algin) {
		Validate.notNull(algin);
		this.titleVertAlgin = algin;
		updateTitlePos();
	}
	
	public AlginH getHAlgin() {
		return titleHorAlgin;
	}
	
	public void setTitleAlginH(AlginH algin) {
		Validate.notNull(algin);
		this.titleHorAlgin = algin;
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
			float titleWidth = titleFont.getWidth(title);
			float titleHeight = titleFont.getFontHeight();
			
			titleX = bounds.getX() + titleOffsetX;
			titleY = bounds.getY() + titleOffsetY;
			
			switch (titleHorAlgin) {
			case CENTER:
				titleX += (bounds.getWidth() - titleWidth) / 2;
				break;
			case LEFT:
				break;
			case RIGHT:
				titleX += bounds.getWidth();
				break;
			}
			
			switch (titleVertAlgin) {
			case CENTER:
				titleY += (bounds.getHeight() - titleHeight) / 2;
				break;
			case TOP:
				break;
			case BOTTOM:
				titleY += bounds.getHeight() - titleHeight;
				break;
			}
		}
	}
	
	@Override
	public void render() {
		super.render();
		if (titleFont != null && title != null) {
			titleFont.renderText(title, titleX, titleY, titleColor, titleHorAlgin);
		}
	}
	
}
