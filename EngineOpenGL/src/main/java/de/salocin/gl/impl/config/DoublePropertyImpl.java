package de.salocin.gl.impl.config;

import de.salocin.gl.config.DoubleProperty;

public class DoublePropertyImpl extends AbstractProperty<Double> implements DoubleProperty {
	
	public DoublePropertyImpl(String name, Double defaultValue) {
		super(name, defaultValue);
	}
	
	@Override
	public void setObject(Object value) throws Exception {
		if (value == null) {
			return;
		}
		
		setDouble(Double.parseDouble(value.toString()));
	}
	
}
