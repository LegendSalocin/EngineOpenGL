package de.salocin.engine.geom;

public class Vector2f {
	
	public float x;
	public float y;
	
	public Vector2f() {
		this(0.0f, 0.0f);
	}
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f setX(float x) {
		this.x = x;
		return this;
	}
	
	public float getX() {
		return x;
	}
	
	public Vector2f setY(float y) {
		this.y = y;
		return this;
	}
	
	public float getY() {
		return y;
	}
	
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public Vector2f add(Vector2f other) {
		this.x += other.x;
		this.y += other.y;
		return this;
	}
	
	public Vector2f subtract(Vector2f other) {
		this.x -= other.x;
		this.y -= other.y;
		return this;
	}
	
	public Vector2f scale(float scale) {
		this.x *= scale;
		this.y *= scale;
		return this;
	}
	
	public float dot(Vector3f other) {
		return x * other.x + y * other.y;
	}
	
	public Vector2f normalize() {
		float length = length();
		
		if (length != 0) {
			this.x /= length;
			this.y /= length;
		}
		
		return this;
	}
	
	public Vector2f negate() {
		x = -x;
		y = -y;
		return this;
	}
	
	public float length() {
		return (float) Math.sqrt(lengthSquared());
	}
	
	public float lengthSquared() {
		return (x * x) + (y * y);
	}
	
	public float distanceTo(Vector2f to) {
		return distance(this, to).length();
	}
	
	public float distanceToSquared(Vector2f to) {
		return distance(this, to).lengthSquared();
	}
	
	public Vector2f copy() {
		return new Vector2f(x, y);
	}
	
	public Vector3f toVector3f() {
		return new Vector3f(x, y, 0.0f);
	}
	
	@Override
	public String toString() {
		return "Vector2f[" + x + "|" + y + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vector2f) {
			Vector2f other = (Vector2f) obj;
			return other.x == x && other.y == y;
		}
		
		return false;
	}
	
	public static Vector2f distance(Vector2f from, Vector2f to) {
		return new Vector2f(to.x - from.x, to.y - from.y);
	}
	
}
