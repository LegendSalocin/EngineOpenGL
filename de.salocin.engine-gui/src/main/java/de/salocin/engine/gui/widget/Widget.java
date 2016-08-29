package de.salocin.engine.gui.widget;

import org.apache.commons.lang3.Validate;

import de.salocin.engine.event.KeyEvent;
import de.salocin.engine.event.MouseButtonEvent;
import de.salocin.engine.event.MouseMoveEvent;
import de.salocin.engine.geom.Insets;
import de.salocin.engine.geom.Point;
import de.salocin.engine.geom.Rectangle;
import de.salocin.engine.gui.layout.LayoutConstraint;
import de.salocin.engine.utils.property.BooleanProperty;
import de.salocin.engine.utils.property.DoubleProperty;
import de.salocin.engine.utils.property.SimpleProperty;

public abstract class Widget {
	
	protected Pane parent;
	protected LayoutConstraint layoutConstraint;
	private DoubleProperty posX = new DoubleProperty(new Double(1));
	private DoubleProperty posY = new DoubleProperty();
	private DoubleProperty width = new DoubleProperty();
	private DoubleProperty height = new DoubleProperty();
	private SimpleProperty<Insets> padding = new SimpleProperty<Insets>(new Insets());
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
		this.width.setMin((double) width);
	}
	
	public void setMinHeight(float height) {
		this.height.setMin((double) height);
	}
	
	public float getMinWidth() {
		return width.getMin().floatValue();
	}
	
	public float getMinHeight() {
		return height.getMin().floatValue();
	}
	
	public void setMaxSize(float width, float height) {
		setMinWidth(width);
		setMinHeight(height);
	}
	
	public void setMaxWidth(float width) {
		this.width.setMax((double) width);
	}
	
	public void setMaxHeight(float height) {
		this.height.setMax((double) height);
	}
	
	public float getMaxWidth() {
		return width.getMax().floatValue();
	}
	
	public float getMaxHeight() {
		return height.getMax().floatValue();
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
	}
	
	public void pack() {
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
