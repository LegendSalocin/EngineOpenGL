package de.salocin.gl.config;

import org.apache.commons.lang3.Validate;

import de.salocin.gl.impl.config.BooleanPropertyImpl;

/**
 * Represents a single property in a configuration file. The value type is a
 * {@link Boolean}
 */
public interface BooleanProperty extends Property<Boolean> {
	
	/**
	 * Alias for {@link #setValue(Boolean)} with a primitive boolean variable
	 * 
	 * @param value
	 *            The new property's value
	 */
	default void setBoolean(boolean value) {
		setValue(value ? Boolean.TRUE : Boolean.FALSE);
	}
	
	/**
	 * Alias for {@link #getValue()} with a primitive boolean variable
	 * 
	 * @return The property's current value
	 */
	default boolean getBoolean() {
		return getValue().booleanValue();
	}
	
	/**
	 * Creates a new instance of this property.
	 * 
	 * @param name
	 *            The property's name. This can not be changed.
	 * @param defaultValue
	 *            The property's default value. This can not be changed.
	 * 
	 * @return A new instance of this property
	 * 
	 * @see #getName()
	 * @see #getDefaultValue()
	 */
	public static BooleanProperty newInstance(String name, boolean defaultValue) {
		return newInstance(name, defaultValue ? Boolean.TRUE : Boolean.FALSE);
	}
	
	/**
	 * Creates a new instance of this property.
	 * 
	 * @param name
	 *            The property's name. This can not be changed.
	 * @param defaultValue
	 *            The property's default value. This can not be changed.
	 * 
	 * @return A new instance of this property
	 * 
	 * @see #getName()
	 * @see #getDefaultValue()
	 */
	public static BooleanProperty newInstance(String name, Boolean defaultValue) {
		return new BooleanPropertyImpl(Validate.notNull(name), Validate.notNull(defaultValue));
	}
	
}
