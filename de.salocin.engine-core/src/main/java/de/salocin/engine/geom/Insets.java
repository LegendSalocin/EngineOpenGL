package de.salocin.engine.geom;

public class Insets {
	
	private final float top;
	private final float right;
	private final float bottom;
	private final float left;
	
	public Insets() {
		this(0.0f);
	}
	
	public Insets(float insets) {
		this(insets, insets, insets, insets);
	}
	
	public Insets(float top, float right, float bottom, float left) {
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
	}
	
	public float getTop() {
		return top;
	}
	
	public float getRight() {
		return right;
	}
	
	public float getBottom() {
		return bottom;
	}
	
	public float getLeft() {
		return left;
	}
	
}
