package de.salocin.engine.gui.widget;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

import de.salocin.engine.geom.Point;

public class BorderPane extends Pane {
	
	public static enum Location {
		CENTER,
		TOP,
		RIGHT,
		BOTTOM,
		LEFT;
	}
	
	private final EnumMap<Location, Widget> children = new EnumMap<Location, Widget>(Location.class);
	
	@Override
	public void add(Widget child) {
		add(child, Location.CENTER);
	}
	
	public void add(Widget child, BorderPane.Location location) {
		children.put(location, child);
		
	}
	
	@Override
	public Collection<Widget> getChildren() {
		return children.values();
	}
	
	@Override
	protected Point childPosWithinParent(Widget child) {
		Location loc = null;
		
		for (Map.Entry<Location, Widget> entry : children.entrySet()) {
			if (entry.getValue().equals(child)) {
				loc = entry.getKey();
				break;
			}
		}
		
		if (loc == null) {
			throw new IllegalArgumentException("widget has not been added to this Pane");
		}
		
		Point point = new Point();
		
		switch (loc) {
		case CENTER:
			break;
		case TOP:
			point.set(0.0f, 0.0f);
			break;
		case RIGHT:
			break;
		case BOTTOM:
			break;
		case LEFT:
			break;
		}
		
		return point;
	}
	
}
