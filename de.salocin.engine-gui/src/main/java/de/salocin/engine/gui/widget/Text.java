package de.salocin.engine.gui.widget;

import de.salocin.engine.gui.GuiPlugin;
import de.salocin.engine.gui.util.Align;
import de.salocin.engine.utils.config.StringProperty;
import de.salocin.engine.utils.core.Color;
import de.salocin.engine.utils.font.Font;

public class Text extends Widget {
	
	private StringProperty text = new StringProperty();
	private Font textFont;
	private Color textColor;
	private Align textAlign;
	protected float textOffsetX;
	protected float textOffsetY;
	
	public Text(CharSequence text) {
		setText(text);
		setTextFont(GuiPlugin.getDefaultFont());
		setTextColor(Color.WHITE);
		setTextAlign(Align.NONE);
	}
	
	public void setText(CharSequence text) {
		this.text.setString(text == null ? "" : text.toString());
	}
	
	public void setText(char... text) {
		this.text.setString(new String(text));
	}
	
	public String getText() {
		return text.getString();
	}
	
	public void setTextFont(Font textFont) {
		this.textFont = textFont == null ? GuiPlugin.getDefaultFont() : textFont;
	}
	
	public Font getTextFont() {
		return textFont;
	}
	
	public void setTextColor(Color textColor) {
		this.textColor = textColor == null ? Color.WHITE : textColor;
	}
	
	public Color getTextColor() {
		return textColor;
	}
	
	public void setTextAlign(Align textAlign) {
		this.textAlign = textAlign == null ? Align.NONE : textAlign;
	}
	
	public Align getTextAlign() {
		return textAlign;
	}
	
	public StringProperty propertyText() {
		return text;
	}
	
	@Override
	public void pack() {
		final float textWidth = textFont.getMetrics().getWidth(text.getString());
		final float textHeight = textFont.getMetrics().getLineHeight();
		final float baselineOffset = textFont.getMetrics().getAscent();
		
		float prefWidth = textWidth + getPadding().left + getPadding().right;
		float prefHeight = textHeight + getPadding().top + getPadding().bottom;
		
		setSize(prefWidth, prefHeight);
		
		float x = textAlign.getHorizontalOffset();
		float y = textAlign.getVerticalOffset() + baselineOffset;
		
		Align.Horizontal h = textAlign.getHorizontal();
		Align.Vertical v = textAlign.getVertical();
		
		switch (h) {
		case LEFT:
			break;
		case CENTER:
			x += (prefWidth - textWidth) / 2.0f;
			break;
		case RIGHT:
			x += prefWidth - textWidth;
			break;
		}
		
		switch (v) {
		case TOP:
			break;
		case MIDDLE:
			y += (prefHeight - textHeight) / 2.0f;
			break;
		case BOTTOM:
			y += prefHeight - textHeight;
			break;
		}
		
		textOffsetX = x;
		textOffsetY = y;
	}
	
	@Override
	public void render() {
		super.render();
		
		textColor.bind();
		renderText();
	}
	
	protected void renderText() {
		textFont.renderText(text.getString(), getPosX() + textOffsetX, getPosY() + textOffsetY);
	}
	
}
