package de.salocin.engine.gui.widget;

import java.util.Collection;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.Validate;

import de.salocin.engine.geom.Point;
import de.salocin.engine.geom.Vector2f;

public class AbsolutePane extends Pane {
	
	private LinkedHashMap<Widget, Point> children = new LinkedHashMap<Widget, Point>();
	
	@Override
	public void add(Widget child) {
		add(child, new Point());
	}
	
	public void add(Widget child, float x, float y) {
		add(child, new Point(x, y));
	}
	
	public void add(Widget child, Vector2f pos) {
		add(child, new Point(pos));
	}
	
	public void add(Widget child, Point pos) {
		Validate.notNull(child, "child");
		Validate.notNull(pos, "pos");
		child.parent = this;
		children.put(child, pos);
	}
	
	@Override
	public Collection<Widget> getChildren() {
		return children.keySet();
	}
	
	@Override
	protected Point computeChildPos(Widget child) {
		if (!children.containsKey(child)) {
			throw new IllegalArgumentException("widget has not been added to this Pane");
		}
		
		return children.get(child);
	}
	
	@Override
	protected Point childPosWithinParent(Widget child) {
		return children.get(child);
	}
	
}
