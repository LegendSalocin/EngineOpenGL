package de.salocin.engine.utils.config;

import org.apache.commons.lang3.Validate;

public abstract class AbstractProperty<T> implements Property<T> {
	
	private final String name;
	private final T defaultValue;
	private T value;
	
	protected AbstractProperty(String name, T defaultValue) {
		this.name = Validate.notNull(name);
		this.defaultValue = Validate.notNull(defaultValue);
		setValue(null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getDefaultValue() {
		return defaultValue;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(T value) {
		this.value = value == null ? defaultValue : value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getValue() {
		return value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract void setObject(Object value) throws Exception;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Property) {
			return ((Property<?>) obj).getName().equalsIgnoreCase(getName());
		}
		
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getObject().toString();
	}
	
}
