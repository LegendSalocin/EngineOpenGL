package de.salocin.engine.gui.layout;

public class BorderLayoutConstraint implements LayoutConstraint {
	
	public BorderConstraint constraint;
	
	public BorderLayoutConstraint() {
		this(BorderConstraint.CENTER);
	}
	
	public BorderLayoutConstraint(BorderConstraint constraint) {
		this.constraint = constraint;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BorderLayoutConstraint) {
			return ((BorderLayoutConstraint) obj).constraint == constraint;
		}
		
		return false;
	}
	
}
