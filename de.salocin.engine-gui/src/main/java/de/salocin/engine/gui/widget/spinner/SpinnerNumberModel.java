package de.salocin.engine.gui.widget.spinner;

public interface SpinnerNumberModel<T extends Number> extends SpinnerModel<T> {
	
	default T getDefault() {
		return getMin();
	}
	
	T getMin();
	
	T getStep();
	
	T getMax();
	
}
