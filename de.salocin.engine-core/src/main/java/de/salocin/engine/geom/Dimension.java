package de.salocin.engine.geom;

import org.apache.commons.lang3.Validate;

public class Dimension {
	
	private Vector2f vec = new Vector2f();
	
	public Dimension() {
	}
	
	public Dimension(float width, float height) {
		set(width, height);
	}
	
	public Dimension(Vector2f vector) {
		set(vector);
	}
	
	public Dimension set(Vector2f vector) {
		this.vec = Validate.notNull(vector);
		return this;
	}
	
	public Dimension set(float width, float height) {
		vec.x = width;
		vec.y = height;
		return this;
	}
	
	public float getWidth() {
		return vec.x;
	}
	
	public Dimension setWidth(float width) {
		vec.x = width;
		return this;
	}
	
	public float getHeight() {
		return vec.y;
	}
	
	public Dimension setHeight(float height) {
		vec.y = height;
		return this;
	}
	
	public Vector2f toVector() {
		return vec;
	}
	
	public Dimension copy() {
		return new Dimension(vec.copy());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Dimension) {
			return ((Dimension) obj).vec.equals(vec);
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "Dimension[" + vec.x + "|" + vec.y + "]";
	}
	
}
