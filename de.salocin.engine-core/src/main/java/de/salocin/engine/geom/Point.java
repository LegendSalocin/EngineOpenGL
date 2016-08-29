package de.salocin.engine.geom;

import org.apache.commons.lang3.Validate;

public class Point {
	
	private Vector2f vec = new Vector2f();
	
	public Point() {
	}
	
	public Point(float x, float y) {
		set(x, y);
	}
	
	public Point(Vector2f vector) {
		set(vector);
	}
	
	public Point set(Vector2f vector) {
		this.vec = Validate.notNull(vector);
		return this;
	}
	
	public Point set(float x, float y) {
		vec.x = x;
		vec.y = y;
		return this;
	}
	
	public float getX() {
		return vec.x;
	}
	
	public Point setX(float x) {
		vec.x = x;
		return this;
	}
	
	public float getY() {
		return vec.y;
	}
	
	public Point setY(float y) {
		vec.y = y;
		return this;
	}
	
	public Vector2f toVector() {
		return vec;
	}
	
	public Point copy() {
		return new Point(vec.copy());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			return ((Point) obj).vec.equals(vec);
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "Point[" + vec.x + "|" + vec.y + "]";
	}
	
}
