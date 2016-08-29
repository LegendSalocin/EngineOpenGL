package de.salocin.engine.event;

import de.salocin.engine.geom.Point;

public class MouseMoveEvent extends Event {
	
	private final Point oldPos;
	private final Point newPos;
	
	public MouseMoveEvent(Point oldPos, Point newPos) {
		this.oldPos = oldPos;
		this.newPos = newPos;
	}
	
	public Point getOldPos() {
		return oldPos;
	}
	
	public Point getNewPos() {
		return newPos;
	}
	
	@Override
	public boolean isCancelable() {
		return false;
	}
	
}
