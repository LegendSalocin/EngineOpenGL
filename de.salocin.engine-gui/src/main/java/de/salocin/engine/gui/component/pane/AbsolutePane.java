package de.salocin.engine.gui.component.pane;

import de.salocin.engine.gui.component.Component;
import de.salocin.engine.gui.layout.AbsoluteLayout;
import de.salocin.engine.gui.layout.AbsoluteLayoutConstraint;

public class AbsolutePane extends Pane<AbsoluteLayoutConstraint> {
	
	public AbsolutePane() {
		super(new AbsoluteLayout());
	}
	
	public void add(Component component, float x, float y) {
		super.add(component, new AbsoluteLayoutConstraint(x, y));
	}
	
}
