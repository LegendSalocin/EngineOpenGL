package de.salocin.gl.impl.config;

import de.salocin.gl.config.BooleanProperty;

public class BooleanPropertyImpl extends AbstractProperty<Boolean> implements BooleanProperty {
	
	public BooleanPropertyImpl(String name, Boolean defaultValue) {
		super(name, defaultValue);
	}
	
	@Override
	public void setObject(Object value) throws Exception {
		if (value == null) {
			return;
		}
		
		setBoolean(Boolean.parseBoolean(value.toString()));
	}
	
}
