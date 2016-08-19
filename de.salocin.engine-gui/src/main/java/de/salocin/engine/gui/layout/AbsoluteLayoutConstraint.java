package de.salocin.engine.gui.layout;

public class AbsoluteLayoutConstraint implements LayoutConstraint {
	
	public float x;
	public float y;
	
	public AbsoluteLayoutConstraint() {
		this(0.0f, 0.0f);
	}
	
	public AbsoluteLayoutConstraint(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public <T extends AbsoluteLayoutConstraint> boolean equals(AbsoluteLayoutConstraint constraint) {
		return false;
	}
	
}
