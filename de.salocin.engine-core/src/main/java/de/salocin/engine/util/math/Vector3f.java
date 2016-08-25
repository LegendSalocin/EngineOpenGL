package de.salocin.engine.util.math;

public class Vector3f {
	
	public float x;
	public float y;
	public float z;
	
	public Vector3f() {
		this(0.0f, 0.0f, 0.0f);
	}
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f setX(float x) {
		this.x = x;
		return this;
	}
	
	public float getX() {
		return x;
	}
	
	public Vector3f setY(float y) {
		this.y = y;
		return this;
	}
	
	public float getY() {
		return y;
	}
	
	public Vector3f setZ(float z) {
		this.z = z;
		return this;
	}
	
	public float getZ() {
		return z;
	}
	
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public Vector3f add(Vector3f other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		return this;
	}
	
	public Vector3f subtract(Vector3f other) {
		this.x -= other.x;
		this.y -= other.y;
		this.z -= other.z;
		return this;
	}
	
	public Vector3f scale(float scale) {
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
		return this;
	}
	
	public float dot(Vector3f other) {
		return x * other.x + y * other.y + z * other.z;
	}
	
	public Vector3f normalize() {
		float length = length();
		
		if (length != 0) {
			this.x /= length;
			this.y /= length;
			this.z /= length;
		}
		
		return this;
	}
	
	public Vector3f negate() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	
	public float length() {
		return (float) Math.sqrt(lengthSquared());
	}
	
	public float lengthSquared() {
		return (x * x) + (y * y) + (z * z);
	}
	
	public float distance(Vector3f to) {
		return distance(to, this).length();
	}
	
	public float distanceSquared(Vector3f to) {
		return distance(to, this).lengthSquared();
	}
	
	public Vector3f copy() {
		return new Vector3f(x, y, z);
	}
	
	@Override
	public String toString() {
		return "Vector3f[" + x + "|" + y + "|" + z + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vector3f) {
			Vector3f other = (Vector3f) obj;
			return other.x == x && other.y == y && other.z == z;
		}
		
		return false;
	}
	
	public static Vector3f distance(Vector3f from, Vector3f to) {
		return new Vector3f(to.x - from.x, to.y - from.y, to.z - from.z);
	}
	
	public static Vector3f cross(Vector3f a, Vector3f b) {
		float x = a.y * b.z - a.z * b.y;
		float y = a.z * b.x - a.x * b.z;
		float z = a.x * b.y - a.y * b.x;
		return new Vector3f(x, y, z);
	}
	
}
