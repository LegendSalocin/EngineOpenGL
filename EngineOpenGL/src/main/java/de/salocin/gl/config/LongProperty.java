package de.salocin.gl.config;

import org.apache.commons.lang3.Validate;

import de.salocin.gl.impl.config.LongPropertyImpl;

/**
 * Represents a single property in a configuration file. The value type is a
 * {@link Long}
 */
public interface LongProperty extends Property<Long> {
	
	/**
	 * Alias for {@link #setValue(Long)} with a primitive long variable
	 * 
	 * @param value
	 *            The new property's value
	 */
	default void setLong(long value) {
		setValue(new Long(value));
	}
	
	/**
	 * Alias for {@link #getValue()} with a primitive long variable
	 * 
	 * @return The property's current value
	 */
	default long getLong() {
		return getValue().longValue();
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
	public static LongProperty newInstance(String name, long defaultValue) {
		return newInstance(name, new Long(defaultValue));
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
	public static LongProperty newInstance(String name, Long defaultValue) {
		return new LongPropertyImpl(Validate.notNull(name), Validate.notNull(defaultValue));
	}
	
}
