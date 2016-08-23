package de.salocin.engine.gui.layout;

import java.util.ArrayList;

import de.salocin.engine.gui.util.LayoutUtil;
import de.salocin.engine.gui.widget.Pane;
import de.salocin.engine.gui.widget.Widget;

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
		
		LayoutUtil.setWidgetPos(root, rootX, rootY);
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
