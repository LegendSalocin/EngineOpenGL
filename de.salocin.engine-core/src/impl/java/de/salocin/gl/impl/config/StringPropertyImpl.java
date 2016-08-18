package de.salocin.gl.impl.config;

import de.salocin.gl.config.StringProperty;

public class StringPropertyImpl extends AbstractProperty<String> implements StringProperty {
	
	public StringPropertyImpl(String name, String defaultValue) {
		super(name, defaultValue);
	}
	
	@Override
	public void setObject(Object value) throws Exception {
		if (value == null) {
			return;
		}
		
		setString(value.toString());
	}
	
}
