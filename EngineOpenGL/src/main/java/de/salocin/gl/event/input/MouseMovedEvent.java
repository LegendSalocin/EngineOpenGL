package de.salocin.gl.event.input;

import de.salocin.gl.util.math.Point;

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
