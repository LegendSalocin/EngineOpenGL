package de.salocin.engine.gui.widget;

import java.util.ArrayList;

import de.salocin.engine.event.KeyEvent;
import de.salocin.engine.event.MouseButtonEvent;
import de.salocin.engine.event.MouseMoveEvent;
import de.salocin.engine.gui.layout.AbsoluteLayout;
import de.salocin.engine.gui.layout.LayoutConstraint;
import de.salocin.engine.gui.layout.LayoutManager;

public class Pane extends Widget {
	
	private LayoutManager<?> layoutManager;
	private ArrayList<Widget> children = new ArrayList<Widget>();
	private Widget focused;
	
	public Pane() {
		this(AbsoluteLayout.getInstance());
	}
	
	public Pane(LayoutManager<?> layoutManager) {
		super.propertyFocus().setBoolean(true);
		setLayoutManager(layoutManager);
	}
	
	/**
	 * All children will be removed!
	 * 
	 * @param layoutManager
	 */
	public void setLayoutManager(LayoutManager<?> layoutManager) {
		this.layoutManager = layoutManager == null ? AbsoluteLayout.getInstance() : layoutManager;
		children.clear();
	}
	
	public LayoutManager<?> getLayoutManager() {
		return layoutManager;
	}
	
	public boolean add(Widget child) {
		return add(child, null);
	}
	
	public boolean add(Widget child, LayoutConstraint constraint) {
		if (!contains(child)) {
			if (layoutManager.isConstraintNeeded()) {
				if (layoutManager.hasDefaultConstraint() && constraint == null) {
					constraint = layoutManager.getDefaultConstraint();
				}
				
				if (constraint == null || !layoutManager.getConstraintClass().isAssignableFrom(constraint.getClass())) {
					throw new IllegalArgumentException("Wrong LayoutConstraint! Needed '" + layoutManager.getConstraintClass() + "' but was '" + constraint.getClass() + "'.");
				}
			}
			
			child.parent = this;
			child.layoutConstraint = constraint;
			children.add(child);
			// child.requestFocus();
			
			return true;
		}
		
		return false;
	}
	
	public boolean remove(Widget child) {
		if (contains(child)) {
			child.parent = null;
			child.layoutConstraint = null;
			children.remove(child);
			
			return true;
		}
		
		return false;
	}
	
	public boolean contains(Widget child) {
		return children.contains(child);
	}
	
	public ArrayList<Widget> getChildren() {
		return children;
	}
	
	@Override
	public void pack() {
		float maxWidth = 0.0f;
		float maxHeight = 0.0f;
		
		for (Widget widget : children) {
			widget.pack();
			
			if (widget.getWidth() > maxWidth) {
				maxWidth = widget.getWidth();
			}
			
			if (widget.getHeight() > maxHeight) {
				maxHeight = widget.getHeight();
			}
		}
		
		setSize(maxWidth, maxHeight);
		layoutManager.layoutWidgets(this, children);
	}
	
	@Override
	public void render() {
		super.render();
		
		for (Widget widget : children) {
			widget.render();
		}
	}
	
	@Override
	public void requestFocus() {
		requestFocus(null);
	}
	
	public void requestFocus(Widget child) {
		if (focused != null) {
			if (child.equals(focused)) {
				return;
			} else {
				focused.propertyFocus().setValue(false);
				focused = null;
			}
		}
		
		for (Widget widget : children) {
			if (widget.equals(child)) {
				widget.propertyFocus().setBoolean(true);
				focused = widget;
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
		
		for (Widget widget : children) {
			widget.onMouseMove(e, widget.hasFocus());
		}
	}
	
	@Override
	protected void onMouseButton(MouseButtonEvent e, boolean hasFocus) {
		super.onMouseButton(e, hasFocus);
		
		for (Widget widget : children) {
			widget.onMouseButton(e, widget.hasFocus());
		}
	}
	
	@Override
	protected void onKey(KeyEvent e, boolean hasFocus) {
		super.onKey(e, hasFocus);
		
		for (Widget widget : children) {
			widget.onKey(e, widget.hasFocus());
		}
	}
	
}
