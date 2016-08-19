package de.salocin.engine.gui.layout;

import de.salocin.engine.gui.component.pane.Pane;
import de.salocin.gl.util.math.Rectangle;

public abstract class AbstractLayoutManager<T extends LayoutConstraint> implements LayoutManager<T> {
	
	@Override
	public void layoutRoot(Pane<T> root) {
		Rectangle rootBounds = new Rectangle();
	}
	
}
