package de.salocin.engine.gui.widget;

import java.util.ArrayList;

import de.salocin.engine.gui.layout.AbsoluteLayout;
import de.salocin.engine.gui.layout.LayoutConstraint;
import de.salocin.engine.gui.layout.LayoutManager;

public class Pane extends Widget {
	
	private LayoutManager<?> layoutManager;
	private ArrayList<Widget> children = new ArrayList<Widget>();
	
	public Pane() {
		this(AbsoluteLayout.getInstance());
	}
	
	public Pane(LayoutManager<?> layoutManager) {
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
	
}
