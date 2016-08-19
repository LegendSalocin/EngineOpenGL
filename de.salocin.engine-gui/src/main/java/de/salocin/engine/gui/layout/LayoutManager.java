package de.salocin.engine.gui.layout;

import java.util.Map;

import de.salocin.engine.gui.component.Component;
import de.salocin.engine.gui.component.pane.Pane;
import de.salocin.engine.util.ReflectionUtils;
import de.salocin.engine.util.math.Rectangle;

public interface LayoutManager<T extends LayoutConstraint> {
	
	void layoutRoot(Pane<T> root);
	
	void layoutComponents(Pane<T> root, Map<Component, T> children);
	
	default void applyLayout(Component component, Rectangle componentBounds) {
		ReflectionUtils.setFieldValue(component, "bounds", componentBounds);
	}
	
}
