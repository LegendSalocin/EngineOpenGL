package de.salocin.engine.gui.component;

import org.apache.commons.lang3.Validate;

import de.salocin.gl.display.Color;
import de.salocin.gl.display.Display;
import de.salocin.gl.display.font.Font;

public class GuiTitle extends GuiComponent {
	
	private CharSequence title;
	private Font titleFont;
	private Color titleColor;
	private float titleOffsetX;
	private float titleOffsetY;
	private AlignH alignH = AlignH.CENTER;
	private AlignV alignV = AlignV.MIDDLE;
	private float titleX;
	private float titleY;
	
	public GuiTitle(CharSequence title, float x, float y, float width, float height) {
		super(x, y, width, height);
		setTitle(title);
		setTitleFont(Display.getInstance().getDefaultEngineFont());
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
	
	public void setAlignH(AlignH alignH) {
		Validate.notNull(alignH);
		this.alignH = alignH;
	}
	
	public AlignH getAlignH() {
		return alignH;
	}
	
	public void setAlignV(AlignV alignV) {
		Validate.notNull(alignV);
		this.alignV = alignV;
	}
	
	public AlignV getAlignV() {
		return alignV;
	}
	
	@Override
	protected void onBoundsUpdated() {
		updateTitlePos();
	}
	
	protected void updateTitlePos() {
		if (titleFont != null && title != null && bounds.getWidth() >= 0.0f && bounds.getHeight() >= 0.0f) {
			float titleWidth = titleFont.getMetrics().getWidth(title);
			
			titleX = bounds.getX() + titleOffsetX;
			titleY = bounds.getY() + titleOffsetY;
			
			switch (alignH) {
			case CENTER:
				titleX += (bounds.getWidth() - titleWidth) / 2;
				break;
			case LEFT:
				break;
			case RIGHT:
				titleX += bounds.getWidth();
				break;
			}
			
			switch (alignV) {
			case MIDDLE:
				titleY += bounds.getHeight() / 2;
				break;
			case TOP:
				titleY += titleFont.getMetrics().getAscent();
				break;
			case BOTTOM:
				titleY += bounds.getHeight() - Math.abs(titleFont.getMetrics().getDescent());
				break;
			}
			
			System.out.println(titleX + " " + titleY);
		}
	}
	
	@Override
	public void render() {
		super.render();
		if (titleFont != null && title != null) {
			titleFont.renderText(title, titleX, titleY, titleColor);
		}
	}
	
	public static enum AlignV {
		TOP,
		MIDDLE,
		BOTTOM;
	}
	
	public static enum AlignH {
		LEFT,
		CENTER,
		RIGHT;
	}
	
}
