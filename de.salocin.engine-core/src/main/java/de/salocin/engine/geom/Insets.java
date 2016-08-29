package de.salocin.engine.geom;

public class Insets {
	
	public float top;
	public float right;
	public float bottom;
	public float left;
	
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
	
	public Insets setTop(float top) {
		this.top = top;
		return this;
	}
	
	public float getRight() {
		return right;
	}
	
	public Insets setRight(float right) {
		this.right = right;
		return this;
	}
	
	public float getBottom() {
		return bottom;
	}
	
	public Insets setBottom(float bottom) {
		this.bottom = bottom;
		return this;
	}
	
	public float getLeft() {
		return left;
	}
	
	public Insets setLeft(float left) {
		this.left = left;
		return this;
	}
	
}
