package de.salocin.engine.gui.layout;

public class AbsoluteConstraint implements LayoutConstraint {
	
	public static final AbsoluteConstraint DEFAULT = new AbsoluteConstraint(0.0f, 0.0f);
	
	public float x;
	public float y;
	
	public AbsoluteConstraint(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
}
