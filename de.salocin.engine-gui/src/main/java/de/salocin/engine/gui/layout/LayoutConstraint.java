package de.salocin.engine.gui.layout;

public interface LayoutConstraint {
	
	<T extends LayoutConstraint> boolean equals(T constraint);
	
}
