package de.salocin.engine.gui.layout;

import java.util.Map;

import de.salocin.engine.gui.component.Component;
import de.salocin.engine.gui.component.pane.Pane;
import de.salocin.engine.util.math.Dimension;
import de.salocin.engine.util.math.Rectangle;

public class AbsoluteLayout extends AbstractLayoutManager<AbsoluteLayoutConstraint> implements LayoutManager<AbsoluteLayoutConstraint> {
	
	@Override
	public void layoutComponents(Pane<AbsoluteLayoutConstraint> root, Map<Component, AbsoluteLayoutConstraint> children) {
		for (Map.Entry<Component, AbsoluteLayoutConstraint> entry : children.entrySet()) {
			final Component key = entry.getKey();
			final Dimension size = key.getPrefSize();
			final AbsoluteLayoutConstraint value = entry.getValue();
			
			Rectangle bounds = new Rectangle(value.x, value.y, size.getWidth(), size.getHeight());
			applyLayout(root, bounds);
		}
	}
	
}
