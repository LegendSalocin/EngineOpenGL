package de.salocin.gl.util.math;

import de.salocin.gl.util.Copyable;

public class Point implements Copyable<Point> {
	
	private float x;
	private float y;
	
	public Point() {
		this(0.0f, 0.0f);
	}
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public Point setX(float x) {
		this.x = x;
		return this;
	}
	
	public float getY() {
		return y;
	}
	
	public Point setY(float y) {
		this.y = y;
		return this;
	}
	
	@Override
	public Point copy() {
		return new Point(x, y);
	}
	
}
