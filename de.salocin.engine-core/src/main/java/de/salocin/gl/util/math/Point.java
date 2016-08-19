package de.salocin.gl.util.math;

public class Point implements Cloneable {
	
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
	public Point clone() {
		return new Point(x, y);
	}
	
}
