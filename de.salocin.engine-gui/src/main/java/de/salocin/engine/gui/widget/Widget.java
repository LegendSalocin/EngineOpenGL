package de.salocin.engine.gui.widget;

import org.apache.commons.lang3.Validate;

import de.salocin.engine.display.Renderer;
import de.salocin.engine.event.KeyEvent;
import de.salocin.engine.event.MouseButtonEvent;
import de.salocin.engine.event.MouseMoveEvent;
import de.salocin.engine.geom.Insets;
import de.salocin.engine.geom.Point;
import de.salocin.engine.geom.Rectangle;
import de.salocin.engine.gui.layout.LayoutConstraint;
import de.salocin.engine.gui.util.InsetsProperty;
import de.salocin.engine.utils.config.BooleanProperty;
import de.salocin.engine.utils.config.DoubleProperty;
import de.salocin.engine.utils.core.Color;
import de.salocin.engine.utils.texture.Texture;

public abstract class Widget {
	
	protected Pane parent;
	protected LayoutConstraint layoutConstraint;
	private Texture background;
	private Color backgroundColor;
	private DoubleProperty posX = new DoubleProperty();
	private DoubleProperty posY = new DoubleProperty();
	private DoubleProperty width = new DoubleProperty();
	private DoubleProperty height = new DoubleProperty();
	private DoubleProperty minWidth = new DoubleProperty();
	private DoubleProperty minHeight = new DoubleProperty();
	private DoubleProperty maxWidth = new DoubleProperty(null, Float.MAX_VALUE);
	private DoubleProperty maxHeight = new DoubleProperty(null, Float.MAX_VALUE);
	private InsetsProperty padding = new InsetsProperty(new Insets());
	private InsetsProperty margin = new InsetsProperty(new Insets());
	private BooleanProperty mouseOver = new BooleanProperty();
	private BooleanProperty focus = new BooleanProperty();
	
	public Widget getParent() {
		return parent;
	}
	
	public boolean hasParent() {
		return parent != null;
	}
	
	public LayoutConstraint getLayoutConstraint() {
		return layoutConstraint;
	}
	
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
	
	public float getPosX() {
		return posX.getValue().floatValue();
	}
	
	public float getPosY() {
		return posY.getValue().floatValue();
	}
	
	public float getWidth() {
		return width.getValue().floatValue();
	}
	
	public float getHeight() {
		return height.getValue().floatValue();
	}
	
	public void setMinSize(float width, float height) {
		setMinWidth(width);
		setMinHeight(height);
	}
	
	public void setMinWidth(float width) {
		minWidth.setDouble(width);
	}
	
	public void setMinHeight(float height) {
		minHeight.setDouble(height);
	}
	
	public float getMinWidth() {
		return minWidth.getValue().floatValue();
	}
	
	public float getMinHeight() {
		return minHeight.getValue().floatValue();
	}
	
	public void setMaxSize(float width, float height) {
		setMinWidth(width);
		setMinHeight(height);
	}
	
	public void setMaxWidth(float width) {
		maxWidth.setDouble(width);
	}
	
	public void setMaxHeight(float height) {
		maxHeight.setDouble(height);
	}
	
	public float getMaxWidth() {
		return maxWidth.getValue().floatValue();
	}
	
	public float getMaxHeight() {
		return maxHeight.getValue().floatValue();
	}
	
	public boolean isInside(float x, float y) {
		return new Rectangle(getPosX(), getPosY(), getWidth(), getHeight()).contains(new Point(x, y));
	}
	
	public void setPadding(Insets padding) {
		this.padding.setValue(Validate.notNull(padding, "padding"));
	}
	
	public Insets getPadding() {
		return padding.getValue();
	}
	
	public void setMargin(Insets margin) {
		this.margin.setValue(Validate.notNull(margin, "margin"));
	}
	
	public Insets getMargin() {
		return margin.getValue();
	}
	
	public boolean isMouseOver() {
		return mouseOver.getBoolean();
	}
	
	public boolean hasFocus() {
		return focus.getBoolean();
	}
	
	public void requestFocus() {
		if (parent != null) {
			parent.requestFocus(this);
		}
	}
	
	public void setPos(float x, float y) {
		posX.setDouble(x);
		posY.setDouble(y);
	}
	
	public void setSize(float prefWidth, float prefHeight) {
		if (prefWidth < getMinWidth()) {
			prefWidth = getMinWidth();
		} else if (prefWidth > getMaxWidth()) {
			prefWidth = getMaxWidth();
		}
		
		if (prefHeight < getMinHeight()) {
			prefHeight = getMinHeight();
		} else if (prefHeight > getMaxHeight()) {
			prefHeight = getMaxHeight();
		}
		
		width.setDouble(prefWidth);
		height.setDouble(prefHeight);
	}
	
	public DoubleProperty propertyPosX() {
		return posX;
	}
	
	public DoubleProperty propertyPosY() {
		return posY;
	}
	
	public DoubleProperty propertyWidth() {
		return width;
	}
	
	public DoubleProperty propertyHeight() {
		return height;
	}
	
	public BooleanProperty propertyMouseOver() {
		return mouseOver;
	}
	
	public BooleanProperty propertyFocus() {
		return focus;
	}
	
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
			Renderer.renderQuad(getPosX() - getPadding().left, getPosY() - getPadding().top, getWidth() + getPadding().right, getHeight() + getPadding().bottom);
		}
	}
	
	protected void pack() {
	}
	
	protected void onMouseMove(MouseMoveEvent e, boolean hasFocus) {
		mouseOver.setBoolean(isInside(e.getNewPos().getX(), e.getNewPos().getY()));
	}
	
	protected void onMouseButton(MouseButtonEvent e, boolean hasFocus) {
		if (!hasFocus && isMouseOver()) {
			requestFocus();
		}
	}
	
	protected void onKey(KeyEvent e, boolean hasFocus) {
	}
	
}
