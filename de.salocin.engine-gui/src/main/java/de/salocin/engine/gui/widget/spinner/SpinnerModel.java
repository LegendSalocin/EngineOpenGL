package de.salocin.engine.gui.widget.spinner;

import de.salocin.engine.utils.config.Property;

public interface SpinnerModel<T> {
	
	T getDefault();
	
	T getValue();
	
	void setValue(T value);
	
	Property<T> propertyValue();
	
	void increase();
	
	void decrease();
	
	void parse(String text) throws Exception;
	
}
