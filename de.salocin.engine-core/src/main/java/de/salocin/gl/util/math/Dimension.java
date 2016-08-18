package de.salocin.gl.util.math;

import de.salocin.gl.util.Copyable;

public class Dimension implements Copyable<Dimension> {
	
	private float width;
	private float height;
	
	public Dimension() {
		this(0.0f, 0.0f);
	}
	
	public Dimension(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	public float getWidth() {
		return width;
	}
	
	public Dimension setWidth(float width) {
		this.width = width;
		return this;
	}
	
	public float getHeight() {
		return height;
	}
	
	public Dimension setHeight(float height) {
		this.height = height;
		return this;
	}
	
	@Override
	public Dimension copy() {
		return new Dimension(width, height);
	}
	
}
