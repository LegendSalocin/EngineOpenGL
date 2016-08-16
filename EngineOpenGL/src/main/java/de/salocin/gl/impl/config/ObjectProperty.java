package de.salocin.gl.impl.config;

public class ObjectProperty extends AbstractProperty<Object> {
	
	public ObjectProperty(String name, Object defaultValue) {
		super(name, defaultValue);
	}
	
	@Override
	public void setObject(Object value) {
		setValue(value);
	}
	
}
