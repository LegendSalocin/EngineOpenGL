package de.salocin.gl.impl.config;

import de.salocin.gl.config.LongProperty;

public class LongPropertyImpl extends AbstractProperty<Long> implements LongProperty {
	
	public LongPropertyImpl(String name, Long defaultValue) {
		super(name, defaultValue);
	}
	
	@Override
	public void setObject(Object value) throws Exception {
		if (value == null) {
			return;
		}
		
		setLong(Long.parseLong(value.toString()));
	}
	
}
