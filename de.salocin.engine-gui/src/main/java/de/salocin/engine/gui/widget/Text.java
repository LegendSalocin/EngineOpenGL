package de.salocin.engine.gui.widget;

import de.salocin.engine.geom.Dimension;
import de.salocin.engine.gui.GuiPlugin;
import de.salocin.engine.gui.util.Align;
import de.salocin.engine.utils.core.Color;
import de.salocin.engine.utils.core.ColoredString;
import de.salocin.engine.utils.font.Font;
import de.salocin.engine.utils.property.StringProperty;

public class Text extends WidgetBackground {
	
	private StringProperty text = new StringProperty();
	private Font textFont;
	private Color textColor;
	private Align textAlign;
	protected float textOffsetX;
	protected float textOffsetY;
	
	public Text(CharSequence text) {
		setText(text);
		setTextFont(GuiPlugin.getDefaultFont());
		setTextColor(Color.BLACK);
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
	protected Dimension computeSize() {
		Dimension dim = new Dimension();
		if (text.getString() != null) {
			dim.setWidth(textFont.getMetrics().getWidth(text.getString()));
			dim.setHeight(textFont.getMetrics().getLineHeight());
		}
		
		return dim;
	}
	
	@Override
	protected void onLayout() {
		if (text.getString() != null) {
			final float textWidth = textFont.getMetrics().getWidth(text.getString());
			final float textHeight = textFont.getMetrics().getLineHeight();
			final float baselineOffset = textFont.getMetrics().getAscent();
			
			float x = textAlign.getHorizontalOffset();
			float y = textAlign.getVerticalOffset() + baselineOffset;
			
			Align.Horizontal h = textAlign.getHorizontal();
			Align.Vertical v = textAlign.getVertical();
			
			switch (h) {
			case LEFT:
				break;
			case CENTER:
				x += (getWidth() - textWidth) / 2.0f;
				break;
			case RIGHT:
				x += getHeight() - textWidth;
				break;
			}
			
			switch (v) {
			case TOP:
				break;
			case MIDDLE:
				y += (getWidth() - textHeight) / 2.0f;
				break;
			case BOTTOM:
				y += getHeight() - textHeight;
				break;
			}
			
			textOffsetX = x;
			textOffsetY = y;
		}
	}
	
	@Override
	public void render() {
		super.render();
		
		renderText(new ColoredString().add(getText(), textColor));
	}
	
	protected void renderText(ColoredString text) {
		text.render(textFont, getPosX() + textOffsetX, getPosY() + textOffsetY);
		// textFont.renderText(this.text.getString(), getPosX() + textOffsetX,
		// getPosY() + textOffsetY);
	}
	
}
