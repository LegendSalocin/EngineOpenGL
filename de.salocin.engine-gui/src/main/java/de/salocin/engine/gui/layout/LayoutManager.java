package de.salocin.engine.gui.layout;

import java.util.ArrayList;

import de.salocin.engine.gui.widget.Pane;
import de.salocin.engine.gui.widget.Widget;

public interface LayoutManager<T extends LayoutConstraint> {
	
	void layoutWidgets(Pane root, ArrayList<Widget> children);
	
	Class<T> getConstraintClass();
	
	T getDefaultConstraint();
	
	default boolean isConstraintNeeded() {
		return getConstraintClass() != null && !getConstraintClass().equals(LayoutConstraint.class);
	}
	
	default boolean hasDefaultConstraint() {
		return isConstraintNeeded() && getDefaultConstraint() != null;
	}
	
}
