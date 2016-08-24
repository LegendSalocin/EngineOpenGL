package de.salocin.engine.gui.layout;

import java.util.ArrayList;

import de.salocin.engine.gui.widget.Pane;
import de.salocin.engine.gui.widget.Widget;
import de.salocin.engine.util.exception.EngineException;

public class AbsoluteLayout implements LayoutManager<AbsoluteConstraint> {
	
	private static final AbsoluteLayout instance = new AbsoluteLayout();
	
	public static AbsoluteLayout getInstance() {
		return instance;
	}
	
	@Override
	public void layoutWidgets(Pane root, ArrayList<Widget> children) {
		float rootX = 0.0f;
		float rootY = 0.0f;
		
		if (root.hasParent()) {
			Widget parent = root.getParent();
			
			rootX = parent.getPosX();
			rootY = parent.getPosY();
			
			if (root.getLayoutConstraint() != null) {
				AbsoluteConstraint a = (AbsoluteConstraint) root.getLayoutConstraint();
				
				rootX += a.x;
				rootY += a.y;
			}
		}
		
		root.setPos(rootX, rootY);
		
		for (Widget widget : children) {
			try {
				AbsoluteConstraint c = (AbsoluteConstraint) widget.getLayoutConstraint();
				widget.setPos(rootX + c.x, rootY + c.y);
			} catch (ClassCastException e) {
				new EngineException("This error should not occur. Please create a bug report.", e).printStackTrace();
			}
		}
	}
	
	@Override
	public AbsoluteConstraint getDefaultConstraint() {
		return AbsoluteConstraint.DEFAULT;
	}
	
	@Override
	public Class<AbsoluteConstraint> getConstraintClass() {
		return AbsoluteConstraint.class;
	}
	
}
