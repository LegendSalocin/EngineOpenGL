package de.salocin.engine.event.input;

import de.salocin.engine.util.math.Point;

public class MouseMovedEvent extends InputEvent {
	
	private final Point oldPos;
	private final Point newPos;
	
	public MouseMovedEvent(Point oldPos, Point newPos) {
		this.oldPos = oldPos;
		this.newPos = newPos;
	}
	
	public Point getOldPos() {
		return oldPos;
	}
	
	public Point getNewPos() {
		return newPos;
	}
	
}
