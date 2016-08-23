package de.salocin.engine.gui.util;

public class Insets {
	
	public static final Insets NONE = new Insets(0.0f);
	
	public float top;
	public float right;
	public float bottom;
	public float left;
	
	public Insets(float insets) {
		this(insets, insets, insets, insets);
	}
	
	public Insets(float top, float right, float bottom, float left) {
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
	}
	
}
