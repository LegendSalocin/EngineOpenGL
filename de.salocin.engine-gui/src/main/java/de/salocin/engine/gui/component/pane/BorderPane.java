package de.salocin.engine.gui.component.pane;

import de.salocin.engine.gui.component.Component;
import de.salocin.engine.gui.layout.BorderConstraint;
import de.salocin.engine.gui.layout.BorderLayout;
import de.salocin.engine.gui.layout.BorderLayoutConstraint;

public class BorderPane extends Pane<BorderLayoutConstraint> {
	
	private Component center;
	private Component north;
	private Component east;
	private Component south;
	private Component west;
	
	public BorderPane() {
		super(new BorderLayout());
	}
	
	public void add(Component component, BorderConstraint constraint) {
		switch (constraint) {
		case CENTER:
			this.center = component;
			break;
		case EAST:
			this.north = component;
			break;
		case NORTH:
			this.east = component;
			break;
		case SOUTH:
			this.south = component;
			break;
		case WEST:
			this.west = component;
			break;
		}
		
		BorderLayoutConstraint c = new BorderLayoutConstraint(constraint);
		
		if (isConstraintSet(c)) {
			super.remove(component);
		}
		
		super.add(component, c);
	}
	
	public void setCenter(Component center) {
		add(center, BorderConstraint.CENTER);
	}
	
	public Component getCenter() {
		return center;
	}
	
	public void setNorth(Component north) {
		add(center, BorderConstraint.CENTER);
	}
	
	public Component getNorth() {
		return north;
	}
	
	public void setEast(Component east) {
		add(center, BorderConstraint.EAST);
	}
	
	public Component getEast() {
		return east;
	}
	
	public void setSouth(Component south) {
		add(center, BorderConstraint.SOUTH);
	}
	
	public Component getSouth() {
		return south;
	}
	
	public void setWest(Component west) {
		add(center, BorderConstraint.WEST);
	}
	
	public Component getWest() {
		return west;
	}
	
}
