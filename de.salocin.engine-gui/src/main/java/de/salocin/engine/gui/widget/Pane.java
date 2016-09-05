package de.salocin.engine.gui.widget;

import java.util.Collection;

import de.salocin.engine.event.KeyEvent;
import de.salocin.engine.event.MouseButtonEvent;
import de.salocin.engine.event.MouseMoveEvent;
import de.salocin.engine.geom.Dimension;
import de.salocin.engine.geom.Insets;
import de.salocin.engine.geom.Point;
import de.salocin.engine.utils.core.Color;

public abstract class Pane extends WidgetBackground {
	
	private Widget focused;
	
	public Pane() {
		setBackgroundColor(Color.fromRGB(0xeeeeee));
		focus.setBoolean(true);
	}
	
	public abstract void add(Widget child);
	
	public abstract Collection<Widget> getChildren();
	
	protected abstract Point childPosWithinParent(Widget child);
	
	public void layout() {
		float x = 0.0f;
		float y = 0.0f;
		
		if (hasParent()) {
			Point p = getParent().childPosWithinParent(this);
			x = p.getX();
			y = p.getY();
		}
		
		posX.setValue((double) x);
		posY.setValue((double) y);
		
		float maxX = x;
		float maxY = y;
		
		for (Widget child : getChildren()) {
			if (Pane.class.isAssignableFrom(child.getClass())) {
				((Pane) child).layout();
				continue;
			}
			
			final Insets padding = child.padding.getValue();
			final Dimension dim = child.computeSize();
			final Point childPos = childPosWithinParent(child);
			
			double childX = (double) (getPosX() + childPos.getX() - padding.getLeft());
			double childY = (double) (getPosY() + childPos.getY() - padding.getTop());
			double childWidth = (double) (dim.getWidth() + padding.getLeft() + padding.getRight());
			double childHeight = (double) (dim.getHeight() + padding.getTop() + padding.getBottom());
			
			child.posX.setValue(childX);
			child.posY.setValue(childY);
			child.width.setValue(childWidth);
			child.height.setValue(childHeight);
			child.onLayout();
			
			if (childX + childWidth > maxX) {
				maxX = (float) (childX + childWidth);
			}
			
			if (childY + childHeight > maxY) {
				maxY = (float) (childY + childHeight);
			}
		}
		
		double width = maxX - x;
		double height = maxY - y;
		
		super.width.setValue(width);
		super.height.setValue(height);
	}
	
	@Override
	protected final Dimension computeSize() {
		throw new UnsupportedOperationException("Unsupported for panes");
	}
	
	@Override
	public void render() {
		super.render();
		
		for (Widget widget : getChildren()) {
			widget.render();
		}
	}
	
	@Override
	public void requestFocus() {
		requestFocus(null);
	}
	
	public void requestFocus(Widget child) {
		if (focused != null) {
			if (child != null && child.equals(focused)) {
				return;
			} else {
				focused.focus.setValue(false);
				focused = null;
			}
		}
		
		if (child != null) {
			for (Widget widget : getChildren()) {
				if (widget.equals(child)) {
					widget.focus.setBoolean(true);
					focused = widget;
				}
			}
		}
	}
	
	public void simulateMouseMoveEvent(MouseMoveEvent e) {
		onMouseMove(e, hasFocus());
	}
	
	public void simulateMouseButtonEvent(MouseButtonEvent e) {
		onMouseButton(e, hasFocus());
	}
	
	public void simulateKeyEvent(KeyEvent e) {
		onKey(e, hasFocus());
	}
	
	@Override
	protected void onMouseMove(MouseMoveEvent e, boolean hasFocus) {
		super.onMouseMove(e, hasFocus);
		
		for (Widget widget : getChildren()) {
			widget.onMouseMove(e, widget.hasFocus());
		}
	}
	
	@Override
	protected void onMouseButton(MouseButtonEvent e, boolean hasFocus) {
		super.onMouseButton(e, hasFocus);
		
		for (Widget widget : getChildren()) {
			widget.onMouseButton(e, widget.hasFocus());
		}
	}
	
	@Override
	protected void onKey(KeyEvent e, boolean hasFocus) {
		super.onKey(e, hasFocus);
		
		for (Widget widget : getChildren()) {
			widget.onKey(e, widget.hasFocus());
		}
	}
	
}
