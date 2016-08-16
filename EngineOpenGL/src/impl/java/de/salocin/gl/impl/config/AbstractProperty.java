package de.salocin.gl.impl.config;

import de.salocin.gl.config.Property;

public abstract class AbstractProperty<T> implements Property<T> {
	
	private final String name;
	private final T defaultValue;
	private T value;
	
	public AbstractProperty(String name, T defaultValue) {
		this.name = name;
		this.defaultValue = defaultValue;
		setValue(null);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public T getDefaultValue() {
		return defaultValue;
	}
	
	@Override
	public void setValue(T value) {
		this.value = value == null ? defaultValue : value;
	}
	
	@Override
	public T getValue() {
		return value;
	}
	
	@Override
	public abstract void setObject(Object value) throws Exception;
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Property) {
			return ((Property<?>) obj).getName().equalsIgnoreCase(getName());
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return getObject().toString();
	}
	
}
