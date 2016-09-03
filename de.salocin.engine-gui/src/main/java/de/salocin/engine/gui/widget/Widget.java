package de.salocin.engine.gui.widget;

import de.salocin.engine.event.KeyEvent;
import de.salocin.engine.event.MouseButtonEvent;
import de.salocin.engine.event.MouseMoveEvent;
import de.salocin.engine.geom.Dimension;
import de.salocin.engine.geom.Insets;
import de.salocin.engine.geom.Point;
import de.salocin.engine.utils.property.BooleanProperty;
import de.salocin.engine.utils.property.DoubleProperty;
import de.salocin.engine.utils.property.ReadOnlyProperty;
import de.salocin.engine.utils.property.SimpleProperty;

public abstract class Widget {
	
	protected Pane parent;
	protected DoubleProperty posX = new DoubleProperty();
	protected DoubleProperty posY = new DoubleProperty();
	protected DoubleProperty width = new DoubleProperty();
	protected DoubleProperty height = new DoubleProperty();
	protected SimpleProperty<Insets> padding = new SimpleProperty<Insets>(new Insets());
	protected BooleanProperty mouseOver = new BooleanProperty();
	protected BooleanProperty focus = new BooleanProperty();
	
	/* Basic methods */
	public Pane getParent() {
		return parent;
	}
	
	public boolean hasParent() {
		return parent != null;
	}
	
	public boolean isInside(Point point) {
		return isInside(point.getX(), point.getY());
	}
	
	public boolean isInside(float x, float y) {
		return x >= getPosX() && y >= getPosY() && x <= getPosX() + getWidth() && y <= getPosY() + getHeight();
	}
	
	public void requestFocus() {
		if (parent != null) {
			parent.requestFocus(this);
		}
	}
	
	/* Property Getter */
	public ReadOnlyProperty<Double> propertyPosX() {
		return posX.readOnly();
	}
	
	public ReadOnlyProperty<Double> propertyPosY() {
		return posY.readOnly();
	}
	
	public ReadOnlyProperty<Double> propertyWidth() {
		return width.readOnly();
	}
	
	public ReadOnlyProperty<Double> propertyHeight() {
		return height.readOnly();
	}
	
	public ReadOnlyProperty<Boolean> propertyMouseOver() {
		return mouseOver.readOnly();
	}
	
	public ReadOnlyProperty<Boolean> propertyFocus() {
		return focus.readOnly();
	}
	
	public SimpleProperty<Insets> propertyPadding() {
		return padding;
	}
	
	/* Getter */
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
	
	public boolean isMouseOver() {
		return mouseOver.getBoolean();
	}
	
	public boolean hasFocus() {
		return focus.getBoolean();
	}
	
	/* Setter */
	public void setMinSize(float minWidth, float minHeight) {
		width.setMin((double) minWidth);
		height.setMin((double) minHeight);
	}
	
	public void setMaxSize(float maxWidth, float maxHeight) {
		width.setMax((double) maxWidth);
		height.setMax((double) maxHeight);
	}
	
	/* Methods for subclasses */
	protected abstract Dimension computeSize();
	
	public void render() {
	}
	
	protected void onLayout() {
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
